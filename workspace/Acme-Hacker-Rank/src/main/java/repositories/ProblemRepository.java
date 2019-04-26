
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("select p from Position pos join pos.problems p where pos.id = ?1")
	Collection<Problem> findProblemsByPositionId(int positionId);

	@Query("select p from Problem p where p.company.id = ?1")
	Collection<Problem> findByOwner(int id);

	@Query("select p.problems from Position p")
	Collection<Problem> findByPosition();

	@Query("select p from Problem p where p.company.id = ?1 and p.isDraft = false")
	Collection<Problem> findByOwnerFinal(int id);

}
