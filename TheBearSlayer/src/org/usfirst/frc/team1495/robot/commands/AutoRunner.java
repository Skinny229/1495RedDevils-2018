package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRunner extends CommandGroup {

	
	private double angleTurnin = 90.0;
	public enum ElevPos{
		kSwitch,kScale,kStop
	}
	
	
    public AutoRunner() {
    	
    	requires(Robot.intake);
    	requires(Robot.arm);
    	requires(Robot.elevator);
    	
    	
		System.out.println("Game Data: ");
		System.out.println("Friendly Switch: " + Robot.gameData.charAt(0));
		System.out.println("Scale: " + Robot.gameData.charAt(1));
		System.out.println("Enemy Switch: " + Robot.gameData.charAt(2));
		System.out.println("Selected Starting Position: " + Robot.posStart);
    	
    	//No matter what raise the elevator to the switch position and and drop the intake
    	this.addParallel(new RaiseElev(ElevPos.kSwitch));
    	this.addParallel(new OpenIntake(true));
		switch (Robot.posStart) {
		case 'L':
		case 'R':
			addSideRunner();
			if (Robot.posStart == Robot.gameData.charAt(0)){
		      	addTurnNRam();
				System.out.println("Targeting Switch....");
			}
			else if(Robot.posStart == Robot.gameData.charAt(1) && Robot.goScale){
				addScaleRunner();
				System.out.println("Targetin Scale...");
			}
			break;
		case 'M':
				addMiddleRunner();
			break;
		}
    }
    

    
    public void addMiddleRunner() {
    	this.addParallel(new DriveDistEncoder(RobotMap.distSwitchFromMiddle));
    	if(Robot.gameData.charAt(0) == 'L') {
    			angleTurnin *= -1;
    		}
    	this.addSequential(new TurnXDegrees(angleTurnin));
    	this.addSequential(new DriveDistEncoder(RobotMap.distSwitchFromMiddle));
    	angleTurnin *= -1;
    	this.addSequential(new TurnXDegrees(angleTurnin));
    	this.addSequential(new DriveToWall());
    	this.addSequential(new dropCubeAuto());
    }
    
    public void addSideRunner() {
    	this.addSequential(new DriveDistEncoder(RobotMap.distSwitch));
    }
    
    public void addTurnNRam() {
    	if(Robot.posStart == 'L') {
    		angleTurnin *= -1;
    	}
    	this.addSequential(new TurnXDegrees(angleTurnin));
    	this.addSequential(new DriveToWall());
    	this.addSequential(new dropCubeAuto());
    }
    
    public void addScaleRunner() {
    	System.out.print("Nothing for now...");
    	/*
    	 * Not to be use right now
    	 * 
    	 * 
    	this.addParallel(new DriveDistEncoder(RobotMap.distScaleFromSwitch));
    	this.addParallel(new RaiseElev());
    	*/
    }
    
    
}
