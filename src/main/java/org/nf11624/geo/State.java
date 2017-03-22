package org.nf11624.geo;

import org.nf11624.geo.core.PolyPoint;
import org.nf11624.geo.core.Polygon;

public class State {

	private String state;
	private PolyPoint[] border;
	private Polygon borderGon;
	
	public Polygon getBorderGon() {
		return borderGon;
	}
	public String getState() {
		return state;
	}
	public PolyPoint[] getBorder() {
		return border;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setBorder(PolyPoint[] border) {
		this.border = border;
	}
	
	public void setBorderGon(Polygon borderGon) {
		this.borderGon = borderGon;
	}
	
	
}
