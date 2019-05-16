
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousDataRepository;
import domain.Actor;
import domain.Curricula;
import domain.Rookie;
import domain.MiscellaneousData;

@Transactional
@Service
public class MiscellaneousDataService {

	//Repository
	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	//Services
	@Autowired
	private ActorService				actorService;

	@Autowired
	private CurriculaService			curriculaService;


	//Create
	public MiscellaneousData create() {
		Rookie principal;
		MiscellaneousData result;

		principal = (Rookie) this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		result = new MiscellaneousData();

		return result;
	}

	//Save
	public MiscellaneousData save(final MiscellaneousData data, final int curriculaId) {
		Rookie principal;
		MiscellaneousData dataDB = new MiscellaneousData();
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula = null;
		MiscellaneousData result;

		principal = (Rookie) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByRookie(principal.getId());

		//Checking creating a data in a own curricula
		if (data.getId() != 0) {
			currentCurricula = this.curriculaService.findOne(curriculaId);
			
			Assert.isTrue(currentCurricula.getMiscellaneousData().contains(data));
			Assert.isTrue(principalCurriculas.contains(currentCurricula));
			Assert.isTrue(currentCurricula.getRookie().getId() == principal.getId());
			

			dataDB = this.miscellaneousDataRepository.findOne(data.getId());

			dataDB.setText(data.getText());
			dataDB.setAttachements(data.getAttachements());

			result = this.miscellaneousDataRepository.save(dataDB);

		} else {
			Assert.notNull(data.getText(), "md.commit.error");
			Assert.notNull(data.getAttachements());
			
			currentCurricula = this.curriculaService.findOne(curriculaId);
			
			Assert.isTrue(principalCurriculas.contains(currentCurricula));
			Assert.isTrue(currentCurricula.getRookie().getId() == principal.getId());
			

			result = this.miscellaneousDataRepository.save(data);
			
			currentCurricula.getMiscellaneousData().add(data);
		}

		return result;

	}

	//Delete
	public void delete(final MiscellaneousData data) {
		Rookie principal;
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		MiscellaneousData db = new MiscellaneousData();

		db = this.miscellaneousDataRepository.findOne(data.getId());

		principal = (Rookie) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByRookie(principal.getId());

		currentCurricula = this.curriculaService.getCurriculaByMiscellaneousData(data.getId());

		Assert.isTrue(currentCurricula.getRookie().getId() == principal.getId());
		Assert.isTrue(principalCurriculas.contains(currentCurricula));
		Assert.isTrue(currentCurricula.getMiscellaneousData().contains(db));

		currentCurricula.getMiscellaneousData().remove(db);

		this.miscellaneousDataRepository.delete(db);
	}

	//Finds
	public MiscellaneousData findOne(final int dataId) {
		final MiscellaneousData result = this.miscellaneousDataRepository.findOne(dataId);

		return result;
	}

	public Collection<MiscellaneousData> findAll() {
		final Collection<MiscellaneousData> result = this.miscellaneousDataRepository.findAll();

		return result;
	}

	public MiscellaneousData createCopy() {
		Actor principal;
		MiscellaneousData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		result = new MiscellaneousData();

		return result;
	}

	public MiscellaneousData saveCopy(final MiscellaneousData data) {
		Actor principal;
		MiscellaneousData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		Assert.notNull(data.getText());

		result = this.miscellaneousDataRepository.save(data);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.miscellaneousDataRepository.flush();
	}

	public void deleteMiscRookie(final Collection<MiscellaneousData> col) {

		this.miscellaneousDataRepository.deleteInBatch(col);

	}
	public void checkOwnerMiscellaneousData(final Integer id) {
		final Actor principal = this.actorService.findByPrincipal();
		final Curricula c = this.curriculaService.getCurriculaByMiscellaneousData(id);
		Assert.isTrue(c.getRookie().equals(principal));
	}

}
