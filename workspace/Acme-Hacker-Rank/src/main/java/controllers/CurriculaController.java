package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CurriculaService;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@Controller
@RequestMapping("/curricula/hacker")
public class CurriculaController extends AbstractController{

	//Services

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private ActorService actorService;


	//Listing

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(){
		final ModelAndView result;
		Collection<Curricula> curriculas;
		Hacker principal = (Hacker) this.actorService.findByPrincipal();

		curriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

		result = new ModelAndView("curricula/list");
		result.addObject("curriculas",curriculas);

		return result;
	}

	//Edition

	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		
		try{
			this.curriculaService.create();

		}catch(IllegalArgumentException oopsi){
			System.out.println(oopsi.getMessage());
		}
		
		result = new ModelAndView("redirect:list.do");

		return result;

	}


	//Display

	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam int curriculaId){
		Hacker principal;
		Curricula curricula;
		Collection<MiscellaneousData> miscellaneousData;
		Collection<EducationData> educationData;
		Collection<PositionData> positionData;
		PersonalData personalData;
		ModelAndView result;
		
		boolean emptyMiscellaneous,emptyEducation,emptyPosition;


		principal = (Hacker) this.actorService.findByPrincipal();

		curricula = this.curriculaService.findOne(curriculaId);
		Assert.notNull(curricula);

		Assert.isTrue(curricula.getHacker().getId() == principal.getId());

		miscellaneousData = curricula.getMiscellaneousData();
		educationData = curricula.getEducationData();
		positionData = curricula.getPositionData();
		personalData = curricula.getPersonalData();

		result = new ModelAndView("curricula/display");

		if(!(miscellaneousData.isEmpty())){
			emptyMiscellaneous = false;
			result.addObject("miscellanousData", miscellaneousData.iterator().next());
		}else{
			emptyMiscellaneous = true;
			result.addObject("miscellaneousData", miscellaneousData);
		}

		if(!(educationData.isEmpty())){
			emptyEducation = false;
			result.addObject("educationData", educationData.iterator().next());
			
		}else{
			emptyEducation = true;
			result.addObject("educationData", educationData);
		}

		if(!(positionData.isEmpty())){
			emptyPosition = false;
			result.addObject("positionData", positionData.iterator().next());
		}else{
			emptyPosition = true;
			result.addObject("positionData", positionData);
		}
		
		result.addObject("emptyMiscellaneous",emptyMiscellaneous);
		result.addObject("emptyEducation", emptyEducation);
		result.addObject("emptyPosition", emptyPosition);
		result.addObject("curricula", curricula);
		result.addObject("personalData", personalData);

		return result;

	}

	//Delete

	@RequestMapping(value="/display", method = RequestMethod.POST, params="delete")
	public ModelAndView delete(final Curricula curricula, BindingResult binding){
		ModelAndView result = null;
		
		Curricula db = this.curriculaService.findOne(curricula.getId());

		try{
			this.curriculaService.delete(db);
			result = new ModelAndView("redirect:list.do");
		}catch(Throwable oops){
			System.out.println(oops.getMessage());
		}
		return result;
	}


}
