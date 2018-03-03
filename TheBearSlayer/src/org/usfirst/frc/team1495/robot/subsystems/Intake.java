package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	private VictorSP leftMotor;
	private VictorSP rightMotor;
	
	public Intake() {
		leftMotor = new VictorSP(RobotMap.kLeftIntakeMotorID);
		rightMotor = new VictorSP(RobotMap.kRightIntakeMotorID);
		leftMotor.setSafetyEnabled(RobotMap.kIntakeMotorSafety);
		rightMotor.setSafetyEnabled(RobotMap.kIntakeMotorSafety);
	}
	
	public void set(double speed) {
		leftMotor.set(speed);
		rightMotor.set(-speed);
	}
	
	public void stop() {
		leftMotor.stopMotor();
		rightMotor.stopMotor();
	}

	public void initDefaultCommand() {
	}
}

