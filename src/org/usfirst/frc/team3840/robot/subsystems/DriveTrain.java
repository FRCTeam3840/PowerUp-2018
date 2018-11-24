package org.usfirst.frc.team3840.robot.subsystems;

import org.usfirst.frc.team3840.robot.Constants;
import org.usfirst.frc.team3840.robot.RobotMap;
import org.usfirst.frc.team3840.robot.commands.DriveRobot;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *Drive Train SubSystem
 */
public class DriveTrain extends Subsystem {

	private final DifferentialDrive arcadeTrain = RobotMap.driveTrainArcadeTrain;
	private final WPI_TalonSRX LeftEncoderMotor = RobotMap.driveTrainTalonSRX1;
	private final WPI_TalonSRX RightEncoderMotor = RobotMap.driveTrainTalonSRX3;
	
	//public variables used for commands isFinished
		public int intSensorLeftPosition;
		public int intSensorRightPosition;
	// Used to get numbers from the smart dashboard perference values
		final String DriveStraight = "autoSpeed";
	//backup key values not returned from perference table on shuffleboard
		final double autoSpeedSp = 0.3;
		
		
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		setDefaultCommand(new DriveRobot());
	}
	

	public void periodic() {
		// put code here to be run every loop
		//Gets the current sensor position
		intSensorLeftPosition =  LeftEncoderMotor.getSelectedSensorPosition(Constants.kPIDLoopIdx);
		intSensorRightPosition =  RightEncoderMotor.getSelectedSensorPosition(Constants.kPIDLoopIdx);
		//pushes values to the dashboard
		SmartDashboard.putNumber("LeftDriveSensPos", intSensorLeftPosition);
		SmartDashboard.putNumber("LeftOutputPercent", LeftEncoderMotor.getMotorOutputPercent());
		SmartDashboard.putNumber("LeftPositionError", LeftEncoderMotor.getClosedLoopError(Constants.kPIDLoopIdx));
		SmartDashboard.putNumber("RightDriveSensPos", intSensorRightPosition);
		SmartDashboard.putNumber("RightOutputPercent", RightEncoderMotor.getMotorOutputPercent());
		SmartDashboard.putNumber("RightPositionError", RightEncoderMotor.getClosedLoopError(Constants.kPIDLoopIdx));
	} 
	
	//Arcade Drive from Drive via XboxConroller input 
	public void arcadeDrive(XboxController joystick1) {
		double joyThreshold = 0.15; // Default threshold value from XboxController
		double stickX = joystick1.getX();
		double stickY = joystick1.getY()*-1 ;
		// display on dashboards
		SmartDashboard.putNumber("speed X: ", stickX);
		SmartDashboard.putNumber("speed Y: ", stickY);
		//Checks for min joystick input
		if(Math.abs(stickX) > joyThreshold|| Math.abs(stickY) > joyThreshold) {
			arcadeTrain.arcadeDrive(stickX, stickY, true);
		}
	}

	//Stop motion for arcade drive
	public void StopMotion() {
		arcadeTrain.stopMotor();
		LeftEncoderMotor.setNeutralMode(NeutralMode.Coast);
    	RightEncoderMotor.setNeutralMode(NeutralMode.Coast);
	}
	
	public void zeroDistanceTraveled() {
		// record current positions as "zero" for all of the Talon SRX encoders
		
		/* zero the sensor */
		LeftEncoderMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		RightEncoderMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}
	
	//Used to feedback to the command the total distance traveled
	public double getDistanceTraveled() {
		double tempDistance = 0;
		// add up the absolute value of the distances from left and right drive train
		tempDistance = (Math.abs(intSensorLeftPosition) + Math.abs(intSensorRightPosition)) / 2;
		return (tempDistance);
	}
	
	
	//This runs under it hits the distance by encoders
	public void DriveToDistance() {
		double backUp = autoSpeedSp;
		double setPoint;
		//gets the current value
    	setPoint = getPreferencesDouble(DriveStraight, backUp);
    	LeftEncoderMotor.setNeutralMode(NeutralMode.Brake);
    	RightEncoderMotor.setNeutralMode(NeutralMode.Brake);
		arcadeTrain.tankDrive(setPoint*0.96, setPoint*-1);
	}
	
	//Turns left
	public void TurnLeft() {
		LeftEncoderMotor.setNeutralMode(NeutralMode.Brake);
    	RightEncoderMotor.setNeutralMode(NeutralMode.Brake);
    	double stickX = -0.75;
    	//arcadeTrain.arcadeDrive(stickX, 0, true);
    	//values that work (-0.8, -0.8) with time .8
    	//arcadeTrain.tankDrive(-0.8, -0.8, true);
    	arcadeTrain.curvatureDrive(-0.55,-0.55, false);
	}
	
	//Turns right
		public void TurnRight() {
			LeftEncoderMotor.setNeutralMode(NeutralMode.Brake);
	    	RightEncoderMotor.setNeutralMode(NeutralMode.Brake);
	    	//arcadeTrain.arcadeDrive(stickX, 0, true);
	    	//values that work (0.6, 1.0) with time .95
	    	 arcadeTrain.tankDrive(0.6, 1.0, true);
	    	//arcadeTrain.curvatureDrive(0.65, 0.25, false);
		}
	
	
	 /**
   	 * Retrieve numbers from the preferences table. If the specified key is in
   	 * the preferences table, then the preference value is returned. Otherwise,
   	 * return the backup value, and also start a new entry in the preferences
   	 * table.
   	 */
       private static double getPreferencesDouble(String key, double backup) {
   		Preferences preferences = Preferences.getInstance();
   		if (!preferences.containsKey(key)) {
   			preferences.putDouble(key, backup);
   		}
   		return preferences.getDouble(key, backup);
   	}

}
	
	
	
	
