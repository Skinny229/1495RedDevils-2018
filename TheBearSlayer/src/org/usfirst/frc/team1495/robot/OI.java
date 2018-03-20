package org.usfirst.frc.team1495.robot;

import org.usfirst.frc.team1495.robot.commands.*;

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
	public Joystick operator = new Joystick(RobotMap.kOperatorControllerPort);

	/*
	 * // Xbox public Button climb = new JoystickButton(operator, 1); public
	 * Button openArm = new JoystickButton(operator, 6); public Button closeArm
	 * = new JoystickButton(operator, 5);
	 */

	// Joystick

	public Button in = new JoystickButton(operator, 2);
	public Button out = new JoystickButton(operator, 1);
	public Button climb = new JoystickButton(operator, 8);
	public Button open = new JoystickButton(operator, 3);
	public Button  close = new JoystickButton(operator, 5);

	public OI() {
		// joystick
		in.whileHeld(new SpinIntake(RobotMap.kIntakeSpeed));
		out.whileHeld(new SpinIntake(-RobotMap.kIntakeSpeed));
		close.whenPressed(new OpenIntake(false));
		open.whenPressed(new OpenIntake(true));
		climb.whileHeld(new Climb(RobotMap.kClimberSpeed));

		/*
		 * climb.whileHeld(new Climb(RobotMap.kClimberSpeed));
		 * openArm.whenPressed(new OpenIntake(!RobotMap.kDefDownDir));
		 * closeArm.whenPressed(new OpenIntake(RobotMap.kDefDownDir));
		 */

	}
}
