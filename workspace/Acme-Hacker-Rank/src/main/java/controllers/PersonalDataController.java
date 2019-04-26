package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.PersonalDataService;
import domain.Curricula;
import domain.PersonalData;

@Controller
@RequestMapping(value="personalData/hacker")
public class PersonalDataController extends AbstractController{
	//Services

	@Autowired
	private PersonalDataService personalDataService;

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private Validator validator;



	//Display

	@RequestMapping(value="/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int dataId,@RequestParam int curriculaId){
		ModelAndView result;
		PersonalData data;

		data = this.personalDataService.findOne(dataId);

		result = new ModelAndView("personalData/display");

		result.addObject("data", data);
		result.addObject("curriculaId", curriculaId);

		return result;


	}

	//Edition

	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int dataId, @RequestParam int curriculaId){
		ModelAndView result = null;
		PersonalData data;

		try{

			data = this.personalDataService.findOne(dataId);

			result = this.createEditModelAndView(data,curriculaId);

		}catch(Throwable oops){

		}

		return result;
	}

	@RequestMapping(value="/edit", method = RequestMethod.POST, params="save")
	public ModelAndView save(PersonalData data,int curriculaId, final BindingResult binding){
		ModelAndView result;
		
		this.validator.validate(data, binding);
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(data,curriculaId);
		}else{
			try{
				this.personalDataService.save(data,curriculaId);
				Curricula currentCurricula = this.curriculaService.findOne(curriculaId);

				result = new ModelAndView("redirect:display.do?dataId="+data.getId()+"&curriculaId="+ currentCurricula.getId());
			}catch(Throwable oops){
				result = this.createEditModelAndView(data,curriculaId, "md.commit.error");
			}
		}
		return result;

	}


	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int curriculaId){
		ModelAndView result = null;
		try{
			PersonalData data = this.personalDataService.create();
			result = this.createEditModelAndView(data,curriculaId);
		}catch(Throwable oops){
			System.out.println(oops.getMessage());
		}

		return result;

	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final PersonalData data,int curriculaId) {
		ModelAndView result;

		result = this.createEditModelAndView(data, curriculaId, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final PersonalData data, Integer curriculaId,final String messageError) {
		ModelAndView result;
		Curricula currentCurricula;

		result = new ModelAndView("personalData/edit");

		if(!(curriculaId==null)){
			currentCurricula = this.curriculaService.findOne(curriculaId);
			result.addObject("currentCurricula", currentCurricula);
		}




		result.addObject("personalData", data);
		result.addObject("messageError", messageError);

		return result;
	}


}
