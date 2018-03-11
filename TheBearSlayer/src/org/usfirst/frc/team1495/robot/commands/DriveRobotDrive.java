package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;
import org.usfirst.frc.team1495.robot.subsystems.GeneratedMotionProfiles;

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
		// if (Robot.posStart == 1 || Robot.posStart == 2)
		// Robot.elevator.clearSwitchFence(0);
		/*
		 * Verifies all Data needed to run this autonomous is retreived and good
		 * to go
		 */
		if (Robot.gameData.length() == 0 || Robot.sideStart == ' ' || Robot.posStart == -1) {
			isFin = true;
			DriverStation.reportError("Warning! Data needed to run Autonomous is non exixstent! Skipping auto....",
					false);
		}
		Robot.rodMP.startMotionProfile();
		stageToPass = 1;
		
	}

	private int stageToPass = 1;

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		switch (stage) {
		case 0:
			System.out.println("Running MP?");
			Robot.rodMP.control();
			SetValueMotionProfile setOutput = Robot.rodMP.getSetValue();
			Robot.leftDriveMotor.set(ControlMode.MotionProfile, setOutput.value);
			Robot.leftDriveMotor2.set(ControlMode.Follower, RobotMap.kLeftDriveMotorID);
			Robot.rightDriveMotor.set(ControlMode.MotionProfile, setOutput.value);
			Robot.rightDriveMotor2.set(ControlMode.Follower, RobotMap.kRightDriveMotorID); 

			if (!Robot.rodMP.isMPRunning) {
				System.out.println("Abiamo Finito");
				stage = stageToPass;
			}

			break;
		case 1: {

			switch (Robot.posStart) {
			case 2: { // When we are in the middle
				Robot.rodMP.reset(GeneratedMotionProfiles.PointsDef);
				double angleTurning = 90; // Degrees
				if (Robot.gameData.charAt(0) == 'L')
					angleTurning *= -1;
				turnXDegress(angleTurning);
				stage = 0;
				stageToPass = 2;

			}
				break;

			case 1:
			case 3:
				boolean goingToScale;
				double[][] distToTravel;
				int travelinTo;
				if (Robot.gameData.charAt(0) == Robot.sideStart) {
					distToTravel = GeneratedMotionProfiles.PointsDef; /*
																		 * To the
																		 * Switch
																		 */
					goingToScale = false;
					travelinTo = 0;
				} else if (Robot.gameData.charAt(1) == Robot.sideStart) {
					distToTravel = GeneratedMotionProfiles.PointsDef; /*
																		 * To the
																		 * Scale
																		 */
					goingToScale = true;
					travelinTo = 1;
				} else {
					isFin = true;
					return;
				}

				Robot.rodMP.reset(distToTravel);
				Robot.rodMP.startMotionProfile();
				stage = 0;
				stageToPass = 2;
				break;

			}
		}
			break; // Breaking Stage 1
		case 2: {

		}

			break;// Breaking Stage 2
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
/*
	private class MPExecuter implements java.lang.Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// System.out.println("??");
			Robot.rodMP.control();
			setTalonMPMode();
		}

	}
	

	public static boolean isRunning;
	Notifier runner = new Notifier(new MPExecuter());

	private void executeMP() {
		System.out.println("Starting MP...");
		isRunning = true;
		Robot.rodMP.startMotionProfile();
		runner.startPeriodic(.020);
		while (isRunning) {
			isRunning = Robot.rodMP.isMPRunning;
			Timer.delay(.020);
		}
		runner.stop();
		System.out.println("We have finished MP");
	}
*/
	private void setTalonMPMode() {

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

	public static void stopEverything() {
		Robot.roboDrive.stopMotor();
		Robot.intake.stop();
		Robot.elevator.motor.stopMotor();
	}
}
