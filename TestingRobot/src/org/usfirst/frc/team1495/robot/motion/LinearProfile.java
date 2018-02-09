package org.usfirst.frc.team1495.robot.motion;

import java.util.ArrayList;

public class LinearProfile {
	ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
	Waypoint current;
	int id = 0;
	double nexttime;
	
	double maxvel;
	double maxacc;
	
	public LinearProfile(Waypoint... wp) {
		for (Waypoint w : wp) {
			waypoints.add(w);
		}
		current = waypoints.get(id);
		nexttime = waypoints.get(id+1).time;
	}
	
	public Waypoint interpolate(double time) {
		if (nexttime < time) {
			id++;
			current = waypoints.get(id);
			if(waypoints.get(id+1) != null) {
				nexttime = waypoints.get(id+1).time;
				}
			}
		return new Waypoint(time, current.pos + current.vel * time, current.vel + current.acc * time, current.acc);
	}
}
