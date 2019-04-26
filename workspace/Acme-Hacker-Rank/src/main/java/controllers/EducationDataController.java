package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.EducationDataService;
import domain.Curricula;
import domain.EducationData;

@Controller
@RequestMapping(value = "educationData/hacker")
public class EducationDataController extends AbstractController {

	// Services

	@Autowired
	private EducationDataService educationDataService;

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private Validator validator;
	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int curriculaId) {
		ModelAndView result;
		Curricula currentCurricula;
		Collection<EducationData> educationData;

		currentCurricula = this.curriculaService.findOne(curriculaId);

		educationData = currentCurricula.getEducationData();

		result = new ModelAndView("educationData/list");

		result.addObject("currentCurricula", currentCurricula);
		result.addObject("educationData", educationData);

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int dataId,
			@RequestParam int curriculaId) {
		ModelAndView result;
		EducationData data;

		data = this.educationDataService.findOne(dataId);

		result = new ModelAndView("educationData/display");

		result.addObject("data", data);
		result.addObject("curriculaId", curriculaId);

		return result;

	}

	// Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int dataId,
			@RequestParam int curriculaId) {
		ModelAndView result = null;
		EducationData data;

		try {

			data = this.educationDataService.findOne(dataId);

			result = this.createEditModelAndView(data, curriculaId);

		} catch (Throwable oops) {

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(EducationData data, int curriculaId,
			final BindingResult binding) {
		ModelAndView result;
		
		this.validator.validate(data, binding);
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(data, curriculaId,
					"md.commit.error");
		} else {
			try {
				this.educationDataService.save(data, curriculaId);
				Curricula currentCurricula = this.curriculaService
						.findOne(curriculaId);

				result = new ModelAndView("redirect:list.do?curriculaId="
						+ currentCurricula.getId());
			} catch (Throwable oops) {
				result = this.createEditModelAndView(data, curriculaId,
						"md.commit.error");
			}
		}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(EducationData data, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(data, null, "md.commit.error");
		} else {
			try {
				Curricula currentCurricula = this.curriculaService
						.getCurriculaByEducationData(data.getId());
				this.educationDataService.delete(data);
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
			EducationData data = this.educationDataService.create();
			result = this.createEditModelAndView(data, curriculaId);
		} catch (Throwable oops) {
			System.out.println(oops.getMessage());
		}

		return result;

	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final EducationData data,
			int curriculaId) {
		ModelAndView result;

		result = this.createEditModelAndView(data, curriculaId, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final EducationData data,
			Integer curriculaId, final String messageError) {
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
