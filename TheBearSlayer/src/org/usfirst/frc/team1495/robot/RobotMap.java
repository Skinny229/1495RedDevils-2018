package org.usfirst.frc.team1495.robot;

public class RobotMap {
	// Drive
	public static final int kLeftDriveTalon = 0; // CAN ID
	public static final int kRightDriveTalon = 0; // CAN ID
	public static final boolean kDriveSafety = true;

	// Intake
	public static final int kLeftIntakeTalon = 0; // CAN ID
	public static final int kRightIntakeTalon = 0; // CAN ID
	public static final boolean kIntakeSafety = true;
	
	public static final int kLeftIntakeSolenoid = 0; // Module ID
	public static final int kLeftIntakeSolenoidForward = 0; // PCM ID
	public static final int kLeftIntakeSolenoidReverse = 0; // PCM ID
	
	public static final int kRightIntakeSolenoid = 0; // Module ID
	public static final int kRightIntakeSolenoidForward = 0; // PCM ID
	public static final int kRightIntakeSolenoidReverse = 0; // PCM ID

	// Elevator
	public static final int kElevatorTalon = 0; // CAN ID
	public static final boolean kElevatorSafety = true;

	// Climber
	public static final int kClimberTalon = 0; // CAN ID
	public static final boolean kClimberSafety = true;

	// Other
	public static final int kPDP = 0; // CAN ID
	public static final int kCompressor = 0;// PCM ID

	// Control
	public static final int kDriverControllerPort = 0;
	public static final int kOperatorControllerPort = 1;
	
	// Constants
	public static final double kEncoderConversionFactor = 0.00851;
}
