package org.usfirst.frc.team1495.robot.pathgen;

public class QuadraticSegment extends CurveSegment {

	public QuadraticSegment(Point s, Point c, Point e) {
		super(s, c, e);
	}

	public Point interpolate(double t) {
		return new Point(Math.pow(1 - t, 2) * start().x + 2 * (1 - t) * t * points[1].x + Math.pow(t, 2) * end().x,
				Math.pow(1 - t, 2) * start().y + 2 * (1 - t) * t * points[1].y + Math.pow(t, 2) * end().y);
	}
}
