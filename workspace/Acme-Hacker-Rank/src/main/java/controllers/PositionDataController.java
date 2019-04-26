package controllers;

import java.util.Collection;

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
import services.PositionDataService;
import domain.Curricula;
import domain.PositionData;

@Controller
@RequestMapping(value="positionData/hacker")
public class PositionDataController extends AbstractController{

	//Services

	@Autowired
	private PositionDataService positionDataService;

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private Validator validator;



	//Listing

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam int curriculaId){
		ModelAndView result;
		Curricula currentCurricula;
		Collection<PositionData> positionData;


		currentCurricula = this.curriculaService.findOne(curriculaId);

		positionData = currentCurricula.getPositionData();

		result = new ModelAndView("positionData/list");

		result.addObject("currentCurricula", currentCurricula);
		result.addObject("positionData", positionData);

		return result;
	}

	//Display

	@RequestMapping(value="/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int dataId,@RequestParam int curriculaId){
		ModelAndView result;
		PositionData data;

		data = this.positionDataService.findOne(dataId);

		result = new ModelAndView("positionData/display");

		result.addObject("data", data);
		result.addObject("curriculaId", curriculaId);

		return result;


	}

	//Edition

	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int dataId, @RequestParam int curriculaId){
		ModelAndView result = null;
		PositionData data;

		try{

			data = this.positionDataService.findOne(dataId);

			result = this.createEditModelAndView(data,curriculaId);

		}catch(Throwable oops){

		}

		return result;
	}

	@RequestMapping(value="/edit", method = RequestMethod.POST, params="save")
	public ModelAndView save(PositionData data,int curriculaId, final BindingResult binding){
		ModelAndView result;
		
		this.validator.validate(data, binding);
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(data,curriculaId,"md.commit.error");
		}else{
			try{
				this.positionDataService.save(data,curriculaId);
				Curricula currentCurricula = this.curriculaService.findOne(curriculaId);

				result = new ModelAndView("redirect:list.do?curriculaId="+ currentCurricula.getId());
			}catch(Throwable oops){
				result = this.createEditModelAndView(data,curriculaId, "md.commit.error");
			}
		}
		return result;

	}

	@RequestMapping(value="/edit", method = RequestMethod.POST, params="delete")
	public ModelAndView delete(@Valid PositionData data,final BindingResult binding){
		ModelAndView result;

		if(binding.hasErrors()){
			result = this.createEditModelAndView(data,null,null);
		}else{
			try{
				Curricula currentCurricula = this.curriculaService.getCurriculaByPositionData(data.getId());
				this.positionDataService.delete(data);
				result = new ModelAndView("redirect:list.do?curriculaId="+ currentCurricula.getId());
			}catch(Throwable oops){
				result = this.createEditModelAndView(data,null, "md.commit.error");
			}
		}
		return result;

	}

	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int curriculaId){
		ModelAndView result = null;
		try{
			PositionData data = this.positionDataService.create();
			result = this.createEditModelAndView(data,curriculaId);
		}catch(Throwable oops){
			System.out.println(oops.getMessage());
		}

		return result;

	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final PositionData data,int curriculaId) {
		ModelAndView result;

		result = this.createEditModelAndView(data, curriculaId, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final PositionData data, Integer curriculaId,final String messageError) {
		ModelAndView result;
		Curricula currentCurricula;

		result = new ModelAndView("positionData/edit");

		if(!(curriculaId==null)){
			currentCurricula = this.curriculaService.findOne(curriculaId);
			result.addObject("currentCurricula", currentCurricula);
		}




		result.addObject("positionData", data);
		result.addObject("messageError", messageError);

		return result;
	}
}
