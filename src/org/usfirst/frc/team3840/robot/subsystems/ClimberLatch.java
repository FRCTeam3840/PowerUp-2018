package org.usfirst.frc.team3840.robot.subsystems;

import org.usfirst.frc.team3840.robot.RobotMap;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Unlatches the climber arms.
 */
public class ClimberLatch extends Subsystem {

	public final SpeedController climberLatch = RobotMap.climberLatch;
	final String UnlatchLeft = "UnlatchLeft";
	final String UnlatchRight = "UnlatchRight";
	final double SpeedLeft = 0.5;
	final double SpeedRight = -0.5;
	private double setSpeed;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
    public void UnlatchArm_CW() {
    	double backup = SpeedLeft;
    	setSpeed = getPreferencesDouble(UnlatchLeft ,backup); 
    	
    	climberLatch.set(setSpeed);
    }
    
    public void UnlatchArm_CCW() {
    	double backup = SpeedRight;
    	setSpeed = getPreferencesDouble(UnlatchRight,backup); 
    	climberLatch.set(-0.5);
    }
    
    public void StopMotor() {
    	climberLatch.stopMotor();
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

