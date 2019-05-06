
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select MIN(p.salary) from Position p")
	Double minSalarayPositions();
	@Query("select MAX(p.salary) from Position p")
	Double maxSalaryPositions();

	@Query("select AVG(p.salary) from Position p")
	Double AVGSalaryPositions();

	@Query("select STDDEV(p.salary) from Position p") 
	Double STDDEVSalaryPositions();
	@Query(nativeQuery=true,value="select p.title from Position p where p.salary=( select max(p.salary) from Position p)ORDER BY p.id  DESC LIMIT 1")
	String bestPositionSalary();
	@Query(nativeQuery=true,value="select p.title from Position p where p.salary=( select min(p.salary) from Position p)ORDER BY p.id  DESC LIMIT 1")
	String worstPositionSalary();
	
	//@Query(nativeQuery=true,value="select max(p.company.commercialName) from Position p ORDER BY p.id  DESC LIMIT 1")
	@Query("select max(p.company.commercialName) from Position p ORDER BY p.id")
	String companyWithMorePositions();

	@Query("select max(1.0*(select count(p) from Position p where p.company.id = c.id)) from Company c")
	Integer maxPositionPerCompany();
	@Query("select min(1.0*(select count(p) from Position p where p.company.id = c.id)) from Company c")
	Integer minPositionPerCompany();
	@Query("select avg(1.0*(select count(p) from Position p where p.company.id = c.id)) from Company c")
	Double avgPositionPerCompany();
	@Query("select stddev(1.0*(select count(p) from Position p where p.company.id = c.id)) from Company c")
	Double stddevPositionPerCompany();

	@Query("select p from Position p where p.company.id = ?1")
	Collection<Position> findByOwner(int id);

	@Query("select p from Position p where p.ticker = ?1")
	Position findByTicker(String ticker);

	@Query("select p from Position p where p.isDraft = false")
	Collection<Position> findAllFinal();

	@Query("select p from Position p where p.company.id = ?1 and p.isDraft = false")
	Collection<Position> findByOwnerFinal(int id);

	@Query("select p from Position p where p.isDraft = false and p.isCancelled = false")
	Collection<Position> findAllToApply();

	@Query("select p from Application a join a.position p where a.rookie.id = ?1 and a.status != 'REJECTED'")
	Collection<Position> findAllAppliedPositionsByRookieId(int rookieId);

	@Query("select max(p.sponsorships.size), min(p.sponsorships.size), avg(p.sponsorships.size), stddev(p.sponsorships.size) from Position p")
	Double[] statsSponsorshipsPerPosition();
	
	@Query("select avg(p.salary) from Position p where (select avg(a.score) from Audit a where a.position = p) > (select avg(1.0*(select avg(a.score) from Audit a where a.position = p1)) from Position p1)")
	Double avgSalaryPerPositionHighestScoreAudits();
	
}
