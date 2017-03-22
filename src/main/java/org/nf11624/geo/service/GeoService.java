package org.nf11624.geo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.nf11624.geo.State;
import org.nf11624.geo.core.PolyPoint;
import org.nf11624.geo.core.PolyPointDeserializer;
import org.nf11624.geo.core.Polygon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Service
public class GeoService {
	
	private static final Logger logger = Logger.getLogger(GeoService.class);
	
	@Value("${states.json.location}")
	private String jsonLocation;
	
	private List<State> states;
	
	// Lookup the state that the point is contained in
	public String lookupState(BigDecimal latitude, BigDecimal longitude) {
		PolyPoint point = new PolyPoint(longitude, latitude);
		for (State state : states) {
			if (state.getBorderGon().isPointContained(point)) {
				return state.getState() + "\n";
			}
		}
		return "Point not found in any state\n";
	}
	
	// Initialize the service data from provided json location (in application.properties)
	// This method is called from the bean post processor
	public List<State> generateStates() {
		logger.info("loading Data");
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(jsonLocation);
		
		SimpleModule module = new SimpleModule();
		module.addDeserializer(PolyPoint.class, new PolyPointDeserializer());

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(module);
		
		List<State> stateList = new LinkedList<State>();
		try {
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			line = reader.readLine();
			while (line != null) {
				State state = mapper.readValue(line, State.class);
				state.setBorderGon(new Polygon());
				state.getBorderGon().setPointsArray(state.getBorder());
				stateList.add(state);
				line = reader.readLine();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		return stateList;
	}
	
	// Set the list of states.  This method is used by bean post processor.
	public void setStates(List<State> stateList) {
		this.states = stateList;
	}
}
