package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnXDegrees extends Command {

	
	private double angleTarget, turnSpeed = RobotMap.kAutoTurnRate;
	private boolean isTurningLeft, isFin;
    public TurnXDegrees(double angleRequested) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	angleTarget = angleRequested;
    }

    // Called once when the command executes
    protected void initialize() {
    	isFin = true;
    	setTimeout(7);
    	if(angleTarget < 0) {
    		turnSpeed *= -1;
    		isTurningLeft = true;
    	}else {
    		isTurningLeft = false;
    	}
    	
    }

    
    protected void execute() {
    	if(isTurningLeft) {
    		if (Robot.gyro.getRawAngleDegrees() >= angleTarget && !this.isCanceled()) {
				Robot.roboDrive.arcadeDrive(0, -turnSpeed);
			} else {
				isFin = true;
			}
    	}else {
    		if (Robot.gyro.getRawAngleDegrees() <= angleTarget && !this.isCanceled()) {
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
