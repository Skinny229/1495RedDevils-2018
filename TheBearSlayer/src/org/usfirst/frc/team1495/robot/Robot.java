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
import org.usfirst.frc.team1495.robot.subsystems.Climber;
import org.usfirst.frc.team1495.robot.subsystems.Elevator;
import org.usfirst.frc.team1495.robot.subsystems.Intake;

public class Robot extends TimedRobot {
	//Drive
	public static DifferentialDrive roboDrive;
	public static CAN_TalonSRXE leftDriveTalon;
	public static CAN_TalonSRXE rightDriveTalon;
	//Subsystems
	public static Intake intake;
	public static Elevator elevator;
	public static Climber climber;
	//Control
	public static OI oi;
	//Other
	public static PowerDistributionPanel PDP;
	public static Compressor compressor;

	SendableChooser<Command> autoChooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		leftDriveTalon = new CAN_TalonSRXE(RobotMap.kLeftDriveTalon, RobotMap.kDriveSafety);
		rightDriveTalon = new CAN_TalonSRXE(RobotMap.kRightDriveTalon, RobotMap.kDriveSafety);
		roboDrive = new DifferentialDrive(leftDriveTalon, rightDriveTalon);
		intake = new Intake();
		elevator = new Elevator();
		climber = new Climber();
		oi = new OI();
		PDP = new PowerDistributionPanel(RobotMap.kPDP);
		compressor = new Compressor();
		PDP.clearStickyFaults();
		/*
		autoChooser.addDefault("Default Auto", new ExampleCommand());
		chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", autoChooser);
		*/
	}
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.start();
		//}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.cancel();
		//}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		//roboDrive.arcadeDrive(oi.driverController.getY(Hand.kLeft), oi.driverController.getX(Hand.kRight));
		roboDrive.arcadeDrive(oi.joystick.getY(), .65*oi.joystick.getTwist());
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
