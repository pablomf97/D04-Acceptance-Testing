package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select c from Provider c where c.userAccount.username = ?1")
	Provider findByUsername(String username);
	@Query("select max(1.0*(select count(*) from Item i where i.provider=p)),min(1.0*(select count(*) from Item i where i.provider=p)),avg(1.0*(select count(*) from Item i where i.provider=p)),stddev(1.0*(select count(*) from Item i where i.provider=p)) from Provider p")
	Double[] statsItemsPerProvider();

	@Query("select p.name  from Provider p  order by ((1.0*(select count(*) from Item i where i.provider=p))) desc ")
	Collection<String> top5ProvidersWithItems();
	
	@Query("select max(1.0*(select count(*) from Sponsorship s where s.provider=p)),min(1.0*(select count(*) from Sponsorship s where s.provider=p)),avg(1.0*(select count(*) from Sponsorship s where s.provider=p)),stddev(1.0*(select count(*) from Sponsorship s where s.provider=p)) from Provider p")
	Double[] statsSponsorshipsPerProvider();
	
	@Query("select p.name from Provider p where (select count(s) from Sponsorship s where s.provider.id = p.id) > 1.1*(select avg(1.0 * (select count(s) from Sponsorship s where s.provider.id = p.id)) from Provider p))")
	Collection<String> Percentage10AVGSponsorshipPerProvider();
	
	// select p from Position p where (select count(*) from Audit a where a.position = p)>1.0*( select max(1.0*(select avg(a.score) from Audit a where a.position = p)) from Position p); 
	
}
