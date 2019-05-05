
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
import services.HackerService;
import domain.Finder;
import domain.Hacker;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@Controller
@RequestMapping("/hacker")
public class HackerController extends AbstractController {

	/* Services */
	@Autowired
	private FinderService	finderService;
	@Autowired
	private HackerService	hackerService;

	@Autowired
	private ActorService	actorService;


	/* Methods */

	/**
	 * 
	 * Display hacker
	 * 
	 * @params id (optional)
	 * 
	 * @return ModelAndView
	 * **/
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) final Integer id) {
		ModelAndView res;
		Hacker toDisplay;
		Boolean found = true;

		try {
			if (id != null) {
				toDisplay = (Hacker) this.actorService.findOne(id);
				if (toDisplay == null)
					found = false;
			} else
				toDisplay = (Hacker) this.actorService.findByPrincipal();

			res = new ModelAndView("hacker/display");
			res.addObject("hacker", toDisplay);
			res.addObject("found", found);
		} catch (final Throwable oops) {
			found = false;
			res = new ModelAndView("hacker/display");
			res.addObject("found", found);
		}

		return res;
	}

	/**
	 * 
	 * Edit hacker GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/hacker/edit", method = RequestMethod.GET)
	public ModelAndView editHacker() {
		ModelAndView res;
		Hacker principal;

		principal = (Hacker) this.actorService.findByPrincipal();
		final EditionFormObject editionFormObject = new EditionFormObject(principal);

		res = this.createEditModelAndView(editionFormObject);

		return res;
	}

	/**
	 * 
	 * Edit hacker POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/hacker/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final EditionFormObject editionFormObject, final BindingResult binding) {

		ModelAndView res;

		try {

			Assert.isTrue(this.actorService.findByPrincipal().getId() == editionFormObject.getId() && this.actorService.findOne(this.actorService.findByPrincipal().getId()) != null);

			Hacker hacker = new Hacker();
			hacker = this.hackerService.create();

			hacker = this.hackerService.reconstruct(editionFormObject, binding);

			if (binding.hasErrors())
				res = this.createEditModelAndView(editionFormObject);
			else
				try {

					this.hackerService.save(hacker);

					res = new ModelAndView("redirect:/");

				} catch (final Throwable oops) {
					res = this.createEditModelAndView(editionFormObject, "hacker.commit.error");

				}
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/");
		}
		return res;
	}

	/**
	 * 
	 * Register hacker GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/hacker/register", method = RequestMethod.GET)
	public ModelAndView registerNewHacker() {
		ModelAndView res;

		final RegisterFormObject registerFormObject = new RegisterFormObject();
		registerFormObject.setTermsAndConditions(false);

		res = this.createRegisterModelAndView(registerFormObject);

		return res;
	}

	/**
	 * 
	 * Register hacker POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/hacker/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final RegisterFormObject registerFormObject, final BindingResult binding) {

		ModelAndView res;

		Hacker hacker = new Hacker();
		hacker = this.hackerService.create();

		hacker = this.hackerService.reconstruct(registerFormObject, binding);

		if (binding.hasErrors())
			res = this.createRegisterModelAndView(registerFormObject);
		else
			try {

				final Hacker hack = this.hackerService.save(hacker);
				final Finder f = this.finderService.defaultFinder();
				this.hackerService.saveFinder(hack, f);
				res = new ModelAndView("redirect:/");

			} catch (final Throwable oops) {
				oops.printStackTrace();
				res = this.createRegisterModelAndView(registerFormObject, "hacker.commit.error");

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

		result = new ModelAndView("hacker/edit");
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

		result = new ModelAndView("hacker/register");
		result.addObject("registerFormObject", registerFormObject);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/hacker/edit", method = RequestMethod.POST, params = "deleteHacker")
	public ModelAndView deleteHacker(final EditionFormObject editionFormObject, final BindingResult binding, final HttpSession session) {
		ModelAndView result;
		Hacker hacker;

		hacker = this.hackerService.findOne(editionFormObject.getId());

		if (binding.hasErrors())
			result = this.createEditModelAndView(editionFormObject, "administrator.commit.error");
		else
			try {
				this.hackerService.delete(hacker);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(editionFormObject, "administrator.commit.error");

			}

		return result;
	}

}
