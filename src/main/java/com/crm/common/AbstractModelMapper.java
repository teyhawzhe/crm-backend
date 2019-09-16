package com.crm.common;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converters.Converter;

public abstract class AbstractModelMapper<S, D> extends AbstractConverter<S, D> implements Converter<S, D> {

	@Override
	public D convert(S source) {
		// TODO Auto-generated method stub
		if (null == source) {
			return null;
		}
		return doConvert(source);
	}

	public abstract D doConvert(final S source);
}
