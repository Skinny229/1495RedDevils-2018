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
        requires(Robot.elevUpper);
        requires(Robot.elevLower);
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
    	if(speed > 0 && ! Robot.elevUpper.limit.get())
    		Robot.elevator.motor.set(speed);
    	else if(speed < 0 && ! Robot.elevLower.limit.get())
    		Robot.elevator.motor.set(speed);
    	else
    		Robot.elevator.motor.stopMotor();
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
