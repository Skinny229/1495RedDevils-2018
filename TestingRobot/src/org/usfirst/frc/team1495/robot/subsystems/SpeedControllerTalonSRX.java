package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SpeedControllerTalonSRX extends Subsystem {

	public PWMTalonSRX speedController;

	public SpeedControllerTalonSRX(int port, boolean safety) {
		speedController = new PWMTalonSRX(port);
		speedController.setSafetyEnabled(safety);
	}

	public void stop() {
		speedController.stopMotor();
	}

	public void spin(double speed) {
		if (speed > 1)
			speed = 1;
		else if (speed < -1)
			speed = -1;
		speedController.setSpeed(speed);
	}

	public void updateSaftey(boolean safety) {
		speedController.setSafetyEnabled(safety);
	}

	public void initDefaultCommand() {
	}
}
