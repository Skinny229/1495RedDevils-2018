package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;
import org.usfirst.frc.team1495.robot.subsystems.MotionProfileRunner;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistMotionProfile extends Command {

	double[][] trajectory;
	int trajectoryPointsNum;
	MotionProfileRunner mp = AutoRunnerMP.mpExecuter;
	SetValueMotionProfile setOutput;
	private boolean isDone = false;
	
    public DriveDistMotionProfile(double[][] pointsWanted) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	trajectory = pointsWanted;
    	trajectoryPointsNum = 185;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	mp.startMotionProfile();
    	isDone = false;
    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	mp.control();
    	setOutput = mp.getSetValue();
    	if(setOutput.value == SetValueMotionProfile.Enable.value || timeSinceInitialized() < 3){
    		Robot.leftDriveMotor.set(ControlMode.MotionProfile, setOutput.value);
    		Robot.leftDriveMotor2.set(ControlMode.Follower, RobotMap.kLeftDriveMotorID);
    		Robot.rightDriveMotor.set(ControlMode.MotionProfile, setOutput.value);
    		Robot.rightDriveMotor2.set(ControlMode.Follower, RobotMap.kRightDriveMotorID);
    	}else{
    		isDone = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.leftDriveMotor.set(0);
    	Robot.leftDriveMotor2.set(0);
    	Robot.rightDriveMotor.set(0);
    	Robot.rightDriveMotor2.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.leftDriveMotor.set(0);
    	Robot.leftDriveMotor2.set(0);
    	Robot.rightDriveMotor.set(0);
    	Robot.rightDriveMotor2.set(0);
    }
}

