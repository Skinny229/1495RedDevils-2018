package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	CAN_TalonSRX talon;

	public Climber() {
		talon = new CAN_TalonSRX(RobotMap.kClimberTalon, RobotMap.kClimberSafety);
	}

	public void initDefaultCommand() {
	}
}
