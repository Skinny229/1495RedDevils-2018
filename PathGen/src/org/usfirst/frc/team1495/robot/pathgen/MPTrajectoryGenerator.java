package org.usfirst.frc.team1495.robot.pathgen;

public class MPTrajectoryGenerator {

	
	
	public void generatePoints(Path path, double totalTime, double durPerPoint){
		int pointsRequired =  (int) (totalTime / durPerPoint);
		int[] x = new int[pointsRequired],y = new int[pointsRequired];
		for(int i = 0; i < pointsRequired; i ++){
			
		}
		
	}
	
}
