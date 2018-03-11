package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class NoDrive extends Command{
	
	protected void intialize(){
		Robot.roboDrive.arcadeDrive(.65, 0);
		Timer.delay(5.0);
		Robot.roboDrive.stopMotor();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
