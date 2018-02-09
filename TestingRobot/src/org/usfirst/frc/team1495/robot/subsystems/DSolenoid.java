package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DSolenoid extends Subsystem {

	DoubleSolenoid doubleSolenoid = new DoubleSolenoid(0, 1); // add ports later

	public void forward() {
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void reverse() {
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	public void initDefaultCommand() {
	}
}
