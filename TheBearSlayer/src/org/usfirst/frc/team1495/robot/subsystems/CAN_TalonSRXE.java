package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

public class CAN_TalonSRXE extends CAN_TalonSRX {

	public CAN_TalonSRXE(int deviceNumber, boolean safety) {
		super(deviceNumber, safety);
		configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	}
	
	public void setUpMotionProfile(){
		this.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		this.setSensorPhase(true); /* keep sensor and motor in phase */
		//rightDriveMotor.configNeutralDeadband(Constants.kNeutralDeadband, 0);

		this.config_kF(0, RobotMap.kf, RobotMap.timeoutEncoders);
		this.config_kP(0, RobotMap.kp, RobotMap.timeoutEncoders);
		this.config_kI(0, RobotMap.ki, RobotMap.timeoutEncoders);
		this.config_kD(0, RobotMap.kd, RobotMap.timeoutEncoders);

		/* Our profile uses 10ms timing */
		this.configMotionProfileTrajectoryPeriod(RobotMap.baseTrajTimeMS, RobotMap.timeoutEncoders); 
		/*
		 * status 10 provides the trajectory target for motion profile AND
		 * motion magic
		 */
		this.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
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
		setSelectedSensorPosition(0, 0, 0);
	}
}
