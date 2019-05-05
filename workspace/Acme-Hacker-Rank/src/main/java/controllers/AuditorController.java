package controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditorService;
import domain.Administrator;
import domain.Auditor;
import domain.Hacker;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@Controller
@RequestMapping("/auditor")
public class AuditorController extends AbstractController {

	/* Services */

	@Autowired
	private AuditorService auditorService;

	@Autowired
	private ActorService actorService;

	/* Methods */

	/**
	 * 
	 * Display auditor
	 * 
	 * @params id (optional)
	 * 
	 * @return ModelAndView
	 * **/
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) Integer id) {
		ModelAndView res;
		Auditor toDisplay;
		Boolean found = true;

		try {
			if (id != null) {
				toDisplay = (Auditor) this.actorService.findOne(id);
				if (toDisplay == null)
					found = false;
			} else {
				toDisplay = (Auditor) this.actorService.findByPrincipal();
			}

			res = new ModelAndView("auditor/display");
			res.addObject("auditor", toDisplay);
			res.addObject("found", found);
		} catch (Throwable oops) {
			found = false;
			res = new ModelAndView("auditor/display");
			res.addObject("found", found);
		}

		return res;
	}

	/**
	 * 
	 * Edit auditor GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/auditor/edit", method = RequestMethod.GET)
	public ModelAndView editAuditor() {
		ModelAndView res;
		Auditor principal;

		principal = (Auditor) this.actorService.findByPrincipal();
		EditionFormObject editionFormObject = new EditionFormObject(principal);

		res = this.createEditModelAndView(editionFormObject);

		return res;
	}

	/**
	 * 
	 * Edit auditor POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid EditionFormObject editionFormObject,
			BindingResult binding) {

		ModelAndView res;

		try {

			Assert.isTrue(this.actorService.findByPrincipal().getId() == editionFormObject
					.getId()
					&& this.actorService.findOne(this.actorService
							.findByPrincipal().getId()) != null);

			Auditor auditor = new Auditor();
			auditor = this.auditorService.create();

			auditor = this.auditorService.reconstruct(editionFormObject, binding);

			if (binding.hasErrors()) {
				res = this.createEditModelAndView(editionFormObject);
			} else {
				try {

					this.auditorService.save(auditor);

					res = new ModelAndView("redirect:/");

				} catch (Throwable oops) {
					res = this.createEditModelAndView(editionFormObject,
							"auditor.commit.error");

				}
			}
		} catch (Throwable oops) {
			res = new ModelAndView("redirect:/");
		}
		return res;
	}

	/**
	 * 
	 * Register auditor GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/auditor/register", method = RequestMethod.GET)
	public ModelAndView registerNewAuditor() {
		ModelAndView res;

		RegisterFormObject registerFormObject = new RegisterFormObject();
		registerFormObject.setTermsAndConditions(false);

		res = this.createRegisterModelAndView(registerFormObject);

		return res;
	}

	/**
	 * 
	 * Register auditor POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/auditor/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid RegisterFormObject registerFormObject,
			BindingResult binding) {

		ModelAndView res;

		Auditor auditor = new Auditor();
		auditor = this.auditorService.create();

		auditor = this.auditorService.reconstruct(registerFormObject, binding);

		if (binding.hasErrors()) {
			res = this.createRegisterModelAndView(registerFormObject);
		} else {
			try {

				this.auditorService.save(auditor);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				res = this.createRegisterModelAndView(registerFormObject,
						"auditor.commit.error");

			}
		}
		return res;
	}

	/* Auxiliary methods */

	protected ModelAndView createEditModelAndView(
			EditionFormObject editionFormObject) {
		ModelAndView result;

		result = this.createEditModelAndView(editionFormObject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			EditionFormObject editionFormObject, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("auditor/edit");
		result.addObject("editionFormObject", editionFormObject);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(
			RegisterFormObject registerFormObject) {
		ModelAndView result;

		result = this.createRegisterModelAndView(registerFormObject, null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(
			RegisterFormObject registerFormObject, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("auditor/register");
		result.addObject("registerFormObject", registerFormObject);
		result.addObject("message", messageCode);

		return result;
	}


	@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST, params = "deleteAuditor")
	public ModelAndView deleteAuditor(final EditionFormObject editionFormObject,
			final BindingResult binding, final HttpSession session) {
		ModelAndView result;
		Auditor auditor;

		auditor = this.auditorService.findOne(editionFormObject.getId());

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(editionFormObject,
					"administrator.commit.error");

		} else
			try {
				this.auditorService.delete(auditor);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(editionFormObject,
						"administrator.commit.error");

			}

		return result;
	}

}
