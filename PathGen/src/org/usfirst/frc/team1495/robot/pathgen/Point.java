package org.usfirst.frc.team1495.robot.pathgen;

public class Point {
	public final double x;
	public final double y;
	
	public Point(double nx, double ny) {
		x = nx;
		y = ny;
	}
	
	public static boolean equals(Point first, Point second) {
		return first != null && second != null && first.x == second.x && first.y == second.y;
	}
}
