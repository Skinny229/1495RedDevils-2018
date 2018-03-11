package org.usfirst.frc.team1495.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1495.robot.commands.DriveRobotDrive;
import org.usfirst.frc.team1495.robot.commands.DriveRobotWithCaution;
import org.usfirst.frc.team1495.robot.commands.NoDrive;
import org.usfirst.frc.team1495.robot.commands.TestingMP;
import org.usfirst.frc.team1495.robot.subsystems.*;

public class Robot extends TimedRobot {
	public enum DriveState {
		FINETUNE, NOFINETUNE
	}

	// Drive Train
	public static DifferentialDrive roboDrive;
	public static CAN_TalonSRXE leftDriveMotor = new CAN_TalonSRXE(RobotMap.kLeftDriveMotorID,
			RobotMap.kDriveMotorSafety);
	public static CAN_TalonSRXE leftDriveMotor2 = new CAN_TalonSRXE(RobotMap.kLeftDrive2MotorID,
			RobotMap.kDriveMotorSafety);
	public static CAN_TalonSRXE rightDriveMotor2 = new CAN_TalonSRXE(RobotMap.kRightDrive2MotorID,
			RobotMap.kDriveMotorSafety);
	public static CAN_TalonSRXE rightDriveMotor = new CAN_TalonSRXE(RobotMap.kRightDriveMotorID,
			RobotMap.kDriveMotorSafety);
	public static DriveState controlStatus = DriveState.NOFINETUNE;
	// Subsystems
	public static Intake intake = new Intake();
	public static Elevator elevator = new Elevator();
	public static Climber climber = new Climber();
	public static Arm arm = new Arm();
	public static LimitSwitch upperElevatorLS = new LimitSwitch(RobotMap.kUpperElevatorLSPort);
	public static LimitSwitch lowerElevatorLS = new LimitSwitch(RobotMap.kLowerElevatorLSPort);
	// Control
	public static Gyro gyro = new Gyro();
	public static OI oi = new OI();
	// Other
	public static PowerDistributionPanel PDP = new PowerDistributionPanel(RobotMap.kPDP);
	public static Compressor compressor = new Compressor();
	public static InteractiveLEDS lights = new InteractiveLEDS();
	// Autonomous
	static Command autoRoutine;
	SendableChooser<Command> autoChooser = new SendableChooser<>();
	SendableChooser<Character> autoPosChooser = new SendableChooser<>();
	SendableChooser<Boolean> goScaleChooser = new SendableChooser<>();
	public static char posStart = ' ';
	public static String gameData = "";
	public static boolean goScale;

	public static IntegratedMP rodMP = new IntegratedMP(leftDriveMotor, rightDriveMotor);

	@Override
	public void robotInit() {

		roboDrive = new DifferentialDrive(new SpeedControllerGroup(leftDriveMotor, leftDriveMotor2),
				new SpeedControllerGroup(rightDriveMotor, rightDriveMotor2));
		roboDrive.setSafetyEnabled(RobotMap.kDriveMotorSafety);

		// Start loading starting Motion Profile
		// Starting distance should be at least breaking auto line
		rodMP.reset(GeneratedMotionProfiles.PointsDef);

		PDP.clearStickyFaults();

		leftDriveMotor.setUpMotionProfile();
		rightDriveMotor.setUpMotionProfile();

		autoChooser.addDefault("Encoder Control", new DriveRobotWithCaution());
		autoChooser.addObject("Drive Straight", new NoDrive());
		autoChooser.addObject("Motion Profile", new DriveRobotDrive());
		autoChooser.addObject("Experimental Motion Profile", new TestingMP());
		SmartDashboard.putData("Autonomous selection", autoChooser);
		

		autoPosChooser.addDefault("Left", 'L');
		autoPosChooser.addObject("Middle", 'M');
		autoPosChooser.addObject("Right", 'R');
		SmartDashboard.putData("MotionProfile starting Positin", autoPosChooser);
		
		goScaleChooser.addDefault("Go to Scale if possible", true);
		goScaleChooser.addObject("Don't go to scale", false);
		SmartDashboard.putData(goScaleChooser);
		
		gyro.calibrate();
		gyro.reset();
	}

	@Override
	public void disabledInit() {
		PDP.clearStickyFaults();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		autoRoutine = autoChooser.getSelected();
		posStart = autoPosChooser.getSelected();
		goScale = goScaleChooser.getSelected();
		gameData = DriverStation.getInstance().getGameSpecificMessage();

		if (autoRoutine != null) {
			autoRoutine.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autoRoutine != null) {
			autoRoutine.cancel();
		}
		DriveRobotDrive.stopEverything();
	}

	@Override
	public void teleopPeriodic() {
		runDriveTrain();
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}

	public void runDriveTrain() {
		double fineTuneY = 0.0;
		double fineTuneX = 0.0;
		switch (controlStatus) {
		case FINETUNE:
			fineTuneY = oi.stick.getY() * RobotMap.kFineTuneMult;
			fineTuneX = oi.stick.getX() * RobotMap.kFineTuneMult;
			break;
		default:
			break;
		}
		roboDrive.arcadeDrive(-oi.driverController.getY(Hand.kLeft) + -fineTuneY,
				(oi.driverController.getX(Hand.kRight) + fineTuneX) * RobotMap.kDTTurnMult);
	}

}
