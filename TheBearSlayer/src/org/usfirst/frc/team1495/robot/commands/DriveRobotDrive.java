package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRobotDrive extends Command {

	
    public DriveRobotDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.rodMP.reset();
    	Robot.rodMP.startMotionProfile();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.rodMP.control();
    //	Robot.rightDriveMotor.set
    	SetValueMotionProfile setOutput = Robot.rodMP.getSetValue();

		Robot.leftDriveMotor.set(ControlMode.MotionProfile, setOutput.value);
		Robot.leftDriveMotor2.set(ControlMode.Follower, RobotMap.kLeftDriveMotorID);
		Robot.rightDriveMotor.set(ControlMode.MotionProfile, setOutput.value);
		Robot.rightDriveMotor2.set(ControlMode.Follower, RobotMap.kRightDriveMotorID);
    	
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
