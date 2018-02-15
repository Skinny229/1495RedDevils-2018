package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	public CAN_TalonSRX leftMotor;
	public CAN_TalonSRX rightMotor;
	//public PWM_VictorSP leftMotor;
	//public PWM_VictorSP rightMotor;
	public DoubleSolenoid leftSolenoid;
	public DoubleSolenoid rightSolenoid;

	public Intake() {
		leftMotor = new CAN_TalonSRX(RobotMap.kLeftIntakeMotorID, RobotMap.kIntakeMotorSafety);
		rightMotor = new CAN_TalonSRX(RobotMap.kRightIntakeMotorID, RobotMap.kIntakeMotorSafety);
		//leftMotor = new PWM_VictorSP(RobotMap.kLeftIntakeMotorID, RobotMap.kIntakeMotorSafety);
		//rightMotor = new PWM_VictorSP(RobotMap.kRightIntakeMotorID, RobotMap.kIntakeMotorSafety);
		rightMotor.setInverted(true);
		leftSolenoid = new DoubleSolenoid(RobotMap.kLeftIntakeSolenoidForward, RobotMap.kLeftIntakeSolenoidReverse);
		rightSolenoid = new DoubleSolenoid(RobotMap.kRightIntakeSolenoidForward, RobotMap.kRightIntakeSolenoidReverse);
	}

	public void initDefaultCommand() {
	}
}
