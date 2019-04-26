
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
import services.ProblemService;
import domain.Actor;
import domain.Problem;

@Controller
@RequestMapping("/problem")
public class ProblemController extends AbstractController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ProblemService	problemService;


	public ProblemController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		try {
			result = new ModelAndView("problem/list");
			final Actor actor = this.actorService.findByPrincipal();
			final Collection<Problem> problems = this.problemService.findByOwner(actor);
			result.addObject("requestURI", "/problem/list.do");
			result.addObject("problems", problems);
			try {
				result.addObject("name", actor.getUserAccount().getUsername());
			} catch (final Throwable opps) {
			}
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteProblem(@RequestParam final int Id) {
		ModelAndView result;
		try {
			final Problem problem = this.problemService.findOne(Id);
			this.problemService.delete(problem);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			if (opps.getMessage().equals("problem.used"))
				result.addObject("problemUsed", "problemUsed");
			else {
				result = new ModelAndView("redirect:../welcome/index.do");
				result.addObject("messageCode", "problem.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveProblemFinal(final Problem problem, final BindingResult binding) {
		ModelAndView result;
		Problem res = null;
		try {
			res = this.problemService.reconstruct(problem, binding);
			if (binding.hasErrors()) {
				result = new ModelAndView("problem/edit");
				result.addObject("problem", problem);
			} else
				try {
					res.setIsDraft(false);
					this.problemService.save(res);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable opps) {
					opps.printStackTrace();
					result = new ModelAndView("problem/edit");
					result.addObject("problem", problem);
					result.addObject("messageCode", "problem.commit.error");
				}
		} catch (final Throwable opps) {
			//TODO: pantalla de error
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView saveProblem(final Problem problem, final BindingResult binding) {
		ModelAndView result;
		Problem res = null;
		try {
			res = this.problemService.reconstruct(problem, binding);
			if (binding.hasErrors()) {
				result = new ModelAndView("problem/edit");
				result.addObject("problem", problem);
			} else
				try {
					this.problemService.save(res);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable opps) {
					opps.printStackTrace();

					result = new ModelAndView("problem/edit");
					result.addObject("problem", problem);
					result.addObject("messageCode", "problem.commit.error");

				}
		} catch (final Throwable opps) {
			//TODO: pantalla de error
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editProblem(@RequestParam final int Id) {
		ModelAndView result;
		Problem problem;
		try {
			problem = this.problemService.findOne(Id);
			final Actor actor = this.actorService.findByPrincipal();
			Assert.isTrue(problem.getCompany().equals(actor));
			result = new ModelAndView("problem/edit");
			result.addObject("problem", problem);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayProblem(@RequestParam final int Id) {
		ModelAndView result;
		Problem problem;
		try {
			problem = this.problemService.findOne(Id);

			result = new ModelAndView("problem/display");
			final BindingResult binding = null;
			final Collection<String> attachments = this.problemService.checkSplitPictures(problem.getAttachments(), binding);
			result.addObject("attachments", attachments);
			result.addObject(problem);
			try {
				final Actor actor2 = this.actorService.findByPrincipal();
				result.addObject("name", actor2.getUserAccount().getUsername());
			} catch (final Throwable opps) {
			}
		} catch (final Throwable opps) {
			opps.printStackTrace();
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final Actor actor = this.actorService.findByPrincipal();
			result = new ModelAndView("problem/edit");
			final Problem problem = this.problemService.create(actor);
			result.addObject("problem", problem);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "problem.commit.error");
		}
		return result;

	}
}
