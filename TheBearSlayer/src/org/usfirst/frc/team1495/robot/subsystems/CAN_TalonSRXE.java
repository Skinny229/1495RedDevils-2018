package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class CAN_TalonSRXE extends CAN_TalonSRX {

	public CAN_TalonSRXE(int deviceNumber, boolean safety) {
		super(deviceNumber, safety);
		configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	}

	public int getRawEncoderPosition() {
		return getSelectedSensorPosition(0);
	}

	public int getRawEncoderVelocity() {
		return getSelectedSensorVelocity(0);
	}

	public double getEncoderPosition() {
		return getSelectedSensorPosition(0) * RobotMap.kEncoderConversionFactor;
	}

	public void resetEncoder() {
		// HOW DO I DO THIS?
	}
}
