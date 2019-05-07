
package services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Curricula;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.Position;
import domain.PositionData;
import domain.Problem;
import domain.Rookie;

@Transactional
@Service
public class ApplicationService {

	// Managed repository ------------------------------------

	@Autowired
	private ApplicationRepository		applicationRepository;

	// Supporting services -----------------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CurriculaService			curriculaService;

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private EducationDataService		educationDataService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private PositionDataService			positionDataService;

	@Autowired
	private Validator					validator;


	// Simple CRUD methods -----------------------------------

	public Application create() {
		Actor principal;
		Application result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"), "not.allowed");

		result = new Application();

		result.setApplicationMoment(new Date(System.currentTimeMillis() - 1));
		result.setStatus("PENDING");
		result.setRookie((Rookie) principal);

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;
		result = this.applicationRepository.findAll();

		return result;
	}

	public Application findOne(final int applicationId) {
		Application result;
		result = this.applicationRepository.findOne(applicationId);

		return result;
	}

	public Application save(final Application application) {
		Actor principal;
		Application result;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal, "not.allowed");
		Assert.notNull(application);

		Assert.notNull(application.getPosition());
		Assert.notNull(application.getRookie());
		Assert.notNull(application.getApplicationMoment());

		if (this.actorService.checkAuthority(principal, "ROOKIE")) {

			Assert.isTrue(application.getRookie().equals(principal));
			Assert.isTrue(application.getStatus().equalsIgnoreCase("PENDING"));

			if (application.getId() == 0) {

				final Collection<Application> alreadyApplied = this.findApplicationsNotRejectedByRookieId(principal.getId());
				for (final Application app : alreadyApplied)
					Assert.isTrue(!application.getPosition().equals(app.getPosition()), "already.applied");

			} else {

				Assert.isTrue(!this.findOne(application.getId()).getStatus().equals("ACCEPTED"));
				Assert.isTrue(!this.findOne(application.getId()).getStatus().equals("REJECTED"));
				Assert.notNull(application.getExplanation());
				Assert.notNull(application.getLinkCode());
				Assert.notNull(application.getCopyCurricula());

				application.setSubmitMoment(new Date(System.currentTimeMillis() - 1));
				application.setStatus("SUBMITTED");

				Assert.isTrue(application.getApplicationMoment().before(application.getSubmitMoment()));

				Curricula curriculaCopy = this.curriculaService.createCopy();

				try {
					PersonalData personalDataCopy = this.personalDataService.createCopy();
					personalDataCopy = application.getCopyCurricula().getPersonalData().clone();
					personalDataCopy = this.personalDataService.saveCopy(personalDataCopy);

					curriculaCopy.setPersonalData(personalDataCopy);

					if (!application.getCopyCurricula().getEducationData().isEmpty()) {

						final Collection<EducationData> auxEdCopy = new ArrayList<>(application.getCopyCurricula().getEducationData());
						final Collection<EducationData> educationCopy = new ArrayList<EducationData>();

						for (final EducationData edData : auxEdCopy) {
							EducationData educationDataCopy = this.educationDataService.createCopy();
							educationDataCopy = edData.clone();
							educationDataCopy = this.educationDataService.saveCopy(educationDataCopy);
							educationCopy.add(educationDataCopy);
						}
						curriculaCopy.setEducationData(educationCopy);
					}

					if (!application.getCopyCurricula().getMiscellaneousData().isEmpty()) {

						final Collection<MiscellaneousData> auxMiscCopy = new ArrayList<>(application.getCopyCurricula().getMiscellaneousData());
						final Collection<MiscellaneousData> miscellaneousCopy = new ArrayList<MiscellaneousData>();

						for (final MiscellaneousData miscData : auxMiscCopy) {
							MiscellaneousData miscellaneousDataCopy = this.miscellaneousDataService.createCopy();
							miscellaneousDataCopy = miscData.clone();
							miscellaneousDataCopy = this.miscellaneousDataService.saveCopy(miscellaneousDataCopy);
							miscellaneousCopy.add(miscellaneousDataCopy);
						}
						curriculaCopy.setMiscellaneousData(miscellaneousCopy);
					}

					if (!application.getCopyCurricula().getPositionData().isEmpty()) {

						final Collection<PositionData> auxPosCopy = new ArrayList<>(application.getCopyCurricula().getPositionData());
						final Collection<PositionData> positionCopy = new ArrayList<PositionData>();

						for (final PositionData posData : auxPosCopy) {
							PositionData positionDataCopy = this.positionDataService.createCopy();
							positionDataCopy = posData.clone();
							positionDataCopy = this.positionDataService.saveCopy(positionDataCopy);
							positionCopy.add(positionDataCopy);
						}
						curriculaCopy.setPositionData(positionCopy);
					}

				} catch (final CloneNotSupportedException e) {
					e.printStackTrace();
				}
				curriculaCopy = this.curriculaService.save(curriculaCopy);

				application.setCopyCurricula(curriculaCopy);
			}

		} else if (this.actorService.checkAuthority(principal, "COMPANY")) {

			Assert.isTrue(!application.getStatus().equals("PENDING"));
			Assert.isTrue(!application.getStatus().equals("SUBMITTED"));

			Assert.isTrue(application.getPosition().getCompany().equals(principal));

			Assert.isTrue(application.getId() != 0);
			Assert.notNull(application.getExplanation());
			Assert.notNull(application.getLinkCode());
			Assert.notNull(application.getCopyCurricula());

		}

		result = this.applicationRepository.save(application);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Application application) {
		Actor principal;

		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0, "wrong.id");

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"), "not.allowed");

		Assert.isTrue(application.getRookie().equals(principal), "not.allowed");

		this.applicationRepository.delete(application.getId());

	}

	// Other business methods -------------------------------

	public Application reconstruct(final Application application, final BindingResult binding) {
		Application result = this.create();

		if (application.getId() == 0) {
			result = new Application();

			result.setPosition(application.getPosition());

		} else {
			final Application orig = this.findOne(application.getId());
			result.setApplicationMoment(orig.getApplicationMoment());
			result.setCopyCurricula(orig.getCopyCurricula());
			result.setId(orig.getId());
			result.setPosition(orig.getPosition());
			result.setProblem(orig.getProblem());
			result.setRookie(orig.getRookie());
			result.setStatus(orig.getStatus());
			try {
				Assert.isTrue(!application.getExplanation().isEmpty(), "explanation.needed");
			} catch (final Exception e) {
				binding.rejectValue("explanation", "explanation.needed");
			}

			try {
				Assert.isTrue(!application.getLinkCode().isEmpty(), "link.needed");
			} catch (final Exception e) {
				binding.rejectValue("linkCode", "link.needed");
			}

			try {
				Assert.notNull(application.getCopyCurricula());
			} catch (final Exception e) {
				binding.rejectValue("copyCurricula", "curricula.needed");
			}

			result.setExplanation(application.getExplanation());
			result.setLinkCode(application.getLinkCode());
			result.setCopyCurricula(application.getCopyCurricula());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<Application> findApplicationsByRookieId(final int rookieId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationsByRookieId(rookieId);

		return applications;
	}

	public Collection<Application> findApplicationsNotRejectedByRookieId(final int rookieId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationsNotRejectedByRookieId(rookieId);

		return applications;
	}

	public Collection<Application> findApplicationsByCompanyId(final int companyId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationsByCompanyId(companyId);

		return applications;
	}

	public Problem selectProblem(final Collection<Problem> problems) {
		Problem result;
		final SecureRandom rnd = new SecureRandom();
		final List<Problem> listProblems = new ArrayList<>(problems);

		Integer a = (rnd.nextInt() % 10);
		while (a < 0 || a > (problems.size() - 1))
			a = (rnd.nextInt() % 10);

		result = listProblems.get(a);

		return result;
	}

	public void flush() {
		this.applicationRepository.flush();

	}

	public Collection<Application> findByProblem(final Problem problem) {
		Assert.notNull(problem);
		final Collection<Application> res = this.applicationRepository.findByProblem(problem.getId());
		return res;
	}

	public Collection<Application> findByPosition(final Position position) {
		Assert.notNull(position);
		final Collection<Application> res = this.applicationRepository.findByPosition(position.getId());
		return res;
	}

	public Integer maxApplicationsPerRookie() {

		return this.applicationRepository.maxApplicationsPerRookie();
	}

	public Integer minApplicationsPerRookie() {

		return this.applicationRepository.minApplicationsPerRookie();
	}

	public Double avgApplicationsPerRookie() {

		return this.applicationRepository.avgApplicationsPerRookie();
	}

	public Double sttdevApplicationsPerRookie() {

		return this.applicationRepository.stddevApplicationsPerRookie();
	}

	protected void deleteApp(final Rookie rookie) {
		Collection<Application> apps;
		apps = this.applicationRepository.findApplicationPerRookie(rookie.getId());

		this.applicationRepository.deleteInBatch(apps);

	}

	public void deleteAppPerPos(final Application app) {
		this.applicationRepository.delete(app);
	}
	public void rejectedByCompany(final Company company) {
		Assert.isTrue(this.actorService.findByPrincipal().equals(company));
		final Collection<Application> applys = this.applicationRepository.findApplicationsNotRejectedByCompanyId(company.getId());
		for (final Application a : applys)
			a.setStatus("REJECTED");
	}
	public Collection<Application> findApplicationByCurricula(final Curricula cur) {
		Assert.notNull(cur);
		final Collection<Application> app = this.applicationRepository.findApplicationByCurricula(cur.getId());
		Assert.notNull(app);
		return app;
	}
}
