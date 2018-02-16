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
import org.usfirst.frc.team1495.robot.subsystems.CAN_TalonSRXE;
import org.usfirst.frc.team1495.robot.subsystems.Claw;
import org.usfirst.frc.team1495.robot.subsystems.Climber;
import org.usfirst.frc.team1495.robot.subsystems.Elevator;
import org.usfirst.frc.team1495.robot.subsystems.Intake;
import org.usfirst.frc.team1495.robot.subsystems.PWM_VictorSP;

public class Robot extends TimedRobot {
	//Drive
	public static DifferentialDrive roboDrive;
	public static CAN_TalonSRXE leftDriveMotor;
	public static CAN_TalonSRXE rightDriveMotor;
	//Subsystems
	public static Intake intake;
	public static Elevator elevator;
	public static Climber climber;
	public static Claw claw;
	//Control
	public static OI oi;
	//Other
	//public static PowerDistributionPanel PDP;
	//public static Compressor compressor;

	SendableChooser<Command> autoChooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		//leftDriveMotor = new CAN_TalonSRXE(RobotMap.kLeftDriveMotorID, RobotMap.kDriveMotorSafety);
		//rightDriveMotor = new CAN_TalonSRXE(RobotMap.kRightDriveMotorID, RobotMap.kDriveMotorSafety);
		//roboDrive = new DifferentialDrive(leftDriveMotor, rightDriveMotor);
		intake = new Intake();
		elevator = new Elevator();
		climber = new Climber();
		oi = new OI();
		//PDP = new PowerDistributionPanel(RobotMap.kPDP);
		//compressor = new Compressor();
		//PDP.clearStickyFaults();
		/*
		autoChooser.addDefault("Default Auto", new ExampleCommand());
		chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", autoChooser);
		*/
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
		//m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.start();
		//}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.cancel();
		//}
	}

	@Override
	public void teleopPeriodic() {
		//roboDrive.arcadeDrive(-oi.driverController.getY(Hand.kLeft), oi.driverController.getX(Hand.kRight));
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
