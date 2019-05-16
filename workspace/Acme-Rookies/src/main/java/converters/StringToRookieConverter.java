package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import repositories.RookieRepository;

import domain.Rookie;

@Component
@Transactional
public class StringToRookieConverter implements Converter<String, Rookie> {

	@Autowired
	RookieRepository	actorRepository;

	@Override
	public Rookie convert(final String text) {
		Rookie result;

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