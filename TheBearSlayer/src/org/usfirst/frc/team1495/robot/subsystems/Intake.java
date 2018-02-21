package org.usfirst.frc.team1495.robot.subsystems;

import org.usfirst.frc.team1495.robot.RobotMap;

<<<<<<< HEAD
=======
import edu.wpi.first.wpilibj.DoubleSolenoid;
>>>>>>> 6e9eac1441d702576fe4d0f235393b64ea33fca1
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	public CAN_TalonSRX leftMotor;
	public CAN_TalonSRX rightMotor;
<<<<<<< HEAD
=======
	//public PWM_VictorSP leftMotor;
	//public PWM_VictorSP rightMotor;
	public DoubleSolenoid leftSolenoid;
	public DoubleSolenoid rightSolenoid;
	
	public enum IntakeDir{
		Inwards,Outwards
	}
>>>>>>> 6e9eac1441d702576fe4d0f235393b64ea33fca1

	public Intake() {
		leftMotor = new CAN_TalonSRX(RobotMap.kLeftIntakeMotorID, RobotMap.kIntakeMotorSafety);
		rightMotor = new CAN_TalonSRX(RobotMap.kRightIntakeMotorID, RobotMap.kIntakeMotorSafety);
	}
	
	public void set(double speed) {
		leftMotor.set(speed);
		rightMotor.set(-speed);
	}
	
	public void stop() {
		leftMotor.stopMotor();
		rightMotor.stopMotor();
	}
	
	public void setIn(){
		leftMotor.set(RobotMap.kIntakeSpeed);
		rightMotor.set(RobotMap.kIntakeSpeed);
	}
	
	public void setOut(){
		rightMotor.set(-RobotMap.kIntakeSpeed);
		leftMotor.set(-RobotMap.kIntakeSpeed);
	}
	
	public void stopSpin(){
		leftMotor.stopMotor();
		rightMotor.stopMotor();
	}

	public void initDefaultCommand() {
	}
}

