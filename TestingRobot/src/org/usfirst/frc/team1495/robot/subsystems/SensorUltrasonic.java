package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SensorUltrasonic extends Subsystem {

	private AnalogInput ultrasonic;

	public SensorUltrasonic(int port) {
		ultrasonic = new AnalogInput(port);
	}

	protected void initDefaultCommand() {
	}

	public double getDistanceMMRAW() {
		// Distances less than 300mm will read as 300mm
		return  ultrasonic.getVoltage() * 997;

	}
	public int getDistanceMMINT(){
		return (int) ultrasonic.getVoltage()* 997;
	}

}

