package services;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Transactional
@Service
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;

	/**
	 * Get all actors from database
	 * 
	 * @return Collection<Actor>
	 */
	public Collection<Actor> findAll() {
		Collection<Actor> result = null;
		result = this.actorRepository.findAll();
		return result;
	}

	/**
	 * Find an actor by id in the database
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
	 * Check the authority of the actor
	 * 
	 * @param actor
	 * @param authority
	 * @return boolean
	 */
	public boolean checkAuthority(final Actor actor, final String authority) {
		Assert.notNull(actor);
		boolean result = false;
		if (actor.getUserAccount().getAuthorities().iterator().next()
				.getAuthority().equals(authority))
			result = true;
		return result;
	}

	/**
	 * Find all actors minus principal
	 * 
	 * @return Collection<Actor>
	 */
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

	/**
	 * Validate email pattern
	 * 
	 * @param email
	 * @return messageCode
	 */
	public Boolean checkEmail(final String email, final String authority) {
		Boolean result;
		String emailLowerCase = email.toLowerCase();

		final Pattern pattern = Pattern
				.compile("(^(([a-z]|[0-9]){1,}[@]{1}([a-z]|[0-9]){1,}([.]{1}([a-z]|[0-9]){1,}){1,})$)|(^((([a-z]|[0-9]){1,}[ ]{1}){1,}<(([a-z]|[0-9]){1,}[@]{1}([a-z]|[0-9]){1,}([.]{1}([a-z]|[0-9]){1,}){1,})>)$)");
		final Matcher matcher = pattern.matcher(emailLowerCase);
		if (authority.equals("ADMIN")) {
			final Pattern patternAdmin = Pattern
					.compile("(^((([a-z]|[0-9]){1,}[@])$)|(^(([a-z]|[0-9]){1,}[ ]{1}){1,}<(([a-z]|[0-9]){1,}[@]>))$)");
			final Matcher matcherAdmin = patternAdmin.matcher(emailLowerCase);
			result = matcherAdmin.matches() ? true : false;
		} else
			result = matcher.matches() ? true : false;
		return result;
	}

	public Boolean existsUsername(String username) {
		return !(this.actorRepository.existsUsername(username) != null);
	}
}
