package org.nf11624.geo.core;

import java.math.BigDecimal;

public class PolyPoint {

	private BigDecimal x;
	private BigDecimal y;
	private BigDecimal[] asArray;
	
	public PolyPoint() {
		
	}
	
	public PolyPoint(BigDecimal x, BigDecimal y) {
		this.x = x;
		this.y = y;
	}
	public BigDecimal getX() {
		return x;
	}
	public BigDecimal getY() {
		return y;
	}
	
	public void setX(BigDecimal x) {
		this.x = x;
	}
	
	public void setY(BigDecimal y) {
		this.y = y;
	}
	
	public void setAsArray(BigDecimal[] asArray) {
		this.x = asArray[0];
		this.y = asArray[1];
	}
	
	public BigDecimal[] getAsArray() {
		return asArray;
	}
	
	public String toString() {
		return "{" + x + ", " + y + "}";
	}
	
	
}
