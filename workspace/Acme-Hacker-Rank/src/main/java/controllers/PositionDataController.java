
package controllers;

import java.util.Collection;

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
import services.PositionDataService;
import domain.Actor;
import domain.Curricula;
import domain.PositionData;

@Controller
@RequestMapping(value = "positionData/rookie")
public class PositionDataController extends AbstractController {

	//Services
	@Autowired
	private ActorService		actorService;
	@Autowired
	private PositionDataService	positionDataService;

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private Validator			validator;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int curriculaId) {
		ModelAndView result;
		Curricula currentCurricula;
		Collection<PositionData> positionData;
		try {
			currentCurricula = this.curriculaService.findOne(curriculaId);

			positionData = currentCurricula.getPositionData();

			result = new ModelAndView("positionData/list");

			result.addObject("currentCurricula", currentCurricula);
			result.addObject("positionData", positionData);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}
		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int dataId, @RequestParam final int curriculaId) {
		ModelAndView result;
		PositionData data;
		try {
			final Actor actor = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(actor, "COMPANY") || this.actorService.checkAuthority(actor, "ROOKIE"));

			data = this.positionDataService.findOne(dataId);

			result = new ModelAndView("positionData/display");

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
		PositionData data;

		try {
			this.positionDataService.checkOwnerPositionData(dataId);

			data = this.positionDataService.findOne(dataId);

			result = this.createEditModelAndView(data, curriculaId);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final PositionData data, final int curriculaId, final BindingResult binding) {
		ModelAndView result;

		this.validator.validate(data, binding);
		

		if (binding.hasErrors())
			result = this.createEditModelAndView(data, curriculaId, "md.commit.error");
		else
			try {
				if (data.getId() != 0)
					this.positionDataService.checkOwnerPositionData(data.getId());
				this.positionDataService.save(data, curriculaId);
				final Curricula currentCurricula = this.curriculaService.findOne(curriculaId);

				result = new ModelAndView("redirect:list.do?curriculaId=" + currentCurricula.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:list.do?curriculaId=" + curriculaId);
				result.addObject("messageCode", "problem.commit.error");
			}
		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final PositionData data, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(data, null, null);
		else
			try {
				final Curricula currentCurricula = this.curriculaService.getCurriculaByPositionData(data.getId());
				this.positionDataService.delete(data);
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
			final PositionData data = this.positionDataService.create();
			result = this.createEditModelAndView(data, curriculaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}

		return result;

	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final PositionData data, final int curriculaId) {
		ModelAndView result;

		result = this.createEditModelAndView(data, curriculaId, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final PositionData data, final Integer curriculaId, final String messageError) {
		ModelAndView result;
		Curricula currentCurricula;

		result = new ModelAndView("positionData/edit");

		if (!(curriculaId == null)) {
			currentCurricula = this.curriculaService.findOne(curriculaId);
			result.addObject("currentCurricula", currentCurricula);
		}

		result.addObject("positionData", data);
		result.addObject("message", messageError);

		return result;
	}
}
