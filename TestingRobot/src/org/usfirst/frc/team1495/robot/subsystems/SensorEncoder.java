package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SensorEncoder extends Subsystem {

	private Encoder encoder;

	public void initDefaultCommand() {
	}

	public SensorEncoder(int radius) {
		//encoder = new Encoder();
	}

	public void reset() {
		encoder.reset();
	}

	public double getDistance() {
		// scale?
		
		return encoder.getDistance();
	}
}
