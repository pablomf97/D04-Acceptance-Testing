package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.CurriculaService;
import services.PositionService;
import services.ProblemService;
import domain.Actor;
import domain.Application;
import domain.Curricula;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	// Services

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private ProblemService problemService;

	@Autowired
	private PositionService positionService;

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {

		ModelAndView result;
		Application application;
		boolean isPrincipal = false;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		application = this.applicationService.findOne(applicationId);

		if (application.getHacker().getId() == principal.getId())
			isPrincipal = true;

		result = new ModelAndView("application/display");
		result.addObject("application", application);
		result.addObject("isPrincipal", isPrincipal);
		result.addObject("requestURI", "application/display.do?applicationId="
				+ applicationId);

		return result;
	}

	// List

	/* List of enrollments of a member */
	@RequestMapping(value = "/listHacker", method = RequestMethod.GET)
	public ModelAndView listHacker() {
		ModelAndView res;
		Actor principal;
		Collection<Application> applications;
		Boolean permission;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

			applications = this.applicationService
					.findApplicationsByHackerId(principal.getId());

			permission = true;

			res = new ModelAndView("application/listHacker");
			res.addObject("applications", applications);
			res.addObject("permission", permission);
		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {
			res = new ModelAndView("application/listHacker");
			permission = false;

			res.addObject("errMsg", oopsie);
			res.addObject("permission", permission);
		}
		return res;
	}

	/* List of enrollments of a brotherhood */
	@RequestMapping(value = "/listCompany", method = RequestMethod.GET)
	public ModelAndView listCompany() {
		ModelAndView res;
		Actor principal;
		Collection<Application> applications;
		Boolean permission;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService
					.checkAuthority(principal, "COMPANY"));

			applications = this.applicationService
					.findApplicationsByCompanyId(principal.getId());

			permission = true;

			res = new ModelAndView("application/listCompany");
			res.addObject("applications", applications);
			res.addObject("permission", permission);
		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {
			res = new ModelAndView("application/listCompany");
			permission = false;

			res.addObject("errMsg", oopsie);
			res.addObject("permission", permission);
		}
		return res;
	}

	// Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int positionId) {
		ModelAndView result;
		Application application;
		Collection<Problem> problems;
		Problem toSolve;
		Position position;

		Actor principal;
		Boolean error;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

			application = this.applicationService.create();

			position = this.positionService.findOne(positionId);
			application.setPosition(position);

			problems = this.problemService.findProblemsByPositionId(application
					.getPosition().getId());
			toSolve = this.applicationService.selectProblem(problems);
			application.setProblem(toSolve);

			application = this.applicationService.save(application);

			result = new ModelAndView("redirect:/application/listHacker.do");
		} catch (final IllegalArgumentException oops) {
			result = new ModelAndView("misc/403");
		} catch (final Throwable oopsie) {

			result = new ModelAndView("redirect:/application/listHacker.do");
			error = true;

			result.addObject("oopsie", oopsie);
			result.addObject("error", error);
		}
		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		Collection<Curricula> curriculas;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);
		curriculas = this.curriculaService.findCurriculasByHackerId(application
				.getHacker().getId());
		result = this.createEditModelAndView(application);
		result.addObject("curriculas", curriculas);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Application application, BindingResult binding) {
		ModelAndView result;
		Application application2;
		try {
			application2 = this.applicationService.reconstruct(application,
					binding);
			if (binding.hasErrors()) {
				Application recuperada = (this.applicationService
						.findOne(application.getId()));
				Collection<Curricula> curriculas = this.curriculaService
						.findCurriculasByHackerId(recuperada.getHacker()
								.getId());

				result = new ModelAndView("application/edit");
				result.addObject("application", application);
				result.addObject("curriculas", curriculas);
				result.addObject("binding", binding);
			} else
				try {

					this.applicationService.save(application2);
					result = new ModelAndView(
							"redirect:/application/listHacker.do");
				} catch (final Throwable oops) {
					result = new ModelAndView("application/edit");
					result.addObject("application", application2);
					result.addObject("messageCode", oops.getMessage());
				}
		} catch (final Throwable oops) {
			application = this.applicationService.findOne(application.getId());
			result = this
					.createEditModelAndView(application, oops.getMessage());
		}
		return result;
	}

	/* Accept or reject an application */
	@RequestMapping(value = "/company/action", method = RequestMethod.GET)
	public ModelAndView actionsEnrolments(@RequestParam String action,
			@RequestParam int applicationId) {
		ModelAndView res;
		Actor principal;
		Application application;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue((this.actorService.checkAuthority(principal,
					"COMPANY")));

			application = this.applicationService.findOne(applicationId);
			
			Assert.isTrue(application.getStatus().equals("SUBMITTED"));

			if (action.equals("accept")) {

				application.setStatus("ACCEPTED");
				this.applicationService.save(application);
				res = new ModelAndView("redirect:/application/listCompany.do");

			} else if (action.equals("reject")) {

				application.setStatus("REJECTED");
				this.applicationService.save(application);
				res = new ModelAndView("redirect:/application/listCompany.do");
			} else {

				res = new ModelAndView("misc/403");

			}
		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {
			res = new ModelAndView("redirect:/application/listCompany.do");
		}
		return res;
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam final int applicationId) {
		ModelAndView result;
		Application toDelete;
		Collection<Application> applications;
		Actor principal;

		principal = this.actorService.findByPrincipal();

		toDelete = this.applicationService.findOne(applicationId);
		this.applicationService.delete(toDelete);

		applications = this.applicationService
				.findApplicationsNotRejectedByHackerId(principal.getId());

		final String requestURI = "application/listHacker.do";
		result = new ModelAndView("application/listHacker");
		result.addObject("requestURI", requestURI);
		result.addObject("applications", applications);

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			final Application application, final String messageCode) {
		final ModelAndView result;
		Actor principal;
		boolean isPrincipal = false;
		Collection<Curricula> curriculas;

		principal = this.actorService.findByPrincipal();

		if (principal.getId() == application.getHacker().getId())
			isPrincipal = true;

		curriculas = this.curriculaService.findCurriculasByHackerId(application
				.getHacker().getId());

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("message", messageCode);
		result.addObject("isPrincipal", isPrincipal);
		result.addObject("curriculas", curriculas);

		return result;
	}

}
