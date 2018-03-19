package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToWall extends Command {
	
	private boolean isFin;

    public DriveToWall() {
    }

    
    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Running: DriveToWall");
    	isFin = false;
    	Robot.roboDrive.arcadeDrive(RobotMap.fowardRateAuto-.1, 0);
    }

    /**
     * Drive for at least .75 seconds and then stop when you hit something
     */
    protected void execute() {
    	
		if (Robot.leftDriveMotor.getRawEncoderVelocity() != 0 || timeSinceInitialized() < .75) {
			Robot.roboDrive.arcadeDrive(RobotMap.fowardRateAuto-.1, 0);
		} else {
			isFin = true;
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
