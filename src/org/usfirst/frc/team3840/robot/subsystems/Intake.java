package org.usfirst.frc.team3840.robot.subsystems;

import org.usfirst.frc.team3840.robot.RobotMap;
import org.usfirst.frc.team3840.robot.commands.ManuallyCube;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Handling the cube play.
 */
public class Intake extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public final SpeedController intakeLeft = RobotMap.intakeLeft;
	public final SpeedController intakeRight = RobotMap.intakeRight;
	
	//  SmartDashBoard , SuffleBoard 
	final String IntakeSpeed ="IntakeSpeed";
	final String OutTakeSpeed = "OutTakeSpeed";
	final String HoldSpeed = "HoldSpeed";
	
	final double SpeedIn = 1.0;
	final double SpeedOut = -0.5;
	final double SpeedHold = 0.2;
			
	
	private  double setSpeed;
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new ManuallyCube());
    	
    }
    
    public void intakeCube() {
    	double backup = SpeedIn;
    	setSpeed = getPreferencesDouble(IntakeSpeed ,backup); 
    	
    	intakeLeft.set(setSpeed);
    	intakeRight.set(setSpeed);    
    }
    
    public void ejectCube() {
    	double backup = SpeedOut;
    	setSpeed = getPreferencesDouble(OutTakeSpeed ,backup);
    	
    	intakeLeft.set(setSpeed);
    	intakeRight.set(setSpeed);
    }
    
   public void holdCube() {
	   double backup = SpeedHold;
	   setSpeed = getPreferencesDouble(HoldSpeed ,backup);
	   
	   intakeLeft.set(-0.25);
	   intakeRight.set(-0.25);
   }
   
   public void autoEject() {
	   intakeLeft.set(0.8);
   	   intakeRight.set(0.8);
   }

   /**
    * 
    * @param speed
    */
   public void ManuallyEjectCube(double speed) {    	
   		intakeLeft.set(speed);
   		intakeRight.set(speed);		
   }
   
 
	public void stopMotion() {
    	intakeLeft.set(0.0);
    	intakeRight.set(0.0);
    }
	
	/**
   	 * Retrieve numbers from the preferences table. If the specified key is in
   	 * the preferences table, then the preference value is returned. Otherwise,
   	 * return the backup value, and also start a new entry in the preferences
   	 * table. */
    private static double getPreferencesDouble(String key, double backup) {
    	Preferences preferences = Preferences.getInstance();
    	if(!preferences.containsKey(key)) {
    		preferences.putDouble(key, backup);
    	}
    	return preferences.getDouble(key, backup);
    		
    	}
  
    
 }

