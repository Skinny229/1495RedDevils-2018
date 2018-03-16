package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class dropCubeAuto extends Command {

	
	private boolean isFin;
	
    public dropCubeAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Running: dropCubeAuto");
    	setTimeout(1.5);
    	isFin = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timeSinceInitialized()  < .4) {
    		Robot.intake.set(-RobotMap.kIntakeSpeed);
    	}else {
    		isFin = true;
    	}
   
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFin;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stop();
    }
}
