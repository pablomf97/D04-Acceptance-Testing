package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {
	
	@Query("select c from Curricula c where c.hacker.id = ?1 and c.isCopy = 'false'")
	Collection<Curricula> findCurriculasByHackerId(int hackerId);


	@Query("select c from Curricula c where c.hacker.id=?1")
	Collection<Curricula> getCurriculasByHacker(int hackerId);
	
	@Query("select c from Curricula c join c.miscellaneousData m where m.id=?1")
	Curricula getCurriculaByMiscellaneousData(int dataId);
	
	@Query("select c from Curricula c join c.educationData e where e.id=?1")
	Curricula getCurriculaByEducationData(int dataId);
	
	@Query("select c from Curricula c join c.positionData p where p.id=?1")
	Curricula getCurriculaByPositionData(int dataId);
	
	@Query("select c from Curricula c where c.personalData.id=?1")
	Curricula getCurriculaByPersonalData(int dataId);


	
	@Query("select c from Curricula c where c.hacker.id= ?1")
	Collection<Curricula> findCVPerHacker(int hackerId);
	

}
