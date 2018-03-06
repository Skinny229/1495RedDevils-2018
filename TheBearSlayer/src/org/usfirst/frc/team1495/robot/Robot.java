package org.usfirst.frc.team1495.robot;

import edu.wpi.first.wpilibj.Compressor;
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
import org.usfirst.frc.team1495.robot.commands.OpenIntakeVertical;
import org.usfirst.frc.team1495.robot.subsystems.Arm;
import org.usfirst.frc.team1495.robot.subsystems.CAN_TalonSRXE;
import org.usfirst.frc.team1495.robot.subsystems.Climber;
import org.usfirst.frc.team1495.robot.subsystems.Elevator;
import org.usfirst.frc.team1495.robot.subsystems.Intake;
import org.usfirst.frc.team1495.robot.subsystems.IntegratedMP;
import org.usfirst.frc.team1495.robot.subsystems.LimitSwitch;


public class Robot extends TimedRobot {
	public enum DriveState{
		FINETUNE, NOFINETUNE
	}
	
	//Drive
	public static DifferentialDrive roboDrive;
	public static CAN_TalonSRXE leftDriveMotor = new CAN_TalonSRXE(RobotMap.kLeftDriveMotorID, RobotMap.kDriveMotorSafety);
	public static CAN_TalonSRXE  leftDriveMotor2 = new CAN_TalonSRXE(RobotMap.kLeftDrive2MotorID, RobotMap.kDriveMotorSafety);
	public static CAN_TalonSRXE  rightDriveMotor2 = new CAN_TalonSRXE(RobotMap.kRightDrive2MotorID, RobotMap.kDriveMotorSafety);
	public static CAN_TalonSRXE rightDriveMotor = new CAN_TalonSRXE(RobotMap.kRightDriveMotorID, RobotMap.kDriveMotorSafety);
	public static DriveState controlStatus = DriveState.NOFINETUNE;
	//Subsystems
	public static Intake intake = new Intake();
	public static Elevator elevator = new Elevator();
	public static Climber climber = new Climber();
	public static Arm arm = new Arm();
	public static LimitSwitch upperElevatorLS = new LimitSwitch(RobotMap.kUpperElevatorLSPort);
	public static LimitSwitch lowerElevatorLS = new LimitSwitch(RobotMap.kLowerElevatorLSPort);
	//Control
	public static OI oi = new OI();
	//Other
	public static PowerDistributionPanel PDP = new PowerDistributionPanel(RobotMap.kPDP);
	//public static Compressor compressor = new Compressor();
	public static InteractiveLEDS lights = new InteractiveLEDS();
	//Autonomous
	static Command autoRoutine;	
	SendableChooser<Command> autoChooser = new SendableChooser<>();
	public static IntegratedMP rodMP = new IntegratedMP(leftDriveMotor, rightDriveMotor);

	@Override
	public void robotInit() {
		roboDrive = new DifferentialDrive(new SpeedControllerGroup(leftDriveMotor, leftDriveMotor2), new SpeedControllerGroup(rightDriveMotor, rightDriveMotor2));
		rodMP.reset();
		/*autoChooser.addDefault(...);
		SmartDashboard.putData("Auto Routine", autoChooser);*/
		PDP.clearStickyFaults();
		
		leftDriveMotor.setUpMotionProfile();
		rightDriveMotor.setUpMotionProfile();
		
		autoChooser.addDefault("MP", new DriveRobotDrive());
		SmartDashboard.putData(autoChooser);
		SmartDashboard.putData("Bring Down Intake From vertical", new OpenIntakeVertical());
		SmartDashboard.putData("Test", new DriveRobotDrive());
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		autoRoutine = autoChooser.getSelected();

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
		/*if (autoRoutine != null) {
			autoRoutine.cancel();
		}*/
	}

	@Override
	public void teleopPeriodic() {
		runDriveTrain();
		//roboDrive.tankDrive(-oi.driverController.getY(Hand.kLeft) * .98 , -oi.driverController.getY(Hand.kRight));
		//roboDrive.arcadeDrive(-oi.driverController.getY(), oi.driverController.getX());
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
	
	public void runDriveTrain(){
	double fineTuneY = 0.0;
	double fineTuneX = 0.0;
		switch(controlStatus){
		case FINETUNE:
				fineTuneY = oi.stick.getY();
				fineTuneX = oi.stick.getX();
			break;
		default:
			break;	
		}
		//roboDrive.arcadeDrive(-oi.driverController.getY(Hand.kLeft) + -fineTuneY, (oi.driverController.getX(Hand.kRight) + fineTuneX) * .8);
	}
}
