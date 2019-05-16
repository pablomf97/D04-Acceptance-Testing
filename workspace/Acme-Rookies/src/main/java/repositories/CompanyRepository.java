package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select c from Company c where c.userAccount.username = ?1")
	Company findByUsername(String username);
	
	@Query("select max(c.score),min(c.score),avg(c.score),stddev(c.score) from Company c")
	Double[] statsScoreCompanies();
	
	@Query("select c from Company c where c.score=( select max(c.score) from Company c)")
	Collection<Company> CompaniesHighestScores();
	
	
	@Query("select a.score from Audit a where a.position.company.id = ?1")
	Collection<Integer> getScoresAuditedByCompany(int companyId);
	
}
