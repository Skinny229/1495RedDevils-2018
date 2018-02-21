package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Elevate extends Command {

	double speed;
	
    public Elevate(double s) {
        requires(Robot.elevator);
        speed = s;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(speed > 0)
    		Robot.lights.addCmd("D");
    	else
    		Robot.lights.addCmd("F");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.motor.set(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.motor.stopMotor();
    	Robot.lights.addCmd("E");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.motor.stopMotor();
    	Robot.lights.addCmd("E");
    }
}
