package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.subsystems.CAN_TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CalibrateEncoder extends Command {

	public CalibrateEncoder() {

	}

	boolean completed = false;
	int rightDistance;
	int leftDistance;

	protected void initialize(CAN_TalonSRX rightTalon, CAN_TalonSRX leftTalon) {
		rightDistance = Robot.rightDriveTalon.getRawEncoderPosition();
		leftDistance = Robot.leftDriveTalon.getRawEncoderPosition();
		Robot.roboDrive.arcadeDrive(-.5, 0);
		Timer.delay(1);
		Robot.roboDrive.stopMotor();
	}

	protected void execute() {
		if (Robot.rightDriveTalon.getRawEncoderVelocity() == 0 && Robot.leftDriveTalon.getRawEncoderVelocity() == 0) {
			System.out.println("Right Encoder Value: "
					+ Math.abs(Math.abs(Robot.rightDriveTalon.getRawEncoderPosition()) - Math.abs(rightDistance)));
			System.out.println("Left Encoder Value: "
					+ Math.abs(Math.abs(Robot.leftDriveTalon.getRawEncoderPosition()) - Math.abs(leftDistance)));
			completed = true;
		}
	}

	protected boolean isFinished() {
		return completed;
	}

	protected void end() {
		Robot.roboDrive.stopMotor();
	}

	protected void interrupted() {
		Robot.roboDrive.stopMotor();
	}
}