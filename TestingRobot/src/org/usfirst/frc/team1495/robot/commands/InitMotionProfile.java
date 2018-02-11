package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.MotionProfileExecuter;
import org.usfirst.frc.team1495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class InitMotionProfile extends Command{
	
	boolean isFin;
	MotionProfileExecuter mpExec;

	public InitMotionProfile() {
		// TODO Auto-generated constructor stub
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	mpExec = new MotionProfileExecuter(Robot.driveTalon);
    	mpExec.reset();
    	isFin = false;
    	mpExec.startMotionProfile();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.isMPDone){
    		isFin = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFin;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
