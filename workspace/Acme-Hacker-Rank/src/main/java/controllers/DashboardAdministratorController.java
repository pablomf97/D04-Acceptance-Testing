package controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.FinderService;
import services.HackerService;
import services.PositionService;

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
	private HackerService hackerService;

		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display() {
			ModelAndView result;
			//LEVEL B
			Double AvgCurriculaPerHacker=this.finderService.AvgCurriculaPerHacker();
			Double ratioFinders=this.finderService.ratioFinders();
			Integer MaxCurriculaPerHacker=this.finderService.MaxCurriculaPerHacker();
			Double stdevCurriculaPerHacker =this.finderService.stdevCurriculaPerHacker();
			Double []statsFinder=this.finderService.StatsFinder();
			Integer MinCurriculaPerHacker =this.finderService.MinCurriculaPerHacker();
			
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
			 
			 Integer maxApplicationsPerHacker=this.applicationService.maxApplicationsPerHacker();
			 Integer minApplicationsPerHacker=this.applicationService.minApplicationsPerHacker();
			 Double avgApplicationsPerHacker=this.applicationService.avgApplicationsPerHacker();
			 Double sttdevApplicationsPerHacker=this.applicationService.sttdevApplicationsPerHacker();
			 
			 String hackerWithMoreApplications=this.hackerService.hackerWithMoreApplications();
			 
			result = new ModelAndView("administrator/statistics");
			
			result.addObject("hackerWithMoreApplications",hackerWithMoreApplications);
			result.addObject("sttdevApplicationsPerHacker",sttdevApplicationsPerHacker);
			result.addObject("avgApplicationsPerHacker",avgApplicationsPerHacker);
			result.addObject("minApplicationsPerHacker",minApplicationsPerHacker);
			result.addObject("maxApplicationsPerHacker",maxApplicationsPerHacker);
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
		
			
			
			result.addObject("AvgCurriculaPerHacker",AvgCurriculaPerHacker);
			result.addObject("ratioFinders",ratioFinders);
			result.addObject("statsFinder",statsFinder);
			result.addObject("MinCurriculaPerHacker",MinCurriculaPerHacker);
			result.addObject("MaxCurriculaPerHacker",MaxCurriculaPerHacker);
			result.addObject("stdevCurriculaPerHacker",stdevCurriculaPerHacker);
			result.addObject("requestURI", "statistics/administrator/display.do");
			return result;
		}
}
