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
	
	private char startingPos, switchPos;
	private int targetLoc, otherLoc;
	
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
    public AutoRunnerMP(char startPos, char closeSwitchPos,int priorityLoc) {
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
    	
    	requires(Robot.arm);
    	requires(Robot.intake);
    	requires(Robot.elevator);
    	
    	//No matter what run these
    	this.addParallel(new OpenIntake(RobotMap.kDefDownDir));
    	this.addParallel(new RaiseElev(ElevPos.kSwitch));
    	
    	startingPos = startPos;
    	switchPos = closeSwitchPos;
    	targetLoc = priorityLoc;
    	if(targetLoc == 0)
    		otherLoc = 1;
    	else if(targetLoc == 1)
    		otherLoc = 0;
    	else
    		otherLoc = -1;
    	
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
		if(switchPos == 'L'){
			//Add Middle MP Runner 
		}else if(switchPos == 'R'){
			//Add Middle -> R MP Runner
		}
		this.addSequential(new dropCubeAuto());
	}
	
	public void addSideRunner(){
		if(startingPos == Robot.gameData.charAt(targetLoc)){
			goToLocalTarget(targetLoc);
		}else if(startingPos == Robot.gameData.charAt(otherLoc)){
			goToLocalTarget(otherLoc);
		}else if(Robot.canCross && Robot.goScale){
			
		}
			
	}
	
	public void addCrossRunner(){
		if(startingPos == 'L'){
			//Add Left->Right MP cross
		}else if(startingPos == 'R'){
			//Add Right->Left MP Cross
		}
		this.addSequential(new RaiseElev(ElevPos.kScale));
		this.addSequential(new dropCubeAuto());
	}
	public void goToLocalTarget(int target){
		if(target == 0){
			double angle = 90.0;
			if (Robot.posStart == 'R') {
				angle *= -1;
			}
			this.addSequential(new TurnXDegrees(angle));
			this.addSequential(new DriveToWall());
			this.addSequential(new dropCubeAuto());
		}else if(target == 1 && Robot.goScale){
			this.addParallel(new RaiseElev(ElevPos.kScale));
			//this.addParallel(new DriveDistMotionProfile(something something trajectory));
			this.addSequential(new dropCubeAuto());
		}
	}
}
