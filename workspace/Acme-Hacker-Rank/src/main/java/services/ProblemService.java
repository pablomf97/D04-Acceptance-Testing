
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import domain.Actor;
import domain.Company;
import domain.Problem;

@Transactional
@Service
public class ProblemService {

	// Managed repository ------------------------------------

	@Autowired
	private ProblemRepository	problemRepository;

	@Autowired
	private ApplicationService	applicationService;

	// Supporting services -----------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods -----------------------------------

	public Problem create(final Actor actor) {
		Actor principal;
		Problem result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "COMPANY"), "not.allowed");

		result = new Problem();
		result.setCompany((Company) actor);
		result.setIsDraft(true);

		return result;
	}

	public Collection<Problem> findAll() {
		Collection<Problem> result;
		result = this.problemRepository.findAll();

		return result;
	}

	public Problem findOne(final int problemId) {
		Problem result;

		result = this.problemRepository.findOne(problemId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Problem> findByOwnerFinal(final Actor actor) {
		Assert.notNull(actor);
		return this.problemRepository.findByOwnerFinal(actor.getId());

	}

	public Problem save(final Problem problem) {
		Actor principal;
		Problem result = null;
		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "COMPANY"), "not.allowed");
		Assert.isTrue(problem.getCompany().equals(principal), "not.allowed");
		if (problem.getId() == 0)
			result = problem;
		else {
			result = this.findOne(problem.getId());
			Assert.isTrue(result.getCompany().equals(principal), "not.allowed");
			Assert.isTrue(result.getIsDraft());
			result.setAttachments(problem.getAttachments());
			result.setIsDraft(problem.getIsDraft());
			result.setOptionalHint(problem.getOptionalHint());
			result.setStatement(problem.getStatement());
			result.setTitle(problem.getTitle());
		}
		Assert.notNull(result);
		result = this.problemRepository.save(result);

		return result;
	}

	public void delete(final Problem problem) {
		Actor principal;

		Assert.notNull(problem);
		Assert.isTrue(problem.getId() != 0, "wrong.id");
		Assert.isTrue((this.applicationService.findByProblem(problem)).isEmpty(), "problem.used");
		Assert.isTrue(!this.problemRepository.findByPosition().contains(problem), "problem.used");

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "COMPANY"), "not.allowed");
		Assert.isTrue(problem.getCompany().getId() == principal.getId(), "not.allowed");

		this.problemRepository.delete(problem.getId());

	}
	public Collection<Problem> findByOwner(final Actor actor) {
		Assert.notNull(actor);
		return this.problemRepository.findByOwner(actor.getId());

	}

	// Other business methods -------------------------------

	public Problem reconstruct(final Problem problem, final BindingResult binding) {
		final Actor principal = this.actorService.findByPrincipal();
		Problem result = this.create(principal);

		if (problem.getId() == 0)
			result = problem;
		else {
			final Problem orig = this.findOne(problem.getId());
			result.setId(orig.getId());
			Assert.notNull(orig);
			Assert.isTrue(orig.getCompany().getId() == principal.getId());
		}
		result.setCompany((Company) principal);
		result.setAttachments(problem.getAttachments());
		result.setIsDraft(problem.getIsDraft());
		result.setOptionalHint(problem.getOptionalHint());
		result.setStatement(problem.getStatement());
		result.setTitle(problem.getTitle());
		this.validator.validate(result, binding);
		this.checkSplitPictures(result.getAttachments(), binding);
		return result;
	}

	public Collection<String> checkSplitPictures(final String attachments, final BindingResult binding) {
		final Collection<String> res = new ArrayList<>();
		if (attachments != null && !attachments.isEmpty()) {
			final String[] slice = attachments.split(",");
			for (final String p : slice)
				if (p.trim() != "") {
					try {
						Assert.isTrue(ResourceUtils.isUrl(p), "error.url");
					} catch (final Throwable oops) {
						binding.rejectValue("attachments", "error.url");
					}
					res.add(p);
				}
		}
		return res;
	}

	public Collection<Problem> findProblemsByPositionId(final int positionId) {
		Collection<Problem> problems;

		problems = this.problemRepository.findProblemsByPositionId(positionId);

		return problems;
	}

	public void flush() {
		this.problemRepository.flush();
	}

	public void DeleteProblemPerCompany(final Company company) {
		final Collection<Problem> col = this.findByOwner(company);

		this.problemRepository.deleteInBatch(col);

	}

}
