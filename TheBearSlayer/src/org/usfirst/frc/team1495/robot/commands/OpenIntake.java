package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class OpenIntake extends Command {

	boolean down;

	public OpenIntake(boolean value) {
		requires(Robot.arm);
		down = value;
	}
 
	// Called just before this Command runs the first time
	protected void initialize() {
		if(down)
			Robot.arm.solenoid.set(Value.kForward);
		else
			Robot.arm.solenoid.set(Value.kReverse);
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
