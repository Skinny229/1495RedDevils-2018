package org.usfirst.frc.team1495.robot.pathgen;

public class CubicSegment extends CurveSegment {

	public CubicSegment(Point s, Point c1, Point c2, Point e) {
		super(s, c1, c2, e);
	}

	public Point interpolate(double t) {
		return new Point(
				Math.pow(1 - t, 3) * start().x + 3 * Math.pow(1 - t, 2) * t * points[1].x
						+ 3 * (1 - t) * Math.pow(t, 2) * points[2].x + Math.pow(t, 3) * end().x,
				Math.pow(1 - t, 3) * start().y + 3 * Math.pow(1 - t, 2) * t * points[1].y
						+ 3 * (1 - t) * Math.pow(t, 2) * points[2].y + Math.pow(t, 3) * end().y);
	}
}
