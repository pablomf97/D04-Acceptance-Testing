
package repositories;


import java.util.Collection;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	
	@Query("select a from Application a where a.hacker.id = ?1")
	Collection<Application> findApplicationsByHackerId(int hackerId);
	
	@Query("select a from Application a where a.hacker.id = ?1 and a.status != 'REJECTED'")
	Collection<Application> findApplicationsNotRejectedByHackerId(int hackerId);
	
	@Query("select a from Application a join a.position p where p.company.id = ?1 and a.status != 'PENDING'")
	Collection<Application> findApplicationsByCompanyId(int companyId);
	
	@Query("select avg(1.0*(select count(*) from Application a where a.hacker=h)) from Hacker h")
	Double avgApplicationsPerHacker();
	@Query("select max(1.0*(select count(*) from Application a where a.hacker=h)) from Hacker h")
	Integer maxApplicationsPerHacker();
	@Query("select min(1.0*(select count(*) from Application a where a.hacker=h)) from Hacker h")
	Integer minApplicationsPerHacker();
	@Query("select stddev(1.0*(select count(*) from Application a where a.hacker=h)) from Hacker h")
	Double stddevApplicationsPerHacker();

	@Query("select a from Application a where a.problem.id = ?1")
	Collection<Application> findByProblem(int id);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findByPosition(int id);
	
	@Query("select a from Application a where a.hacker.id = ?1")
	Collection<Application> findApplicationPerHacker(int hackerId);

}
