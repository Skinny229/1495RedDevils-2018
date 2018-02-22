package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;


import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	public CAN_TalonSRX leftMotor;
	public CAN_TalonSRX rightMotor;
	
	public Intake() {
		leftMotor = new CAN_TalonSRX(RobotMap.kLeftIntakeMotorID, RobotMap.kIntakeMotorSafety);
		rightMotor = new CAN_TalonSRX(RobotMap.kRightIntakeMotorID, RobotMap.kIntakeMotorSafety);
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

