package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import domain.Actor;
import domain.Curricula;
import domain.Hacker;
import domain.PositionData;

@Transactional
@Service
public class PositionDataService {

	//Repository
	@Autowired
	private PositionDataRepository positionDataRepository;

	//Services
	@Autowired
	private ActorService actorService;

	@Autowired
	private CurriculaService curriculaService;

	//Create
	public PositionData create(){
		Hacker principal;
		PositionData result;
		Collection<Curricula> principalCurriculas;

		principal = (Hacker) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		//Checking creating a data in an own curricula
		for(Curricula c : principalCurriculas){
			Assert.isTrue(c.getHacker().getId() == principal.getId());
		}

		result = new PositionData();

		return result;
	}

	//Save
	public PositionData save(PositionData data,int curriculaId){
		Hacker principal;
		PositionData dataDB = new PositionData();
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		PositionData result;

		principal = (Hacker) this.actorService.findByPrincipal();



		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		if(data.getId()!=0){

			currentCurricula = this.curriculaService.getCurriculaByPositionData(data.getId());

			Assert.isTrue(principalCurriculas.contains(currentCurricula));
			Assert.isTrue(currentCurricula.getHacker().getId() == principal.getId());
			Assert.isTrue(currentCurricula.getPositionData().contains(data));

			dataDB = this.positionDataRepository.findOne(data.getId());

			dataDB.setTitle(data.getTitle());
			dataDB.setDescription(data.getDescription());
			dataDB.setStartDate(data.getStartDate());
			dataDB.setEndDate(data.getEndDate());


			result = this.positionDataRepository.save(dataDB);

		}else{
			Assert.notNull(data.getTitle());
			Assert.notNull(data.getDescription());
			Assert.notNull(data.getStartDate());

			if(!(data.getEndDate()==null)){
				Assert.isTrue(data.getStartDate().before(data.getEndDate()));
			}
			result = this.positionDataRepository.save(data);

			currentCurricula = this.curriculaService.findOne(curriculaId);

			currentCurricula.getPositionData().add(data);
		}


		return result;

	}

	//Delete
	public void delete(PositionData data){
		Hacker principal;
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		PositionData db = new PositionData();

		db = this.positionDataRepository.findOne(data.getId());

		principal = (Hacker) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		currentCurricula = this.curriculaService.getCurriculaByPositionData(data.getId());

		Assert.isTrue(currentCurricula.getHacker().getId() == principal.getId());
		Assert.isTrue(principalCurriculas.contains(currentCurricula));
		Assert.isTrue(currentCurricula.getPositionData().contains(db));

		currentCurricula.getPositionData().remove(db);

		this.positionDataRepository.delete(db);
	}

	//Finds
	public PositionData findOne(int dataId){
		PositionData result = this.positionDataRepository.findOne(dataId);

		return result;
	}

	public Collection<PositionData> findAll(){
		Collection<PositionData>result = this.positionDataRepository.findAll();

		return result;
	}

	public void flush(){
		this.positionDataRepository.flush();
	}

	public void deletePosHacker(PositionData pd){
		this.positionDataRepository.delete(pd);
	}
	public PositionData createCopy(){
		Actor principal;
		PositionData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

		result = new PositionData();

		return result;
	}

	public PositionData saveCopy(PositionData data){
		Actor principal;
		PositionData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

		Assert.notNull(data.getTitle());
		Assert.notNull(data.getDescription());
		Assert.notNull(data.getStartDate());

		result = this.positionDataRepository.save(data);
		Assert.notNull(result);

		return result;

	}
}
