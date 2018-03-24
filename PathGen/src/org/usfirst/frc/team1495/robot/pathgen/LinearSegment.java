package org.usfirst.frc.team1495.robot.pathgen;

public class LinearSegment extends CurveSegment {
	public LinearSegment(Point s, Point e) {
		super(s, e);
	}

	public Point interpolate(double t) {
		return new Point((1 - t) * start().x + t * end().x, (1 - t) * start().y + t * end().y);
	}
}
