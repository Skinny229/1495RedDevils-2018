package org.usfirst.frc.team1495.robot.commands;



import org.usfirst.frc.team1495.robot.MPV2;
import org.usfirst.frc.team1495.robot.Robot;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MPAuto extends Command {

    public MPAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    boolean isFin;
    MPV2 autoMP = Robot.mp2;
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	isFin = false;
    	autoMP.reset();
    	autoMP.control();
    	System.out.println("MP Ready to rock and roll going to control loop...");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	autoMP.control();
    	
    	SetValueMotionProfile output = autoMP.getSetValue();
    	
    	Robot.driveTalonL.set(ControlMode.MotionProfile, output.value);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
