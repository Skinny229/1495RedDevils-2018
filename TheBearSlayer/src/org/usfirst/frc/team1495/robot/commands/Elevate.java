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
	
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		if (speed > 0)
			Robot.lights.addCmd("F");
		else
			Robot.lights.addCmd("D");
		
		/*if (speed > 0 && !Robot.upperElevatorLS.get())
			Robot.elevator.motor.set(speed);
		else if (speed < 0 && !Robot.lowerElevatorLS.get())
			Robot.elevator.motor.set(speed);
		else{
			Robot.elevator.motor.stopMotor();
			speed = 0;
		}*/
		Robot.elevator.motor.set(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return speed == 0;
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
