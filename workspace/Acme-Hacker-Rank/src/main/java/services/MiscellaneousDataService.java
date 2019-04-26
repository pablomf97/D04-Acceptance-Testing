package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousDataRepository;
import domain.Actor;
import domain.Curricula;
import domain.Hacker;
import domain.MiscellaneousData;

@Transactional
@Service
public class MiscellaneousDataService {

	//Repository
	@Autowired
	private MiscellaneousDataRepository miscellaneousDataRepository;

	//Services
	@Autowired
	private ActorService actorService;

	@Autowired
	private CurriculaService curriculaService;

	//Create
	public MiscellaneousData create(){
		Hacker principal;
		MiscellaneousData result;
		Collection<Curricula> principalCurriculas;

		principal = (Hacker) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		//Checking creating a data in an own curricula
		for(Curricula c : principalCurriculas){
			Assert.isTrue(c.getHacker().getId() == principal.getId());
		}

		result = new MiscellaneousData();

		return result;
	}

	//Save
	public MiscellaneousData save(MiscellaneousData data,int curriculaId){
		Hacker principal;
		MiscellaneousData dataDB = new MiscellaneousData();
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula = null;
		MiscellaneousData result;

		principal = (Hacker) this.actorService.findByPrincipal();
		
		
		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());
		
		//Checking creating a data in a own curricula
		if(data.getId()!=0){
			currentCurricula = this.curriculaService.getCurriculaByMiscellaneousData(data.getId());	
			Assert.isTrue(principalCurriculas.contains(currentCurricula));
			Assert.isTrue(currentCurricula.getHacker().getId() == principal.getId());
			Assert.isTrue(currentCurricula.getMiscellaneousData().contains(data));
			
			dataDB = this.miscellaneousDataRepository.findOne(data.getId());

			dataDB.setText(data.getText());
			dataDB.setAttachements(data.getAttachements());


			result = this.miscellaneousDataRepository.save(dataDB);

		}else{
			Assert.notNull(data.getText(), "md.commit.error");
			Assert.notNull(data.getAttachements());

			result = this.miscellaneousDataRepository.save(data);
			currentCurricula = this.curriculaService.findOne(curriculaId);
			currentCurricula.getMiscellaneousData().add(data);
		}


		return result;

	}

	//Delete
	public void delete(MiscellaneousData data){
		Hacker principal;
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		MiscellaneousData db = new MiscellaneousData();
		
		db = this.miscellaneousDataRepository.findOne(data.getId());

		principal = (Hacker) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		currentCurricula = this.curriculaService.getCurriculaByMiscellaneousData(data.getId());

		Assert.isTrue(currentCurricula.getHacker().getId() == principal.getId());
		Assert.isTrue(principalCurriculas.contains(currentCurricula));
		Assert.isTrue(currentCurricula.getMiscellaneousData().contains(db));
		
		currentCurricula.getMiscellaneousData().remove(db);
		
		this.miscellaneousDataRepository.delete(db);
	}

	//Finds
	public MiscellaneousData findOne(int dataId){
		MiscellaneousData result = this.miscellaneousDataRepository.findOne(dataId);

		return result;
	}

	public Collection<MiscellaneousData> findAll(){
		Collection<MiscellaneousData>result = this.miscellaneousDataRepository.findAll();

		return result;
	}
	
	
	public MiscellaneousData createCopy(){
		Actor principal;
		MiscellaneousData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

		result = new MiscellaneousData();

		return result;
	}
	
	public MiscellaneousData saveCopy(MiscellaneousData data){
		Actor principal;
		MiscellaneousData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

		Assert.notNull(data.getText());

		result = this.miscellaneousDataRepository.save(data);
		Assert.notNull(result);

		return result;
	}

	public void flush(){
		this.miscellaneousDataRepository.flush();
	}
	
	public void deleteMiscHacker(Collection<MiscellaneousData> col){
		
		this.miscellaneousDataRepository.deleteInBatch(col);
		
	}
	
	
}
