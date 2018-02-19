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

		@Override
		public void run() {
			System.out.println("limit 1: " + limit.get());
			System.out.print("Limit 2: " + limit2.get());
			if(limit.get()){
				sendToArduino("A");
			}else if (!limit2.get()){
				sendToArduino("B");
			}else{
				sendToArduino("C");
			}
			
			//System.out.println(cmdQueue.get(0));
			if (hasArduinoLink) {
				/*
				if (cmdQueue.size() == 0) {
					sendToArduino("0");
				} else {
					System.out.println("Sent!");
					sendToArduino(cmdQueue.get(0));
					cmdQueue.remove(0);
				}
				*/
			} else {
				sendToArduino("REEEEE why you dont connect");
				try {
					byte[] thatByte = { 0 };
					if (!i2.readOnly(thatByte, 1) && thatByte[0] == 1) {
						System.out.println("READ SOMETHING: " + thatByte[0]);
						System.out.println("ARDUINO LINKED CONFIRMED");
						hasArduinoLink = true;
					}
				} catch (RuntimeException e) {

				}
			}

		}

	}

	// private class ArduinoTimeoutChecker

	Notifier notify = new Notifier(new CommandCenter());

	public ReactingLED() {
		i2 = new I2C(Port.kOnboard, 4);
		// s2 = new SerialPort(0, Port.);
		notify.startPeriodic(0.5);
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
		System.out.println("Sending...");
		i2.writeBulk(WriteData);
	}

	public void addCmd(String cmd) {
		cmdQueue.add(cmd);
	}

}
