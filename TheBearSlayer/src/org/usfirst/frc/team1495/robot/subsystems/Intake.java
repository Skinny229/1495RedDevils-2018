package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	public CAN_TalonSRX leftTalon;
	public CAN_TalonSRX rightTalon;
	public DoubleSolenoid leftSolenoid;
	public DoubleSolenoid rightSolenoid;

	public Intake() {
		leftTalon = new CAN_TalonSRX(RobotMap.kLeftIntakeTalon, RobotMap.kIntakeSafety);
		rightTalon = new CAN_TalonSRX(RobotMap.kRightIntakeTalon, RobotMap.kIntakeSafety);
		rightTalon.setInverted(true);
		leftSolenoid = new DoubleSolenoid(RobotMap.kLeftIntakeSolenoidForward, RobotMap.kLeftIntakeSolenoidReverse);
		rightSolenoid = new DoubleSolenoid(RobotMap.kRightIntakeSolenoidForward, RobotMap.kRightIntakeSolenoidReverse);
	}

	public void initDefaultCommand() {
	}
}
