
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
import services.AuditService;
import services.PositionService;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("/audit")
public class AuditController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private AuditService	auditService;

	@Autowired
	private PositionService	positionService;


	public AuditController() {
		super();
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAllByPosition(@RequestParam final int Id) {
		ModelAndView result;
		try {
			final Collection<Audit> audits = this.auditService.findAllByPositionFinal(Id);
			result = new ModelAndView("audit/list");
			result.addObject("requestURI", "/audit/list.do");
			result.addObject("audits", audits);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listLoged() {
		ModelAndView result;
		try {
			result = new ModelAndView("audit/list");
			final Actor actor = this.actorService.findByPrincipal();
			try {
				result.addObject("name", actor.getUserAccount().getUsername());
			} catch (final Throwable opps) {
			}
			final Collection<Audit> audits = this.auditService.findByOwner(actor);
			result.addObject("requestURI", "/audit/list.do");
			result.addObject("audits", audits);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deletePosition(@RequestParam final int Id) {
		ModelAndView result;
		try {
			final Audit audit = this.auditService.findOne(Id);
			this.auditService.delete(audit);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView savePositionFinal(final Audit audit, final BindingResult binding) {
		ModelAndView result;
		Audit res = null;
		try {
			audit.setIsDraft(false);
			res = this.auditService.reconstruct(audit, binding);
			if (binding.hasErrors()) {
				result = new ModelAndView("audit/edit");
				result.addObject("audit", audit);
				final Actor actor = this.actorService.findByPrincipal();
			} else
				try {
					res.setIsDraft(false);
					this.auditService.save(res);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable opps) {
					result = new ModelAndView("redirect:../welcome/index.do");
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
	public ModelAndView savePosition(final Audit audit, final BindingResult binding) {
		ModelAndView result;
		Audit res = null;
		try {
			final Actor actor = this.actorService.findByPrincipal();

			res = this.auditService.reconstruct(audit, binding);
			if (binding.hasErrors()) {
				result = new ModelAndView("audit/edit");
				result.addObject("audit", audit);
			} else
				try {
					this.auditService.save(res);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable opps) {
					result = new ModelAndView("redirect:../welcome/index.do");
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
	public ModelAndView editPosition(@RequestParam final int Id) {
		ModelAndView result;
		Audit audit;
		try {
			audit = this.auditService.findOne(Id);
			result = new ModelAndView("audit/edit");
			result.addObject(audit);
			final Actor actor = this.actorService.findByPrincipal();
			Assert.isTrue(audit.getAuditor().equals(actor));
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayPosition(@RequestParam final int Id) {
		ModelAndView result;
		Audit audit;
		Actor actor = null;
		Boolean b = true;
		try {
			result = new ModelAndView("audit/display");
			audit = this.auditService.findOne(Id);
			try {
				actor = this.actorService.findByPrincipal();
				if ((audit.getAuditor() != ((Auditor) actor)) && (audit.getIsDraft() == true))
					b = false;
				result.addObject("name", actor.getUserAccount().getUsername());
			} catch (final Throwable opps) {
				if (audit.getIsDraft() == true)
					b = false;
			}
			Assert.isTrue(b);
			result.addObject(audit);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int Id) {
		ModelAndView result;
		try {
			final Position position = this.positionService.findOne(Id);
			Assert.isTrue(!position.getIsDraft());
			result = new ModelAndView("audit/edit");
			final Audit audit = this.auditService.create(position);
			result.addObject("audit", audit);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "position.commit.error");
		}
		return result;

	}
}
