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
import org.usfirst.frc.team1495.robot.subsystems.CAN_TalonSRX;
import org.usfirst.frc.team1495.robot.subsystems.CAN_TalonSRXE;
import org.usfirst.frc.team1495.robot.subsystems.Climber;
import org.usfirst.frc.team1495.robot.subsystems.Elevator;
import org.usfirst.frc.team1495.robot.subsystems.Intake;
import org.usfirst.frc.team1495.robot.subsystems.IntegratedMP;
import org.usfirst.frc.team1495.robot.subsystems.LimitSwitch;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

@SuppressWarnings("unused")
public class Robot extends TimedRobot {
	public enum DriveState{
		FINETUNE, NOFINETUNE
	}
	
	//Drive
	public static DifferentialDrive roboDrive;
	public static WPI_TalonSRX leftDriveMotor = new CAN_TalonSRXE(RobotMap.kLeftDriveMotorID, RobotMap.kDriveMotorSafety);
	public static WPI_TalonSRX  leftDriveMotor2 = new CAN_TalonSRXE(RobotMap.kLeftDrive2MotorID, RobotMap.kDriveMotorSafety);
	public static WPI_TalonSRX  rightDriveMotor2 = new CAN_TalonSRXE(RobotMap.kRightDrive2MotorID, RobotMap.kDriveMotorSafety);
	public static WPI_TalonSRX rightDriveMotor = new CAN_TalonSRXE(RobotMap.kRightDriveMotorID, RobotMap.kDriveMotorSafety);
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
	public static IntegratedMP rodMP = new IntegratedMP(leftDriveMotor, leftDriveMotor2);

	@Override
	public void robotInit() {
		roboDrive = new DifferentialDrive(new SpeedControllerGroup(leftDriveMotor, leftDriveMotor2), new SpeedControllerGroup(rightDriveMotor, rightDriveMotor2));
		rodMP.reset();
		/*autoChooser.addDefault(...);
		SmartDashboard.putData("Auto Routine", autoChooser);*/
		PDP.clearStickyFaults();
		

		leftDriveMotor2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		leftDriveMotor2.setSensorPhase(true); /* keep sensor and motor in phase */
		//rightDriveMotor.configNeutralDeadband(Constants.kNeutralDeadband, 0);

		leftDriveMotor2.config_kF(0, 0.076, 0);
		leftDriveMotor2.config_kP(0, .4, 0);
		leftDriveMotor2.config_kI(0, 0.0, 0);
		leftDriveMotor2.config_kD(0, 20.0, 0);

		/* Our profile uses 10ms timing */
		leftDriveMotor2.configMotionProfileTrajectoryPeriod(10, 0); 
		/*
		 * status 10 provides the trajectory target for motion profile AND
		 * motion magic
		 */
		leftDriveMotor2.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
		

		
		leftDriveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		leftDriveMotor.setSensorPhase(true); /* keep sensor and motor in phase */
		//rightDriveMotor.configNeutralDeadband(Constants.kNeutralDeadband, 0);

		leftDriveMotor.config_kF(0, 0.076, 0);
		leftDriveMotor.config_kP(0, .4, 0);
		leftDriveMotor.config_kI(0, 0.0, 0);
		leftDriveMotor.config_kD(0, 0, 0);

		/* Our profile uses 10ms timing */
		leftDriveMotor.configMotionProfileTrajectoryPeriod(10, 0); 
		/*
		 * status 10 provides the trajectory target for motion profile AND
		 * motion magic
		 */
		leftDriveMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
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
				fineTuneY = oi.stick.getY() * .2;
				fineTuneX = oi.stick.getX() * .2;
			break;
		default:
			break;	
		}
		roboDrive.arcadeDrive(-oi.driverController.getY(Hand.kLeft) + -fineTuneY, (oi.driverController.getX(Hand.kRight) + fineTuneX) * .8);
	}
}
