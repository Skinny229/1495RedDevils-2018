package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CANTalonSRX extends Subsystem implements SpeedController {

	WPI_TalonSRX speedController;
	
	public CANTalonSRX(int deviceNumber, boolean safety) {
		speedController = new WPI_TalonSRX(deviceNumber);
		speedController.setSafetyEnabled(safety);
		speedController.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	}

	public void stop() {
		stopMotor();
	}

	public void spin(double speed) {
		if (speed > 1)
			speed = 1;
		else if (speed < -1)
			speed = -1;
		speedController.set(speed);
	}

	public void updateSaftey(boolean safety) {
		speedController.setSafetyEnabled(safety);
	}

	public int getRawEncoderPosition() {
		return speedController.getSelectedSensorPosition(0);
	}

	public int getRawEncoderVelocity() {
		return speedController.getSelectedSensorVelocity(0);
	}

	public double getEncoderPosition() {
		return speedController.getSelectedSensorPosition(0) * RobotMap.ENCODER_CONVERSION_FACTOR;
	}

	public void initDefaultCommand() {
	}
	
	@Override
	public void disable() { speedController.disable(); }
	@Override
	public double get() { return speedController.get(); }
	@Override
	public boolean getInverted() { return speedController.getInverted(); }
	@Override
	public void set(double speed) { speedController.set(speed); }
	@Override
	public void setInverted(boolean isInverted) { speedController.setInverted(isInverted); }
	@Override
	public void stopMotor() { speedController.stopMotor(); }
	@Override
	public void pidWrite(double output) { speedController.pidWrite(output); }
}
