package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RaiseElev extends Command {

	private boolean isFin;
	private AutoRunner.ElevPos setTo;
    public RaiseElev(AutoRunner.ElevPos setToThis) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    	setTo = setToThis;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isFin = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(setTo) {
    	case kScale:
    		if(timeSinceInitialized() < RobotMap.timeToSwitch) {
    			Robot.elevator.motor.set(-1.0);
    		}else {
    			isFin = true;
    		}
    		break;
    	case kSwitch:
    		if(!Robot.upperElevatorLS.get()) {
    			Robot.elevator.motor.set(-1.0);
    		}else {
    			isFin = true;
    		}
    		break;
    	case kStop:
    		isFin = true;
    		break;
    	}
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFin;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.motor.stopMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.motor.stopMotor();
    }
}
