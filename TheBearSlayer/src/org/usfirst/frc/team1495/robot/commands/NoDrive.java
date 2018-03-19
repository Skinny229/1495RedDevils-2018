package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class NoDrive extends Command{
	
	
	private boolean isDone = false;
	protected void intialize(){
		Robot.roboDrive.arcadeDrive(.65, 0);
		isDone = false;
	}
	
	protected void execute(){
		if(timeSinceInitialized() < 3.0){
			Robot.roboDrive.arcadeDrive(.65, 0);
		}else{
			isDone = true;
		}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isDone;
	}

}
