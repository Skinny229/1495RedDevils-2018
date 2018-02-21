package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LimitSwitch extends Subsystem {
	
	public DigitalInput limit;
	
	public LimitSwitch(int port){
		limit = new DigitalInput(port);
	}

	@Override
	protected void initDefaultCommand() {
		
		
	}
	
    


}

