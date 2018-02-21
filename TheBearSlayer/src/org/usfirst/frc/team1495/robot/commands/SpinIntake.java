package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.subsystems.Intake;
import org.usfirst.frc.team1495.robot.subsystems.Intake.IntakeDir;

import edu.wpi.first.wpilibj.command.Command;

public class SpinIntake extends Command {

	double speed;
	IntakeDir dir;
	
    public SpinIntake(IntakeDir direction) {
		requires(Robot.intake);
		dir = direction;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	protected void execute() {
		Robot.intake.set(speed);

		if(dir == IntakeDir.Inwards)
			Robot.intake.setIn();
		else if(dir == IntakeDir.Outwards)
			Robot.intake.setOut();

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intake.stop();
		Robot.intake.stopSpin();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.intake.stop();
		Robot.intake.stopSpin();
	}
}
