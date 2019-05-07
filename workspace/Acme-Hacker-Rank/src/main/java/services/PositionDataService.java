
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import domain.Actor;
import domain.Curricula;
import domain.Rookie;
import domain.PositionData;

@Transactional
@Service
public class PositionDataService {

	//Repository
	@Autowired
	private PositionDataRepository	positionDataRepository;

	//Services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private CurriculaService		curriculaService;


	//Create
	public PositionData create() {
		Rookie principal;
		PositionData result;

		principal = (Rookie) this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		result = new PositionData();

		return result;
	}

	//Save
	public PositionData save(final PositionData data, final int curriculaId) {
		Rookie principal;
		PositionData dataDB = new PositionData();
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		PositionData result;

		principal = (Rookie) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByRookie(principal.getId());

		if (data.getId() != 0) {

			currentCurricula = this.curriculaService.getCurriculaByPositionData(data.getId());

			Assert.isTrue(principalCurriculas.contains(currentCurricula));
			Assert.isTrue(currentCurricula.getRookie().getId() == principal.getId());
			Assert.isTrue(currentCurricula.getPositionData().contains(data));

			dataDB = this.positionDataRepository.findOne(data.getId());

			dataDB.setTitle(data.getTitle());
			dataDB.setDescription(data.getDescription());
			dataDB.setStartDate(data.getStartDate());
			dataDB.setEndDate(data.getEndDate());

			result = this.positionDataRepository.save(dataDB);

		} else {
			Assert.notNull(data.getTitle());
			Assert.notNull(data.getDescription());
			Assert.notNull(data.getStartDate());

			if (!(data.getEndDate() == null))
				Assert.isTrue(data.getStartDate().before(data.getEndDate()),"Start date must be before then end date");
			result = this.positionDataRepository.save(data);

			currentCurricula = this.curriculaService.findOne(curriculaId);

			currentCurricula.getPositionData().add(data);
		}

		return result;

	}

	//Delete
	public void delete(final PositionData data) {
		Rookie principal;
		Collection<Curricula> principalCurriculas;
		Curricula currentCurricula;
		PositionData db = new PositionData();

		db = this.positionDataRepository.findOne(data.getId());

		principal = (Rookie) this.actorService.findByPrincipal();

		principalCurriculas = this.curriculaService.getCurriculasByRookie(principal.getId());

		currentCurricula = this.curriculaService.getCurriculaByPositionData(data.getId());

		Assert.isTrue(currentCurricula.getRookie().getId() == principal.getId());
		Assert.isTrue(principalCurriculas.contains(currentCurricula));
		Assert.isTrue(currentCurricula.getPositionData().contains(db));

		currentCurricula.getPositionData().remove(db);

		this.positionDataRepository.delete(db);
	}

	//Finds
	public PositionData findOne(final int dataId) {
		final PositionData result = this.positionDataRepository.findOne(dataId);

		return result;
	}

	public Collection<PositionData> findAll() {
		final Collection<PositionData> result = this.positionDataRepository.findAll();

		return result;
	}

	public void flush() {
		this.positionDataRepository.flush();
	}

	public void deletePosRookie(final PositionData pd) {
		this.positionDataRepository.delete(pd);
	}
	public PositionData createCopy() {
		Actor principal;
		PositionData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		result = new PositionData();

		return result;
	}

	public PositionData saveCopy(final PositionData data) {
		Actor principal;
		PositionData result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ROOKIE"));

		Assert.notNull(data.getTitle());
		Assert.notNull(data.getDescription());
		Assert.notNull(data.getStartDate());

		result = this.positionDataRepository.save(data);
		Assert.notNull(result);

		return result;

	}

	public void checkOwnerPositionData(final Integer id) {
		final Actor principal = this.actorService.findByPrincipal();
		final Curricula c = this.curriculaService.getCurriculaByPositionData(id);
		Assert.isTrue(c.getRookie().equals(principal));
	}
}
