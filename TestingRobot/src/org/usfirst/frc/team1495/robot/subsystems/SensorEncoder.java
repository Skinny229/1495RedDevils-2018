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
    
    public SensorEncoder() {
    	//needs to be defined
    	//set distance per pulse (pulses per revolution)
    }
    
    public void reset() {
    	encoder.reset();
    }
    
    public double getDistance() {
    	//scale?
    	return encoder.getDistance();
    }
}

