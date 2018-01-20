package org.usfirst.frc.team1495.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI oi;
	
	public static DifferentialDrive roboDrive;
	
	//Command autonomousCommand;
//	SendableChooser<Command> autoChooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		oi = new OI();
		//chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
//		SmartDashboard.putData("Auto mode", autoChooser);
		roboDrive = new DifferentialDrive(new VictorSP(RobotMap.DRIVE_PORT_LEFT), new VictorSP(RobotMap.DRIVE_PORT_RIGHT));
		roboDrive.setSafetyEnabled(false);		
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
//		autonomousCommand = autoChooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
//		if (autonomousCommand != null) {
//			autonomousCommand.start();
//		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
//		if (autonomousCommand != null) {
//			autonomousCommand.cancel();
//		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		roboDrive.arcadeDrive(-OI.joystick.getY(), OI.joystick.getTwist());
	}

	@Override
	public void testPeriodic() {
	}
}
