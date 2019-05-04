package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Audit;
import domain.Company;
import domain.Position;

import repositories.AuditRepository;


@Transactional
@Service
public class AuditService {

	/* Working repository */

	@Autowired
	private AuditRepository auditRepository;
	@Autowired
	private PositionService positionService;

	/* Services */
	
	public Double[]statsAuditPositions(){
		
		return this.auditRepository.statsAuditPositions();
	}
	public Collection<Audit> auditsPerPosition(int id){
		return this.auditRepository.auditsPerPosition(id);
	}
	
	public void deleteAuditsPerCompany(Company c){
		final Collection<Position> positions = this.positionService.findByOwner(c);
		Collection<Audit> audits= new ArrayList<Audit>();
		for(Position p:positions){
			audits.addAll(this.auditsPerPosition(p.getId()));
			
		}
		this.auditRepository.deleteInBatch(audits);
	}
	public Collection<Audit> auditsPerAuditor(int id){
		return this.auditRepository.auditsPerAuditor(id);
	}
	public void deleteAuditsPerAuditor(Collection<Audit>cols){
		
		this.auditRepository.deleteInBatch(cols);
		
	}
}
