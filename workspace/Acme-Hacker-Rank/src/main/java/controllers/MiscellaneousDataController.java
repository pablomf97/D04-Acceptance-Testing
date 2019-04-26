package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.MiscellaneousDataService;
import domain.Curricula;
import domain.MiscellaneousData;

@Controller
@RequestMapping(value = "miscellaneousData/hacker")
public class MiscellaneousDataController extends AbstractController {

	// Services

	@Autowired
	private MiscellaneousDataService miscellaneousDataService;

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private Validator validator;

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int curriculaId) {
		ModelAndView result;
		Curricula currentCurricula;
		Collection<MiscellaneousData> miscellaneousData;

		currentCurricula = this.curriculaService.findOne(curriculaId);

		miscellaneousData = currentCurricula.getMiscellaneousData();

		result = new ModelAndView("miscellaneousData/list");

		result.addObject("currentCurricula", currentCurricula);
		result.addObject("miscellaneousData", miscellaneousData);

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int dataId,
			@RequestParam int curriculaId) {
		ModelAndView result;
		MiscellaneousData data;

		data = this.miscellaneousDataService.findOne(dataId);

		result = new ModelAndView("miscellaneousData/display");

		result.addObject("data", data);
		result.addObject("curriculaId", curriculaId);

		return result;

	}

	// Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int dataId,
			@RequestParam int curriculaId) {
		ModelAndView result = null;
		MiscellaneousData data;

		try {

			data = this.miscellaneousDataService.findOne(dataId);

			result = this.createEditModelAndView(data, curriculaId);

		} catch (Throwable oops) {

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(MiscellaneousData data,
			@RequestParam int curriculaId, final BindingResult binding) {
		ModelAndView result;
		
		this.validator.validate(data, binding);
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(data, curriculaId, "md.commit.error");
		} else {
			try {
				this.miscellaneousDataService.save(data, curriculaId);
				Curricula currentCurricula = this.curriculaService
						.findOne(curriculaId);

				result = new ModelAndView("redirect:list.do?curriculaId="
						+ currentCurricula.getId());
			} catch (Throwable oops) {
				Curricula currentCurricula = this.curriculaService
						.findOne(curriculaId);

				result = new ModelAndView("redirect:list.do?curriculaId="
						+ currentCurricula.getId());
			}
		}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid MiscellaneousData data,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(data, null, "md.commit.error");
		} else {
			try {
				Curricula currentCurricula = this.curriculaService
						.getCurriculaByMiscellaneousData(data.getId());
				this.miscellaneousDataService.delete(data);
				result = new ModelAndView("redirect:list.do?curriculaId="
						+ currentCurricula.getId());
			} catch (Throwable oops) {
				result = this.createEditModelAndView(data, null,
						"md.commit.error");
			}
		}
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int curriculaId) {
		ModelAndView result = null;
		try {
			MiscellaneousData data = this.miscellaneousDataService.create();
			result = this.createEditModelAndView(data, curriculaId,"md.commit.error");
		} catch (Throwable oops) {
			System.out.println(oops.getMessage());
		}

		return result;

	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final MiscellaneousData data,
			int curriculaId) {
		ModelAndView result;

		result = this.createEditModelAndView(data, curriculaId, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final MiscellaneousData data,
			Integer curriculaId, final String messageError) {
		ModelAndView result;
		Curricula currentCurricula;

		result = new ModelAndView("miscellaneousData/edit");

		if (!(curriculaId == null)) {
			currentCurricula = this.curriculaService.findOne(curriculaId);
			result.addObject("currentCurricula", currentCurricula);
		}

		result.addObject("miscellaneousData", data);
		result.addObject("messageError", messageError);

		return result;
	}

}
