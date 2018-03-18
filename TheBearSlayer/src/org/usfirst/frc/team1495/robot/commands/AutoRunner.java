package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

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

	public AutoRunner() {

		requires(Robot.intake);
		requires(Robot.arm);
		requires(Robot.elevator);

		// No matter what raise the elevator to the switch position and and drop the
		// intake
		this.addParallel(new RaiseElev(ElevPos.kSwitch));
		this.addParallel(new OpenIntake(RobotMap.kDefDownDir));
		System.out.print("Analyzing where to go...");
		switch (Robot.posStart) {
		case 'L':
		case 'R':
			addSideRunner();
			break;
		case 'M':
			addMiddleRunner();
			
			target = "switch";
			break;
		}
	}

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

	public void addSideRunner() {
		this.addSequential(new DriveDistEncoder(RobotMap.distSwitch));
		if (Robot.posStart == Robot.gameData.charAt(Robot.priorityLocation)) {
			goToLocation(Robot.priorityLocation);
		} else if (Robot.posStart == Robot.gameData.charAt(Robot.otherLocation)) {
			goToLocation(Robot.otherLocation);
		}
	}

	public void goToLocation(int location) {
		if (location == 0) {
			addTurnNRam();
		} else if (location == 1 && Robot.goScale) {
			addScaleRunner();
		}
	}

	public void addTurnNRam() {
		if (Robot.posStart == 'R') {
			angleTurnin *= -1;
		}
		this.addSequential(new TurnXDegrees(angleTurnin));
		this.addSequential(new DriveToWall());
		this.addSequential(new dropCubeAuto());
	}

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
