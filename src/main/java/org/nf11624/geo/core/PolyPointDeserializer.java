package org.nf11624.geo.core;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class PolyPointDeserializer extends StdDeserializer<PolyPoint> {

	public PolyPointDeserializer() {
		super(PolyPoint.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public PolyPoint deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		PolyPoint result = new PolyPoint();
		BigDecimal[] latAndLong = parser.getCodec().readValue(parser, BigDecimal[].class);
		result.setAsArray(latAndLong);
		return result;
	}
	
	
	
}
