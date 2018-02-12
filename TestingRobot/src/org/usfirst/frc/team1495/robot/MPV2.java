package org.usfirst.frc.team1495.robot;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;

public class MPV2 {

	private MotionProfileStatus status = new MotionProfileStatus();

	private WPI_TalonSRX talon;

	private int state = 0;

	private int loopTimeout = -1;

	private boolean allSystemsGo = false;

	@SuppressWarnings("unused")
	private SetValueMotionProfile setValue = SetValueMotionProfile.Disable;

	private static final int kMinPointsInTalon = 5;

	private static final int kNumLoopsTimeout = 10;

	class PeriodicRunnable implements java.lang.Runnable {
		public void run() {
			talon.processMotionProfileBuffer();
		}
	}

	Notifier notifier = new Notifier(new PeriodicRunnable());

	public MPV2(WPI_TalonSRX talone) {
		talon = talone;
		talon.changeMotionControlFramePeriod(5);
		notifier.startPeriodic(.005);
	}

	public void reset() {
		talon.clearMotionProfileTrajectories();
		setValue = SetValueMotionProfile.Disable;
		state = 0;
		loopTimeout = -1;

		allSystemsGo = false;

	}

	public void control() {

		talon.getMotionProfileStatus(status);

		/*
		 * Loop checker thingy magik
		 */
		if (!(loopTimeout < 0)) {
			if (loopTimeout == 0) {
				DriverStation.reportError("WARNING: LoopTimeout MP = 0", false);
			} else {
				--loopTimeout;
			}
		}

		/*
		 * If We are in MP mode... may god help us
		 */
		if (talon.getControlMode() != ControlMode.MotionProfile) {
			/*
			 * we are not in MP mode. We are probably driving the robot around
			 * using gamepads or some other mode.
			 */
			state = 0;
			loopTimeout = -1;
		} else {
			/*
			 * we are in MP control mode. That means: starting Mps, checking Mp
			 * progress, and possibly interrupting MPs if thats what you want to
			 * do.
			 */
			switch (state) {
				case 0: /* wait for application to tell us to start an MP */
					if (allSystemsGo) {
						allSystemsGo = false;
	
						setValue = SetValueMotionProfile.Disable;
						startFillingDef();
						/*
						 * MP is being sent to CAN bus, wait a small amount of time
						 */
						state = 1;
						loopTimeout = kNumLoopsTimeout;
					}
					break;
				case 1: /*
						 * wait for MP to stream to Talon, really just the first few
						 * points
						 */
					/* do we have a minimum numberof points in Talon */
					if (status.btmBufferCnt > kMinPointsInTalon) {
						/* start (once) the motion profile */
						setValue = SetValueMotionProfile.Enable;
						/* MP will start once the control frame gets scheduled */
						state = 2;
						loopTimeout = kNumLoopsTimeout;
					}
					break;
				case 2: /* check the status of the MP */
					/*
					 * if talon is reporting things are good, keep adding to our
					 * timeout. Really this is so that you can unplug your talon in
					 * the middle of an MP and react to it.
					 */
					if (status.isUnderrun == false) {
						loopTimeout = kNumLoopsTimeout;
					}
					/*
					 * If we are executing an MP and the MP finished, start loading
					 * another. We will go into hold state so robot servo's
					 * position.
					 */
					if (status.activePointValid && status.isLast) {
						/*
						 * because we set the last point's isLast to true, we will
						 * get here when the MP is done
						 */
						setValue = SetValueMotionProfile.Hold;
						state = 0;
						loopTimeout = -1;
					}
					break;
			}
		}
		/* printfs and/or logging */
	}

	private void startFillingDef() {
		startFilling(GeneratedMotionProfile.Points, GeneratedMotionProfile.kNumPoints);
	}

	private void startFilling(double[][] profile, int totalCnt) {

		/* create an empty point */
		TrajectoryPoint point = new TrajectoryPoint();

		/* did we get an underrun condition since last time we checked ? */
		if (status.hasUnderrun) {
			/* better log it so we know about it */
			//instrumentation.OnUnderrun();
			/*
			 * clear the error. This flag does not auto clear, this way 
			 * we never miss logging it.
			 */
			talon.clearMotionProfileHasUnderrun(0);
		}
		/*
		 * just in case we are interrupting another MP and there is still buffer
		 * points in memory, clear it.
		 */
		talon.clearMotionProfileTrajectories();

		/* This is fast since it's just into our TOP buffer */
		for (int i = 0; i < totalCnt; ++i) {
			/* for each point, fill our structure and pass it to API */
			point.position = profile[i][0];
			point.velocity = profile[i][1];
			//point.timeDurMs = (int) profile[i][2];
			point.profileSlotSelect = 0; /* which set of gains would you like to use? */
			/*point.= false; /* set true to not do any position
										 * servo, just velocity feedforward
										 */
			point.zeroPos = false;
			if (i == 0)
				point.zeroPos = true; /* set this to true on the first point */

			point.isLastPoint = false;
			if ((i + 1) == totalCnt)
				point.isLastPoint = true; /* set this to true on the last point  */

			talon.pushMotionProfileTrajectory(point);
		}
	}


	void startMotionProfile() {
		allSystemsGo = true;
	}
	public SetValueMotionProfile getSetValue() {
		return setValue;
	}

}
