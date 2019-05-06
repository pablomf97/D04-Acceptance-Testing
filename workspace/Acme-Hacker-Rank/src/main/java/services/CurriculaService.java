
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
import domain.Rookie;
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
		Rookie principal;

		principal = (Rookie) this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		result = new Curricula();
		result.setEducationData(new ArrayList<EducationData>());
		result.setPositionData(new ArrayList<PositionData>());
		result.setPersonalData(this.personalDataService.defaultData());
		result.setIsCopy(false);
		result.setRookie(principal);
		return result;
	}

	//Save
	public Curricula save(final Curricula curricula) {
		Curricula result, aux = null;
		Rookie principal;

		//Checking curricula owner
		principal = (Rookie) this.actorService.findByPrincipal();
		Assert.isTrue(curricula.getRookie().getId() == principal.getId());

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
		Rookie principal;

		//Checking curricula owner
		principal = (Rookie) this.actorService.findByPrincipal();
		Assert.isTrue(curricula.getRookie() == principal);
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

	public Collection<Curricula> getCurriculasByRookie(final int rookieId) {

		final Collection<Curricula> result = this.curriculaRepository.getCurriculasByRookie(rookieId);

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
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		result = new Curricula();
		result.setEducationData(new ArrayList<EducationData>());
		result.setPositionData(new ArrayList<PositionData>());
		result.setMiscellaneousData(new ArrayList<MiscellaneousData>());
		result.setIsCopy(true);
		result.setRookie((Rookie) principal);

		return result;
	}

	//Save
	public Curricula saveCopy(final Curricula curricula) {
		Curricula result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));
		Assert.isTrue(curricula.getRookie().getId() == principal.getId());

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

	public Collection<Curricula> findCurriculasByRookieId(final int rookieId) {
		Collection<Curricula> curriculas;

		curriculas = this.curriculaRepository.findCurriculasByRookieId(rookieId);

		return curriculas;
	}

	public void delete(final Integer entity) {
		this.curriculaRepository.delete(entity);
	}

	protected void deleteCV(final Rookie rookie) {
		Collection<Curricula> cvs;
		cvs = this.curriculaRepository.findCVPerRookie(rookie.getId());

		for (final Curricula cv : cvs) {

			this.miscellaneousDataService.deleteMiscRookie(cv.getMiscellaneousData());
			for (final EducationData ed : cv.getEducationData())
				this.educationDataService.deleteEDRookie(ed);
			for (final PositionData pd : cv.getPositionData())
				this.positionDataService.deletePosRookie(pd);

			this.curriculaRepository.delete(cv);
		}
	}

}
