package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;
import org.usfirst.frc.team1495.robot.subsystems.MotionProfileRunner;
import org.usfirst.frc.team1495.robot.subsystems.MotionProfileTrajectories;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRunner extends CommandGroup {

	private double angleTurnin = 90.0;

	public enum ElevPos {
		kSwitch, kScale, kStop
	}

	public static String target = " ";
	
	public static MotionProfileRunner mpExecuter = new MotionProfileRunner(Robot.leftDriveMotor, Robot.rightDriveMotor);
	
	public AutoRunner() {

		requires(Robot.intake);
		requires(Robot.arm);
		requires(Robot.elevator);

		// No matter what raise the elevator to the switch position and and drop the
		// intake
		this.addParallel(new RaiseElev(ElevPos.kSwitch));
		this.addParallel(new OpenIntake(RobotMap.kDefDownDir));
		
		if(Robot.gameData.charAt(0) == 'T'){
			this.addParallel(new DriveDistMotionProfile(MotionProfileTrajectories.Points));
			return;
		}
		
		switch (Robot.posStart) {
		case 'L':
		case 'R':
			addSideRunner();
			break;
		case 'M':
			target = "switch";
			addMiddleRunner();
			break;
		default:
			this.addParallel(new NoDrive()); // Will just drive straight foward
		}
	}

	/**
	 * Auto Sequence for when we start in the middle
	 * */
	public void addMiddleRunner() {
		this.addParallel(new DriveDistEncoder(RobotMap.distStartingMiddle));
		if (Robot.gameData.charAt(0) == 'L') {
			angleTurnin *= -1;
			// Turn Left is the switch side is on our left
		}
		this.addSequential(new TurnXDegrees(angleTurnin));
		if (Robot.gameData.charAt(0) == 'R') {
			this.addSequential(new DriveDistEncoder(20.0));
		} else {
			this.addSequential(new DriveDistEncoder(40.0));
		}
		angleTurnin *= -1;
		this.addSequential(new TurnXDegrees(angleTurnin));
		this.addSequential(new DriveToWall());
		this.addSequential(new dropCubeAuto());
	}

	/**
	 * Current Autonomous for when the robot is on the sides
	 * */
	public void addSideRunner() {
		this.addSequential(new DriveDistEncoder(RobotMap.distSwitch));
		if (Robot.posStart == Robot.gameData.charAt(Robot.priorityLocation)) {
			goToLocation(Robot.priorityLocation);
		} else if (Robot.posStart == Robot.gameData.charAt(Robot.otherLocation)) {
			goToLocation(Robot.otherLocation);
		}else if(Robot.canCross){
			cross(Robot.priorityLocation);
		}
	}

	public void cross(int location){
		//Align with path way
		this.addSequential(new DriveDistEncoder(30.0));
		if(Robot.posStart == 'R'){
			angleTurnin *= -1;
		}
		this.addSequential(new TurnXDegrees(angleTurnin));
		this.addSequential(new DriveDistEncoder(160.0));
		if(Robot.priorityLocation == 0){
			this.addSequential(new TurnXDegrees(angleTurnin));
			this.addSequential(new DriveToWall());
			this.addSequential(new dropCubeAuto());
		}else if(Robot.priorityLocation == 1){
			angleTurnin *= -1;
			this.addParallel(new TurnXDegrees(angleTurnin));
			this.addParallel(new RaiseElev(ElevPos.kScale));
			this.addSequential(new dropCubeAuto());
			
		}
		
	}
	
	
	
	/**
	 * Heads to a location on the Robot's starting side
	 * 
	 * @param
	 * location 0 or 1. 0 being the switch and 1 being scale
	 * */
	public void goToLocation(int location) {
		if (location == 0) {
			addTurnNRam();
		} else if (location == 1 && Robot.goScale) {
			addScaleRunner();
		}
	}

	/**
	 * 
	 * Turns inwards the field and will drive straight until it is stopped
	 * by a field element
	 * 
	 * */
	public void addTurnNRam() {
		if (Robot.posStart == 'R') {
			angleTurnin *= -1;
		}
		this.addSequential(new TurnXDegrees(angleTurnin));
		this.addSequential(new DriveToWall());
		this.addSequential(new dropCubeAuto());
	}

	/**
	 *  Auto sequence to going to the scale from the switch side
	 *  
	 * */
	public void addScaleRunner() {
		this.addSequential(new DriveDistEncoder(RobotMap.distScaleFromSwitch));
		if (Robot.posStart == 'R') {
			angleTurnin *= -1;
		}
		this.addSequential(new TurnXDegrees(angleTurnin));
		this.addSequential(new RaiseElev(ElevPos.kScale));
		this.addSequential(new dropCubeAuto(-1.0));
	}

}
