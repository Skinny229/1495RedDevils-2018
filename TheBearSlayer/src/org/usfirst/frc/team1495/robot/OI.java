package org.usfirst.frc.team1495.robot;

import org.usfirst.frc.team1495.robot.commands.Elevate;
import org.usfirst.frc.team1495.robot.commands.SpinIntake;
import org.usfirst.frc.team1495.robot.commands.theClaw;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	public XboxController operatorController = new XboxController(RobotMap.kOperatorControllerPort);
	public Joystick joystick = new Joystick(RobotMap.kTestJoystickPort);

	public Button in = new JoystickButton(driverController, 5); // Intake Motors
	public Button out = new JoystickButton(driverController, 6); // Intake Motors
	public Button raise = new JoystickButton(driverController, 3); // Elevator Motor
	public Button lower = new JoystickButton(driverController, 4); // Elevator Motor
	public Button forward = new JoystickButton(driverController, 1); // Claw Solenoids
	public Button reverse = new JoystickButton(driverController, 2); // Claw Solenoids
	public Button off = new JoystickButton(driverController, 8); // Claw Solenoids

	public OI() {
		in.whileHeld(new SpinIntake(1.0));
		out.whileHeld(new SpinIntake(-1.0));
		raise.whileHeld(new Elevate(-1.0));
		lower.whileHeld(new Elevate(1.0));
		//forward.whenPressed(new theClaw(DoubleSolenoid.Value.kForward));
		//reverse.whenPressed(new theClaw(DoubleSolenoid.Value.kReverse));
		//off.whenPressed(new theClaw(DoubleSolenoid.Value.kOff));
	}
}
