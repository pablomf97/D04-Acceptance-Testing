
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select max(c.score),min(c.score),avg(c.score),stddev(c.score) from Audit c")
	Double[] statsAuditPositions();

	@Query("select a from Audit a where a.position.id= ?1")
	Collection<Audit> auditsPerPosition(int id);

	@Query("select a from Audit a where a.auditor.id= ?1")
	Collection<Audit> auditsPerAuditor(int id);

}
