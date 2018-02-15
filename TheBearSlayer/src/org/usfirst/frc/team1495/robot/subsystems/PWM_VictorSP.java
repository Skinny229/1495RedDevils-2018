package org.usfirst.frc.team1495.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;

public class PWM_VictorSP extends VictorSP {

	public PWM_VictorSP(int port, boolean safety) {
		super(port);
		setSafetyEnabled(safety);
	}
}
