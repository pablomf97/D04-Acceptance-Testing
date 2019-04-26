package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import domain.Actor;
import domain.Curricula;
import domain.Hacker;
import domain.PersonalData;

@Transactional
@Service
public class PersonalDataService {
	
	//Repository
	@Autowired
	private PersonalDataRepository personalDataRepository;

	//Services
	@Autowired
	private ActorService actorService;

	@Autowired
	private CurriculaService curriculaService;

	//Create
	public PersonalData create(){
		Hacker principal;
		PersonalData result;
		Collection<Curricula> principalCurriculas;

		principal = (Hacker) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		//Checking creating a data in an own curricula
		for(Curricula c : principalCurriculas){
			Assert.isTrue(c.getHacker().getId() == principal.getId());
		}

		result = new PersonalData();

		return result;
	}

	//Save
	public PersonalData save(PersonalData data,int curriculaId){
		Hacker principal;
		PersonalData dataDB = new PersonalData();
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		PersonalData result;

		principal = (Hacker) this.actorService.findByPrincipal();

		

		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		

		if(data.getId()!=0){
			currentCurricula = this.curriculaService.getCurriculaByPersonalData(data.getId());
			
			Assert.isTrue(currentCurricula.getPersonalData().getId() == data.getId());
			Assert.isTrue(principalCurriculas.contains(currentCurricula));
			Assert.isTrue(currentCurricula.getHacker().getId() == principal.getId());

			dataDB = this.personalDataRepository.findOne(data.getId());

			dataDB.setGithubProfile(data.getGithubProfile());
			dataDB.setLinkedIn(data.getLinkedIn());
			dataDB.setFullName(data.getFullName());
			dataDB.setStatement(data.getStatement());
			dataDB.setPhoneNumber(data.getPhoneNumber());


			result = this.personalDataRepository.save(dataDB);

		}else{
			Assert.notNull(data.getGithubProfile());
			Assert.notNull(data.getLinkedIn());
			Assert.notNull(data.getFullName());
			Assert.notNull(data.getStatement());
			
			result = this.personalDataRepository.save(data);
			
			currentCurricula = this.curriculaService.findOne(curriculaId);
			
			currentCurricula.setPersonalData(data);
		}


		return result;

	}



	//Finds
	public PersonalData findOne(int dataId){
		PersonalData result = this.personalDataRepository.findOne(dataId);

		return result;
	}

	public Collection<PersonalData> findAll(){
		Collection<PersonalData>result = this.personalDataRepository.findAll();

		return result;
	}
	
	public void delete(int personalDataId){
		
		this.personalDataRepository.delete(personalDataId);

	}
	
	public PersonalData defaultData(){
		PersonalData result;
		
		result = new PersonalData();
		result.setFullName("Its neccessary to introduce your full name");
		result.setStatement("It is a must the statement");
		
		return result;
	}
	
	
	
	
	public PersonalData createCopy(){
		Actor principal;
		PersonalData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

		result = new PersonalData();

		return result;
	}
	

	public PersonalData saveCopy(PersonalData data){
		Actor principal;
		PersonalData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));
		
		Assert.notNull(data.getGithubProfile());
		Assert.notNull(data.getLinkedIn());
		Assert.notNull(data.getFullName());
		Assert.notNull(data.getStatement());
		
		result = this.personalDataRepository.save(data);

		return result;

	}
	public void flush(){
		
		 this.personalDataRepository.flush();
	}
	
}
