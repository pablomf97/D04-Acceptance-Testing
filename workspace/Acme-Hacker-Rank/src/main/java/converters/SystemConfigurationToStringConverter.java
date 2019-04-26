package converters;

import org.springframework.core.convert.converter.Converter;


import domain.SystemConfiguration;

public class SystemConfigurationToStringConverter implements Converter<SystemConfiguration, String> {
	@Override
	public String convert(final SystemConfiguration actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());
		return result;
	}
}
