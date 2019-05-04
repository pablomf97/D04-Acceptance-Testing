package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Company;
import domain.Position;
import domain.Sponsorship;

import repositories.SponsorshipRepository;

@Transactional
@Service
public class SponsorshipService {
	
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	public void deleteSponsorshipPerCompany(Position p) {
		
			
			this.sponsorshipRepository.deleteInBatch(p.getSponsorships());
		
		
	}
	
	public Collection<Sponsorship> sponsorshipsPerProvider(int id ){
		return this.sponsorshipRepository.sponsorshipsPerProvider(id);
	}
	
	public void deleteSponsorshipsPerProvider(Collection<Sponsorship> col){
		
		for(Sponsorship s:col){
		//this.creditCardService.deleteCreditCardPerSponsorship(s.getCreditCard());
		for(Position p:this.positionService.findAll()){
			
			for(Sponsorship sp: p.getSponsorships()){
				if(s.getId()==sp.getId()){
					this.sponsorshipRepository.delete(sp);
				}
			}
			
		}
		
		}
		
		
		
		//this.sponsorshipRepository.deleteInBatch(col);
	}

}
