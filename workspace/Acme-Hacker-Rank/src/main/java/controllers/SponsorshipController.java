
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
import services.SponsorshipService;
import domain.Actor;
import domain.Provider;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship")
public class SponsorshipController extends AbstractController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private SponsorshipService	sponsorshipService;
	
	public SponsorshipController() {
		super();
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {

		ModelAndView result;
		Sponsorship sponsorship;
		boolean isPrincipal = false;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		sponsorship = this.sponsorshipService.findOne(sponsorshipId);

		if (sponsorship.getProvider().equals((Provider)principal))
			isPrincipal = true;

		result = new ModelAndView("sponsorship/display");
		result.addObject("sponsorship", sponsorship);
		result.addObject("isPrincipal", isPrincipal);
		result.addObject("requestURI", "sponsorship/display.do?sponsorshipId="
				+ sponsorshipId);

		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result = new ModelAndView("sponsorship/list");
		Collection<Sponsorship> sponsorships;
		Actor principal;
		
		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal, "PROVIDER"));
			
			sponsorships = this.sponsorshipService.sponsorshipsPerProvider(principal.getId());
			
			result.addObject("sponsorships", sponsorships);
			result.addObject("permission", true);
			
		} catch (Throwable oops) {
			result.addObject("errMsg", oops);
			result.addObject("permission", false);
		}
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int positionId) {
		ModelAndView result = null;
		try {
			Sponsorship sponsorship = this.sponsorshipService.create();
			Integer aux = positionId;
			
			sponsorship.setTarget(aux.toString());
			result = this.createEditModelAndView(sponsorship);
		} catch (Throwable oops) {
			System.out.println(oops.getMessage());
		}

		return result;

	}
	
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		try {
			sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			Assert.notNull(sponsorship);
			
			result = this.createEditModelAndView(sponsorship);
			result.addObject("sponsorshipId", sponsorshipId);
		} catch (Throwable oops) {
			
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Sponsorship sponsorship, BindingResult binding) {
		ModelAndView result;
		Sponsorship aux;
		try {
			aux = this.sponsorshipService.reconstruct(sponsorship,
					binding);
			if (binding.hasErrors()) {

				result = new ModelAndView("sponsorship/edit");
				result.addObject("sponsorship", sponsorship);
				result.addObject("binding", binding);
				result.addObject("isPrincipal", true);
			} else {
				try {

					this.sponsorshipService.save(aux);
					result = new ModelAndView(
							"redirect:list.do");
				} catch (final Throwable oops) {
					result = new ModelAndView("sponsorship/edit");
					result.addObject("sponsorship", aux);
					result.addObject("messageCode", oops.getMessage());
				}
			}
		} catch (final Throwable oops) {
			if(binding.hasErrors()) {
				result = this.createEditModelAndView(sponsorship, "jpa.error");
			} else {
				result = this.createEditModelAndView(sponsorship, "commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			this.sponsorshipService.delete(sponsorship);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", oops.getMessage());
		}
		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			final Sponsorship sponsorship, final String messageCode) {
		final ModelAndView result;
		Actor principal;
		boolean isPrincipal = true;
		
		if(messageCode == null) {
			principal = this.actorService.findByPrincipal();

			if (principal.getId() != sponsorship.getProvider().getId())
				isPrincipal = false;
		}
		
		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("isPrincipal", isPrincipal);
		result.addObject("message", messageCode);

		return result;
	}
}
