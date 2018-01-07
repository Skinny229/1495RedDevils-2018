package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SubsystemGyro extends Subsystem {

	private ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	public void initDefaultCommand() {
	}

	public void calibrate() {
		gyro.calibrate();
	}

	public void reset() {
		gyro.reset();
	}

	public double getAngleDegrees() {
		return (int) gyro.getAngle() % 360 < 0 ? ((int) gyro.getAngle() % 360) + 360
				: (int) gyro.getAngle() % 360;
	}

	public double getRawAngleDegrees() {
		return gyro.getAngle();
	}

	public ADXRS450_Gyro getSendable() {
		return gyro;
	}
}
