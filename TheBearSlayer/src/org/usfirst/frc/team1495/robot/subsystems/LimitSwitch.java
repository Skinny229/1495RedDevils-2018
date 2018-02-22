package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LimitSwitch extends DigitalInput {
	
	public LimitSwitch(int port){
		super(port);
	}
}

