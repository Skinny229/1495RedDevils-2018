package org.usfirst.frc.team1495.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class CAN_TalonSRX extends WPI_TalonSRX {

	public CAN_TalonSRX(int deviceNumber, boolean safety) {
		super(deviceNumber);
		setSafetyEnabled(safety);
	}

	public void spin(double speed) {
		if (speed > 1)
			speed = 1;
		else if (speed < -1)
			speed = -1;
		set(speed);
	}
}
