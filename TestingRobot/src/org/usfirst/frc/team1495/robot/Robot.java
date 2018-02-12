/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1495.robot;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team1495.robot.commands.DoNothing;
import org.usfirst.frc.team1495.robot.commands.InitMotionProfile;
import org.usfirst.frc.team1495.robot.commands.MPAuto;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	//public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	
	public static OI oi;
	
	public static PowerDistributionPanel pdp = new PowerDistributionPanel(RobotMap.PDP);
	
	
	//Operators
	private XboxController nick = new XboxController(RobotMap.DRIVE_CONTROLLER);
	
	
	//DriveTrain
	public static WPI_TalonSRX driveTalonL = new WPI_TalonSRX(RobotMap.kDriveTalonL);
	//public static WPI_TalonSRX driveTalonR = new WPI_TalonSRX(RobotMap.kDriveTalonR);
	private ArrayList<WPI_TalonSRX> escDrive = new ArrayList<WPI_TalonSRX>();
	
	
	public static DifferentialDrive roboDrive = new DifferentialDrive(driveTalonL, new VictorSP(1));
	
	public static MPV2 mp2 = new MPV2(driveTalonL);
	
	
	public static boolean isMPDone = false;
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	class StickyFaultNoMore implements java.lang.Runnable{
		public void run() {
			pdp.clearStickyFaults();
		}
	}
	
	Notifier noPdpNotify;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Clear Sticky faults and every 10 seconds clear the faults just in case
		pdp.clearStickyFaults();
		noPdpNotify = new Notifier(new StickyFaultNoMore());
		noPdpNotify.startPeriodic(10.0);
		
		//Iniate Button Conditions
		oi = new OI();
		
		//Set up Talon for PID Control Auton
		mp2.startMotionProfile(); //Start Loading Motion Profile and buffering Trajectory Points
		//Config Settings for drive Talons
		escDrive.add(driveTalonL);
	//	escDrive.add(driveTalonR);
		for(int i = 0; i < escDrive.size(); i++){
			WPI_TalonSRX curTalon = escDrive.get(i);
			curTalon.config_kP(0, .1, 0); 
			curTalon.config_kI(0, .2, 0);
			curTalon.setSafetyEnabled(true);
			curTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		}
		
		
		
		//Every 5 Seconds this will clear stick faults no matter what
		
		
		
		chooser.addDefault("Default Auto", new DoNothing());
		chooser.addObject("Motion Profile Test", new InitMotionProfile());
		chooser.addObject("Motion Profile V2 Test", new MPAuto());
		SmartDashboard.putData("Auto mode", chooser);
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
		autonomousCommand = chooser.getSelected();


		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
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
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		roboDrive.arcadeDrive(nick.getY(Hand.kLeft), nick.getX(Hand.kRight));
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
	

	
}
