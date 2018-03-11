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
		Robot.arm.solenoid.set(Value.kReverse);

		stage = 0;
		isFin = false;
		angleTarget = 90;
		Robot.leftDriveMotor.resetEncoder();

		System.out.println("Game Data: ");
		System.out.println("Friendly Switch: " + Robot.gameData.charAt(0));
		System.out.println("Scale: " + Robot.gameData.charAt(1));
		System.out.println("Enemy Switch: " + Robot.gameData.charAt(2));
		System.out.println("Selected Starting Position: " + Robot.posStart);

		startEncStart = Robot.leftDriveMotor.getRawEncoderPosition();
		
		//Determing where we going
		if(Robot.posStart != 'M') {
			if(Robot.gameData.charAt(0) == Robot.posStart) {
				goingTo = 0;
				toSwitch = true;
				distToTravel = RobotMap.distSwitch;
			}
			else if(Robot.gameData.charAt(1) == Robot.posStart) {
				goingTo = 1;
				toSwitch = false;
				distToTravel = RobotMap.distScale;
			}else if(Robot.posStart == 'M'){
				toSwitch = true;
				distToTravel = 120.0;
			}else {
				isFin = true;
			}
		}

		hasHitTop = false;
		
		Robot.gyro.reset();

		if (Robot.gameData == "" || Robot.posStart == ' ') {
			isFin = true;
			DriverStation.reportError("Data not valid! Verify data is good. Stopping Auto", false);
		}

		System.out.println("Initializing Finished ... Moving to control loop ... Entering Stage 0");
	}

	private boolean isFin, toSwitch, hasHitTop;
	private double angleTarget, distToTravel, angleToTurn;
	private int startEncStart, stage, goingTo;
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (this.isCanceled())
			isFin = true;
		switch (stage) {
		case 0:
			
			if (startEncStart < ((distToTravel / (RobotMap.wheelDiameterIn * Math.PI) * RobotMap.unitsPerRot) + startEncStart))
				Robot.roboDrive.arcadeDrive(.65, 0);
			else {
				Robot.roboDrive.stopMotor();
				System.out.println("Stage 0 Finished. Moving to the first one.");
				stage = 1;
			}

			break;
		case 1:
			switch (Robot.posStart) {
			case 'R':
			case 'L':
				if (Robot.gameData.charAt(goingTo) == Robot.posStart) {
					if (Robot.posStart == 'R') {
						angleTarget *= -1;
					}

					angleToTurn = angleTarget;

					if (Robot.gyro.getRawAngleDegrees() < angleToTurn)
						stage = 5;
					else
						stage = 6;
				}
				break;
			case 'M':
				isFin = true;
				break;
			default:
				isFin = true;
			}
			break;
		case 2:
			if (Robot.leftDriveMotor.getRawEncoderVelocity() > 0 && !this.isCanceled()) {
			} else {
				dropCube();
				isFin = true;
			}
			break;
		case 3:
			if(!hasHitTop) {
				Robot.elevator.motor.set(-1.0);
				if(Robot.upperElevatorLS.get())
					hasHitTop = true;
			}else {
				startEncStart = Robot.leftDriveMotor.getRawEncoderPosition();
				distToTravel = RobotMap.closeUpToScale;
				Robot.elevator.motor.stopMotor();
				stage = 4;
			}
			break;
		case 4:
			if (startEncStart < ((distToTravel / (RobotMap.wheelDiameterIn * Math.PI) * RobotMap.unitsPerRot) + startEncStart)) {
				Robot.roboDrive.arcadeDrive(.65, 0);
			}
			else {
				Robot.roboDrive.stopMotor();
				System.out.println("Stage 0 Finished. Moving to the first one.");
				dropCube();
				isFin = true;
			}
			break;
		case 5:
			if (Robot.gyro.getRawAngleDegrees() <= angleToTurn && !this.isCanceled()) {
				Robot.roboDrive.arcadeDrive(0, RobotMap.turnRateXDeg);
			} else {
				Robot.roboDrive.stopMotor();
				if(!toSwitch) {
					stage = 3;
					return;
				}
				Robot.elevator.motor.set(-1.0);
				Timer.delay(1.0);
				Robot.roboDrive.arcadeDrive(.5, 0);
				Timer.delay(.3);
				stage = 2;
			}
			break;
		case 6:
			if (Robot.gyro.getRawAngleDegrees() >= angleToTurn && !this.isCanceled()) {
				Robot.roboDrive.arcadeDrive(0, -RobotMap.turnRateXDeg);
			} else {
				Robot.roboDrive.stopMotor();
				if(!toSwitch) {
					stage = 3;
					return;
				}
				Robot.elevator.motor.set(-1.0);
				Timer.delay(1.0);
				Robot.roboDrive.arcadeDrive(.5, 0);
				Timer.delay(.3);
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
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		stopEverything();
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
