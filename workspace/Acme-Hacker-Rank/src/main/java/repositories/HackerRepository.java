package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	
	@Query("select max(a.hacker.name) from Application a")
	String hackerWithMoreApplications();


	@Query("select h from Hacker h where h.userAccount.username = ?1")
	Hacker findByUsername(String username);


}
