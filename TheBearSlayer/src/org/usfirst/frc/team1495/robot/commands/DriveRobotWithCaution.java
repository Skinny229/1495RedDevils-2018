package org.usfirst.frc.team1495.robot.commands;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRobotWithCaution extends Command {

    public DriveRobotWithCaution() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    	requires(Robot.intake);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.solenoid.set(Value.kReverse);
    	
    	stage = 0;
    	isFin = false;
    	angleTarget = 90;
    	Robot.leftDriveMotor.resetEncoder();
    	System.out.println("Game Data: " + Robot.gameData);
    	System.out.println("posStart: " + Robot.posStart);
    }
    boolean isFin, toSwitch;
    double angleTarget;
    double angleToTurn;
    int stage;
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    if(this.isCanceled())
    	isFin = true;
    switch(stage){
    case 0:
    	if(Robot.leftDriveMotor.getRawEncoderPosition() < (125.0/(8.0 * Math.PI) * 4096.0))
    		Robot.roboDrive.arcadeDrive(.65, 0);
    	else{
    		//isFin = true;
    		stage = 1;
    		Robot.roboDrive.stopMotor();
    	}
    		
    	break;
    case 1:
    	switch(Robot.posStart){
    	case 'R':
    	case 'L':
    		if(Robot.gameData.charAt(0) == Robot.posStart){
    			if(Robot.posStart == 'R'){
    				angleTarget *= -1;
    			}
    			
    			//turnXDegrees(angleTarget);
    			//isFin = true;
    			Robot.leftDriveMotor.resetEncoder();
    			
    	    	Robot.gyro.reset();
    	    	angleToTurn = Robot.gyro.getRawAngleDegrees() + angleTarget;
    	    	
    	    	if(Robot.gyro.getRawAngleDegrees() <= angleToTurn)
    	    		stage = 10;
    	    	else
    	    		stage = 11;
    	    		
    		}else if(Robot.gameData.charAt(1) == Robot.posStart){
    			isFin = true;
    		}else{
    			isFin = true;
    		}
    			break;
    	case 'M':
    		isFin = true;	
    		break;
    	default:
    		isFin = true;
    		}
    	break;
    case 2:
    	if(Robot.leftDriveMotor.getRawEncoderVelocity()  > 0 && !this.isCanceled()){}else{
    		dropCube(-.65);
    		isFin = true;
    	}
    	break;
    
    case 3:
    	if(Robot.leftDriveMotor.getRawEncoderPosition() < (160.0/(8.0 * Math.PI) * 4096.0)){
    		Robot.roboDrive.arcadeDrive(.65, 0);
    	}else{
    		Robot.roboDrive.stopMotor();
    		angleTarget = 45;
    		stage = 4;
    		}
    	break;
    case 4:
    	angleTarget = 45;
		if(Robot.posStart == 'R'){
			angleTarget *= -1;
		}
		turnXDegrees(angleTarget);
		dropCube(-1.0);
		isFin = true;
		break;
		
    case 10:
    	if(Robot.gyro.getRawAngleDegrees() <= angleToTurn && !this.isCanceled()){
    		Robot.roboDrive.arcadeDrive(0, RobotMap.turnRateXDeg);
    	}else{
    		Robot.roboDrive.stopMotor();
    		Robot.elevator.motor.set(-1.0);
    		Timer.delay(1.0);
    		Robot.roboDrive.arcadeDrive(.5, 0);
	    	Timer.delay(.3);
    		stage = 2;
    	}
    	break;
    case 11:
    	if(Robot.gyro.getRawAngleDegrees() >= angleToTurn && !this.isCanceled()){
    		Robot.roboDrive.arcadeDrive(0, -RobotMap.turnRateXDeg);
    	}else{
    		Robot.roboDrive.stopMotor();
    		Robot.elevator.motor.set(-1.0);
    		Timer.delay(1.0);
    		Robot.roboDrive.arcadeDrive(.5, 0);
	    	Timer.delay(.3);
    		stage = 2;
    	}
    	break;
    		
    		
    	}
    	
    }
    	

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFin;
    }

    // Called once after isFinished returns true
    protected void end() {
    	stopEverything();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	stopEverything();
    }
	private void dropCube(double speed) {
		Robot.intake.set(speed);
		Timer.delay(.3);
		Robot.intake.stop();
	}

	private void dropCube() {
		dropCube(-.65);
	}

	private void turnXDegrees(double angleTarget) {
		double startingAngle = Robot.gyro.getRawAngleDegrees();
		double angleToTurn = startingAngle + angleTarget;
		
		if (startingAngle < angleToTurn) {
			while (Robot.gyro.getRawAngleDegrees() <= angleToTurn && !this.isCanceled()){
				Robot.roboDrive.arcadeDrive(0, RobotMap.turnRateXDeg);
				System.out.println("Turning 1");
			}
		} else {
			while (Robot.gyro.getRawAngleDegrees() >= angleToTurn && !this.isCanceled()){
				Robot.roboDrive.arcadeDrive(0, -RobotMap.turnRateXDeg);
				System.out.println("Turning 2");
				}
		}
		Robot.roboDrive.stopMotor();
		Robot.elevator.motor.set(-1.0);
		Timer.delay(1.5);
		Robot.elevator.motor.stopMotor();
	}
	private  void stopEverything() {
		Robot.roboDrive.stopMotor();
		Robot.intake.stop();
		Robot.elevator.motor.stopMotor();
	}
}
