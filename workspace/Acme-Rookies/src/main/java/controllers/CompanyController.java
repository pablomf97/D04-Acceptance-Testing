
package controllers;

import java.util.Collection;

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
import services.CompanyService;
import domain.Company;
import forms.EditionCompanyFormObject;
import forms.RegisterCompanyFormObject;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	/* Services */

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private ActorService	actorService;


	/* Methods */

	/**
	 * 
	 * Display company
	 * 
	 * @params id (optional)
	 * 
	 * @return ModelAndView
	 * **/
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) final Integer id) {
		ModelAndView res;
		Company toDisplay;
		Boolean found = true;

		try {
			if (id != null) {
				toDisplay = (Company) this.actorService.findOne(id);
				if (toDisplay == null)
					found = false;
			} else
				toDisplay = (Company) this.actorService.findByPrincipal();

			res = new ModelAndView("company/display");
			res.addObject("company", toDisplay);
			res.addObject("found", found);
		} catch (final Throwable oops) {
			found = false;
			res = new ModelAndView("company/display");
			res.addObject("found", found);
		}

		return res;
	}

	/**
	 * 
	 * Display company
	 * 
	 * @return ModelAndView
	 * **/
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Company> allCompanies;
		Boolean err = false;

		try {
			allCompanies = this.companyService.findAll();
			if (allCompanies.size() >= 10000)
				res = new ModelAndView("redirect:../welcome/index.do");
			else {
				res = new ModelAndView("company/list");
				res.addObject("allCompanies", allCompanies);
				res.addObject("err", err);
			}
		} catch (final Throwable oops) {
			err = true;
			res = new ModelAndView("company/list");
			res.addObject("err", err);
		}

		return res;
	}

	/**
	 * 
	 * Edit company GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
	public ModelAndView editCompany() {
		ModelAndView res;
		Company principal;

		principal = (Company) this.actorService.findByPrincipal();

		final EditionCompanyFormObject editionCompanyFormObject = new EditionCompanyFormObject(principal);

		res = this.createEditModelAndView(editionCompanyFormObject);

		return res;
	}

	/**
	 * 
	 * Edit company POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/company/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final EditionCompanyFormObject editionCompanyFormObject, final BindingResult binding) {

		ModelAndView res;

		try {

			Assert.isTrue(this.actorService.findByPrincipal().getId() == editionCompanyFormObject.getId() && this.actorService.findOne(this.actorService.findByPrincipal().getId()) != null);

			Company company = new Company();
			company = this.companyService.create();

			company = this.companyService.reconstruct(editionCompanyFormObject, binding);

			if (binding.hasErrors())
				res = this.createEditModelAndView(editionCompanyFormObject);
			else
				try {

					this.companyService.save(company);

					res = new ModelAndView("redirect:/");

				} catch (final Throwable oops) {
					res = this.createEditModelAndView(editionCompanyFormObject, "company.commit.error");

				}
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/");
		}
		return res;
	}

	/**
	 * 
	 * Register company GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/company/register", method = RequestMethod.GET)
	public ModelAndView registerNewCompany() {
		ModelAndView res;

		final RegisterCompanyFormObject registerCompanyFormObject = new RegisterCompanyFormObject();
		registerCompanyFormObject.setTermsAndConditions(false);

		res = this.createRegisterModelAndView(registerCompanyFormObject);

		return res;
	}

	/**
	 * 
	 * Register company POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/company/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final RegisterCompanyFormObject registerCompanyFormObject, final BindingResult binding) {

		ModelAndView res;

		Company company = new Company();
		company = this.companyService.create();

		company = this.companyService.reconstruct(registerCompanyFormObject, binding);

		if (binding.hasErrors())
			res = this.createRegisterModelAndView(registerCompanyFormObject);
		else
			try {

				this.companyService.save(company);

				res = new ModelAndView("redirect:/");

			} catch (final Throwable oops) {
				res = this.createRegisterModelAndView(registerCompanyFormObject, "company.commit.error");

			}
		return res;
	}

	/* Auxiliary methods */

	protected ModelAndView createEditModelAndView(final EditionCompanyFormObject editionCompanyFormObject) {
		ModelAndView result;

		result = this.createEditModelAndView(editionCompanyFormObject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EditionCompanyFormObject editionCompanyFormObject, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("company/edit");
		result.addObject("editionCompanyFormObject", editionCompanyFormObject);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(final RegisterCompanyFormObject registerCompanyFormObject) {
		ModelAndView result;

		result = this.createRegisterModelAndView(registerCompanyFormObject, null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(final RegisterCompanyFormObject registerCompanyFormObject, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("company/register");
		result.addObject("registerCompanyFormObject", registerCompanyFormObject);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.POST, params = "deleteCompany")
	public ModelAndView deleteCompany(final EditionCompanyFormObject editionCompanyFormObject, final BindingResult binding, final HttpSession session) {
		ModelAndView result;
		Company company;

		company = this.companyService.findOne(editionCompanyFormObject.getId());

		if (binding.hasErrors())
			result = this.createEditModelAndView(editionCompanyFormObject, "administrator.commit.error");
		else
			try {
				this.companyService.delete(company);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(editionCompanyFormObject, "administrator.commit.error");

			}

		return result;
	}

}
