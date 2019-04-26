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
import domain.Hacker;

@Transactional
@Service
public class EducationDataService {

	//Repository

	@Autowired
	private EducationDataRepository educationDataRepository;

	//Services

	@Autowired
	private ActorService actorService;

	@Autowired
	private CurriculaService curriculaService;


	//Create

	public EducationData create(){
		Hacker principal;
		EducationData result;
		Collection<Curricula> principalCurriculas;

		principal = (Hacker) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		//Checking creating a data in an own curricula
		for(Curricula c : principalCurriculas){
			Assert.isTrue(c.getHacker().getId() == principal.getId());
		}

		result = new EducationData();

		return result;
	}

	//Save
	public EducationData save(EducationData data,int curriculaId){
		Hacker principal;
		EducationData dataDB = new EducationData();
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		EducationData result;

		principal = (Hacker) this.actorService.findByPrincipal();

		

		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		//Checking creating a data in a own curricula
		

		if(data.getId()!=0){
			currentCurricula = this.curriculaService.getCurriculaByEducationData(data.getId());
			
			Assert.isTrue(currentCurricula.getEducationData().contains(data));
			Assert.isTrue(principalCurriculas.contains(currentCurricula));
			Assert.isTrue(currentCurricula.getHacker().getId() == principal.getId());
			
			dataDB = this.educationDataRepository.findOne(data.getId());

			dataDB.setDegree(data.getDegree());
			dataDB.setInstitution(data.getInstitution());
			dataDB.setMark(data.getMark());
			dataDB.setStartDate(data.getStartDate());

			result = this.educationDataRepository.save(dataDB);

		}else{
			Assert.notNull(data.getDegree());
			Assert.notNull(data.getInstitution());
			Assert.notNull(data.getStartDate());

			result = this.educationDataRepository.save(data);
			
			currentCurricula = this.curriculaService.findOne(curriculaId);
			
			currentCurricula.getEducationData().add(data);
		}


		return result;

	}

	//Delete
	public void delete(EducationData data){
		Hacker principal;
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		EducationData db = new EducationData();
		
		db = this.educationDataRepository.findOne(data.getId());
		
		principal = (Hacker) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		currentCurricula = this.curriculaService.getCurriculaByEducationData(data.getId());

		Assert.isTrue(currentCurricula.getHacker().getId() == principal.getId());
		Assert.isTrue(principalCurriculas.contains(currentCurricula));
		Assert.isTrue(currentCurricula.getEducationData().contains(db));
		
		currentCurricula.getEducationData().remove(db);
		
		this.educationDataRepository.delete(db);
	}

	//Finds
	public EducationData findOne(int dataId){
		EducationData result = this.educationDataRepository.findOne(dataId);

		return result;
	}

	public Collection<EducationData> findAll(){
		Collection<EducationData>result = this.educationDataRepository.findAll();

		return result;
	}
	

	
	
	
	public EducationData createCopy(){
		Actor principal;
		EducationData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

		result = new EducationData();

		return result;
	}
	
	public EducationData saveCopy(EducationData data){
		Actor principal;
		EducationData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

		Assert.notNull(data.getDegree());
		Assert.notNull(data.getInstitution());
		Assert.notNull(data.getStartDate());

		result = this.educationDataRepository.save(data);
		Assert.notNull(result);

		return result;
		
	}
	
	public void flush(){
		this.educationDataRepository.flush();
	}

	
	public void deleteEDHacker(EducationData ed){
		this.educationDataRepository.delete(ed);
		
	}
	

}
