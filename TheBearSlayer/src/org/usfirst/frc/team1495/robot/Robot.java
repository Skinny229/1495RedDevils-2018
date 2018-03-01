package org.usfirst.frc.team1495.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1495.robot.subsystems.Arm;
import org.usfirst.frc.team1495.robot.subsystems.CAN_TalonSRXE;
import org.usfirst.frc.team1495.robot.subsystems.Climber;
import org.usfirst.frc.team1495.robot.subsystems.Elevator;
import org.usfirst.frc.team1495.robot.subsystems.Intake;
import org.usfirst.frc.team1495.robot.subsystems.LimitSwitch;

@SuppressWarnings("unused")
public class Robot extends TimedRobot {
	//Drive
	public static MechCantMech roboDrive;
	public static CAN_TalonSRXE leftDriveMotor;
	public static CAN_TalonSRXE rightDriveMotor;
	//Subsystems
	public static Intake intake;
	public static Elevator elevator;
	public static Climber climber;
	public static Arm arm = new Arm();
	//public static LimitSwitch upperElevatorLS;
	//public static LimitSwitch lowerElevatorLS;
	//Control
	public static OI oi;
	//Other
	public static PowerDistributionPanel PDP;
	public static Compressor compressor;
	public static InteractiveLEDS lights;
	//Autonomous
	//static Command autoRoutine;	
	//SendableChooser<Command> autoChooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		leftDriveMotor = new CAN_TalonSRXE(RobotMap.kLeftDriveMotorID, RobotMap.kDriveMotorSafety);
		rightDriveMotor = new CAN_TalonSRXE(RobotMap.kRightDriveMotorID, RobotMap.kDriveMotorSafety);
		roboDrive = new MechCantMech(leftDriveMotor, rightDriveMotor);
		intake = new Intake();
		elevator = new Elevator();
		climber = new Climber();
		oi = new OI();
		//arm = new Arm();
		//upperElevatorLS = new LimitSwitch(RobotMap.kUpperElevatorLSPort);
		//lowerElevatorLS = new LimitSwitch(RobotMap.kLowerElevatorLSPort);
		PDP = new PowerDistributionPanel(RobotMap.kPDP);
		compressor = new Compressor();
		lights = new InteractiveLEDS();
		/*autoChooser.addDefault(...);
		SmartDashboard.putData("Auto Routine", autoChooser);*/
		PDP.clearStickyFaults();
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
		/*autoRoutine = autoChooser.getSelected();

		if (autoRoutine != null) {
			autoRoutine.start();
		} */
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
		roboDrive.arcadeModified(-oi.driverController.getY(Hand.kLeft), oi.driverController.getX(Hand.kRight));
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
