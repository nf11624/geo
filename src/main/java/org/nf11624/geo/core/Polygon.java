package org.nf11624.geo.core;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Polygon {

	private List<PolyPoint> points;
	//	private List<PolyEdge> edges;
	private BoundingBox boundingBox;

	public void setPoints(List<PolyPoint> points) {
		this.points = points;
//		this.points.addAll(points.get(0));
		generateBoundingBox();
	}
	
	public void setPointsArray(PolyPoint[] points) {
		this.setPoints(Arrays.asList(points));
	}

	public List<PolyPoint> getPoints() {
		return points;
	}

	private void generateBoundingBox() {
		BigDecimal negative = new BigDecimal(-1);
		BigDecimal maxX = negative.multiply(BigDecimal.valueOf(Double.MAX_VALUE));
		BigDecimal maxY = negative.multiply(BigDecimal.valueOf(Double.MAX_VALUE));
		BigDecimal minX = BigDecimal.valueOf(Double.MAX_VALUE);
		BigDecimal minY = BigDecimal.valueOf(Double.MAX_VALUE);
		for (PolyPoint point : points) {
			if (point.getX().compareTo(maxX) > 0) {
				maxX = point.getX();
			}
			if (point.getX().compareTo(minX) < 0) {
				minX = point.getX();
			}
			if (point.getY().compareTo(maxY) > 0) {
				maxY = point.getY();
			}
			if (point.getY().compareTo(minY) < 0) {
				minY = point.getY();
			}
		}
		boundingBox = new BoundingBox(minX, maxX, minY, maxY);
	}

	public boolean isPointContained(PolyPoint point) {
		boolean insideBoundingBox = isPointInsideBoundingBox(point);
		int windingNumber = 0;
		if (insideBoundingBox) {
			for (int i = points.size() - 1; i > 0; i--) {
				PolyPoint v1 = points.get(i);
				PolyPoint v2 = points.get(i-1);
				if (rayFromPointIntersectsEdge(point, v1, v2)) {
					boolean pointIsLeft = (isLeft(point, v1, v2) == -1 ? true : false);
					if (v1.getY().compareTo(point.getY()) <= 0) {
						if (point.getY().compareTo(v2.getY()) < 0) {	
							if (pointIsLeft) {
								windingNumber++;
							}
						}
					}
					else {
						if (v2.getY().compareTo(point.getY()) <= 0) {
							if (!pointIsLeft) {
								windingNumber--;
							}
						}
					}
				}
			}
		}
		if (windingNumber == 0) {
			return false;
		}
		return true;
	}

	private boolean isPointInsideBoundingBox(PolyPoint point) {
		return boundingBox.isContained(point);
	}

	public boolean rayFromPointIntersectsEdge(PolyPoint point, PolyPoint v1, PolyPoint v2) {
		return ((point.getY().compareTo(v1.getY()) > 0 && point.getY().compareTo(v2.getY()) < 0) ||
				(point.getY().compareTo(v2.getY()) > 0 && point.getY().compareTo(v1.getY()) < 0)); 
//				&& (point.getX().compareTo(v1.getX()) <= 0 && point.getX().compareTo(v2.getX()) <= 0);
	}
	
	public int isLeft(PolyPoint point, PolyPoint v1, PolyPoint v2) {
		BigDecimal part1 = v2.getX().subtract(v1.getX());
		BigDecimal part2 = point.getY().subtract(v1.getY());
		BigDecimal part3 = point.getX().subtract(v1.getX());
		BigDecimal part4 = v2.getY().subtract(v1.getY());
		
		return (part1.multiply(part2).subtract((part3.multiply(part4)))).compareTo(BigDecimal.ZERO);
	}

	/**
	 * Determine whether a point is to the left or right of an edge defined by two vertices
	 * @param point
	 * @param v1
	 * @param v2
	 * @return -1 if point is to left, 1 if point is to right, 0 if point is on line
	 */
	public int pointLeftOrRightOfEdge(PolyPoint point, PolyPoint v1, PolyPoint v2) {

		BigDecimal negativeSlopeMultiplier = BigDecimal.ONE;
		boolean negativeSlope = false;
		if (v2.getX().subtract(v1.getX()).compareTo(BigDecimal.ZERO) == 0) {
			// No Slope, Vertical line. 
			return point.getX().compareTo(v1.getX());
		}
		BigDecimal slope = (v2.getY().subtract(v1.getY())).divide(v2.getX().subtract(v1.getX()));

		if (slope.compareTo(BigDecimal.ZERO) < 0) {
			negativeSlopeMultiplier = negativeSlopeMultiplier.multiply(new BigDecimal(-1));
			negativeSlope = true;
		}

		BigDecimal expVal = slope.multiply(point.getX().subtract(v1.getX())).add(v1.getY());
		if (negativeSlope) {
			return point.getY().compareTo(expVal);		
		}
		return expVal.compareTo(point.getY());
	}

	private static class BoundingBox {
		BigDecimal minX;
		BigDecimal minY;
		BigDecimal maxX;
		BigDecimal maxY;

		public BoundingBox(BigDecimal minX, BigDecimal maxX, BigDecimal minY, BigDecimal maxY) {
			this.minX = minX;
			this.maxX = maxX;
			this.minY = minY;
			this.maxY = maxY;
		}

		public BigDecimal getMinX() {
			return minX;
		}
		public BigDecimal getMinY() {
			return minY;
		}
		public BigDecimal getMaxX() {
			return maxX;
		}
		public BigDecimal getMaxY() {
			return maxY;
		}
		public void setMinX(BigDecimal minX) {
			this.minX = minX;
		}
		public void setMinY(BigDecimal minY) {
			this.minY = minY;
		}
		public void setMaxX(BigDecimal maxX) {
			this.maxX = maxX;
		}
		public void setMaxY(BigDecimal maxY) {
			this.maxY = maxY;
		}

		public boolean isContained(PolyPoint point) {
			boolean contained = ((minX.compareTo(point.getX()) <= 0) && (maxX.compareTo(point.getX()) >= 0) &&
					(minY.compareTo(point.getY()) <= 0) && (maxY.compareTo(point.getY()) >= 0));
			return contained;
		}

	}
}
