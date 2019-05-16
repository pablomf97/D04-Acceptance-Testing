
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import domain.Actor;
import domain.Curricula;
import domain.Rookie;
import domain.PersonalData;

@Transactional
@Service
public class PersonalDataService {

	//Repository
	@Autowired
	private PersonalDataRepository	personalDataRepository;

	//Services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private CurriculaService		curriculaService;


	//Create
	public PersonalData create() {
		Rookie principal;
		PersonalData result;

		principal = (Rookie) this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		result = new PersonalData();

		return result;
	}
	//Save
	public PersonalData save(final PersonalData data, final int curriculaId) {
		Rookie principal;
		PersonalData dataDB = new PersonalData();
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		PersonalData result;

		principal = (Rookie) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByRookie(principal.getId());

		if (data.getId() != 0) {
			currentCurricula = this.curriculaService.findOne(curriculaId);

			Assert.isTrue(currentCurricula.getPersonalData().getId() == data.getId());
			Assert.isTrue(principalCurriculas.contains(currentCurricula));
			Assert.isTrue(currentCurricula.getRookie().getId() == principal.getId());

			dataDB = this.personalDataRepository.findOne(data.getId());

			dataDB.setGithubProfile(data.getGithubProfile());
			dataDB.setLinkedIn(data.getLinkedIn());
			dataDB.setFullName(data.getFullName());
			dataDB.setStatement(data.getStatement());
			dataDB.setPhoneNumber(data.getPhoneNumber());

			result = this.personalDataRepository.save(dataDB);

		} else {
			Assert.notNull(data.getGithubProfile());
			Assert.notNull(data.getLinkedIn());
			Assert.notNull(data.getFullName());
			Assert.notNull(data.getStatement());

			

			currentCurricula = this.curriculaService.findOne(curriculaId);
			
			Assert.isTrue(principalCurriculas.contains(currentCurricula));
			Assert.isTrue(currentCurricula.getRookie().getId() == principal.getId());
			
			result = this.personalDataRepository.save(data);
			
			currentCurricula.setPersonalData(data);
		}

		return result;

	}

	//Finds
	public PersonalData findOne(final int dataId) {
		final PersonalData result = this.personalDataRepository.findOne(dataId);

		return result;
	}

	public Collection<PersonalData> findAll() {
		final Collection<PersonalData> result = this.personalDataRepository.findAll();

		return result;
	}

	public void delete(final int personalDataId) {

		this.personalDataRepository.delete(personalDataId);

	}

	public PersonalData defaultData() {
		PersonalData result;

		result = new PersonalData();
		result.setFullName("Its neccessary to introduce your full name");
		result.setStatement("It is a must the statement");

		return result;
	}

	public PersonalData createCopy() {
		Actor principal;
		PersonalData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		result = new PersonalData();

		return result;
	}

	public PersonalData saveCopy(final PersonalData data) {
		Actor principal;
		PersonalData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		Assert.notNull(data.getGithubProfile());
		Assert.notNull(data.getLinkedIn());
		Assert.notNull(data.getFullName());
		Assert.notNull(data.getStatement());

		result = this.personalDataRepository.save(data);

		return result;

	}
	public void flush() {

		this.personalDataRepository.flush();
	}
	public PersonalData saveNewCurricula(final PersonalData personalData) {
		Actor principal;
		PersonalData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));
		Assert.isTrue(personalData.getId() == 0);
		Assert.notNull(personalData.getGithubProfile());
		Assert.notNull(personalData.getLinkedIn());
		Assert.notNull(personalData.getFullName());
		Assert.notNull(personalData.getStatement());

		result = this.personalDataRepository.save(personalData);
		final Curricula c = this.curriculaService.create();
		c.setPersonalData(result);
		this.curriculaService.save(c);
		return result;
	}

	public void checkOwnerPersonalData(final Integer id) {
		final Actor principal = this.actorService.findByPrincipal();
		final Curricula c = this.curriculaService.getCurriculaByPersonalData(id);
		Assert.isTrue(c.getRookie().equals(principal));
	}
}
