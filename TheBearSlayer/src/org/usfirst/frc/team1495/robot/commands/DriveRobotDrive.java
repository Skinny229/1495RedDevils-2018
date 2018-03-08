package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRobotDrive extends Command {

	private boolean isFin;
	int stage;

	public DriveRobotDrive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		isFin = false;
		stage = 0;
		Robot.arm.solenoid.set(Value.kReverse);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		switch (stage) {
		case 0: // Break Auto Line ...
			if (Robot.rodMP.isMPRunning) {
				Robot.rodMP.control();
				setTalonMPMode();

			} else {
				stage = 1;
			}
			break;
		case 1: {
			switch (Robot.posStart) {
			case 2:
				Robot.rodMP.reset(null /* Just a lil bit ****/);
				Robot.rodMP.startMotionProfile();
				Robot.rodMP.control();

				double angleTurning = 90; // Degrees
				if (Robot.gameData.charAt(0) == 'L')
					angleTurning *= -1;

				turnXDegress(angleTurning);// This will execute till done no need for timer
				while (Robot.rodMP.isMPRunning) {
					Robot.rodMP.control();
					setTalonMPMode();
				}
				angleTurning *= -1;
				turnXDegress(angleTurning);
				driveTillStopped();
				Robot.intake.set(1.0);
				Timer.delay(.3);
				isFin = true;
				break;

			case 1: // If right in front of the switch.....
				if (Robot.gameData.charAt(0) == Robot.sideStart) { // And right in front of our side...
					driveTillStopped();
					dropCube();
				}
				// Otherwise just wait till teleop
				isFin = true;
				break;

			}
			isFin = true;
			
		}break;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isFin;
	}

	// Called once after isFinished returns true
	protected void end() {
		stopEverything();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		stopEverything();
	}

	private void setTalonMPMode() {
		SetValueMotionProfile setOutput = Robot.rodMP.getSetValue();
		Robot.leftDriveMotor.set(ControlMode.MotionProfile, setOutput.value);
		Robot.leftDriveMotor2.set(ControlMode.Follower, RobotMap.kLeftDriveMotorID);
		Robot.rightDriveMotor.set(ControlMode.MotionProfile, setOutput.value);
		Robot.rightDriveMotor2.set(ControlMode.Follower, RobotMap.kRightDriveMotorID);
	}

	private void turnXDegress(double angleTarget) {
		double startingAngle = Robot.gyro.getRawAngleDegrees();
		double angleToTurn = startingAngle + angleTarget;
		if (startingAngle < angleToTurn) {
			while (Robot.gyro.getRawAngleDegrees() <= angleToTurn)
				Robot.roboDrive.arcadeDrive(0, RobotMap.turnRateXDeg);
		} else {
			while (Robot.gyro.getRawAngleDegrees() >= angleToTurn)
				Robot.roboDrive.arcadeDrive(0, -RobotMap.turnRateXDeg);
		}
		Robot.roboDrive.stopMotor();
	}

	private void driveTillStopped() {

		Robot.roboDrive.arcadeDrive(.4, 0);
		Timer.delay(.3);
		do {
			Robot.roboDrive.arcadeDrive(.4, 0);

		} while (Robot.leftDriveMotor.getRawEncoderVelocity() != 0);
		Robot.roboDrive.stopMotor();
	}

	private void dropCube() {
		Robot.intake.set(.65);
		Timer.delay(.3);
		Robot.intake.stop();

	}

	private void stopEverything() {
		Robot.roboDrive.stopMotor();
		Robot.intake.stop();
		Robot.elevator.motor.stopMotor();
	}
}
