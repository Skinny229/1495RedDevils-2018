package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	public CAN_TalonSRX motor;

	public Elevator() {
		motor = new CAN_TalonSRXE(RobotMap.kElevatorMotorID, RobotMap.kElevatorMotorSafety);
	}

	
	public void stop() {
		motor.stopMotor();
	}
	
	
	
	public void initDefaultCommand() {
	}
}
