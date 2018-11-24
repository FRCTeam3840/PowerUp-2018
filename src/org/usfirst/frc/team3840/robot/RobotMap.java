/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3840.robot;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static WPI_TalonSRX driveTrainTalonSRX1;
	public static WPI_TalonSRX driveTrainTalonSRX2;
	public static SpeedControllerGroup driveTrainLeftDrive;
	public static WPI_TalonSRX driveTrainTalonSRX3;
	public static WPI_TalonSRX driveTrainTalonSRX4;
	public static SpeedControllerGroup driveTrainRightDrive;
	public static DifferentialDrive driveTrainArcadeTrain; 
	public static WPI_TalonSRX liftElevatorliftMotor5;
	public static SpeedController intakeLeft;
	public static SpeedController intakeRight;
	public static SpeedController climber;
	public static SpeedController climberLatch;
	static double iaccum = 0;
	
	@SuppressWarnings("deprecation")
	public static void init() {
		// Constructors for left drive
		/**
		driveTrainTalonSRX1 = new WPI_TalonSRX(1);
		driveTrainTalonSRX2 = new WPI_TalonSRX(2);
		
		// Speed Controller Group Left Drive 
		driveTrainLeftDrive = new SpeedControllerGroup(driveTrainTalonSRX1, driveTrainTalonSRX2);
		LiveWindow.addActuator("DriveTrain", "LeftDrive", driveTrainLeftDrive);
		// Master slave for Left Drive. Talon2 follows Talon1
		driveTrainTalonSRX2.follow(driveTrainTalonSRX1);					
       
		//Constructors for right drive 
		driveTrainTalonSRX3 = new WPI_TalonSRX(3);
		driveTrainTalonSRX4 = new WPI_TalonSRX(4);
		
		// Speed controller group Right drive 
		driveTrainRightDrive = new SpeedControllerGroup (driveTrainTalonSRX3, driveTrainTalonSRX4);
		LiveWindow.addActuator("DriveTrain", "LeftDrive", driveTrainRightDrive);
		
		// Master slave for Right Drive. Talon4 follows Talon3
		driveTrainTalonSRX4.follow(driveTrainTalonSRX3);
		
		// Invert Right Drive
		driveTrainRightDrive.setInverted(true);
		
		// set differential drive parameters
		driveTrainArcadeTrain = new DifferentialDrive(driveTrainLeftDrive, driveTrainRightDrive );
		LiveWindow.addActuator("driveTrain", "ArcadeTrain", driveTrainArcadeTrain);
		driveTrainArcadeTrain.setSafetyEnabled(false);
		driveTrainArcadeTrain.setExpiration(0.1);
		driveTrainArcadeTrain.setMaxOutput(1.0);
		*/
		//Constructors for intake motors
		intakeLeft = new Spark(0);
		intakeRight = new Spark(1);
		//Invert Right intake motor
		intakeRight.setInverted(true);
		
		//Send Left intake to suffleBoard
		LiveWindow.addActuator("LeftIntake", "LeftIntake", (Spark) intakeLeft);
		//Send Right intake to suffleBoard
		LiveWindow.addActuator("RightIntake", "RightIntake", (Spark) intakeRight);
		
		// Constructors for Climber motor
		climber = new Spark(2);
		// Send Climber to suffleBoard
		LiveWindow.addActuator("Climber", "climber", (Spark) climber);
		
		// Constructor for climber latch motor
		climberLatch = new Spark(3);
		LiveWindow.addActuator("ClimberLatch", "climberLactch", (Spark) climberLatch);
		
		/****************************
		 *  Lift Motor Configuration
		 **************************
		// Motor #5
		liftElevatorliftMotor5 = new WPI_TalonSRX(5);
        LiveWindow.addActuator("Lift Motor", "Lift Elevator", (WPI_TalonSRX) liftElevatorliftMotor5);
        //Setups the encoder position sensor
        liftElevatorliftMotor5.clearStickyFaults(20);

		/* first choose the sensor 
		liftElevatorliftMotor5.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		liftElevatorliftMotor5.setSensorPhase(false);
		liftElevatorliftMotor5.setInverted(true);
		// Reset sensor position
		liftElevatorliftMotor5.setIntegralAccumulator(iaccum, 0, 10);

		/* Set relevant frame periods to be at least as fast as periodic rate 
		liftElevatorliftMotor5.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		liftElevatorliftMotor5.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* set the peak and nominal outputs 
		liftElevatorliftMotor5.configNominalOutputForward(0, Constants.kTimeoutMs);
		liftElevatorliftMotor5.configNominalOutputReverse(0, Constants.kTimeoutMs);
		liftElevatorliftMotor5.configPeakOutputForward(1, Constants.kTimeoutMs);
		liftElevatorliftMotor5.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation 
		liftElevatorliftMotor5.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		liftElevatorliftMotor5.config_kF(0, 0.2481, Constants.kTimeoutMs);
		liftElevatorliftMotor5.config_kP(0, 2.9, Constants.kTimeoutMs);
		liftElevatorliftMotor5.config_kI(0, 0, Constants.kTimeoutMs);
		liftElevatorliftMotor5.config_kD(0, 0, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation 
		liftElevatorliftMotor5.configMotionCruiseVelocity(2061, Constants.kTimeoutMs);
		liftElevatorliftMotor5.configMotionAcceleration(2061, Constants.kTimeoutMs);
		/* zero the sensor 
		liftElevatorliftMotor5.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); 
		
		/**---------------------------------------------
		// Left Drive train Sensor update
		//---------------------------------------------
		/* first choose the sensor 
		driveTrainTalonSRX1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		driveTrainTalonSRX1.setSensorPhase(true);
	
		/* Set relevant frame periods to be at least as fast as periodic rate 
		driveTrainTalonSRX1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		driveTrainTalonSRX1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* set the peak and nominal outputs 
		driveTrainTalonSRX1.configNominalOutputForward(0, Constants.kTimeoutMs);
		driveTrainTalonSRX1.configNominalOutputReverse(0, Constants.kTimeoutMs);
		driveTrainTalonSRX1.configPeakOutputForward(1, Constants.kTimeoutMs);
		driveTrainTalonSRX1.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation 
		driveTrainTalonSRX1.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		driveTrainTalonSRX1.config_kF(0, 0.245, Constants.kTimeoutMs);
		driveTrainTalonSRX1.config_kP(0, 0.2, Constants.kTimeoutMs);
		driveTrainTalonSRX1.config_kI(0, 0, Constants.kTimeoutMs);
		driveTrainTalonSRX1.config_kD(0, 0, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation 
		driveTrainTalonSRX1.configMotionCruiseVelocity(850, Constants.kTimeoutMs);
		driveTrainTalonSRX1.configMotionAcceleration(850, Constants.kTimeoutMs);
		/* zero the sensor 
		driveTrainTalonSRX1.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		
		/**--------------------------------------------
		// Right Drive train Sensor update
		//---------------------------------------------
		/* first choose the sensor 
		driveTrainTalonSRX3.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		driveTrainTalonSRX3.setSensorPhase(false);
			
		/* Set relevant frame periods to be at least as fast as periodic rate 
		driveTrainTalonSRX3.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		driveTrainTalonSRX3.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* set the peak and nominal outputs 
		driveTrainTalonSRX3.configNominalOutputForward(0, Constants.kTimeoutMs);
		driveTrainTalonSRX3.configNominalOutputReverse(0, Constants.kTimeoutMs);
		driveTrainTalonSRX3.configPeakOutputForward(1, Constants.kTimeoutMs);
		driveTrainTalonSRX3.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation 
		driveTrainTalonSRX3.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		driveTrainTalonSRX3.config_kF(0, 0.245, Constants.kTimeoutMs);
		driveTrainTalonSRX3.config_kP(0, 0.2, Constants.kTimeoutMs);
		driveTrainTalonSRX3.config_kI(0, 0, Constants.kTimeoutMs);
		driveTrainTalonSRX3.config_kD(0, 0, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation 
		driveTrainTalonSRX3.configMotionCruiseVelocity(850, Constants.kTimeoutMs);
		driveTrainTalonSRX3.configMotionAcceleration(850, Constants.kTimeoutMs);
		/* zero the sensor 
		driveTrainTalonSRX3.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); */
	}	
			

}
