package org.usfirst.frc.team1495.robot;

public class RobotMap {
	// Drive
	public static final int kLeftDriveMotorID = 2; // CAN ID / PWM
	public static final int kRightDriveMotorID = 3; // CAN ID / PWM
	public static final boolean kDriveMotorSafety = false;

	// Intake
	public static final int kLeftIntakeMotorID = 4; // CAN ID / PWM
	public static final int kRightIntakeMotorID = 5; // CAN ID / PWM
	public static final boolean kIntakeMotorSafety = false;
	
	public static final int kLeftIntakeSolenoidForward = 0; // PCM ID
	public static final int kLeftIntakeSolenoidReverse = 1; // PCM ID
	
	public static final int kRightIntakeSolenoidForward = 2; // PCM ID
	public static final int kRightIntakeSolenoidReverse = 3; // PCM ID

	// Elevator
	public static final int kElevatorMotorID = 6; // CAN ID / PWM
	public static final boolean kElevatorMotorSafety = false;

	// Climber
	public static final int kClimberMotorID = 7; // CAN ID / PWM
	public static final boolean kClimberMotorSafety = false;

	// Control
	public static final int kDriverControllerPort = 0; // USB PORT
	public static final int kOperatorControllerPort = 1; // USB PORT
	public static final int kTestJoystickPort = 2; // USB PORT
	
	//Electronics
	public static final int kPCM = 0; // CAN ID
	public static final int kPDP = 1; // CAN ID
	
	//Reactive LED
	public static final int kI2CAdress = 4;
	public static final double kMainLoopSpeed = 0.05;
	public static final double kTimeoutLoopSpeed = 0.25;
	public static final int kInnerIntakeLimit = 0;
	public static final int kOuterIntakeLimit = 1;
	
	// Constants
	public static final double kEncoderConversionFactor = 0.00851;
}
