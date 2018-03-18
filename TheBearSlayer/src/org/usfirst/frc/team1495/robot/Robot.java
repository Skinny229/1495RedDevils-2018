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

import org.usfirst.frc.team1495.robot.commands.AutoRunner;
import org.usfirst.frc.team1495.robot.commands.DriveRobotWithCaution;
import org.usfirst.frc.team1495.robot.commands.NoDrive;
import org.usfirst.frc.team1495.robot.subsystems.*;

public class Robot extends TimedRobot {

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
	// Subsystems
	public static Intake intake = new Intake();
	public static Elevator elevator = new Elevator();
	public static Climber climber = new Climber();
	public static Arm arm = new Arm();
	public static LimitSwitch upperElevatorLS = new LimitSwitch(RobotMap.kUpperElevatorLSPort);
	// Control
	public static Gyro gyro = new Gyro();
	public static OI oi = new OI();
	// Other
	public static PowerDistributionPanel PDP = new PowerDistributionPanel(RobotMap.kPDP);
	public static Compressor compressor = new Compressor(0);
	// Autonomous
	static Integer autoRoutine;
	SendableChooser<Integer> autoChooser = new SendableChooser<>();
	SendableChooser<Character> autoPosChooser = new SendableChooser<>();
	SendableChooser<Boolean> goScaleChooser = new SendableChooser<>();
	public static char posStart = ' ';
	public static String gameData = "  ";
	public static boolean goScale = false;
	
	@Override
	public void robotInit() {
		compressor.clearAllPCMStickyFaults();
		roboDrive = new DifferentialDrive(new SpeedControllerGroup(leftDriveMotor, leftDriveMotor2),
				new SpeedControllerGroup(rightDriveMotor, rightDriveMotor2));
		roboDrive.setSafetyEnabled(RobotMap.kDriveMotorSafety);

		// Start loading starting Motion Profile
		// Starting distance should be at least breaking auto line

		PDP.clearStickyFaults();

		leftDriveMotor.setUpMotionProfile();
		rightDriveMotor.setUpMotionProfile();

		autoChooser.addDefault("Command Group Auto Runner", 1);
		autoChooser.addObject("Encoder Control", 2);
		autoChooser.addObject("Drive Straight", 3);
		SmartDashboard.putData("Autonomous selection", autoChooser);

		autoPosChooser.addDefault("Left", 'L');
		autoPosChooser.addObject("Middle", 'M');
		autoPosChooser.addObject("Right", 'R');
		SmartDashboard.putData("MotionProfile starting Positin", autoPosChooser);

		goScaleChooser.addDefault("Go to Scale if possible. **NOTE: Will only work with CMDGroup auto runner**", true);
		goScaleChooser.addObject("Don't go to scale if possible", false);
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

	 static Command autoCmd;
	@Override
	public void autonomousInit() {
		System.out.println("-----------Auto Started---------");
		autoRoutine = autoChooser.getSelected();
		posStart = autoPosChooser.getSelected();
		goScale = goScaleChooser.getSelected();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println("Game Data: ");
		System.out.println("Friendly Switch: " + Robot.gameData.charAt(0));
		System.out.println("Scale: " + Robot.gameData.charAt(1));
		System.out.println("Enemy Switch: " + Robot.gameData.charAt(2));
		System.out.println("Selected Starting Position: " + Robot.posStart);

		System.out.print("Running...");
		switch(autoRoutine){
		case 1:
			System.out.println("Auto GroupCMD");
			autoCmd = new AutoRunner();
			break;
		case 2:
			System.out.println("Encoder Auto");
			autoCmd = new DriveRobotWithCaution();
			break;
		case 3:
			autoCmd = new NoDrive();
			break;
		default:
			autoCmd = new NoDrive();
			break;
		}
		autoCmd.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autoCmd != null) {
			autoCmd.cancel();
		}
		intake.stop();
		elevator.stop();
		roboDrive.stopMotor();
		System.out.println("--------------Auto Has Ended!---------------");
	}

	double elevInput = 0.0;
	@Override
	public void teleopPeriodic() {
		roboDrive.arcadeDrive(-oi.driverController.getY(Hand.kLeft), oi.driverController.getX(Hand.kRight) * RobotMap.kDTTurnMult);
		elevInput = oi.operator.getY();
		if(Math.abs(elevInput) < RobotMap.kElevDeadband){
			elevInput = 0.0;
		}
		elevator.set(elevInput);
		
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}



}
