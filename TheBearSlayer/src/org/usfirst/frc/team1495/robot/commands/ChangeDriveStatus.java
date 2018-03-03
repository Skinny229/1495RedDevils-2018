package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeDriveStatus extends Command {
	
		Robot.DriveState state;
    public ChangeDriveStatus(Robot.DriveState statel) {
    	state = statel;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.controlStatus = state;
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

}
