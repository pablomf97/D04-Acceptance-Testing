package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FinderService;
import services.SystemConfigurationService;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Controller
@RequestMapping("/finder")
public class FinderController extends AbstractController{


	// Services

	@Autowired
	private FinderService				finderService;

	@Autowired
	private ActorService 		actorService;


	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	// Constructors

	public FinderController() {
		super();
	}
	// /list

	@RequestMapping(value = "/hacker/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Finder finder;

		Hacker principal;

		principal = (Hacker) this.actorService.findByPrincipal();
		finder = principal.getFinder();
		Assert.isTrue(
				this.actorService.checkAuthority(principal, "HACKER"),
				"not.allowed");

		final Collection<Position> positions = finder.getResults();

		result = new ModelAndView("finder/list");
		result.addObject("positions", positions);
		result.addObject("requestUri", "finder/hacker/list.do");

		return result;
	}
	// DELETE
	@RequestMapping(value = "/hacker/search", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Finder finder, final BindingResult binding) {

		ModelAndView result;
		try {

			this.finderService.delete(finder);
			result = new ModelAndView("redirect:search.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(finder, "finder.commit.error");
		}

		return result;
	}
	// search
	@RequestMapping(value = "/hacker/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		Finder finder;

		Hacker principal;

		principal = (Hacker) this.actorService.findByPrincipal();
		finder = principal.getFinder();
		Assert.isTrue(
				this.actorService.checkAuthority(principal, "HACKER"),
				"not.allowed");
		Date maxLivedMoment = new Date();

		finder = principal.getFinder();
		if (finder.getSearchMoment() != null) {
			final int timeChachedFind = this.systemConfigurationService.findMySystemConfiguration().getTimeResultsCached();
			maxLivedMoment = DateUtils.addHours(maxLivedMoment, -timeChachedFind);

			if (finder.getSearchMoment().before(maxLivedMoment))
				this.finderService.deleteExpiredFinder(finder);
		}

		result = new ModelAndView("finder/search");
		result.addObject("finder", finder);

		result.addObject("positions", finder.getResults());

		result.addObject("requestUri", "finder/hacker/search.do");
		return result;
	}
	
	// searchAnon
		@RequestMapping(value = "/anon/search", method = RequestMethod.GET)
		public ModelAndView searchAnon(@RequestParam (required = false) String keyWord) {
			ModelAndView result;
			Collection<Position> positions = new ArrayList<>();
			
			try {
				result = new ModelAndView("finder/anon/search");

				positions = this.finderService.searchAnon(keyWord);
				
				result.addObject("positions", positions);

				result.addObject("requestUri", "finder/anon/search.do");
			} catch (Throwable oopsie) {
				result = new ModelAndView("application/listHacker");

				result.addObject("errMsg", oopsie);
			}
			return result;
			
		}
		
		
	@RequestMapping(value = "/hacker/search", method = RequestMethod.POST, params = "save")
	public ModelAndView search(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;


		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			result = this.createEditModelAndView(finder);

		} else{
			try {

				this.finderService.search(finder);
				result = new ModelAndView("redirect:/finder/hacker/search.do");

			} catch (final Throwable oops) {
				System.out.println(finder.getResults());
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());

				result = this.createEditModelAndView(finder, "finder.commit.error");

			}
		}
		return result;
	}

	// ancillary methods

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;
		final Collection<Position> positions;
		positions = finder.getResults();

		result = new ModelAndView("finder/search");
		result.addObject("message", messageCode);
		result.addObject("finder", finder);
		result.addObject("positions", positions);

		return result;
	}



}
