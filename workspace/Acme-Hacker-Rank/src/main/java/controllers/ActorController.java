
package controllers;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import services.SystemConfigurationService;
import domain.Actor;

@Controller
public class ActorController extends AbstractController {

	@Autowired
	private ActorRepository				actorRepository;

	@Autowired
	private UserAccountRepository		userAccountRepository;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	//	public ActorForm createForm() {
	//
	//		final ActorForm res = new ActorForm();
	//
	//		return res;
	//	}

	/**
	 * Get all actors from db
	 * 
	 * @return Collection<Actor>
	 */
	public Collection<Actor> findAll() {
		Collection<Actor> result = null;
		result = this.actorRepository.findAll();
		return result;
	}

	/**
	 * Find an actor by id in the db
	 * 
	 * @param actorId
	 * @return actor
	 */
	public Actor findOne(final int actorId) {
		final Actor result = this.actorRepository.findOne(actorId);
		return result;
	}

	/**
	 * Find the logged actor
	 * 
	 * @return actor
	 */

	public Actor findByPrincipal() {
		Actor result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);
		return result;
	}

	/**
	 * Validate email pattern
	 * 
	 * @param email
	 * @return messageCode
	 */
	public String checkEmail(final String email, final String authority) {
		String result = "";
		final Pattern pattern = Pattern.compile("(^(([a-z]|[0-9]){1,}[@]{1}([a-z]|[0-9]){1,}([.]{1}([a-z]|[0-9]){1,}){1,})$)|(^((([a-z]|[0-9]){1,}[ ]{1}){1,}<(([a-z]|[0-9]){1,}[@]{1}([a-z]|[0-9]){1,}([.]{1}([a-z]|[0-9]){1,}){1,})>)$)");
		final Matcher matcher = pattern.matcher(email);
		if (authority.equals("ADMININISTRATOR") && matcher.matches()) {
			// TODO: faltar�a comprobar si se intenta insertar un admin y que
			// compruebe su correo para su caso
			final Pattern patternAdmin = Pattern.compile("(^((([a-z]|[0-9]){1,}[@])$)|(^(([a-z]|[0-9]){1,}[ ]{1}){1,}<(([a-z]|[0-9]){1,}[@]>))$)");
			final Matcher matcherAdmin = patternAdmin.matcher(email);
			result = matcherAdmin.matches() ? "" : "actor.email.error";
		} else
			result = matcher.matches() ? "" : "actor.email.error";
		return result;
	}

	/**
	 * Validate the phone code country
	 * 
	 * @param phoneNumber
	 * @return phoneNumber
	 */
	//	public String checkSetPhoneCC(String phoneNumber) {
	//		final Pattern p = Pattern.compile("([0-9]{4}){1}([0-9]{0,})");
	//		final Matcher m = p.matcher(phoneNumber);
	//		final boolean b = m.matches();
	//		if (b)
	//			phoneNumber = this.systemConfigurationService.findAll().iterator().next().getCountryCode() + " " + phoneNumber;
	//		return phoneNumber;
	//	}

	/**
	 * Check the authority of the actor
	 * 
	 * @param actor
	 * @param authority
	 * @return boolean
	 */
	public boolean checkAuthority(final Actor actor, final String authority) {
		Assert.notNull(actor);
		boolean result = false;
		if (actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals(authority))
			result = true;
		return result;
	}

	public Collection<Actor> findAllExceptPrincipal() {
		Collection<Actor> result;
		Actor principal;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		result.remove(principal);
		return result;
	}

	// Other business methods

	//	public void ban(Actor a) {
	//		Actor principal;
	//
	//		principal = this.findByPrincipal();
	//		Assert.isTrue(this.checkAuthority(principal, "ADMINISTRATOR"));
	//		Assert.notNull(a);
	//		Assert.isTrue(!a.getUserAccount().getIsBanned());
	//		if(a.getSpammer() != null && a.getScore() != null) {
	//			Assert.isTrue(a.getSpammer());
	//			Assert.isTrue(a.getScore() < -0.5);
	//		} else {
	//			Assert.notNull(a.getSpammer());
	//			Assert.notNull(a.getScore());
	//		}
	//		a.getUserAccount().setIsBanned(true);
	//		a = this.actorRepository.save(a);
	//	}

	//	public void unban(Actor a) {
	//		Actor principal;
	//
	//		principal = this.findByPrincipal();
	//		Assert.isTrue(this.checkAuthority(principal, "ADMINISTRATOR"));
	//		Assert.notNull(principal);
	//		Assert.notNull(a);
	//		Assert.isTrue(a.getUserAccount().getIsBanned());
	//
	//		a.getUserAccount().setIsBanned(false);
	//		a = this.actorRepository.save(a);
	//	}
	

	
	

}
