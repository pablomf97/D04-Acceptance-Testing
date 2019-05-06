
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CurriculaService;
import services.PersonalDataService;
import domain.Actor;
import domain.Curricula;
import domain.PersonalData;

@Controller
@RequestMapping(value = "personalData/rookie")
public class PersonalDataController extends AbstractController {

	//Services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private PersonalDataService	personalDataService;

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private Validator			validator;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int dataId, @RequestParam final int curriculaId) {
		ModelAndView result;
		PersonalData data;

		try {
			data = this.personalDataService.findOne(dataId);
			final Actor actor = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(actor, "COMPANY") || this.actorService.checkAuthority(actor, "ROOKIE"));
			result = new ModelAndView("personalData/display");

			result.addObject("data", data);
			result.addObject("curriculaId", curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}
		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int dataId, @RequestParam final int curriculaId) {
		ModelAndView result = null;
		PersonalData data;

		try {
			data = this.personalDataService.findOne(dataId);
			this.personalDataService.checkOwnerPersonalData(dataId);

			result = this.createEditModelAndView(data, curriculaId);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final PersonalData data, final int curriculaId, final BindingResult binding) {
		ModelAndView result;

		this.validator.validate(data, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(data, curriculaId);
		else
			try {
				if (data.getId() != 0)
					this.personalDataService.checkOwnerPersonalData(data.getId());

				this.personalDataService.save(data, curriculaId);
				final Curricula currentCurricula = this.curriculaService.findOne(curriculaId);

				result = new ModelAndView("redirect:display.do?dataId=" + data.getId() + "&curriculaId=" + currentCurricula.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:../welcome/index.do");
				result.addObject("messageCode", "problem.commit.error");
			}
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		ModelAndView result = null;
		try {
			final PersonalData data = this.personalDataService.create();
			result = this.createEditModelAndView(data, curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}

		return result;

	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final PersonalData data, final int curriculaId) {
		ModelAndView result;

		result = this.createEditModelAndView(data, curriculaId, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final PersonalData data, final Integer curriculaId, final String messageError) {
		ModelAndView result;
		Curricula currentCurricula;

		result = new ModelAndView("personalData/edit");

		if (!(curriculaId == null)) {
			currentCurricula = this.curriculaService.findOne(curriculaId);
			result.addObject("currentCurricula", currentCurricula);
		}

		result.addObject("personalData", data);
		result.addObject("messageError", messageError);

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final PersonalData personalData, final BindingResult binding) {
		ModelAndView result = null;
		if (binding.hasErrors()) {
			result = new ModelAndView("curricula/edit");
			result.addObject("personalData", personalData);
		} else
			try {
				this.personalDataService.saveNewCurricula(personalData);

				result = new ModelAndView("redirect:../../curricula/rookie/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:../welcome/index.do");
				result.addObject("messageCode", "problem.commit.error");
			}
		return result;
	}

}
