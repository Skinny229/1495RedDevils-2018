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

import org.usfirst.frc.team1495.robot.commands.DriveForward;
import org.usfirst.frc.team1495.robot.subsystems.Arm;
import org.usfirst.frc.team1495.robot.subsystems.CAN_TalonSRXE;
import org.usfirst.frc.team1495.robot.subsystems.Climber;
import org.usfirst.frc.team1495.robot.subsystems.Elevator;
import org.usfirst.frc.team1495.robot.subsystems.Intake;

public class Robot extends TimedRobot {
	//Drive
	public static DifferentialDrive roboDrive;
	public static CAN_TalonSRXE leftDriveMotor;
	public static CAN_TalonSRXE rightDriveMotor;
	//Subsystems
	public static Intake intake;
	public static Elevator elevator;
	public static Climber climber;
	public static Arm arm;
	//Control
	public static OI oi;
	//Other
	//public static PowerDistributionPanel PDP;
	public static Compressor compressor;
	static Command autoRoutine;

	SendableChooser<Command> autoChooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		leftDriveMotor = new CAN_TalonSRXE(RobotMap.kLeftDriveMotorID, RobotMap.kDriveMotorSafety);
		rightDriveMotor = new CAN_TalonSRXE(RobotMap.kRightDriveMotorID, RobotMap.kDriveMotorSafety);
		roboDrive = new DifferentialDrive(leftDriveMotor, rightDriveMotor);
		intake = new Intake();
		elevator = new Elevator();
		climber = new Climber();
		oi = new OI();
		//arm = new Arm();
		//PDP = new PowerDistributionPanel(RobotMap.kPDP);
		compressor = new Compressor();
		//PDP.clearStickyFaults();
		autoChooser.addDefault(".5s Forward", new DriveForward());
		SmartDashboard.putData("Auto Routine", autoChooser);
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
		if (autoRoutine != null) {
			autoRoutine.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		roboDrive.arcadeDrive(-oi.driverController.getY(Hand.kLeft), oi.driverController.getX(Hand.kRight));
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
