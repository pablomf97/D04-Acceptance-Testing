package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rookie;

@Repository
public interface RookieRepository extends JpaRepository<Rookie, Integer> {

	
	@Query("select max(a.rookie.name) from Application a")
	String rookieWithMoreApplications();


	@Query("select h from Rookie h where h.userAccount.username = ?1")
	Rookie findByUsername(String username);


}
