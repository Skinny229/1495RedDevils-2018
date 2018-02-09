package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SCVictorSP extends Subsystem {

	private VictorSP speedController;

	public SCVictorSP(int port, boolean safety) {
		speedController = new VictorSP(port);
		speedController.setSafetyEnabled(safety);
	}

	public void updateSafety(boolean safety) {
		speedController.setSafetyEnabled(safety);
	}

	public void spin(double speed) {
		if (speed > 1)			speed = 1;
		else if (speed < -1)	speed = -1;
		speedController.setSpeed(speed);
	}

	public void stop() {
		speedController.stopMotor();
	}

	public void initDefaultCommand() {
	}
}

