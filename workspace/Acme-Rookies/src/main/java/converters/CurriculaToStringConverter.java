package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import domain.Curricula;

@Component
@Transactional
public class CurriculaToStringConverter implements Converter<Curricula, String> {
	@Override
	public String convert(final Curricula actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
