
package controllers;

import java.util.Collection;

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
import services.EducationDataService;
import domain.Actor;
import domain.Curricula;
import domain.EducationData;

@Controller
@RequestMapping(value = "educationData/rookie")
public class EducationDataController extends AbstractController {

	// Services
	@Autowired
	private ActorService			actorService;
	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private Validator				validator;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int curriculaId) {
		ModelAndView result;
		Curricula currentCurricula;
		Collection<EducationData> educationData;
		try {
			final Actor actor = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(actor, "COMPANY") || this.actorService.checkAuthority(actor, "ROOKIE"));

			currentCurricula = this.curriculaService.findOne(curriculaId);

			educationData = currentCurricula.getEducationData();

			result = new ModelAndView("educationData/list");

			result.addObject("currentCurricula", currentCurricula);
			result.addObject("educationData", educationData);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}
		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int dataId, @RequestParam final int curriculaId) {
		ModelAndView result;
		EducationData data;
		try {
			final Actor actor = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(actor, "COMPANY") || this.actorService.checkAuthority(actor, "ROOKIE"));

			data = this.educationDataService.findOne(dataId);

			result = new ModelAndView("educationData/display");

			result.addObject("data", data);
			result.addObject("curriculaId", curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}
		return result;

	}

	// Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int dataId, @RequestParam final int curriculaId) {
		ModelAndView result = null;
		EducationData data;

		try {

			data = this.educationDataService.findOne(dataId);
			this.educationDataService.checkOwnerEducationData(dataId);

			result = this.createEditModelAndView(data, curriculaId);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final EducationData data, final int curriculaId, final BindingResult binding) {
		ModelAndView result;

		this.validator.validate(data, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(data, curriculaId, "md.commit.error");
		else
			try {
				if (data.getId() != 0)
					this.educationDataService.checkOwnerEducationData(data.getId());
				this.educationDataService.save(data, curriculaId);
				final Curricula currentCurricula = this.curriculaService.findOne(curriculaId);

				result = new ModelAndView("redirect:list.do?curriculaId=" + currentCurricula.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:../welcome/index.do");
				result.addObject("messageCode", "problem.commit.error");
			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationData data, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(data, null, "md.commit.error");
		else
			try {
				final Curricula currentCurricula = this.curriculaService.getCurriculaByEducationData(data.getId());
				this.educationDataService.delete(data);
				result = new ModelAndView("redirect:list.do?curriculaId=" + currentCurricula.getId());
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
			final EducationData data = this.educationDataService.create();
			result = this.createEditModelAndView(data, curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}

		return result;

	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final EducationData data, final int curriculaId) {
		ModelAndView result;

		result = this.createEditModelAndView(data, curriculaId, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final EducationData data, final Integer curriculaId, final String messageError) {
		ModelAndView result;
		Curricula currentCurricula;

		result = new ModelAndView("educationData/edit");

		if (!(curriculaId == null)) {
			currentCurricula = this.curriculaService.findOne(curriculaId);
			result.addObject("currentCurricula", currentCurricula);
		}

		result.addObject("educationData", data);
		result.addObject("messageError", messageError);

		return result;
	}
}
