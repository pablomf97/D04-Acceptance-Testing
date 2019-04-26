package services;



import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;


import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


import domain.Finder;
import domain.Hacker;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest{
	
	@Autowired
	private ActorService actorService;
	
	@Autowired 
	private FinderService finderService;
	
	
	/*
	 * Total coverage of all tests
	 * 
	 * 
	 * Coverage of the total project (%):
	 * 
	 * 
	 * Coverage of the total project (lines of codes):
	 * 
	 * ################################################################
	 * 
	 * Total coverage by exclusively executing this test class
	 * 
	 * 
	 * Coverage of the total project (%):5.8%
	 * 
	 * 
	 * Coverage of the total project (lines of codes):1405
	 */
	
	
	
	
	//C.U: 17.2
	@Test
	public void searchDriver(){
		Object testingData[][]={
				{"hacker1",3,"position",null,null,null,null},//positive
				{"hacker1",0,"rwer",null,null,null,null},//positive
				{null,3,"position",null,null,null,IllegalArgumentException.class},//negative: non authorize
				{"admin",3,"position",null,null,null,ClassCastException.class},//negative: non authorize 
				{"hacker1",1,null,null,100.0,null,null},//positive
				{"hacker2",3,null,null,null,null,null},//positive
				{"hacker1",3,null,null,null,new GregorianCalendar(2050, Calendar.JANUARY, 1)
				.getTime(),null}//positive
			
				
				
		};
		
		for (int i = 0; i < testingData.length; i++) {
			searchTemplate((String) testingData[i][0], 
					(int) testingData[i][1], (String) testingData[i][2],
					(Date) testingData[i][3],
					(Double) testingData[i][4],(Date) testingData[i][5],(Class<?>) testingData[i][6]);
		}
	}


	private void searchTemplate(String username,int results, String keyWord,
			Date deadline, Double minimumSalary, Date maximumDeadline, Class<?> expected) {
		Class<?> caught;
		
		caught=null;
		 
		try{
			authenticate(username);
			final Hacker principal = (Hacker)this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal,
					"HACKER"));
			
			Finder finder=principal.getFinder();
			finder.setDeadline(deadline);
			finder.setKeyWord(keyWord);
			finder.setMaximumDeadline(maximumDeadline);
			finder.setMinimumSalary(minimumSalary);
			this.finderService.save(finder);
			
			this.finderService.search(finder);
			
			Assert.isTrue(finder.getResults().size()==results);
			
			unauthenticate();
			
		}catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
		
	}
	
	@Test
	public void deleteCacheFinderDrive(){
		
		Date date = DateUtils.addHours(new Date(), -2);
		
		Object testingData[][]={
				
				{"hacker1", new Date(),null},//positive
				{"hacker1", date,IllegalArgumentException.class},//Negative:date past
			
		};
		
		for (int i = 0; i < testingData.length; i++) {
			deleteCacheFinderTemplate((String) testingData[i][0], 
					(Date) testingData[i][1], (Class<?>) testingData[i][2]);
		}
		
	}


	private void deleteCacheFinderTemplate(String username, Date date,
			Class<?> expected) {
		
		Class<?> caught;
		
		caught=null;
		 
		try{
			authenticate(username);
			final Hacker principal = (Hacker)this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal,
					"HACKER"));
			Finder finder=principal.getFinder();
			finder.setSearchMoment(date);
			this.finderService.deleteExpiredFinder(finder);
			
			Assert.isTrue(finder.getSearchMoment()!=null);
		
			
			unauthenticate();
			
		}catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
		
	}
	
	
	
}
