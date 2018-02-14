package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	CAN_TalonSRX leftTalon;
	CAN_TalonSRX rightTalon;
	DoubleSolenoid leftSolenoid;
	DoubleSolenoid rightSolenoid;

	public Intake() {
		leftTalon = new CAN_TalonSRX(RobotMap.kLeftIntakeTalon, RobotMap.kIntakeSafety);
		rightTalon = new CAN_TalonSRX(RobotMap.kRightIntakeTalon, RobotMap.kIntakeSafety);
		leftSolenoid = new DoubleSolenoid( /*RobotMap.kLeftIntakeSolenoid ,*/ 
				RobotMap.kLeftIntakeSolenoidForward, RobotMap.kLeftIntakeSolenoidReverse);
		rightSolenoid = new DoubleSolenoid(/*RobotMap.kRightIntakeSolenoid ,*/ 
				RobotMap.kRightIntakeSolenoidForward, RobotMap.kRightIntakeSolenoidReverse);
	}

	public void initDefaultCommand() {
	}
}
