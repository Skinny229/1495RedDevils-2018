/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1495.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//PWM
	
	
	//USB
	public static final int DRIVE_CONTROLLER = 0;
	public static final int HELPER_CONTROLLER = 1;
	
	//CAN
	public static final int PCM           = 0;
	public static final int PDP           = 1;
	public static final int kDriveTalonL  = 2;
	public static final int kDriveTalonR  = 3;
	
	
}
