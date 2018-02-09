package org.usfirst.frc.team1495.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SpeedControllerTalonSRX extends Subsystem {

	public WPI_TalonSRX speedController;

	// .configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
	
	public SpeedControllerTalonSRX(int port, boolean safety) {
		speedController = new WPI_TalonSRX(port);
		speedController.setSafetyEnabled(safety);
		//speedController.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	}

	public void stop() {
		speedController.stopMotor();
	}

	public void spin(double speed) {
		if (speed > 1)   speed = 1;
		else if (speed < -1)   speed = -1;
		speedController.set(speed);
	}

	public void updateSaftey(boolean safety) {
		speedController.setSafetyEnabled(safety);
	}

	public void initDefaultCommand() {
	}
}
