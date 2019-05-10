
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.Company;
import domain.Position;

@Transactional
@Service
public class AuditService {

	/* Working repository */

	@Autowired
	private AuditRepository	auditRepository;
	@Autowired
	private PositionService	positionService;
	@Autowired
	private ActorService	actorService;

	@Autowired
	private Validator		validator;


	/* Services */
	public Audit create(final Position position) {
		Actor principal;
		Audit result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "AUDITOR"), "not.allowed");
		
		result = new Audit();
		result.setAuditor((Auditor) principal);
		result.setIsDraft(true);
		result.setPosition(position);
		final Date m = new Date();
		result.setMoment(m);
		return result;
	}

	public Collection<Audit> findByOwner(final Actor actor) {
		Assert.notNull(actor);
		return this.auditRepository.auditsPerAuditor(actor.getId());
	}

	public Collection<Audit> findAllByPosition(final Integer id) {
		Assert.notNull(id);
		return this.auditRepository.auditsPerPosition(id);
	}

	public Audit findOne(final int auditId) {
		Audit result;

		result = this.auditRepository.findOne(auditId);
		Assert.notNull(result);
		return result;
	}

	public Audit save(final Audit audit) {
		Actor principal;
		principal = this.actorService.findByPrincipal();
		Audit result = this.create(audit.getPosition());

		Assert.isTrue(this.actorService.checkAuthority(principal, "AUDITOR"), "not.allowed");
		Assert.isTrue(audit.getAuditor().equals(principal));
		if (audit.getId() == 0) {
			result = audit;
			final Date moment = new Date();
			result.setMoment(moment);
		} else {
			result = this.findOne(audit.getId());
			Assert.isTrue(audit.getIsDraft());
			result.setIsDraft(audit.getIsDraft());
			result.setScore(audit.getScore());
			result.setText(audit.getText());
		}
		Assert.notNull(result);
		
		
		result = this.auditRepository.save(result);

		return result;
	}

	public void delete(final Audit audit) {
		Actor principal;
		Assert.isTrue(audit.getIsDraft(), "Only can delete in draft mode");
		Assert.notNull(audit);
		Assert.isTrue(audit.getId() != 0, "wrong.id");
		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "AUDITOR"), "not.allowed");
		final Audit orig = this.findOne(audit.getId());
		Assert.isTrue(audit.getAuditor().getId() == principal.getId(), "not.allowed");
		Assert.isTrue(orig.getId() == audit.getId());
		this.auditRepository.delete(audit.getId());
	}
	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		final Audit result = this.create(audit.getPosition());
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(audit.getPosition().getIsDraft() == false);
		if (audit.getId() == 0) {
			final Date moment = new Date();
			result.setMoment(moment);
		} else {
			final Audit orig = this.findOne(audit.getId());
			Assert.notNull(orig);
			Assert.isTrue(orig.getAuditor().getId() == principal.getId());
			result.setId(orig.getId());
			Assert.isTrue(orig.getIsDraft());
		}
		result.setPosition(audit.getPosition());
		result.setIsDraft(audit.getIsDraft());
		result.setScore(audit.getScore());
		result.setText(audit.getText());
		this.validator.validate(result, binding);
		return result;
	}
	// Statistics
	public Double[] statsAuditPositions() {

		return this.auditRepository.statsAuditPositions();
	}
	public Collection<Audit> auditsPerPosition(final int id) {
		return this.auditRepository.auditsPerPosition(id);
	}

	public void deleteAuditsPerCompany(final Company c) {
		final Collection<Position> positions = this.positionService.findByOwner(c);
		final Collection<Audit> audits = new ArrayList<Audit>();
		for (final Position p : positions)
			audits.addAll(this.auditsPerPosition(p.getId()));
		this.auditRepository.deleteInBatch(audits);
	}
	public Collection<Audit> auditsPerAuditor(final int id) {
		return this.auditRepository.auditsPerAuditor(id);
	}
	public void deleteAuditsPerAuditor(final Collection<Audit> cols) {

		this.auditRepository.deleteInBatch(cols);

	}
	
	public void flush(){
		this.auditRepository.flush();
	}
}
