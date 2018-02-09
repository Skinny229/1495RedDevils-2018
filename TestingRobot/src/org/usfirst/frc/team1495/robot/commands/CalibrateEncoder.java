package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CalibrateEncoder extends Command {

    public CalibrateEncoder() {

    }

    boolean completed = false;
    int distance;
    
    protected void initialize() {
    	distance = Robot.talon.getSelectedSensorPosition(0);
    	Robot.roboDrive.arcadeDrive(-.5, 0);
    	Timer.delay(1);
    	Robot.roboDrive.stopMotor();
    }

    protected void execute() {
    	if(Robot.talon.getSelectedSensorVelocity(0) == 0)
    	{
    		System.out.println("Encoder Value: " + Math.abs(Math.abs(Robot.talon.getSelectedSensorPosition(0)) - Math.abs(distance)));
    		completed = true;
    	}
    }

    protected boolean isFinished() {
        return completed;
    }

    protected void end() {
    	Robot.roboDrive.stopMotor();
    }

    protected void interrupted() {
    	Robot.roboDrive.stopMotor();
    }
}
