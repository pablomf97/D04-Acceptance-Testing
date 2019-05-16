
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.rookie.id = ?1")
	Collection<Application> findApplicationsByRookieId(int rookieId);

	@Query("select a from Application a where a.rookie.id = ?1 and a.status != 'REJECTED'")
	Collection<Application> findApplicationsNotRejectedByRookieId(int rookieId);

	@Query("select a from Application a join a.position p where p.company.id = ?1 and a.status != 'PENDING'")
	Collection<Application> findApplicationsByCompanyId(int companyId);

	@Query("select avg(1.0*(select count(*) from Application a where a.rookie=h)) from Rookie h")
	Double avgApplicationsPerRookie();
	@Query("select max(1.0*(select count(*) from Application a where a.rookie=h)) from Rookie h")
	Integer maxApplicationsPerRookie();
	@Query("select min(1.0*(select count(*) from Application a where a.rookie=h)) from Rookie h")
	Integer minApplicationsPerRookie();
	@Query("select stddev(1.0*(select count(*) from Application a where a.rookie=h)) from Rookie h")
	Double stddevApplicationsPerRookie();

	@Query("select a from Application a where a.problem.id = ?1")
	Collection<Application> findByProblem(int id);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findByPosition(int id);

	@Query("select a from Application a where a.rookie.id = ?1")
	Collection<Application> findApplicationPerRookie(int rookieId);

	@Query("select a from Application a join a.position p where p.company.id = ?1 and a.status = 'PENDING'")
	Collection<Application> findApplicationsNotRejectedByCompanyId(int companyId);

	@Query("select a from Application a where a.copyCurricula.id=?1")
	Collection<Application> findApplicationByCurricula(int curriculaId);

}
