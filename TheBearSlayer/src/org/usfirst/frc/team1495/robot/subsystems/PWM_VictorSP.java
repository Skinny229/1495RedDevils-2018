package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;

public class PWM_VictorSP extends VictorSP {

	public PWM_VictorSP(int port, boolean safety) {
		super(port);
		setSafetyEnabled(safety);
	}

	public void updateSafety(boolean safety) {
		setSafetyEnabled(safety);
	}

	public void spin(double speed) {
		if (speed > 1)
			speed = 1;
		else if (speed < -1)
			speed = -1;
		setSpeed(speed);
	}

	public void stop() {
		stopMotor();
	}
}
