package org.usfirst.frc.team1495.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Notifier;

public class InteractiveLEDS {
	I2C i2;

	public ArrayList<String> cmdQueue = new ArrayList<String>();
	DigitalInput limit = new DigitalInput(RobotMap.kInnerLimitSwitchPort);
	DigitalInput limit2 = new DigitalInput(RobotMap.kOutterLimitSwitchPort);

	public class CommandCenter implements java.lang.Runnable {
		boolean hasArduinoLink = false;
		boolean lastInnerLimitState = false;
		boolean lastOuterLimitState = false;

		@Override
		public void run() {
			//RobotMap.isArduinoConnected = isArduinoHere();
			//sendToArduino("A");
			updateLimitSwitches();
			if (cmdQueue.size() == 0) {

			} else {
				sendToArduino(cmdQueue.get(0));
				cmdQueue.remove(0);

			}
		}

		public void updateLimitSwitches() {
			if (limit.get() != lastInnerLimitState) {
				addCmd("A");
				lastInnerLimitState = limit.get();
			} else if (limit2.get() != lastInnerLimitState) {
				addCmd("B");
				lastOuterLimitState = limit2.get();
			} else {
				addCmd("C");
			}
		}

		final double kTimeoutTimer = 3.0;
		int counter = 0;

		public boolean isArduinoHere() {
			if (sendToArduino("Testing Connection")) {
				counter = 0;
				return true;
			} else {
				counter++;
				if (counter > kTimeoutTimer / .05) {
					return false;
				}
				return true;
			}
		}

	}

	Notifier notify = new Notifier(new CommandCenter());

	public InteractiveLEDS() {
		i2 = new I2C(Port.kOnboard, RobotMap.kI2CAdress);
		notify.startPeriodic(0.5);
	}

	public boolean sendToArduino(String input) {
		char[] CharArray = input.toCharArray();
		byte[] WriteData = new byte[CharArray.length];
		for (int i = 0; i < CharArray.length; i++) {
			WriteData[i] = (byte) CharArray[i];
		}
		return (!i2.writeBulk(WriteData));
	}

	public void addCmd(String cmd) {
		cmdQueue.add(cmd);
	}
}
