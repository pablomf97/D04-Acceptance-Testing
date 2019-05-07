
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationDataRepository;
import domain.Actor;
import domain.Curricula;
import domain.EducationData;
import domain.Rookie;

@Transactional
@Service
public class EducationDataService {

	//Repository

	@Autowired
	private EducationDataRepository	educationDataRepository;

	//Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CurriculaService		curriculaService;


	//Create

	public EducationData create() {
		Rookie principal;
		EducationData result;

		principal = (Rookie) this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		result = new EducationData();

		return result;
	}

	//Save
	public EducationData save(final EducationData data, final int curriculaId) {
		Rookie principal;
		EducationData dataDB = new EducationData();
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		EducationData result;

		principal = (Rookie) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByRookie(principal.getId());

		//Checking creating a data in a own curricula

		if (data.getId() != 0) {
			currentCurricula = this.curriculaService.getCurriculaByEducationData(data.getId());

			Assert.isTrue(currentCurricula.getEducationData().contains(data));
			Assert.isTrue(principalCurriculas.contains(currentCurricula));
			Assert.isTrue(currentCurricula.getRookie().getId() == principal.getId());

			dataDB = this.educationDataRepository.findOne(data.getId());

			dataDB.setDegree(data.getDegree());
			dataDB.setInstitution(data.getInstitution());
			dataDB.setMark(data.getMark());
			dataDB.setStartDate(data.getStartDate());

			result = this.educationDataRepository.save(dataDB);

		} else {
			Assert.notNull(data.getDegree());
			Assert.notNull(data.getInstitution());
			Assert.notNull(data.getStartDate());
			
			if(!(data.getEndDate() == null)){
				Assert.isTrue(data.getStartDate().before(data.getEndDate()));
			}

			result = this.educationDataRepository.save(data);

			currentCurricula = this.curriculaService.findOne(curriculaId);

			currentCurricula.getEducationData().add(data);
		}

		return result;

	}

	//Delete
	public void delete(final EducationData data) {
		Rookie principal;
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		EducationData db = new EducationData();

		db = this.educationDataRepository.findOne(data.getId());

		principal = (Rookie) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByRookie(principal.getId());

		currentCurricula = this.curriculaService.getCurriculaByEducationData(data.getId());

		Assert.isTrue(currentCurricula.getRookie().getId() == principal.getId());
		Assert.isTrue(principalCurriculas.contains(currentCurricula));
		Assert.isTrue(currentCurricula.getEducationData().contains(db));

		currentCurricula.getEducationData().remove(db);

		this.educationDataRepository.delete(db);
	}

	//Finds
	public EducationData findOne(final int dataId) {
		final EducationData result = this.educationDataRepository.findOne(dataId);

		return result;
	}

	public Collection<EducationData> findAll() {
		final Collection<EducationData> result = this.educationDataRepository.findAll();

		return result;
	}

	public EducationData createCopy() {
		Actor principal;
		EducationData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		result = new EducationData();

		return result;
	}

	public EducationData saveCopy(final EducationData data) {
		Actor principal;
		EducationData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		Assert.notNull(data.getDegree());
		Assert.notNull(data.getInstitution());
		Assert.notNull(data.getStartDate());

		result = this.educationDataRepository.save(data);
		Assert.notNull(result);

		return result;

	}

	public void flush() {
		this.educationDataRepository.flush();
	}

	public void deleteEDRookie(final EducationData ed) {
		this.educationDataRepository.delete(ed);

	}
	public void checkOwnerEducationData(final Integer id) {
		final Actor principal = this.actorService.findByPrincipal();
		final Curricula c = this.curriculaService.getCurriculaByEducationData(id);
		Assert.isTrue(c.getRookie().equals(principal));
	}

}
