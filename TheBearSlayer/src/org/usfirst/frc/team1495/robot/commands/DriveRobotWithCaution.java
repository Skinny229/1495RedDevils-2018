package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRobotWithCaution extends Command {

	public DriveRobotWithCaution() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.arm);
		requires(Robot.intake);
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("Autonomous Starting!");
		Robot.arm.solenoid.set(Value.kForward);

		stage = 0;
		isFin = false;
		angleTarget = 90;
		Robot.leftDriveMotor.resetEncoder();


		startEncStart = Robot.leftDriveMotor.getRawEncoderPosition();

		
			//Determing where we going
			System.out.println("Analyzing where to go....");
		
		Robot.gyro.reset();

		if (Robot.gameData == "" || Robot.posStart == ' ') {
			
			DriverStation.reportError("Data not valid! Verify data is good. Stopping Auto and just driving Straight", false);
			Robot.roboDrive.arcadeDrive(.65, 0);
			Timer.delay(3.0);
			Robot.roboDrive.stopMotor();
			isFin = true;
		}else {
			System.out.println("Initializing Finished ... Moving to control loop ... Entering Stage 0");
			}
	}

	private boolean isFin;
	private double angleTarget, angleToTurn;
	private int startEncStart, stage;
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (this.isCanceled()){
			isFin = true;
			return;
		}
		switch (stage) {
		case 0:
			if (Robot.leftDriveMotor.getRawEncoderPosition() < ((125.0 / (8.0 * Math.PI) * 4096) + startEncStart))
				Robot.roboDrive.arcadeDrive(.65, 0);
			else {
				Robot.roboDrive.stopMotor();
				System.out.println("Stage 0 Finished. Moving to Stage 1");
				stage = 1;
			}

			break;
		case 1:
			switch (Robot.posStart) {
			case 'R':
			case 'L':
				if (Robot.gameData.charAt(0) == Robot.posStart) {
					if (Robot.posStart == 'R') {
						angleTarget *= -1;
					}

					angleToTurn = angleTarget;
					Robot.elevator.motor.set(-1.0);
					Timer.delay(2.5);
					Robot.elevator.motor.stopMotor();
					if (Robot.gyro.getRawAngleDegrees() < angleToTurn) {
						stage = 5;
						System.out.println("We need to turn! Moving to Stage 5");
					}
					else {
						System.out.println("We need to turn! Moving to Stage 6");
						stage = 6;
					}
				}else{
					System.out.println("Switch is not on our side.... Stopping");
					isFin = true;
					return;
				}
				break;
			case 'M':
				System.out.println("Driving Straight");
				Robot.roboDrive.arcadeDrive(.65, 0);
				Timer.delay(4.0);
				Robot.roboDrive.stopMotor();
				isFin = true;
				break;
			default:
				isFin = true;
			}
			break;
		case 2:
			if (Robot.leftDriveMotor.getRawEncoderVelocity() > 0 && !this.isCanceled()) {
			} else {
				System.out.println("It seems we have crashed agaisnt something. Droppin Cube....");
				dropCube();
				isFin = true;
			}
			break;
		case 5:
			if (Robot.gyro.getRawAngleDegrees() <= angleToTurn && !this.isCanceled()) {
				Robot.roboDrive.arcadeDrive(0, RobotMap.kAutoTurnRate);
			} else {
				Robot.roboDrive.stopMotor();
				Robot.roboDrive.arcadeDrive(.5, 0);
				Timer.delay(.3);
				System.out.println("Turn Finished. Entering Stage 2");
				stage = 2;
			}
			break;
		case 6:
			if (Robot.gyro.getRawAngleDegrees() >= angleToTurn && !this.isCanceled()) {
				Robot.roboDrive.arcadeDrive(0, -RobotMap.kAutoTurnRate);
			} else {
				Robot.roboDrive.stopMotor();
				Robot.roboDrive.arcadeDrive(.5, 0);
				Timer.delay(.3);
				System.out.println("Turn Finished. Entering Stage 2");
				stage = 2;
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
		System.out.println("Auton has Finished!");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		stopEverything();
		System.out.println("Auton has Finished!!");
	}

	private void dropCube(double speed) {
		Robot.intake.set(speed);
		Timer.delay(.3);
		Robot.intake.stop();
	}

	private void dropCube() {
		dropCube(-.65);
	}


	private void stopEverything() {
		Robot.roboDrive.stopMotor();
		Robot.intake.stop();
		Robot.elevator.motor.stopMotor();
	}
}
