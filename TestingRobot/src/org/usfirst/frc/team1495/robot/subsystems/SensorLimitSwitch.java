package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SensorLimitSwitch extends Subsystem {

    private DigitalInput limitSwitch;
	
	public void initDefaultCommand() { }
    
    public SensorLimitSwitch(int port){
     limitSwitch = new DigitalInput(port);	
    }
    
    public boolean isSwitchPressed(){
    	return limitSwitch.get();
    }
}

