
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
import services.FinderService;
import services.RookieService;
import domain.Finder;
import domain.Rookie;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@Controller
@RequestMapping("/rookie")
public class RookieController extends AbstractController {

	/* Services */
	@Autowired
	private FinderService	finderService;
	@Autowired
	private RookieService	rookieService;

	@Autowired
	private ActorService	actorService;


	/* Methods */

	/**
	 * 
	 * Display rookie
	 * 
	 * @params id (optional)
	 * 
	 * @return ModelAndView
	 * **/
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) final Integer id) {
		ModelAndView res;
		Rookie toDisplay;
		Boolean found = true;

		try {
			if (id != null) {
				toDisplay = (Rookie) this.actorService.findOne(id);
				if (toDisplay == null)
					found = false;
			} else
				toDisplay = (Rookie) this.actorService.findByPrincipal();

			res = new ModelAndView("rookie/display");
			res.addObject("rookie", toDisplay);
			res.addObject("found", found);
		} catch (final Throwable oops) {
			found = false;
			res = new ModelAndView("rookie/display");
			res.addObject("found", found);
		}

		return res;
	}

	/**
	 * 
	 * Edit rookie GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/rookie/edit", method = RequestMethod.GET)
	public ModelAndView editRookie() {
		ModelAndView res;
		Rookie principal;

		principal = (Rookie) this.actorService.findByPrincipal();
		final EditionFormObject editionFormObject = new EditionFormObject(principal);

		res = this.createEditModelAndView(editionFormObject);

		return res;
	}

	/**
	 * 
	 * Edit rookie POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/rookie/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final EditionFormObject editionFormObject, final BindingResult binding) {

		ModelAndView res;

		try {

			Assert.isTrue(this.actorService.findByPrincipal().getId() == editionFormObject.getId() && this.actorService.findOne(this.actorService.findByPrincipal().getId()) != null);

			Rookie rookie = new Rookie();
			rookie = this.rookieService.create();

			rookie = this.rookieService.reconstruct(editionFormObject, binding);

			if (binding.hasErrors())
				res = this.createEditModelAndView(editionFormObject);
			else
				try {

					this.rookieService.save(rookie);

					res = new ModelAndView("redirect:/");

				} catch (final Throwable oops) {
					res = this.createEditModelAndView(editionFormObject, "rookie.commit.error");

				}
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/");
		}
		return res;
	}

	/**
	 * 
	 * Register rookie GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/rookie/register", method = RequestMethod.GET)
	public ModelAndView registerNewRookie() {
		ModelAndView res;

		final RegisterFormObject registerFormObject = new RegisterFormObject();
		registerFormObject.setTermsAndConditions(false);

		res = this.createRegisterModelAndView(registerFormObject);

		return res;
	}

	/**
	 * 
	 * Register rookie POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/rookie/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final RegisterFormObject registerFormObject, final BindingResult binding) {

		ModelAndView res;

		Rookie rookie = new Rookie();
		rookie = this.rookieService.create();

		rookie = this.rookieService.reconstruct(registerFormObject, binding);

		if (binding.hasErrors())
			res = this.createRegisterModelAndView(registerFormObject);
		else
			try {

				final Rookie hack = this.rookieService.save(rookie);
				final Finder f = this.finderService.defaultFinder();
				this.rookieService.saveFinder(hack, f);
				res = new ModelAndView("redirect:/");

			} catch (final Throwable oops) {
				oops.printStackTrace();
				res = this.createRegisterModelAndView(registerFormObject, "rookie.commit.error");

			}
		return res;
	}
	/* Auxiliary methods */

	protected ModelAndView createEditModelAndView(final EditionFormObject editionFormObject) {
		ModelAndView result;

		result = this.createEditModelAndView(editionFormObject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EditionFormObject editionFormObject, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("rookie/edit");
		result.addObject("editionFormObject", editionFormObject);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(final RegisterFormObject registerFormObject) {
		ModelAndView result;

		result = this.createRegisterModelAndView(registerFormObject, null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(final RegisterFormObject registerFormObject, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("rookie/register");
		result.addObject("registerFormObject", registerFormObject);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/rookie/edit", method = RequestMethod.POST, params = "deleteRookie")
	public ModelAndView deleteRookie(final EditionFormObject editionFormObject, final BindingResult binding, final HttpSession session) {
		ModelAndView result;
		Rookie rookie;

		rookie = this.rookieService.findOne(editionFormObject.getId());

		if (binding.hasErrors())
			result = this.createEditModelAndView(editionFormObject, "administrator.commit.error");
		else
			try {
				this.rookieService.delete(rookie);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(editionFormObject, "administrator.commit.error");

			}

		return result;
	}

}
