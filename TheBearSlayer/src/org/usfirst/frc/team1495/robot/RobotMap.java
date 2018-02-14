package org.usfirst.frc.team1495.robot;

public class RobotMap {
	// Drive
	public static final int kLeftDriveTalon = 2; // CAN ID
	public static final int kRightDriveTalon = 3; // CAN ID
	public static final boolean kDriveSafety = true;

	// Intake
	public static final int kLeftIntakeTalon = 4; // CAN ID
	public static final int kRightIntakeTalon = 5; // CAN ID
	public static final boolean kIntakeSafety = true;
	
	public static final int kLeftIntakeSolenoidForward = 0; // PCM ID
	public static final int kLeftIntakeSolenoidReverse = 1; // PCM ID
	
	public static final int kRightIntakeSolenoidForward = 2; // PCM ID
	public static final int kRightIntakeSolenoidReverse = 3; // PCM ID

	// Elevator
	public static final int kElevatorTalon = 6; // CAN ID
	public static final boolean kElevatorSafety = true;

	// Climber
	public static final int kClimberTalon = 7; // CAN ID
	public static final boolean kClimberSafety = true;

	// Other
	public static final int kPDP = 0; // CAN ID

	// Control
	public static final int kDriverControllerPort = 0;
	public static final int kOperatorControllerPort = 1;
	public static final int kTestJoystick = 2;
	
	// Constants
	public static final double kEncoderConversionFactor = 0.00851;
}
