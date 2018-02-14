package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	CAN_TalonSRXE talon;

	public Elevator() {
		talon = new CAN_TalonSRXE(RobotMap.kElevatorTalon, RobotMap.kElevatorSafety);
	}

	public void initDefaultCommand() {
	}
}
