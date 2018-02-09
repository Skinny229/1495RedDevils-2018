package org.usfirst.frc.team1495.robot;

import org.usfirst.frc.team1495.robot.subsystems.CANTalonSRX;
import org.usfirst.frc.team1495.robot.subsystems.Gyro;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedController;
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
	public static Compressor compressor = new Compressor();
	public static PowerDistributionPanel PDP; //make into subsystem 
	
	//public static WPI_TalonSRX talon2;
	public static CANTalonSRX talon;
	
	public static Gyro gyro;
	
	//Command autonomousCommand;
//	SendableChooser<Command> autoChooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		oi = new OI();
		//chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", autoChooser);
		talon = new CANTalonSRX(RobotMap.CAN_ID_TALON, false);
		//talon2 = new WPI_TalonSRX(2);
		//talon2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		roboDrive = new DifferentialDrive(talon, new VictorSP(RobotMap.DRIVE_PORT_RIGHT));
		roboDrive.setSafetyEnabled(RobotMap.DRIVE_SAFETY);		
		PDP = new PowerDistributionPanel(RobotMap.CAN_ID_PDP);
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
		/* EXPERIMENTAL
		double deadzone = .1;
		double twist = OI.joystick.getTwist();
		double rotation;
		double rotationThrottle = .65;
		if (twist < deadzone && twist > -deadzone)
			rotation = 0;
		else if (twist > deadzone)
			rotation = rotationThrottle*(twist-deadzone)*(1/(1-deadzone));
		else if (twist < -deadzone)
			rotation = rotationThrottle*(twist+deadzone)*(1/(1-deadzone));
		else rotation = 0;
		roboDrive.arcadeDrive(OI.joystick.getY(), rotation); */
		switch(RobotMap.CONTROL_TYPE)
		{
		case SingleController:
			roboDrive.arcadeDrive(OI.controller1.getY(Hand.kLeft), OI.controller1.getX(Hand.kRight));
			break;
		case SingleJoystick:
			roboDrive.arcadeDrive(OI.joystick.getY(), .65*OI.joystick.getTwist());
			break;
		default:
			break;
		}
	}

	@Override
	public void testPeriodic() {
	}
}
