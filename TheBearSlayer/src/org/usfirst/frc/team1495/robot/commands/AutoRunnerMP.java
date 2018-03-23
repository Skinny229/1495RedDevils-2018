package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;
import org.usfirst.frc.team1495.robot.commands.AutoRunner.ElevPos;
import org.usfirst.frc.team1495.robot.subsystems.MotionProfileRunner;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRunnerMP extends CommandGroup {

	public static MotionProfileRunner mpExecuter = new MotionProfileRunner(Robot.leftDriveMotor, Robot.rightDriveMotor);
	
	
	
	/**
	 * Runs Autonomous using motion profiles for motion
	 * 
	 * @param startPos
	 *  Starting Location of the Robot
	 * 
	 * @param localSwitchPos
	 * 	Position given by game data of the location of our side of the switch in our side
	 * 
	 * @param
	 * 	Priotizing either Switch or Scale
	 * */
    public AutoRunnerMP(char startPos, char localSwitchPos,int priorityLoc) {
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
    	int target;
    	boolean isCrossing;
    	requires(Robot.arm);
    	requires(Robot.intake);
    	requires(Robot.elevator);
    	
    	//No matter what run these
    	this.addParallel(new OpenIntake(RobotMap.kDefDownDir));
    	this.addParallel(new RaiseElev(ElevPos.kSwitch));
    	
    	switch(startPos){
    	case 'L':
    	case 'R':
    		addSideRunner();    		
    		break;
    	case 'M':
    		addMiddleRunner();
    		return;
    	default:
    		System.out.println("Starting Position Error! Moving straight foward");
    		this.addParallel(new NoDrive());
    		return;
    		
    	}
    	
    }
    
    
	public void addMiddleRunner() {
		double turnAngle = 90;
		this.addParallel(new DriveDistEncoder(RobotMap.distStartingMiddle));
		if (Robot.gameData.charAt(0) == 'L') {
			turnAngle *= -1;
			// Turn Left is the switch side is on our left
		}
		this.addSequential(new TurnXDegrees(turnAngle));
		if (Robot.gameData.charAt(0) == 'R') {
			this.addSequential(new DriveDistEncoder(20.0));
		} else {
			this.addSequential(new DriveDistEncoder(40.0));
		}
		//Revert back to where we were
		turnAngle *= -1;
		this.addSequential(new TurnXDegrees(turnAngle));
		this.addSequential(new DriveToWall());
		this.addSequential(new dropCubeAuto());
	}
	
	public void addSideRunner(){
		
	}
	
}
