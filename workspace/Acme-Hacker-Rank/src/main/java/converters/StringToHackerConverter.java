package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import repositories.HackerRepository;

import domain.Hacker;

@Component
@Transactional
public class StringToHackerConverter implements Converter<String, Hacker> {

	@Autowired
	HackerRepository	actorRepository;

	@Override
	public Hacker convert(final String text) {
		Hacker result;

		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.actorRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}