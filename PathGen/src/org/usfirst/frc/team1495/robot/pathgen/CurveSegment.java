package org.usfirst.frc.team1495.robot.pathgen;

public abstract class CurveSegment {
	public final Point[] points;
	public final int order;

	public CurveSegment(Point... ps) {
		points = ps;
		order = ps.length - 1;
	}

	public Point start() {
		return points[0];
	}

	public Point end() {
		return points[order];
	}

	public abstract Point interpolate(double t);

	public static boolean Continuity0(CurveSegment first, CurveSegment second) {
		return Point.equals(first.end(), second.start());
	}

	public static boolean Continuity1(CurveSegment first, CurveSegment second) {
		return Continuity0(first, second) && ((first.points[first.order - 1].y - first.end().y)
				/ (first.points[first.order - 1].y - first.end().y)) == ((second.points[second.order - 1].y - second.end().y)
						/ (second.points[second.order - 1].y - second.end().y));
	}
}
