package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CubeIO extends Command {

	double speed;

	public CubeIO(double s) {
		requires(Robot.intake);
		speed = s;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	protected void execute() {
		Robot.intake.leftTalon.spin(speed);
		Robot.intake.rightTalon.spin(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intake.leftTalon.stopMotor();
		Robot.intake.rightTalon.stopMotor();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.intake.leftTalon.stopMotor();
		Robot.intake.rightTalon.stopMotor();
	}
}
