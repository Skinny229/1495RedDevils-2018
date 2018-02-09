package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class CANTalonSRXE extends WPI_TalonSRX {

    public CANTalonSRXE(int deviceNumber, boolean safety) {
		super(deviceNumber);
		setSafetyEnabled(safety);
		configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	}
    
	public void stop() {
		stopMotor();
	}

	public void spin(double speed) {
		if (speed > 1)   		speed = 1;
		else if (speed < -1)	speed = -1;
		set(speed);
	}

	public void updateSaftey(boolean safety) {
		setSafetyEnabled(safety);
	}
    
    public int GetRawEncoderPosition() {
    	return getSelectedSensorPosition(0);
    }
    
    public int GetRawEncoderVelocity() {
    	return getSelectedSensorVelocity(0);
    }
    
    public double GetEncoderPosition() {
    	return getSelectedSensorPosition(0) * RobotMap.ENCODER_CONVERSION_FACTOR;
    }
    
	public void initDefaultCommand() {
    }
}

