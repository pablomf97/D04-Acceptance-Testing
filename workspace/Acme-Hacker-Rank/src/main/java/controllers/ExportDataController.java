package controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.ActorService;
import services.ApplicationService;
import services.AuditService;
import services.CurriculaService;
import services.ItemService;
import services.PositionService;
import services.ProblemService;
import services.SponsorshipService;
import domain.Administrator;
import domain.Application;
import domain.Audit;
import domain.Auditor;
import domain.Company;
import domain.Curricula;
import domain.EducationData;
import domain.Rookie;
import domain.Item;
import domain.MiscellaneousData;
import domain.Position;
import domain.PositionData;
import domain.Problem;
import domain.Provider;
import domain.Sponsorship;

@Controller
public class ExportDataController extends AbstractController {

	@Autowired
	private ActorService actorService;
	@Autowired
	private AuditService auditService;
	@Autowired
	private SponsorshipService sponsorshipService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private CurriculaService curriculaService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private ProblemService problemService;

	@RequestMapping(value = "administrator/export.do", method = RequestMethod.GET)
	public @ResponseBody
	void downloadFileAdministrator(HttpServletResponse resp) {
		String downloadFileName = "dataUser";
		String res;

		Administrator actor = (Administrator) this.actorService
				.findByPrincipal();

		res = "Data of your user account:";
		res += "\r\n\r\n";
		res += "Name: " + actor.getName() + " \r\n" + "Surname: "
				+ actor.getSurname() + " \r\n" + "VAT:" + actor.getVAT()
				+ " \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: "
				+ actor.getEmail() + " \r\n" + "Phone Number: "
				+ actor.getPhoneNumber() + " \r\n" + "Address: "
				+ actor.getAddress() + " \r\n" + " \r\n" + "\r\n"
				+ "Credit Card:" + "\r\n" + "Holder:"
				+ actor.getCreditCard().getHolder() + "\r\n" + "Make:"
				+ actor.getCreditCard().getMake() + "\r\n" + "Number:"
				+ actor.getCreditCard().getNumber() + "\r\n"
				+ "Date expiration:"
				+ actor.getCreditCard().getExpirationMonth() + "/"
				+ actor.getCreditCard().getExpirationYear() + "\r\n" + "CVV:"
				+ actor.getCreditCard().getCVV();
		res += "\r\n\r\n";
		res += "----------------------------------------";
		String downloadStringContent = res; // implement this
		try {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("text/plain; charset=utf-8");
			resp.addHeader("Content-Disposition", "attachment; filename=\""
					+ downloadFileName + "\"");
			out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		} catch (IOException e) {
		}

	}

	@RequestMapping(value = "rookie/export.do", method = RequestMethod.GET)
	public @ResponseBody
	void downloadFileRookie(HttpServletResponse resp) {
		String downloadFileName = "dataUser";
		String res;

		Rookie actor = (Rookie) this.actorService.findByPrincipal();

		res = "Data of your user account:";
		res += "\r\n\r\n";
		res += "Name: " + actor.getName() + " \r\n" + "Surname: "
				+ actor.getSurname() + " \r\n" + "VAT:" + actor.getVAT()
				+ " \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: "
				+ actor.getEmail() + " \r\n" + "Phone Number: "
				+ actor.getPhoneNumber() + " \r\n" + "Address: "
				+ actor.getAddress() + " \r\n" + " \r\n" + "\r\n"
				+ "Credit Card:" + "\r\n" + "Holder:"
				+ actor.getCreditCard().getHolder() + "\r\n" +

				"Make:" + actor.getCreditCard().getMake() + "\r\n" + "Number:"
				+ actor.getCreditCard().getNumber() + "\r\n"
				+ "Date expiration:"
				+ actor.getCreditCard().getExpirationMonth() + "/"
				+ actor.getCreditCard().getExpirationYear() + "\r\n" + "CVV:"
				+ actor.getCreditCard().getCVV();
		res += "\r\n\r\n";
		res += "----------------------------------------";
		res += "\r\n\r\n";
		res += "Finder:";
		res += "\r\n";
		res += "Results in last search:" + actor.getFinder().getResults()
				+ "\r\n\r\n";

		res += "\r\n\r\n";
		res += "----------------------------------------";
		res += "\r\n\r\n";

		res += "Curriculas:";
		res += "\r\n\r\n";
		Collection<Curricula> cvs = this.curriculaService
				.findCurriculasByRookieId(actor.getId());

		for (Curricula cv : cvs) {
			res += "----------------------------------------";
			res += "\r\n\r\n";
			res += "Curricula: " + "\r\n\r\n";
			res += "PersonalData: " + "\r\n\r\n" + "GitHub profile: "
					+ cv.getPersonalData().getGithubProfile() + "\r\n\r\n"
					+ "linkedIn: " + cv.getPersonalData().getLinkedIn()
					+ "\r\n\r\n" + "fullName: "
					+ cv.getPersonalData().getFullName() + "\r\n\r\n"
					+ "statement: " + cv.getPersonalData().getStatement()
					+ "\r\n\r\n" + "phoneNumber: "
					+ cv.getPersonalData().getPhoneNumber();
			res += "\r\n\r\n";

			for (MiscellaneousData m : cv.getMiscellaneousData()) {
				res += "Miscellaneous Data: " + "\r\n\r\n";
				res += "Text: " + m.getText() + "\r\n\r\n";
				res += "attachments: " + m.getAttachements() + "\r\n\r\n";
				res += "-----------";
			}
			for (PositionData m : cv.getPositionData()) {
				res += "Position Data: " + "\r\n\r\n";
				res += "title: " + m.getTitle() + "\r\n\r\n";
				res += "Description: " + m.getDescription() + "\r\n\r\n";
				res += "Start date: " + m.getStartDate() + "\r\n\r\n";
				res += "End date: " + m.getEndDate() + "\r\n\r\n";
				res += "-----------";
			}
			for (EducationData m : cv.getEducationData()) {
				res += "Education Data: " + "\r\n\r\n";
				res += "Degree: " + m.getDegree() + "\r\n\r\n";
				res += "Mark: " + m.getInstitution() + "\r\n\r\n";
				res += "Start date: " + m.getMark() + "\r\n\r\n";
				res += "End date: " + m.getStartDate() + "\r\n\r\n";
				res += "Institucion: " + m.getEndDate() + "\r\n\r\n";
				res += "-----------";
			}
		}
		res += "\r\n\r\n";
		res += "----------------------------------------";
		res += "\r\n\r\n";

		res += "Applications:";
		res += "\r\n\r\n";
		Collection<Application> apps = this.applicationService
				.findApplicationsByRookieId(actor.getId());
		for (Application app : apps) {
			res += "Application: " + "\r\n\r\n";
			res += "Application moment: " + app.getApplicationMoment()
					+ "\r\n\r\n";
			res += "Explanation: " + app.getExplanation() + "\r\n\r\n";
			res += "LinkCode: " + app.getLinkCode() + "\r\n\r\n";
			res += "Submit moment: " + app.getSubmitMoment() + "\r\n\r\n";
			res += "Status: " + app.getStatus() + "\r\n\r\n";
			res += "Problem: " + app.getProblem().getTitle() + "\r\n\r\n";
			res += "Rookie: " + app.getRookie().getName() + "\r\n\r\n";
			res += "Position: " + app.getPosition().getTitle() + "\r\n\r\n";
			res += "-----------";

		}

		String downloadStringContent = res;
		try {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("text/plain; charset=utf-8");
			resp.addHeader("Content-Disposition", "attachment; filename=\""
					+ downloadFileName + "\"");
			out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		} catch (IOException e) {
		}

	}

	@RequestMapping(value = "company/export.do", method = RequestMethod.GET)
	public @ResponseBody
	void downloadFileCompany(HttpServletResponse resp) {
		String downloadFileName = "dataUser";
		String res;

		Company actor = (Company) this.actorService.findByPrincipal();

		res = "Data of your user account:";
		res += "\r\n\r\n";
		res += "Name: " + actor.getName() + " \r\n" + "Surname: "
				+ actor.getSurname() + " \r\n" + "VAT:" + actor.getVAT()
				+ " \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: "
				+ actor.getEmail() + " \r\n" + "Phone Number: "
				+ actor.getPhoneNumber() + " \r\n" + "Address: "
				+ actor.getAddress() + "\r\n" + "Commercial name:"
				+ actor.getCommercialName() + " \r\n" + " \r\n" + "\r\n"
				+ "Credit Card:" + "\r\n" + "Holder:"
				+ actor.getCreditCard().getHolder() + "\r\n" +

				"Make:" + actor.getCreditCard().getMake() + "\r\n" + "Number:"
				+ actor.getCreditCard().getNumber() + "\r\n"
				+ "Date expiration:"
				+ actor.getCreditCard().getExpirationMonth() + "/"
				+ actor.getCreditCard().getExpirationYear() + "\r\n" + "CVV:"
				+ actor.getCreditCard().getCVV();
		res += "\r\n\r\n";
		res += "----------------------------------------";
		res += "\r\n\r\n";

		res += "Positions: ";
		res += "\r\n\r\n";
		Collection<Position> pos = this.positionService.findByOwner(actor);
		for (Position p : pos) {
			res += "Position: ";
			res += "\r\n\r\n";
			res += "Title: " + p.getTitle() + "\r\n\r\n";
			res += "Description: " + p.getDescription() + "\r\n\r\n";
			res += "Deadline: " + p.getDeadline() + "\r\n\r\n";
			res += "Profile required: " + p.getProfileRequired() + "\r\n\r\n";
			res += "Technologies requires: " + p.getTechnologiesRequired()
					+ "\r\n\r\n";
			res += "Salary: " + p.getSalary() + "\r\n\r\n";
			res += "Ticker: " + p.getTicker() + "\r\n\r\n";
			res += "Skills required: " + p.getSkillsRequired() + "\r\n\r\n";
			res += "Company: " + p.getCompany().getCommercialName()
					+ "\r\n\r\n";
			res += "Is draft: " + p.getIsDraft() + "\r\n\r\n";
			res += "Is cancelled: " + p.getIsCancelled() + "\r\n\r\n";
			res += "-----------";
		}
		res += "\r\n\r\n";
		res += "----------------------------------------";
		res += "\r\n\r\n";

		res += "Problems: ";
		res += "\r\n\r\n";
		Collection<Problem> pros = this.problemService.findByOwner(actor);

		for (Problem p : pros) {
			res += "Problem: " + "\r\n\r\n";
			res += "Tittle: " + p.getTitle() + "\r\n\r\n";
			res += "Statement: " + p.getStatement() + "\r\n\r\n";
			res += "Optional hint: " + p.getOptionalHint() + "\r\n\r\n";
			res += "Attachments: " + p.getAttachments() + "\r\n\r\n";
			res += "Company: " + p.getCompany().getCommercialName()
					+ "\r\n\r\n";
			res += "Is draft: " + p.getIsDraft() + "\r\n\r\n";
			res += "-----------";
		}
		String downloadStringContent = res;

		try {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("text/plain; charset=utf-8");
			resp.addHeader("Content-Disposition", "attachment; filename=\""
					+ downloadFileName + "\"");
			out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		} catch (IOException e) {
		}

	}

	@RequestMapping(value = "auditor/export.do", method = RequestMethod.GET)
	public @ResponseBody
	void downloadFileAuditor(HttpServletResponse resp) {
		String downloadFileName = "dataUser";
		String res;

		Auditor actor = (Auditor) this.actorService.findByPrincipal();

		res = "Data of your user account:";
		res += "\r\n\r\n";
		res += "Name: " + actor.getName() + " \r\n" + "Surname: "
				+ actor.getSurname() + " \r\n" + "VAT:" + actor.getVAT()
				+ " \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: "
				+ actor.getEmail() + " \r\n" + "Phone Number: "
				+ actor.getPhoneNumber() + " \r\n" + "Address: "
				+ actor.getAddress() + " \r\n" + " \r\n" + "\r\n"
				+ "Credit Card:" + "\r\n" + "Holder:"
				+ actor.getCreditCard().getHolder() + "\r\n" + "Make:"
				+ actor.getCreditCard().getMake() + "\r\n" + "Number:"
				+ actor.getCreditCard().getNumber() + "\r\n"
				+ "Date expiration:"
				+ actor.getCreditCard().getExpirationMonth() + "/"
				+ actor.getCreditCard().getExpirationYear() + "\r\n" + "CVV:"
				+ actor.getCreditCard().getCVV();
		res += "\r\n\r\n";
		res += "----------------------------------------";
		res += "\r\n\r\n";
		res += "Audits:";
		res += "\r\n\r\n";
		for (Audit a : this.auditService.auditsPerAuditor(actor.getId())) {
			res += "Audit:" + " \r\n";
			res += " \r\n" + "Date: " + a.getMoment() + " \r\n" + "Score: "
					+ a.getScore() + " \r\n" + "Position: "
					+ a.getPosition().getTitle() + "Text:" + " \r\n" + "Text:"
					+ a.getText();
		}

		String downloadStringContent = res; // implement this
		try {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("text/plain; charset=utf-8");
			resp.addHeader("Content-Disposition", "attachment; filename=\""
					+ downloadFileName + "\"");
			out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		} catch (IOException e) {
		}

	}

	@RequestMapping(value = "provider/export.do", method = RequestMethod.GET)
	public @ResponseBody
	void downloadFileProvider(HttpServletResponse resp) {
		String downloadFileName = "dataUser";
		String res;

		Provider actor = (Provider) this.actorService.findByPrincipal();

		res = "Data of your user account:";
		res += "\r\n\r\n";
		res += "Name: " + actor.getName() + " \r\n" + "Surname: "
				+ actor.getSurname() + " \r\n" + "VAT:" + actor.getVAT()
				+ " \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: "
				+ actor.getEmail() + " \r\n" + "Phone Number: "
				+ actor.getPhoneNumber() + " \r\n" + "Address: "
				+ actor.getAddress() + " \r\n" + "Make: "
				+ actor.getProviderMake() + " \r\n" + "\r\n" + "Credit Card:"
				+ "\r\n" + "Holder:" + actor.getCreditCard().getHolder()
				+ "\r\n" + "Make:" + actor.getCreditCard().getMake() + "\r\n"
				+ "Number:" + actor.getCreditCard().getNumber() + "\r\n"
				+ "Date expiration:"
				+ actor.getCreditCard().getExpirationMonth() + "/"
				+ actor.getCreditCard().getExpirationYear() + "\r\n" + "CVV:"
				+ actor.getCreditCard().getCVV();
		res += "\r\n\r\n";
		res += "----------------------------------------";
		res += "\r\n\r\n";

		for (Item i : this.itemService.itemsPerProvider(actor.getId())) {

			res += "Item: " + " \r\n";
			res += "Name: " + i.getName() + " \r\n" + "Description: "
					+ i.getDescription() + " \r\n" + "Links: " + i.getLinks()
					+ " \r\n" + "Pictures: " + i.getPictures() + " \r\n"
					+ "Provider: " + i.getProvider().getName() + " \r\n";
		}
		res += "\r\n\r\n";
		res += "----------------------------------------";
		res += "\r\n\r\n";

		for (Sponsorship s : this.sponsorshipService
				.sponsorshipsPerProvider(actor.getId())) {
			res += "Sponsorship: " + " \r\n";
			res += "Banner: " + s.getBanner() + " \r\n" + "Target: "
					+ s.getTarget() + " \r\n" + "Credit card: "
					+ s.getCreditCard().getHolder() + " \r\n" + "Provider: "
					+ s.getProvider().getName() + " \r\n";
		}

		String downloadStringContent = res; // implement this

		try {
			OutputStream out = resp.getOutputStream();
			resp.setContentType("text/plain; charset=utf-8");
			resp.addHeader("Content-Disposition", "attachment; filename=\""
					+ downloadFileName + "\"");
			out.write(downloadStringContent.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		} catch (IOException e) {
		}

	}

}
