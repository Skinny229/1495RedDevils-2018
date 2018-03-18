package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnXDegrees extends Command {

	
	private double angleTarget, turnSpeed = RobotMap.kAutoTurnRate, startingAngle;
	private boolean isTurningLeft, isFin;
    public TurnXDegrees(double angleRequested) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	angleTarget = angleRequested;
    }

    // Called once when the command executes
    protected void initialize() {
    	System.out.println("Running: TurnXDegrees");
    	startingAngle = Robot.gyro.getRawAngleDegrees();
    	isFin = false;
    	setTimeout(5);
    	if(angleTarget < 0) {
    		System.out.println("Turning Left...");
    		isTurningLeft = true;
    	}else {
    		System.out.println("Turning Right...");
    		isTurningLeft = false;
    	}
    	
    }

    
    protected void execute() {
    	if(isTurningLeft) {
    		if (Robot.gyro.getRawAngleDegrees() >= (angleTarget + startingAngle) && !this.isCanceled()) {
				Robot.roboDrive.arcadeDrive(0, -turnSpeed);
			} else {
				isFin = true;
			}
    	}else {
    		if (Robot.gyro.getRawAngleDegrees() <= (angleTarget + startingAngle) && !this.isCanceled()) {
				Robot.roboDrive.arcadeDrive(0, turnSpeed);
			} else {
				isFin = true;
			}
    	}
    }
    
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isFin;
	}

	protected void end() {
		Robot.roboDrive.stopMotor();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.roboDrive.stopMotor();
	}

}
