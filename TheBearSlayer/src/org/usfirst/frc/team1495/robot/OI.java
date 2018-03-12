package org.usfirst.frc.team1495.robot;

import org.usfirst.frc.team1495.robot.commands.ChangeDriveStatus;
import org.usfirst.frc.team1495.robot.commands.Climb;
import org.usfirst.frc.team1495.robot.commands.Elevate;
import org.usfirst.frc.team1495.robot.commands.OpenIntake;
//import org.usfirst.frc.team1495.robot.commands.OpenIntake;
import org.usfirst.frc.team1495.robot.commands.SpinIntake;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/* - DS4 / Xbox Controller Buttons Numbers
 * 1		X / A
 * 2		O / B
 * 3		Square / X
 * 4		Triangle / Y
 * 5		Left Bumper
 * 6		Right Bumper
 * 7		Share
 * 8		Options
 * 9		Left Stick
 * 10		Right Stick
 */

public class OI {
	public XboxController driverController = new XboxController(RobotMap.kDriverControllerPort);
	// public XboxController operatorController = new
	// XboxController(RobotMap.kOperatorControllerPort);
	public Joystick stick = new Joystick(RobotMap.kOperatorControllerPort);

	/*
	 * // Xbox public Button in = new JoystickButton(driverController, 5); public
	 * Button out = new JoystickButton(driverController, 6); public Button lower =
	 * new JoystickButton(driverController, 3); public Button raise = new
	 * JoystickButton(driverController, 4); public Button climb = new
	 * JoystickButton(driverController, 8); public Button drop = new
	 * JoystickButton(driverController, 7); public Button open = new
	 * JoystickButton(driverController, 9); public Button close = new
	 * JoystickButton(driverController, 10);
	 */

	// Joystick
	public Button in = new JoystickButton(stick, 2);
	public Button out = new JoystickButton(stick, 1);
	public Button lower = new JoystickButton(stick, 3);
	public Button raise = new JoystickButton(stick, 5);
	public Button climb = new JoystickButton(stick, 7);
	public Button open = new JoystickButton(stick, 4);
	public Button close = new JoystickButton(stick, 6);
	public Button enblTune = new JoystickButton(stick, 11);
	public Button dsblTune = new JoystickButton(stick, 12);

	public OI() {

		in.whileHeld(new SpinIntake(-1.0));
		out.whileHeld(new SpinIntake(1.0));
		raise.whileHeld(new Elevate(-1.0));
		lower.whileHeld(new Elevate(1.0));
		close.whenPressed(new OpenIntake(false));
		open.whenPressed(new OpenIntake(true));
		climb.whileHeld(new Climb(1.0));
		enblTune.whenPressed(new ChangeDriveStatus(Robot.DriveState.FINETUNE));
		dsblTune.whenPressed(new ChangeDriveStatus(Robot.DriveState.NOFINETUNE));
	}
}
