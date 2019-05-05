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
import services.ProviderService;
import domain.Auditor;
import domain.Provider;
import forms.EditionCompanyFormObject;
import forms.EditionFormObject;
import forms.RegisterCompanyFormObject;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	/* Services */

	@Autowired
	private ProviderService providerService;

	@Autowired
	private ActorService actorService;

	/* Methods */

	/**
	 * 
	 * Display provider
	 * 
	 * @params id (optional)
	 * 
	 * @return ModelAndView
	 * **/
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) Integer id) {
		ModelAndView res;
		Provider toDisplay;
		Boolean found = true;

		try {
			if (id != null) {
				toDisplay = (Provider) this.actorService.findOne(id);
				if (toDisplay == null)
					found = false;
			} else {
				toDisplay = (Provider) this.actorService.findByPrincipal();
			}

			res = new ModelAndView("provider/display");
			res.addObject("provider", toDisplay);
			res.addObject("found", found);
		} catch (Throwable oops) {
			found = false;
			res = new ModelAndView("provider/display");
			res.addObject("found", found);
		}

		return res;
	}

	/**
	 * 
	 * Edit provider GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/provider/edit", method = RequestMethod.GET)
	public ModelAndView editProvider() {
		ModelAndView res;
		Provider principal;

		principal = (Provider) this.actorService.findByPrincipal();

		EditionCompanyFormObject editionCompanyFormObject = new EditionCompanyFormObject(
				principal);

		res = this.createEditModelAndView(editionCompanyFormObject);

		return res;
	}

	/**
	 * 
	 * Edit provider POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/provider/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(
			@Valid EditionCompanyFormObject editionProviderFormObject,
			BindingResult binding) {

		ModelAndView res;

		try {

			Assert.isTrue(this.actorService.findByPrincipal().getId() == editionProviderFormObject
					.getId()
					&& this.actorService.findOne(this.actorService
							.findByPrincipal().getId()) != null);

			Provider provider = new Provider();
			provider = this.providerService.create();

			provider = this.providerService.reconstruct(
					editionProviderFormObject, binding);

			if (binding.hasErrors()) {
				res = this.createEditModelAndView(editionProviderFormObject);
			} else {
				try {

					this.providerService.save(provider);

					res = new ModelAndView("redirect:/");

				} catch (Throwable oops) {
					res = this.createEditModelAndView(
							editionProviderFormObject, "provider.commit.error");

				}
			}
		} catch (Throwable oops) {
			res = new ModelAndView("redirect:/");
		}
		return res;
	}

	/**
	 * 
	 * Register provider GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/provider/register", method = RequestMethod.GET)
	public ModelAndView registerNewProvider() {
		ModelAndView res;

		RegisterCompanyFormObject registerProviderFormObject = new RegisterCompanyFormObject();
		registerProviderFormObject.setTermsAndConditions(false);

		res = this.createRegisterModelAndView(registerProviderFormObject);

		return res;
	}

	/**
	 * 
	 * Register provider POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/provider/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(
			@Valid RegisterCompanyFormObject registerProviderFormObject,
			BindingResult binding) {

		ModelAndView res;

		Provider provider = new Provider();
		provider = this.providerService.create();

		provider = this.providerService.reconstruct(registerProviderFormObject,
				binding);

		if (binding.hasErrors()) {
			res = this.createRegisterModelAndView(registerProviderFormObject);
		} else {
			try {

				this.providerService.save(provider);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				res = this.createRegisterModelAndView(
						registerProviderFormObject, "provider.commit.error");

			}
		}
		return res;
	}

	/* Auxiliary methods */

	protected ModelAndView createEditModelAndView(
			EditionCompanyFormObject editionCompanyFormObject) {
		ModelAndView result;

		result = this.createEditModelAndView(editionCompanyFormObject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			EditionCompanyFormObject editionCompanyFormObject,
			String messageCode) {
		ModelAndView result;

		result = new ModelAndView("provider/edit");
		result.addObject("editionCompanyFormObject", editionCompanyFormObject);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(
			RegisterCompanyFormObject registerCompanyFormObject) {
		ModelAndView result;

		result = this.createRegisterModelAndView(registerCompanyFormObject,
				null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(
			RegisterCompanyFormObject registerCompanyFormObject,
			String messageCode) {
		ModelAndView result;

		result = new ModelAndView("provider/register");
		result.addObject("registerCompanyFormObject", registerCompanyFormObject);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/provider/edit", method = RequestMethod.POST, params = "deleteProvider")
	public ModelAndView deleteProvider(
			final EditionCompanyFormObject editionCompanyFormObject,
			final BindingResult binding, final HttpSession session) {
		ModelAndView result;
		Provider provider;

		provider = this.providerService.findOne(editionCompanyFormObject
				.getId());

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(editionCompanyFormObject,
					"administrator.commit.error");

		} else
			try {
				this.providerService.delete(provider);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(editionCompanyFormObject,
						"administrator.commit.error");

			}

		return result;
	}


}
