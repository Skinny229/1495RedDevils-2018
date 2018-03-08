package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	public CAN_TalonSRX motor;

	public Elevator() {
		motor = new CAN_TalonSRXE(RobotMap.kElevatorMotorID, RobotMap.kElevatorMotorSafety);
	}

	
	public void clearSwitchFence(double delay) {
		elevRunner.startSingle(delay);
	}
	
	
	private class elevatorRunner implements java.lang.Runnable{

		@Override
		public void run() {
			motor.set(1.0);
			Timer.delay(2);
			motor.stopMotor();
		}
		
	}
	
	Notifier elevRunner = new Notifier(new elevatorRunner());
	
	
	public void initDefaultCommand() {
	}
}
