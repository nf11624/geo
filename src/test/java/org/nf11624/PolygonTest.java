package org.nf11624;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.nf11624.geo.core.PolyPoint;
import org.nf11624.geo.core.Polygon;

public class PolygonTest {


	
	@Test
	public void testEdgeLeftOrRight() {
		BigDecimal quarter = new BigDecimal(0.25);
		BigDecimal half = new BigDecimal(0.5);
		BigDecimal two = new BigDecimal(2);
		BigDecimal three = new BigDecimal(3);
		PolyPoint v1 = new PolyPoint(BigDecimal.ONE,BigDecimal.ONE);
		PolyPoint v2 = new PolyPoint(two, BigDecimal.ZERO);
		PolyPoint v3 = new PolyPoint(two, two);
		
		List<PolyPoint> vertices = new ArrayList<PolyPoint>(3);
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		
		PolyPoint p1 = new PolyPoint(quarter, half);
		PolyPoint p2 = new PolyPoint(three, BigDecimal.ONE);
		
		Polygon p = new Polygon();
		p.setPoints(vertices);
		
		assertEquals(1, p.pointLeftOrRightOfEdge(p2, v1, v2));
		assertEquals(-1, p.pointLeftOrRightOfEdge(p1, v1, v2));
		assertEquals(1, p.pointLeftOrRightOfEdge(p2, v1, v3));
		assertEquals(-1, p.pointLeftOrRightOfEdge(p1, v1, v3));
		
		
		
		
	}
	
	@Test
	public void testRayFromPointIntersectsEdge() {
		BigDecimal quarter = new BigDecimal(0.25);
		BigDecimal half = new BigDecimal(0.5);
		BigDecimal two = new BigDecimal(2);
		BigDecimal three = new BigDecimal(3);
		PolyPoint v0 = new PolyPoint(BigDecimal.ZERO, BigDecimal.ZERO);
		PolyPoint v1 = new PolyPoint(BigDecimal.ONE,BigDecimal.ONE);
		PolyPoint v2 = new PolyPoint(two, BigDecimal.ZERO);
		PolyPoint v3 = new PolyPoint(two, two);
		
		List<PolyPoint> vertices = new ArrayList<PolyPoint>(4);
		vertices.add(v0);
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		
		PolyPoint p1 = new PolyPoint(quarter, half);
		PolyPoint p2 = new PolyPoint(three, BigDecimal.ONE);
		PolyPoint p3 = new PolyPoint(three, half);
		
		Polygon p = new Polygon();
		p.setPoints(vertices);
		assertTrue(p.rayFromPointIntersectsEdge(p1, v0, v3));
		assertTrue(p.rayFromPointIntersectsEdge(p3, v0, v1));
	}
	
	@Test
	public void testTriangle() {
		BigDecimal half = new BigDecimal(0.5);
		
		PolyPoint v1 = new PolyPoint(BigDecimal.ZERO, BigDecimal.ONE);
		PolyPoint v2 = new PolyPoint(BigDecimal.ONE, BigDecimal.ZERO);
		PolyPoint v3 = new PolyPoint(new BigDecimal(-1), BigDecimal.ZERO);
		
		PolyPoint p1 = new PolyPoint(BigDecimal.ZERO, half);
		
		List<PolyPoint> vertices = new ArrayList<PolyPoint>(4);
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v1);
		
		Polygon p = new Polygon();
		p.setPoints(vertices);
		
		assertTrue(p.isPointContained(p1));
		
	}
	
	@Test
	public void testSquare() {
	BigDecimal half = new BigDecimal(0.5);
		
		BigDecimal minusOne = new BigDecimal(-1);
	
		PolyPoint v1 = new PolyPoint(minusOne, BigDecimal.ONE);
		PolyPoint v2 = new PolyPoint(BigDecimal.ONE, BigDecimal.ONE);
		PolyPoint v3 = new PolyPoint(BigDecimal.ONE, minusOne);
		PolyPoint v4 = new PolyPoint(minusOne, minusOne);
		
		PolyPoint p1 = new PolyPoint(half, half);
		PolyPoint p4 = new PolyPoint(new BigDecimal(4), new BigDecimal(3));
		
		List<PolyPoint> vertices = new ArrayList<PolyPoint>(4);
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
		vertices.add(v1);
		
		Polygon p = new Polygon();
		p.setPoints(vertices);
		
 		assertTrue(p.isPointContained(p1));
		assertFalse(p.isPointContained(p4));
		
	}

}
