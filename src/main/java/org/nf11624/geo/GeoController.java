package org.nf11624.geo;

import java.math.BigDecimal;

import org.nf11624.geo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeoController {
	
	@Autowired
	private GeoService geoService;
	
	@RequestMapping(path = "/")
	public String findStatePointIsIn(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude) {
	
		
		return lookupState(latitude, longitude);
	}
	
	private String lookupState(BigDecimal latitude, BigDecimal longitude) {
		return geoService.lookupState(latitude, longitude);
	}
	
}
