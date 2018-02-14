package org.usfirst.frc.team1495.robot;

import edu.wpi.first.wpilibj.XboxController;

public class OI {
	public XboxController driverController = new XboxController(RobotMap.kDriverControllerPort);
	public XboxController operatorController = new XboxController(RobotMap.kOperatorControllerPort);
	
	public OI() {
		
	}
}
