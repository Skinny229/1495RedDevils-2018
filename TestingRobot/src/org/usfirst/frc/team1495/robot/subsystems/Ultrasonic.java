package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Ultrasonic extends Subsystem {

	private AnalogInput ultrasonic;

	public Ultrasonic(int port) {
		ultrasonic = new AnalogInput(port);
	}

	protected void initDefaultCommand() {
	}
}
