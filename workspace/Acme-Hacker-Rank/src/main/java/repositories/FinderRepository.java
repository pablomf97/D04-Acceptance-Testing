package repositories;

import java.util.Collection;
import java.util.Date;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Position;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Position p where p.salary >= ?1 and p.isDraft=0 and p.deadline <= ?2 and (p.ticker like %?3% or p.description like %?3% or p.title like %?3% or p.profileRequired like %?3% or p.technologiesRequired like %?3% or p.skillsRequired like %?3% )")
	Collection<Position> search(Double minimumSalary,Date maximumDeadline,String keyWord);
	
	@Query("select p from Position p where p.isDraft = false and (p.ticker like %?1% or p.description like %?1% or p.title like %?1% or p.profileRequired like %?1% or p.technologiesRequired like %?1% or p.skillsRequired like %?1% )")
	Collection<Position> searchAnon(String keyWord);
	
	@Query("select p from Position p where p.isDraft=0 ")
	Collection<Position> AllPositions();
	
	@Query("select max(h.finder.results.size), min(h.finder.results.size), avg(h.finder.results.size),sqrt(sum(h.finder.results.size* h.finder.results.size) / count(h.finder.results.size) -(avg(h.finder.results.size) * avg(h.finder.results.size))) from Hacker h")
	Double[] StatsFinder();

	@Query("select p from Position p where  ( p.deadline between ?1 and  ?1)and p.isDraft=0 ")
	Position searchDeadline(Date deadline);
	@Query("select (sum(case when m.results.size=0 then 1.0 else 0 end)/count(m)) from Finder m")
	Double RatioFindersEmpty();
	
	@Query("select max(1.0*(select count(*) from Curricula a where a.hacker=h)) from Hacker h")
	Integer MaxCurriculaPerHacker();
	@Query("select min(1.0*(select count(*) from Curricula a where a.hacker=h)) from Hacker h")
	Integer MinCurriculaPerHacker();
	@Query("select avg(1.0*(select count(*) from Curricula a where a.hacker=h)) from Hacker h")
	Double AvgCurriculaPerHacker();
	@Query("select stddev(1.0*(select count(*) from Curricula a where a.hacker=h)) from Hacker h")
	Double StddevCurriculaPerHacker();

	
}
