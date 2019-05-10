package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Item;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ItemServiceTest extends AbstractTest{

	@Autowired
	private ItemService itemService;

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
	 * Coverage of the total project (%):
	 * 
	 * 
	 * Coverage of the total project (lines of codes):
	 */

	/*
	 * 
	 * During this test we are going to try:
	 * 
	 * Manage items, which includes listing, displaying,
	 * creating, updating, and deleting it. UC 10.1 Acme-Rookies
	 */

	/*
	 * ####################### TEST CREATE ITEM #######################
	 */

	@Test
	public void driverCreateItem(){
		Object testingData[][] = {
				{"provider1","test","test","https://www.eff.org/imggle-spy-eye.png",
					"https://www.eff.org/imggle-spy-eye.png",null},
					//Positive test case

					{"provider1"," ","test","https://www.eff.org/imggle-spy-eye.png",
						"https://www.eff.org/imggle-spy-eye.png",javax.validation.ConstraintViolationException.class},
						//Negative test case, name cannot be blank

						{"rookie1","test","test","https://www.eff.org/imggle-spy-eye.png",
							"https://www.eff.org/imggle-spy-eye.png",IllegalArgumentException.class},
							//Negative test case, wrong actor

							{null,"test","test","https://www.eff.org/imggle-spy-eye.png",
								"https://www.eff.org/imggle-spy-eye.png",IllegalArgumentException.class}

							//Negative test case, null actor
		};	

		for(int i=0;i < testingData.length; i++){
			this.templateCreateItem((String)testingData[i][0],
					(String)testingData[i][1],
					(String)testingData[i][2],
					(String)testingData[i][3],
					(String)testingData[i][4],
					(Class<?>)testingData[i][5]);
		}

	}

	protected void templateCreateItem(String username,String name,String description,
			String links, String pictures, Class<?>expected){

		Class<?>caught = null;

		try{

			this.authenticate(username);

			Item item = this.itemService.create();

			item.setName(name);
			item.setDescription(description);
			item.setLinks(links);
			item.setPictures(pictures);

			this.itemService.save(item);
			this.itemService.flush();

			this.unauthenticate();

		}catch(Throwable oops){

			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditItem(){
		Object testingData[][] = {
				{"provider1","test","test","https://www.eff.org/imggle-spy-eye.png",
					"https://www.eff.org/imggle-spy-eye.png","item1",null},
					//Positive test case

					{"provider1","test","test","https://www.eff.org/imggle-spy-eye.png",
						"https://www.eff.org/imggle-spy-eye.png","item2",IllegalArgumentException.class},
						//Negative test case, item2 doesn't correspond to provider1

						{"rookie1","test","test","https://www.eff.org/imggle-spy-eye.png",
							"https://www.eff.org/imggle-spy-eye.png","item4",IllegalArgumentException.class},
							//Negative test case, wrong actor

							{null,"test","test","https://www.eff.org/imggle-spy-eye.png",
								"https://www.eff.org/imggle-spy-eye.png","item3",IllegalArgumentException.class},

								//Negative test case, null actor
								{"provider1"," ","test","https://www.eff.org/imggle-spy-eye.png",
									"https://www.eff.org/imggle-spy-eye.png","item1",javax.validation.ConstraintViolationException.class}
								//Negative test case, name cannot be blank
		};	

		for(int i=0;i < testingData.length;i++){
			this.templateEditItem((String)testingData[i][0],
					(String)testingData[i][1],
					(String)testingData[i][2],
					(String)testingData[i][3],
					(String)testingData[i][4],
					(String)testingData[i][5],
					(Class<?>)testingData[i][6]);
		}
	}

	protected void templateEditItem(String username,String name,String description,
			String links, String pictures,String item, Class<?>expected){
		Class<?>caught = null;

		try{

			this.authenticate(username);

			Item aux = this.itemService.findOne(this.getEntityId(item));

			aux.setName(name);
			aux.setDescription(description);
			aux.setLinks(links);
			aux.setPictures(pictures);

			this.itemService.save(aux);
			this.itemService.flush();

			this.unauthenticate();

		}catch(Throwable oops){
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void driverDeleteItem(){
		Object testingData[][] = {
			
				{"provider1","item1",null},
				//Positive test case
				
				{"provider1","item2",IllegalArgumentException.class},
				//Negative test case, item2 doesn't correspond to provider1
				
				{"rookie1","item1",IllegalArgumentException.class},
				//Negative test case, wrong actor
				
				{null,"item1",IllegalArgumentException.class}
				//Negative test case, null actor
				
		};
		
		for(int i=0;i < testingData.length;i++){
			this.templateDeleteItem((String)testingData[i][0],
					(String)testingData[i][1],(Class<?>)testingData[i][2]);
		}
	}
	
	protected void templateDeleteItem(String username,String item,Class<?>expected){
		
		Class<?>caught = null;
		
		try{
			
			this.authenticate(username);
			
			Item aux = this.itemService.findOne(this.getEntityId(item));
			
			this.itemService.delete(aux);
			this.itemService.flush();
			
			this.unauthenticate();
			
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
	}
}
