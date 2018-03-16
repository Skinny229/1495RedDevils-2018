package org.usfirst.frc.team1495.robot;

public class RobotMap {
	// Drive
	public static final int kLeftDriveMotorID = 0; // CAN ID
	public static final int kRightDriveMotorID = 1; // CAN ID
	public static final int kLeftDrive2MotorID = 2;
	public static final int kRightDrive2MotorID = 3;
	public static final double kDTTurnMult = 0.8;
	public static final double kFineTuneMult = 0.4;
	public static final boolean kDriveMotorSafety = false;
	
	// Intake
	public static final int kLeftIntakeMotorID = 0;  // VictorSP
	public static final int kRightIntakeMotorID = 1; // VictorSP
	public static final boolean kIntakeMotorSafety = false;
	public static final double kIntakeSpeed = .75;

	// Arm	
	public static final int kArmSolenoidForward = 0;
	public static final int kArmSoleoidReverse = 1;
	
	// Elevator
	public static final int kElevatorMotorID = 4; // CAN ID
	public static final boolean kElevatorMotorSafety = false;
	public static final int kUpperElevatorLSPort = 2; // Digital
	public static final int kLowerElevatorLSPort = 3; // Digital
	public static final double kElevSpeed = 1.0;
	
	// Climber
	public static final int kClimberMotorID = 5; // CAN ID /
	public static final boolean kClimberMotorSafety = false;
	public static final double kClimberSpeed = 1.0;

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
	public static boolean isArduinoConnected = false;
	
	// Constants Auto
	public static final double kEncoderConversionFactor = 0.00851;
	public static final double kf = 0.076;
	public static final double kp = 0.5;
	public static final double ki = 0.0;
	public static final double kd = 0.0;
	public static final double timeToSwitch = 1.0;
	public static final double distSwitch = 125.0;
	public static final double distScaleFromSwitch = 160.0;
	public static final double distSwitchFromMiddle = 50.0;
	public static final double kWheelDiameterIn = 8.0;
	public static final double kUnitsPerRot = 4096.0;
	public static final double kAutoTurnRate = 0.6;
	public static final double fowardRateAuto = 0.65;
	public static final int timeoutEncoders = 0;
	public static final int baseTrajTimeMS = 10;
	//public static final double kMaxVelocity;
	//public static final double kMaxAcceleration;
	//public static final double kMaxJerk;
	//public static final double kEpsilon = Math.pow(10.0, -6.0);
	//public static final double kPositionTolerance = Math.pow(10.0, -3.0);
	//public static final double kVelocityTolerance = Math.pow(10.0, -3.0);
}
