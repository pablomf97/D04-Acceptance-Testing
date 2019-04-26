
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import domain.Actor;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PositionData;

@Transactional
@Service
public class CurriculaService {

	//Repository

	@Autowired
	private CurriculaRepository			curriculaRepository;

	//Services

	@Autowired
	private ActorService				actorService;

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private PositionDataService			positionDataService;

	@Autowired
	private EducationDataService		educationDataService;


	//Create
	public Curricula create() {
		Curricula result;
		Hacker principal;

		principal = (Hacker) this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

		result = new Curricula();
		result.setEducationData(new ArrayList<EducationData>());
		result.setPositionData(new ArrayList<PositionData>());
		result.setPersonalData(this.personalDataService.defaultData());
		result.setIsCopy(false);
		result.setHacker(principal);
		return result;
	}

	//Save
	public Curricula save(final Curricula curricula) {
		Curricula result, aux = null;
		Hacker principal;

		//Checking curricula owner
		principal = (Hacker) this.actorService.findByPrincipal();
		Assert.isTrue(curricula.getHacker().getId() == principal.getId());

		//Checking persistence
		if (curricula.getId() == 0)
			Assert.notNull(curricula.getPersonalData());
		else {
			Assert.notNull(curricula.getPersonalData());
			Assert.notEmpty(curricula.getEducationData());
			Assert.notEmpty(curricula.getPositionData());
			aux = this.findOne(curricula.getId());

		}

		result = this.curriculaRepository.save(curricula);

		if (aux != null)
			this.personalDataService.delete(aux.getPersonalData().getId());

		return result;
	}

	public void delete(final Curricula curricula) {
		Hacker principal;

		//Checking curricula owner
		principal = (Hacker) this.actorService.findByPrincipal();
		Assert.isTrue(curricula.getHacker().getId() == principal.getId());

		this.curriculaRepository.delete(curricula);

	}

	//Finds
	public Curricula findOne(final int curriculaId) {

		final Curricula result = this.curriculaRepository.findOne(curriculaId);

		return result;
	}

	public Collection<Curricula> findAll() {

		final Collection<Curricula> result = this.curriculaRepository.findAll();

		return result;
	}

	public Collection<Curricula> getCurriculasByHacker(final int hackerId) {

		final Collection<Curricula> result = this.curriculaRepository.getCurriculasByHacker(hackerId);

		return result;
	}

	public Curricula getCurriculaByMiscellaneousData(final int dataId) {
		final Curricula result = this.curriculaRepository.getCurriculaByMiscellaneousData(dataId);

		return result;
	}

	public Curricula getCurriculaByEducationData(final int dataId) {
		final Curricula result = this.curriculaRepository.getCurriculaByEducationData(dataId);

		return result;
	}

	public Curricula getCurriculaByPositionData(final int dataId) {
		final Curricula result = this.curriculaRepository.getCurriculaByPositionData(dataId);

		return result;
	}

	public Curricula getCurriculaByPersonalData(final int dataId) {
		final Curricula result = this.curriculaRepository.getCurriculaByPersonalData(dataId);

		return result;
	}

	public Curricula createCopy() {
		Curricula result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

		result = new Curricula();
		result.setEducationData(new ArrayList<EducationData>());
		result.setPositionData(new ArrayList<PositionData>());
		result.setMiscellaneousData(new ArrayList<MiscellaneousData>());
		result.setIsCopy(true);
		result.setHacker((Hacker) principal);

		return result;
	}

	//Save
	public Curricula saveCopy(final Curricula curricula) {
		Curricula result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));
		Assert.isTrue(curricula.getHacker().getId() == principal.getId());

		//Checking persistence
		if (curricula.getId() == 0)
			Assert.notNull(curricula.getPersonalData());
		else {
			Assert.notNull(curricula.getPersonalData());
			Assert.notEmpty(curricula.getEducationData());
			Assert.notEmpty(curricula.getPositionData());
		}
		result = this.curriculaRepository.save(curricula);

		return result;
	}

	public Collection<Curricula> findCurriculasByHackerId(final int hackerId) {
		Collection<Curricula> curriculas;

		curriculas = this.curriculaRepository.findCurriculasByHackerId(hackerId);

		return curriculas;
	}

	public void delete(final Integer entity) {
		this.curriculaRepository.delete(entity);
	}

	protected void deleteCV(final Hacker hacker) {
		Collection<Curricula> cvs;
		cvs = this.curriculaRepository.findCVPerHacker(hacker.getId());

		for (final Curricula cv : cvs) {

			this.miscellaneousDataService.deleteMiscHacker(cv.getMiscellaneousData());
			for (final EducationData ed : cv.getEducationData())
				this.educationDataService.deleteEDHacker(ed);
			for (final PositionData pd : cv.getPositionData())
				this.positionDataService.deletePosHacker(pd);

			this.curriculaRepository.delete(cv);
		}
	}

}
