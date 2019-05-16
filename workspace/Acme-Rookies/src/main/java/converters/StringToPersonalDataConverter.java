package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import repositories.PersonalDataRepository;

import domain.PersonalData;

@Component
@Transactional
public class StringToPersonalDataConverter implements Converter<String, PersonalData> {

	@Autowired
	PersonalDataRepository	actorRepository;

	@Override
	public PersonalData convert(final String text) {
		PersonalData result;

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