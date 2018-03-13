package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRunner extends CommandGroup {

	
	public double angleTurnin = 90.0;
	private char switchSide = Robot.gameData.charAt(0);
    public AutoRunner() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	requires(Robot.intake);
    	requires(Robot.arm);
    	requires(Robot.elevator);
    	//Raise Elev
    	this.addParallel(new RaiseElev());
    }
    

    
    public void addMiddleRunner() {
    	this.addParallel(new DriveDistEncoder(60));
    	if(switchSide == 'L') {
    			angleTurnin *= -1;
    		}
    	this.addSequential(new TurnXDegrees(angleTurnin));
    	this.addSequential(new DriveDistEncoder(60));
    	angleTurnin *= -1;
    	this.addSequential(new TurnXDegrees(angleTurnin));
    	this.addSequential(new DriveToWall());
    	this.addSequential(new dropCubeAuto());
    }
    
    public void addSideRunner() {
    	this.addParallel(new DriveDistEncoder(RobotMap.distSwitch));
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
    	
    }
    
    
}
