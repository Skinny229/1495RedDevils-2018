package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToWall extends Command {
	
	private boolean isFin;

    public DriveToWall() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    
    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Running: DriveToWall");
    	//setTimeout(6);//Seconds
    	isFin = false;
    	Robot.roboDrive.arcadeDrive(RobotMap.fowardRateAuto - .5, 0);
    	Timer.delay(.3);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if (Robot.leftDriveMotor.getRawEncoderVelocity() > 0 && !this.isCanceled()) {
			Robot.roboDrive.arcadeDrive(RobotMap.fowardRateAuto - .5, 0);
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
