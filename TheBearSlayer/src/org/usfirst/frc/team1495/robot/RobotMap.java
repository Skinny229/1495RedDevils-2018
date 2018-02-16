package org.usfirst.frc.team1495.robot;

public class RobotMap {
	// Drive
	public static final int kLeftDriveMotorID = 2; // CAN ID / PWM 2
	public static final int kRightDriveMotorID = 3; // CAN ID / PWM 3
	public static final boolean kDriveMotorSafety = false;

	// Intake
	public static final int kLeftIntakeMotorID = 4; // CAN ID / PWM 4
	public static final int kRightIntakeMotorID = 5; // CAN ID / PWM 5
	public static final boolean kIntakeMotorSafety = false;
	
	// Claw
	public static final int kLeftClawSolenoidForward = 2; // PCM ID
	public static final int kLeftClawSolenoidReverse = 3; // PCM ID
	
	public static final int kRightClawSolenoidForward = 4; // PCM ID
	public static final int kRightClawSolenoidReverse = 5; // PCM ID

	// Arm
	
	public static final int kArmSolenoidForward = 0;
	public static final int kArmSoleoidReverse = 1;
	
	// Elevator
	public static final int kElevatorMotorID = 6; // CAN ID / PWM 6
	public static final boolean kElevatorMotorSafety = false;

	// Climber
	public static final int kClimberMotorID = 7; // CAN ID / PWM 7
	public static final boolean kClimberMotorSafety = false;

	// Control
	public static final int kDriverControllerPort = 0; // USB PORT
	public static final int kOperatorControllerPort = 1; // USB PORT
	public static final int kTestJoystickPort = 2; // USB PORT
	
	//Electronics
	public static final int kPCM = 0; // CAN ID
	public static final int kPDP = 1; // CAN ID
	
	// Constants
	public static final double kEncoderConversionFactor = 0.00851;
}
