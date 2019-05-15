package services;

import java.text.ParseException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.Actor;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;

@Transactional
@Service
public class SponsorshipService {
	
	// Managed repository ------------------------------------
	
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	// Supporting services -----------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private Validator validator;
	
	
	public Sponsorship create() {
		Actor principal;
		Sponsorship result;
		boolean expired;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "PROVIDER"),
				"not.allowed");
		
		try {
			expired = this.creditCardService.checkIfExpired(principal.getCreditCard().getExpirationMonth(), principal.getCreditCard().getExpirationYear());
			Assert.isTrue(!expired);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result = new Sponsorship();
		result.setCreditCard(principal.getCreditCard());
		result.setProvider((Provider) principal);

		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;
		result = this.sponsorshipRepository.findAll();

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship result;
		result = this.sponsorshipRepository.findOne(sponsorshipId);

		return result;
	}
	
	public Sponsorship save(final Sponsorship sponsorship) {
		Actor principal;
		Sponsorship result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "PROVIDER"), "not.allowed");
		
		Assert.notNull(sponsorship.getBanner());
		Assert.notNull(sponsorship.getCreditCard());
		Assert.notNull(sponsorship.getProvider());
		Assert.notNull(sponsorship.getTarget());
		
		Assert.isTrue((sponsorship.getProvider()).equals(principal), "not.allowed");
		Assert.isTrue(sponsorship.getCreditCard().getNumber().equals(principal.getCreditCard().getNumber()) &&
				sponsorship.getCreditCard().getCVV().equals(principal.getCreditCard().getCVV()), "wrong.creditCard");
		
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(sponsorship.getTarget());
		Assert.isTrue(m.find(), "wrong.target");

		if (sponsorship.getId() == 0) {
			int posId = Integer.parseInt(sponsorship.getTarget());
			Position pos = this.positionService.findOne(posId);
			Collection<Sponsorship> collSpo = pos.getSponsorships();
			
			result = sponsorship;
			result = this.sponsorshipRepository.save(result);
			
			collSpo.add(result);
			pos.setSponsorships(collSpo);
			
		} else {
			result = this.findOne(sponsorship.getId());
			result.setBanner(sponsorship.getBanner());
			
			result = this.sponsorshipRepository.save(result);
		}

		return result;
	}
	
	
	public void delete(final Sponsorship sponsorship) {
		Actor principal;
		Collection<Position> positions;

		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0, "wrong.id");

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "PROVIDER"),
				"not.allowed");

		Assert.isTrue(sponsorship.getProvider().equals(principal), "not.allowed");
		
		positions = this.positionService.findAll();
		
		for(Position pos : positions) {
			if(pos.getSponsorships().contains(sponsorship)) {
				Collection<Sponsorship> collSpo = pos.getSponsorships();
				collSpo.remove(sponsorship);
				pos.setSponsorships(collSpo);
			}
		}

		this.sponsorshipRepository.delete(sponsorship.getId());

	}
	
	
	// Other business methods -------------------------------
	
	public Sponsorship reconstruct(final Sponsorship sponsorship,
			final BindingResult binding) {
		Sponsorship result = null, aux = null;
		
		result = this.create();
		
		if (sponsorship.getId() == 0) {
			
			try {
				Assert.isTrue(!sponsorship.getTarget().isEmpty(),
						"target.needed");
			} catch (Exception e) {
				binding.rejectValue("target", "target.needed");
			}

			result.setTarget(sponsorship.getTarget());

		} else {
			aux = this.findOne(sponsorship.getId());
			result.setCreditCard(aux.getCreditCard());
			result.setProvider(aux.getProvider());
			result.setTarget(aux.getTarget());
		}
		
		result.setBanner(sponsorship.getBanner());


		this.validator.validate(result, binding);
		
		return result;
	}
	
	public void flush() {
		this.sponsorshipRepository.flush();
	}
	
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
