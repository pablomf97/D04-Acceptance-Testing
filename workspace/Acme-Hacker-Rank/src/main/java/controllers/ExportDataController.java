package controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import domain.Administrator;
import domain.Audit;
import domain.Auditor;
import domain.Company;
import domain.Hacker;
import domain.Item;
import domain.Provider;
import domain.Sponsorship;

import services.ActorService;
import services.AuditService;
import services.ItemService;
import services.ProviderService;
import services.SponsorshipService;

@Controller
public class ExportDataController extends AbstractController{
	
	@Autowired
	private ActorService actorService;
	@Autowired
	private AuditService auditService;
	@Autowired
	private SponsorshipService sponsorshipService;
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping(value = "administrator/export.do", method = RequestMethod.GET)
	public @ResponseBody  void downloadFileAdministrator(HttpServletResponse resp) {
		String downloadFileName= "dataUser";
		String res;
		
		Administrator actor=(Administrator)this.actorService.findByPrincipal();
		
		res="Data of your user account:";
		res += "\r\n\r\n";
		res+= "Name: " + actor.getName()  + " \r\n" + "Surname: " + actor.getSurname()+" \r\n" +"VAT:"+actor.getVAT() +
				" \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: " + actor.getEmail() + " \r\n" + "Phone Number: "
	 			+ actor.getPhoneNumber() + " \r\n" + "Address: " + actor.getAddress() + " \r\n"+" \r\n"+"\r\n"
				+"Credit Card:"+"\r\n"+"Holder:"+actor.getCreditCard().getHolder()+"\r\n"+
	 			"Make:"+actor.getCreditCard().getMake()+"\r\n"+"Number:"+actor.getCreditCard().getNumber()
	 			+"\r\n"+"Date expiration:"+actor.getCreditCard().getExpirationMonth()+"/"+actor.getCreditCard().getExpirationYear()
	 			+"\r\n"+"CVV:"+actor.getCreditCard().getCVV();
		res += "\r\n\r\n";
 		res += "----------------------------------------";
 		String downloadStringContent= res; // implement this
		try {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("text/plain; charset=utf-8");
			resp.addHeader("Content-Disposition","attachment; filename=\"" + downloadFileName + "\"");
			out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	
 		
	
	}
	@RequestMapping(value = "hacker/export.do", method = RequestMethod.GET)
	public @ResponseBody  void downloadFileHacker(HttpServletResponse resp) {
		String downloadFileName= "dataUser";
		String res;
		
		Hacker actor=(Hacker)this.actorService.findByPrincipal();
		
		res="Data of your user account:";
		res += "\r\n\r\n";
		res+= "Name: " + actor.getName()  + " \r\n" + "Surname: " + actor.getSurname()+" \r\n" +"VAT:"+actor.getVAT() +
				" \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: " + actor.getEmail() + " \r\n" + "Phone Number: "
	 			+ actor.getPhoneNumber() + " \r\n" + "Address: " + actor.getAddress() + " \r\n"+" \r\n"+"\r\n"
				+"Credit Card:"+"\r\n"+"Holder:"+actor.getCreditCard().getHolder()+"\r\n"+
	 			"Make:"+actor.getCreditCard().getMake()+"\r\n"+"Number:"+actor.getCreditCard().getNumber()
	 			+"\r\n"+"Date expiration:"+actor.getCreditCard().getExpirationMonth()+"/"+actor.getCreditCard().getExpirationYear()
	 			+"\r\n"+"CVV:"+actor.getCreditCard().getCVV();
		res += "\r\n\r\n";
 		res += "----------------------------------------";
 		res += "\r\n\r\n";
 		res+="Finder:";
 		res += "\r\n";
 		res+="Results in last search:"+actor.getFinder().getResults()+"\r\n\r\n";
		
 		res += "\r\n\r\n";
 		res += "----------------------------------------";
 		res += "\r\n\r\n";
		
		
 		
 		
 		String downloadStringContent= res; // implement this
		try {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("text/plain; charset=utf-8");
			resp.addHeader("Content-Disposition","attachment; filename=\"" + downloadFileName + "\"");
			out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	
	}
	
	@RequestMapping(value = "company/export.do", method = RequestMethod.GET)
	public @ResponseBody  void downloadFileCompany(HttpServletResponse resp) {
		String downloadFileName= "dataUser";
		String res;
		
		Company actor=(Company)this.actorService.findByPrincipal();
		
		res="Data of your user account:";
		res += "\r\n\r\n";
		res+= "Name: " + actor.getName()  + " \r\n" + "Surname: " + actor.getSurname()+" \r\n" +"VAT:"+actor.getVAT() +
				" \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: " + actor.getEmail() + " \r\n" + "Phone Number: "
	 			+ actor.getPhoneNumber() + " \r\n" + "Address: " + actor.getAddress()+"\r\n"+"Commercial name:"+actor.getCommercialName() + " \r\n"+" \r\n"+"\r\n"
				+"Credit Card:"+"\r\n"+"Holder:"+actor.getCreditCard().getHolder()+"\r\n"+
	 			"Make:"+actor.getCreditCard().getMake()+"\r\n"+"Number:"+actor.getCreditCard().getNumber()
	 			+"\r\n"+"Date expiration:"+actor.getCreditCard().getExpirationMonth()+"/"+actor.getCreditCard().getExpirationYear()
	 			+"\r\n"+"CVV:"+actor.getCreditCard().getCVV();
		res += "\r\n\r\n";
 		res += "----------------------------------------";
 		res += "\r\n\r\n";
 		
 		
	
 		String downloadStringContent= res; // implement this
		try {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("text/plain; charset=utf-8");
			resp.addHeader("Content-Disposition","attachment; filename=\"" + downloadFileName + "\"");
			out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	
	}
	
	@RequestMapping(value = "auditor/export.do", method = RequestMethod.GET)
	public @ResponseBody  void downloadFileAuditor(HttpServletResponse resp) {
		String downloadFileName= "dataUser";
		String res;
		
		Auditor actor=(Auditor)this.actorService.findByPrincipal();
		
		res="Data of your user account:";
		res += "\r\n\r\n";
		res+= "Name: " + actor.getName()  + " \r\n" + "Surname: " + actor.getSurname()+" \r\n" +"VAT:"+actor.getVAT() +
				" \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: " + actor.getEmail() + " \r\n" + "Phone Number: "
	 			+ actor.getPhoneNumber() + " \r\n" + "Address: " + actor.getAddress()+ " \r\n"+" \r\n"+"\r\n"
				+"Credit Card:"+"\r\n"+"Holder:"+actor.getCreditCard().getHolder()+"\r\n"+
	 			"Make:"+actor.getCreditCard().getMake()+"\r\n"+"Number:"+actor.getCreditCard().getNumber()
	 			+"\r\n"+"Date expiration:"+actor.getCreditCard().getExpirationMonth()+"/"+actor.getCreditCard().getExpirationYear()
	 			+"\r\n"+"CVV:"+actor.getCreditCard().getCVV();
		res += "\r\n\r\n";
 		res += "----------------------------------------";
 		res += "\r\n\r\n";
 		res +="Audits:";
 		res += "\r\n\r\n";
 		for(Audit a : this.auditService.auditsPerAuditor(actor.getId())){
 			res +="Audit:"+" \r\n";
 			res+=" \r\n"+"Date: "+a.getMoment()+" \r\n"+"Score: "+a.getScore()+" \r\n"+"Position: "+a.getPosition().getTitle()+"Text:"+" \r\n"+"Text:"+a.getText();
 		}
 		
 		
 		
 		
	
 		String downloadStringContent= res; // implement this
		try {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("text/plain; charset=utf-8");
			resp.addHeader("Content-Disposition","attachment; filename=\"" + downloadFileName + "\"");
			out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	
	}
	@RequestMapping(value = "provider/export.do", method = RequestMethod.GET)
	public @ResponseBody  void downloadFileProvider(HttpServletResponse resp) {
		String downloadFileName= "dataUser";
		String res;
		
		Provider actor=(Provider)this.actorService.findByPrincipal();
		
		res="Data of your user account:";
		res += "\r\n\r\n";
		res+= "Name: " + actor.getName()  + " \r\n" + "Surname: " + actor.getSurname()+" \r\n" +"VAT:"+actor.getVAT() +
				" \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: " + actor.getEmail() + " \r\n" + "Phone Number: "
	 			+ actor.getPhoneNumber() + " \r\n" + "Address: " + actor.getAddress()+ " \r\n"+
				"Make: "+actor.getProviderMake()+" \r\n"+"\r\n"
				+"Credit Card:"+"\r\n"+"Holder:"+actor.getCreditCard().getHolder()+"\r\n"+
	 			"Make:"+actor.getCreditCard().getMake()+"\r\n"+"Number:"+actor.getCreditCard().getNumber()
	 			+"\r\n"+"Date expiration:"+actor.getCreditCard().getExpirationMonth()+"/"+actor.getCreditCard().getExpirationYear()
	 			+"\r\n"+"CVV:"+actor.getCreditCard().getCVV();
		res += "\r\n\r\n";
 		res += "----------------------------------------";
 		res += "\r\n\r\n";
 		
 		for(Item i:this.itemService.itemsPerProvider(actor.getId())){
 			
 			res+="Item: "+" \r\n";
 			res+="Name: "+i.getName()+" \r\n"+"Description: "+i.getDescription()+" \r\n"+"Links: "+i.getLinks()+" \r\n"+"Pictures: "+i.getPictures()+" \r\n"+"Provider: "+i.getProvider().getName()+" \r\n";
 		}
 		res += "\r\n\r\n";
 		res += "----------------------------------------";
 		res += "\r\n\r\n";
 		
 		for(Sponsorship s :this.sponsorshipService.sponsorshipsPerProvider(actor.getId()) ){
 			res+="Sponsorship: "+" \r\n";
 			res+="Banner: "+s.getBanner()+" \r\n"+"Target: "+s.getTarget()+" \r\n"+"Credit card: "+s.getCreditCard().getHolder()+" \r\n"+"Provider: "+s.getProvider().getName()+" \r\n";
 		}
	
 		String downloadStringContent= res; // implement this
		try {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("text/plain; charset=utf-8");
			resp.addHeader("Content-Disposition","attachment; filename=\"" + downloadFileName + "\"");
			out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	
	}
	
	
}
