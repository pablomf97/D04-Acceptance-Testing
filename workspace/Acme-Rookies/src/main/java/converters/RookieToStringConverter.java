package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import domain.Rookie;

@Component
@Transactional
public class RookieToStringConverter implements Converter<Rookie, String> {
	@Override
	public String convert(final Rookie actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
