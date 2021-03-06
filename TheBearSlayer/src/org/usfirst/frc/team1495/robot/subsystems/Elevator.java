package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.Robot;
import org.usfirst.frc.team1495.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	public CAN_TalonSRX motor;


	
	public Elevator() {
		motor = new CAN_TalonSRXE(RobotMap.kElevatorMotorID, RobotMap.kElevatorMotorSafety);
	}

	/**
	 * This set method will automatically take into account
	 * the upper limit switch when running 
	 * */
	public void set(double speed){
		//switch()
		if(!Robot.upperElevatorLS.get() && speed < 0.0){
			motor.set(speed);
		}else if(speed > 0.0){
			motor.set(speed);
		}else{
			stop();
		}
	}
	
	public void stop() {
		motor.stopMotor();
	}
	
	
	public void initDefaultCommand() {
	}
}
