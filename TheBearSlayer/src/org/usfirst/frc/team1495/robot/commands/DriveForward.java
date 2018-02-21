package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {

    public DriveForward() {
    }

    protected void initialize() {
    Robot.roboDrive.arcadeDrive(1.0, 0);
    for(int i = 0; i < 50; i++) {
    	System.out.println("Left: " + Robot.leftDriveMotor.getEncoderPosition() + "     Right: " + Robot.rightDriveMotor.getEncoderPosition());
    	Timer.delay(0.01);
    }
    Robot.roboDrive.stopMotor();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
