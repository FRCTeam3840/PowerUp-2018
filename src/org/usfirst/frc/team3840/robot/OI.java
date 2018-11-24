
package org.usfirst.frc.team3840.robot;

import org.usfirst.frc.team3840.robot.commands.Climb;
import org.usfirst.frc.team3840.robot.commands.Cube_In;
import org.usfirst.frc.team3840.robot.commands.Cube_Out;
import org.usfirst.frc.team3840.robot.commands.HoldCube;
import org.usfirst.frc.team3840.robot.commands.LatchClimberArm;
import org.usfirst.frc.team3840.robot.commands.LiftToPickUp;
import org.usfirst.frc.team3840.robot.commands.LiftToScale;
import org.usfirst.frc.team3840.robot.commands.LiftToSwitch;
import org.usfirst.frc.team3840.robot.commands.LiftToTravel;
import org.usfirst.frc.team3840.robot.commands.UnLatchClimberArm;
import org.usfirst.frc.team3840.robot.commands.autoDriveStraight;
import org.usfirst.frc.team3840.robot.commands.autoTurnLeft;
import org.usfirst.frc.team3840.robot.commands.autoTurnRight;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/** This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
		
	// JoyStick declares // Joystick stick = new Joystick(port);
	public XboxController driveJoyStick;
	public XboxController actuatorJoyStick;
	public JoystickButton buttonLiftToPickup;
    public JoystickButton buttonLiftSwitch;
    public JoystickButton buttonLiftScale;
    public JoystickButton buttonLiftTravel;
    public JoystickButton buttonClimber;
    public JoystickButton buttonClimberlatchLeft;
    public JoystickButton buttonClimberlatchRight;
    public JoystickButton buttonDriveStraight;
    //private varaible
    private static final double STICK_DEADBAND = 0.05;
    
	public OI() {
    // set constructor for controller
		driveJoyStick = new XboxController(0);
		actuatorJoyStick = new XboxController(1);
		
		//Button in intake
		Button cubeIn = new JoystickButton(actuatorJoyStick, 5);
	    cubeIn.whileHeld(new Cube_In());
        //Button out intake
	    Button cubeOut = new JoystickButton(actuatorJoyStick, 6);
	    cubeOut.whileHeld(new Cube_Out());
	    //Button hold cube
	    Button holdCube = new JoystickButton(actuatorJoyStick, 7);
	   	holdCube.whileHeld(new HoldCube());
	    
	    //Climb the Scale
	    Button climb = new JoystickButton(driveJoyStick, 6);
	    climb.whileHeld(new Climb());
	   
	   //---------------------------------------------
       //button declares for commands to subsystems
       //---------------------------------------------
       // Button #1
       buttonLiftToPickup = new JoystickButton(actuatorJoyStick, 1);
       buttonLiftToPickup.whenPressed(new LiftToPickUp());
       // Button #2
       buttonLiftTravel = new JoystickButton(actuatorJoyStick, 2);
       buttonLiftTravel.whenPressed(new LiftToTravel());
       // Button #3
       buttonLiftSwitch = new JoystickButton(actuatorJoyStick, 3);
       buttonLiftSwitch.whenPressed(new LiftToSwitch());
       // Button #4
       buttonLiftScale = new JoystickButton(actuatorJoyStick, 4);
       buttonLiftScale.whenPressed(new LiftToScale());
       // Button #7
       buttonClimberlatchLeft = new JoystickButton(driveJoyStick, 7);
       buttonClimberlatchLeft.whenPressed(new LatchClimberArm());
       // Button #8
       buttonClimberlatchRight = new JoystickButton(driveJoyStick, 8);
       buttonClimberlatchRight.whenPressed(new UnLatchClimberArm());
       
       // SmartDashboard Buttons
       SmartDashboard.putData("LiftToSwitch", new LiftToSwitch());
      // SmartDashboard.putData("LiftToScale", new LiftToScale());
      // SmartDashboard.putData("LiftToPickup", new LiftToPickUp());
	  // SmartDashboard.putData("DriveRobot", new DriveRobot());
       
       //Testing code
       buttonDriveStraight = new JoystickButton(driveJoyStick, 2);
       buttonDriveStraight.whenPressed(new autoDriveStraight(26380));
       SmartDashboard.putData("Auto Straight", new autoDriveStraight(26380));
       SmartDashboard.putData("TurnRight", new autoTurnRight());
       SmartDashboard.putData("TurnLeft", new autoTurnLeft());
       SmartDashboard.putData("Latch", new LatchClimberArm());
       SmartDashboard.putData("UnLatch", new UnLatchClimberArm());
	 }
	
	private static double stickDeadband(double value, double deadband, double center) {
        return (value < (center + deadband) && value > (center - deadband)) ? center : value;
	}
	
	public XboxController GetDriveJoyStick() {
		return driveJoyStick;
	}
	
	public XboxController GetActuatorJoyStick() {
		return actuatorJoyStick;
		
	}
	
	public double getOpLeftStickY() {
        return stickDeadband(this.actuatorJoyStick.getRawAxis(1), STICK_DEADBAND, 0.0);
	}
	
	
}
