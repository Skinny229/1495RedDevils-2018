package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Drives foward certain number of inches
 *
 *Warning !!! this does not compensate for error over time, This if run for too much distance will veer off target
 */
public class DriveDistEncoder extends Command {

	private double distance, startingEncReading, speed;
	private boolean isFin, goingScale;

	public DriveDistEncoder(double distInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		distance = distInches;
		speed = RobotMap.fowardRateAuto;
	}

	public DriveDistEncoder(double distInches, double speedFoward) {
		distance = distInches;
		speed = speedFoward;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("Running: DriveDistEncoder");
		System.out.println("Driving " +  distance + " inches");
		if(distance > 140 && AutoRunner.target == "scale")
			goingScale = true;
		else 
			goingScale = false;
		
		isFin = false;
		startingEncReading = Robot.leftDriveMotor.getRawEncoderPosition();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	if(goingScale){
		if (Robot.leftDriveMotor.getRawEncoderPosition() < ((distance / (RobotMap.kWheelDiameterIn * Math.PI) * RobotMap.kUnitsPerRot)+ startingEncReading) && !this.isCanceled())
			Robot.roboDrive.tankDrive(speed+.01, speed);
		else {
			isFin = true;
		}
	}
	else{
		if (Robot.leftDriveMotor.getRawEncoderPosition() < ((distance / (RobotMap.kWheelDiameterIn * Math.PI) * RobotMap.kUnitsPerRot)+ startingEncReading) && !this.isCanceled())
			Robot.roboDrive.arcadeDrive(speed, 0);
		else {
			isFin = true;
		}
	}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isFin;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.roboDrive.stopMotor();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.roboDrive.stopMotor();
	}
	
}
