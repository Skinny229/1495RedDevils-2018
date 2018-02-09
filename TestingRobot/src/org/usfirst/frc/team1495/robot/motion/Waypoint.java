package org.usfirst.frc.team1495.robot.motion;

public class Waypoint {
	
	public final double time;
	public final double pos;//ition
	public final double vel;//ocity
	public final double acc;//eleration
	
	public Waypoint(double t, double p, double v, double a) {
		time = t;
		pos = p;
		vel = v;
		acc = a;
	}
}
