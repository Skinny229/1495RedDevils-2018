package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRobotDrive extends Command {

	private boolean isFin = false;
	int stage = 0;

	public DriveRobotDrive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.elevator);
		requires(Robot.arm);
		requires(Robot.intake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		isFin = false;
		stage = 0;
		Robot.arm.solenoid.set(Value.kReverse);
		// If we are not in the side start raising the elevator to drop the cube
		// independently
		if (Robot.posStart == 1 || Robot.posStart == 2)
			Robot.elevator.clearSwitchFence(0);
		/*
		 * Verifies all Data needed to run this autonomous is retreived and good to go
		 */
		if (Robot.gameData.length() == 0 || Robot.sideStart == ' ' || Robot.posStart == -1) {
			isFin = true;
			DriverStation.reportError("Warning! Data needed to run Autonomous is non exixstent! Skipping auto....",
					false);
		}

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		switch (stage) {
		case 0:
			executeMP();
			stage = 1;
			break;
		case 1: {
			switch (Robot.posStart) {
			case 2: { // When we are in the middle
				Robot.rodMP.reset(null /* Just a lil bit ****/);
				Robot.rodMP.startMotionProfile();
				Robot.rodMP.control();
				double angleTurning = 90; // Degrees
				if (Robot.gameData.charAt(0) == 'L')
					angleTurning *= -1;
				turnXDegress(angleTurning);// This will execute till done no need for timer
				executeMP();
				angleTurning *= -1;
				turnXDegress(angleTurning);
				driveTillStopped();
				dropCube();
				isFin = true;
			}
				break;

			case 1: { // If right in front of the switch.....
				if (Robot.gameData.charAt(0) == Robot.sideStart) { // And right in front of our side...
					driveTillStopped();
					dropCube();
				}
				// Otherwise just wait till teleop
				isFin = true;
				break;
			}
			case 0: {
				boolean goingToScale;
				double[][] distToTravel;
				int travelinTo;
				if (Robot.gameData.charAt(0) == Robot.sideStart) {
					distToTravel = null; /* To the Switch */
					goingToScale = false;
					travelinTo = 0;
					Robot.elevator.clearSwitchFence(0);
				} else if (Robot.gameData.charAt(1) == Robot.sideStart) {
					distToTravel = null; /* To the Scale */
					goingToScale = true;
					travelinTo = 1;
				} else {
					isFin = true;
					return;
				}

				Robot.rodMP.reset(distToTravel);
				Robot.rodMP.startMotionProfile();
				Robot.rodMP.control();
				executeMP();
				double angleTurning = 90; // Degrees
				if (Robot.gameData.charAt(travelinTo) == 'L')
					angleTurning *= -1;
				turnXDegress(angleTurning);

				if (goingToScale) {
					dropCube(1.0); // Full Speed ahead!
				} else {
					driveTillStopped();
					dropCube();
				}

				isFin = true;

			}
				break;

			}

			isFin = true;
			stage = -1; // If we finished this stage we have finished auton this is to prevent running
						// anything else
		}
			break;
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

	private void executeMP() {
		while (Robot.rodMP.isMPRunning) {
			Robot.rodMP.control();
			setTalonMPMode();
		}
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

		Robot.roboDrive.arcadeDrive(RobotMap.fowardRateAuto, 0);
		Timer.delay(.3);
		do {
			Robot.roboDrive.arcadeDrive(RobotMap.fowardRateAuto, 0);

		} while (Robot.leftDriveMotor.getRawEncoderVelocity() != 0);
		Robot.roboDrive.stopMotor();
	}

	private void dropCube(double speed) {
		Robot.intake.set(speed);
		Timer.delay(.3);
		Robot.intake.stop();
	}

	private void dropCube() {
		dropCube(.65);
	}

	private void stopEverything() {
		Robot.roboDrive.stopMotor();
		Robot.intake.stop();
		Robot.elevator.motor.stopMotor();
	}
}
