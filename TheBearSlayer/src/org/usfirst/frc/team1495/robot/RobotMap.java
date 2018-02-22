package org.usfirst.frc.team1495.robot;

public class RobotMap {
	// Drive
	public static final int kLeftDriveMotorID = 0; // CAN ID
	public static final int kRightDriveMotorID = 1; // CAN ID
	public static final boolean kDriveMotorSafety = false;

	// Intake
	public static final int kLeftIntakeMotorID = 2; // CAN ID
	public static final int kRightIntakeMotorID = 3; // CAN ID
	public static final boolean kIntakeMotorSafety = false;

	// Arm	
	public static final int kArmSolenoidForward = 0;
	public static final int kArmSoleoidReverse = 1;
	
	// Elevator
	public static final int kElevatorMotorID = 4; // CAN ID
	public static final boolean kElevatorMotorSafety = false;
	//public static final int kUpperElevatorLSPort = 2; // PWM
	//public static final int kLowerElevatorLSPort = 3; // PWM

	// Climber
	public static final int kClimberMotorID = 5; // CAN ID /
	public static final boolean kClimberMotorSafety = false;

	// Control
	public static final int kDriverControllerPort = 0; // USB PORT
	public static final int kOperatorControllerPort = 1; // USB PORT
	public static final int kTestJoystickPort = 2; // USB PORT
	
	//Electronics
	public static final int kPCM = 0; // CAN ID
	public static final int kPDP = 1; // CAN ID
	
	//Interactive LEDS
	public static final int kInnerLimitSwitchPort = 0;
	public static final int kOutterLimitSwitchPort = 1;
	public static final int kI2CAdress = 4;
	
	// Constants
	public static final double kEncoderConversionFactor = 0.00851;
	//public static final double kMaxVelocity;
	//public static final double kMaxAcceleration;
	//public static final double kMaxJerk;
	//public static final double kEpsilon = Math.pow(10.0, -6.0);
	//public static final double kPositionTolerance = Math.pow(10.0, -3.0);
	//public static final double kVelocityTolerance = Math.pow(10.0, -3.0);
}
