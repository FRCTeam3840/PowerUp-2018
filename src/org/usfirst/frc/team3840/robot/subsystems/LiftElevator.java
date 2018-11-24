// *************************************************
//	Team 3840  2018 PowerUp
//  Lift/Elevator Subsytem
//  /* Motion Magic - 4096 ticks/rev */
//  Talon Motor 5 using CTRE Mag Encoder
//**************************************************

package org.usfirst.frc.team3840.robot.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3840.robot.Constants;
import org.usfirst.frc.team3840.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 *  Lift Elevator Up/Down SubSystem
 */
public class LiftElevator extends Subsystem {

    private final WPI_TalonSRX liftMotor5 = RobotMap.liftElevatorliftMotor5;
    // Used to get numbers from the smart dashboard perference values
	 final String LiftToTravel = "LiftToTravel";
	 final String LiftToPickUp = "LiftToPickUp";
	 final String LiftToSwitch = "LiftToSwitch";
	 final String LiftToScale = "LiftToScale";
	 final String autoLiftToScale = "autoLiftToScale";
	 //backup key values not returned from perference table on shuffleboard
	 final double TravelLocation = 5;
	 final double SwitchLocation = 8;
	 final double PickUpLocation = 0.2;
	 final double ScaleLocation = 9;
	 final double AutoLiftToScale = 8;
	 //local setpoint for moving to position by magic motion
	 private double setPoint;
	 //public variables used for commands isFinished
	 public int intSensorPosition;
	 public int intUpperRange;
	 public int intLowwerRange;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	//setDefaultCommand(new LiftToManualPosition());
    }

    @Override
    public void periodic() {
    	//Gets the current sensor position
    	intSensorPosition = liftMotor5.getSelectedSensorPosition(Constants.kPIDLoopIdx);
    	//pushes values to the dashboard
    	SmartDashboard.putNumber("LiftSensorPosition", liftMotor5.getSelectedSensorPosition(Constants.kPIDLoopIdx));
		SmartDashboard.putNumber("LiftOutputPercent", liftMotor5.getMotorOutputPercent());
		SmartDashboard.putNumber("PositionError", liftMotor5.getClosedLoopError(Constants.kPIDLoopIdx));
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    /**
     * Public LiftToPosition
     * Commands:  Lift To Travel, Lift To Switch, 
     *            Lift Scale, Lift To Pickup Location
     */
    public void LiftToPosition(String Key) {
    	double backUp = TravelLocation;
    	
    	//set up the grab from values at Smart Dashboard perference table
    	 switch (Key) {
         case LiftToTravel:;
         	backUp = TravelLocation;
         	break;
         case LiftToPickUp:;
         	backUp = PickUpLocation;
         	break;
         case LiftToSwitch:;
         	backUp = SwitchLocation;
         	break;
         case LiftToScale:;
         	backUp = ScaleLocation;
         	break;
         case autoLiftToScale:;
         	backUp = AutoLiftToScale;
         	break;
    	 }
    	 
    	//gets the current value
    	setPoint = getPreferencesDouble(Key, backUp);
    	
    	//dashboard/perference table sends rotations
    	setPoint = this.getValues(setPoint);      //setPoint * 4096;
    	
    	if (intSensorPosition > setPoint|| true) {
    		/* set acceleration and vcruise velocity - see documentation */
        	liftMotor5.configMotionCruiseVelocity(1237, Constants.kTimeoutMs);
        	liftMotor5.configMotionAcceleration(1237, Constants.kTimeoutMs);
    	}
    	
    	if (intSensorPosition < setPoint|| true) {
    		/* set acceleration and vcruise velocity - see documentation */
        	liftMotor5.configMotionCruiseVelocity(2061, Constants.kTimeoutMs);
        	liftMotor5.configMotionAcceleration(2061, Constants.kTimeoutMs);
    	}
    			
    	/* Motion Magic - 4096 ticks/rev */
    	liftMotor5.set(ControlMode.MotionMagic,setPoint);
    }
    
    /**
     * Used to manually lift elevator up/down from setpoints
     * @param _joystick
     */
    public void ManuallyMoveLift(XboxController _joystick) {
    	// 1 rev = 4096
    	double dblValue = _joystick.getY();
    	double dblSetPoint = this.getValues(dblValue) + intSensorPosition;
    	
    	//debug code
    	SmartDashboard.putNumber("Manual Lift Position",dblValue);
    	
    	//lift does not go less then zero and not travel over max limit
    	if (dblSetPoint > 10|| true) {
    		/* Servo position, plus/minus one CTRE Mag Enc rotation via gamepad */
        	liftMotor5.set(ControlMode.MotionMagic, dblSetPoint);
        	//debug code
        	SmartDashboard.putString("LiftMoving", "Moving...");
    	}
    	
    }
    
    public void ResetEncoders() {
    	//Setups the encoder position sensor
    	liftMotor5.clearStickyFaults(20);
        // Reset sensor position
    	liftMotor5.setIntegralAccumulator(0, 0, 10);
    }
    
    /**
     * getValues for commands (Ranges)
     * @param dblValue
     * @return new setpoint for lift position
     */
    private double getValues(double dblValue) {
    	
    	//Return the setpoint for moving lift
    	dblValue = setPoint * 4096;
    	//Calc the ranges for the commands
    	intUpperRange = (int)(dblValue + 500);
    	intLowwerRange = (int)(dblValue - 500);
    	
    	return dblValue;
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

