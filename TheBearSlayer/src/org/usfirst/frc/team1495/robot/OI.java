package org.usfirst.frc.team1495.robot;

import org.usfirst.frc.team1495.robot.commands.CloseIntake;
import org.usfirst.frc.team1495.robot.commands.CubeIO;
import org.usfirst.frc.team1495.robot.commands.OpenIntake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public XboxController driverController = new XboxController(RobotMap.kDriverControllerPort);
	public XboxController operatorController = new XboxController(RobotMap.kOperatorControllerPort);
	public Joystick joystick = new Joystick(RobotMap.kTestJoystick);
	
	public Button close = new JoystickButton(joystick, 7);
	public Button open = new JoystickButton(joystick, 8);
	public Button cubeI = new JoystickButton(joystick, 9);
	public Button cubeO = new JoystickButton(joystick, 10);
	
	public OI() {
		close.whenPressed(new CloseIntake());
		open.whenPressed(new OpenIntake());
		cubeI.whileHeld(new CubeIO(.5));
		cubeO.whileHeld(new CubeIO(-.5));
		
	}
}
