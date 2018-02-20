package org.usfirst.frc.team1495.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Notifier;

public class ReactingLED {

	I2C i2;

	public ArrayList<String> cmdQueue = new ArrayList<String>();
	DigitalInput limit = new DigitalInput(0);
	DigitalInput limit2 = new DigitalInput(1);
	

	public class CommandCenter implements java.lang.Runnable {
		boolean hasArduinoLink = false;
		boolean lastLimitState = false;
		boolean lastLimit2State = false;
		
		@Override
		public void run() {
				if(limit.get() != lastLimitState){
					lastLimitState = limit.get();
					if(limit.get())
						addCmd("A");
					else
						addCmd("C");
					}
				if(limit2.get() != lastLimit2State){
					lastLimit2State = limit2.get();
					if(limit2.get())
						addCmd("D");
					else
						addCmd("F");
					}
				sendToArduino("Z");
				if(cmdQueue.size() == 0){
					
				}else{
					sendToArduino(cmdQueue.get(0));
					cmdQueue.remove(0);
					
				}
			}
		}

	// private class ArduinoTimeoutChecker

	Notifier notify = new Notifier(new CommandCenter());

	public ReactingLED() {
		i2 = new I2C(Port.kOnboard, 4);
		// s2 = new SerialPort(0, Port.);
		notify.startPeriodic(0.05);
	}

	public void sendToArduino(String input) {// writes to the arduino
		char[] CharArray = input.toCharArray();// creates a char array from the
												// input string
		byte[] WriteData = new byte[CharArray.length];// creates a byte array
														// from the char array
		for (int i = 0; i < CharArray.length; i++) {// writes each byte to the
													// arduino
			WriteData[i] = (byte) CharArray[i];// adds the char elements to the
												// byte array
		}
		// System.out.println("Data to Arduino" + WriteData);
		 //i2.transaction(WriteData, WriteData.length, null, 0);//sends each
		// byte to arduino
		//System.out.println("Sending...");
		i2.writeBulk(WriteData);
	}

	public void addCmd(String cmd) {
		cmdQueue.add(cmd);
	}

}
