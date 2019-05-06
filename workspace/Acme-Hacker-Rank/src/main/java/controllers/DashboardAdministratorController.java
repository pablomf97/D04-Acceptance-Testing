package controllers;



import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Company;

import services.ApplicationService;
import services.AuditService;
import services.CompanyService;
import services.FinderService;
import services.RookieService;
import services.PositionService;
import services.ProviderService;

@Controller
@RequestMapping(value = "statistics/administrator")
public class DashboardAdministratorController extends AbstractController{

	// Display
	@Autowired
	private FinderService finderService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private RookieService rookieService;
	@Autowired
	private AuditService auditService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ProviderService providerService;

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		//LEVEL B
		Double AvgCurriculaPerRookie=this.finderService.AvgCurriculaPerRookie();
		Double ratioFinders=this.finderService.ratioFinders();
		Integer MaxCurriculaPerRookie=this.finderService.MaxCurriculaPerRookie();
		Double stdevCurriculaPerRookie =this.finderService.stdevCurriculaPerRookie();
		Double []statsFinder=this.finderService.StatsFinder();
		Integer MinCurriculaPerRookie =this.finderService.MinCurriculaPerRookie();

		//LEVEL C
		Double minSalarayPositions=this.positionService.minSalarayPositions();
		Double maxSalaryPositions=this.positionService.maxSalaryPositions();
		Double AVGSalaryPositions=this.positionService.AVGSalaryPositions();
		Double STDDEVSalaryPositions=this.positionService.STDDEVSalaryPositions();
		String bestPositionSalary=this.positionService.bestPositionSalary();
		String worstPositionSalary=this.positionService.worstPositionSalary();
		String  companyWithMorePositions=this.positionService.companyWithMorePositions();
		Integer maxPositionPerCompany=this.positionService.maxPositionPerCompany();
		Integer minPositionPerCompany=this.positionService.minPositionPerCompany();
		Double avgPositionPerCompany=this.positionService.avgPositionPerCompany();
		Double sttdevPositionPerCompany=this.positionService.sttdevPositionPerCompany();

		Integer maxApplicationsPerRookie=this.applicationService.maxApplicationsPerRookie();
		Integer minApplicationsPerRookie=this.applicationService.minApplicationsPerRookie();
		Double avgApplicationsPerRookie=this.applicationService.avgApplicationsPerRookie();
		Double sttdevApplicationsPerRookie=this.applicationService.sttdevApplicationsPerRookie();

		String rookieWithMoreApplications=this.rookieService.rookieWithMoreApplications();

		//ACME ROOKIES
		//LEVEL C
		Double[] statsAuditPositions=this.auditService.statsAuditPositions();
		Double[] statsScoreCompanies=this.companyService.statsScoreCompanies();
		Collection<Company> CompaniesHighestScores=this.companyService.CompaniesHighestScores();
		Double[] statsItemsPerProvider=this.providerService.statsItemsPerProvider();
		Collection<String> top5ProvidersWithItems=this.providerService.top5ProvidersWithItems();
		Double[] statsSponsorshipsPerProvider=this.providerService.statsSponsorshipsPerProvider();
		Double[] statsSponsorshipsPerPosition=this.positionService.statsSponsorshipsPerPosition();
		Collection<String> Percentage10AVGSponsorshipPerProvider=this.providerService.Percentage10AVGSponsorshipPerProvider();
		Double avgSalaryPerPositionHighestScoreAudits=this.positionService.avgSalaryPerPositionHighestScoreAudits();
		
		result = new ModelAndView("administrator/statistics");
		
		result.addObject("avgSalaryPerPositionHighestScoreAudits",avgSalaryPerPositionHighestScoreAudits);
		result.addObject("Percentage10AVGSponsorshipPerProvider",Percentage10AVGSponsorshipPerProvider);
		result.addObject("statsSponsorshipsPerPosition",statsSponsorshipsPerPosition);
		result.addObject("statsSponsorshipsPerProvider",statsSponsorshipsPerProvider);
		result.addObject("top5ProvidersWithItems",top5ProvidersWithItems);
		result.addObject("statsItemsPerProvider",statsItemsPerProvider);
		result.addObject("statsAuditPositions",statsAuditPositions);
		result.addObject("statsScoreCompanies",statsScoreCompanies);
		result.addObject("CompaniesHighestScores",CompaniesHighestScores);

		result.addObject("rookieWithMoreApplications",rookieWithMoreApplications);
		result.addObject("sttdevApplicationsPerRookie",sttdevApplicationsPerRookie);
		result.addObject("avgApplicationsPerRookie",avgApplicationsPerRookie);
		result.addObject("minApplicationsPerRookie",minApplicationsPerRookie);
		result.addObject("maxApplicationsPerRookie",maxApplicationsPerRookie);
		result.addObject("sttdevPositionPerCompany",sttdevPositionPerCompany);
		result.addObject("avgPositionPerCompany",avgPositionPerCompany);
		result.addObject("minPositionPerCompany",minPositionPerCompany);
		result.addObject("maxPositionPerCompany",maxPositionPerCompany);
		result.addObject("companyWithMorePositions",companyWithMorePositions);
		result.addObject("worstPositionSalary",worstPositionSalary);
		result.addObject("bestPositionSalary",bestPositionSalary);
		result.addObject("STDDEVSalaryPositions",STDDEVSalaryPositions);
		result.addObject("AVGSalaryPositions",AVGSalaryPositions);
		result.addObject("maxSalaryPositions",maxSalaryPositions);
		result.addObject("minSalarayPositions",minSalarayPositions);



		result.addObject("AvgCurriculaPerRookie",AvgCurriculaPerRookie);
		result.addObject("ratioFinders",ratioFinders);
		result.addObject("statsFinder",statsFinder);
		result.addObject("MinCurriculaPerRookie",MinCurriculaPerRookie);
		result.addObject("MaxCurriculaPerRookie",MaxCurriculaPerRookie);
		result.addObject("stdevCurriculaPerRookie",stdevCurriculaPerRookie);
		result.addObject("requestURI", "statistics/administrator/display.do");
		return result;
	}
}
