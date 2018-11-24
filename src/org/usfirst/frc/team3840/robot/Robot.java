/*** 
 *  Team 3840  TNT
 *  PowerUp 2018 Robot Code
 *  Robot Main
 */
package org.usfirst.frc.team3840.robot;

import org.usfirst.frc.team3840.robot.commands.Climb;
import org.usfirst.frc.team3840.robot.commands.LeftSideCloseScaleAuto;
import org.usfirst.frc.team3840.robot.commands.LeftSideCloseSwitchAuto;
import org.usfirst.frc.team3840.robot.commands.ManuallyCube;
import org.usfirst.frc.team3840.robot.commands.RightSideCloseScaleAuto;
import org.usfirst.frc.team3840.robot.commands.RightSideCloseSwitchAuto;
import org.usfirst.frc.team3840.robot.commands.autoDefaultDriveForward;
import org.usfirst.frc.team3840.robot.subsystems.Climber;
import org.usfirst.frc.team3840.robot.subsystems.ClimberLatch;
import org.usfirst.frc.team3840.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3840.robot.subsystems.Intake;
import org.usfirst.frc.team3840.robot.subsystems.LiftElevator;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    public static OI oi;
    public static DriveTrain driveTrain;
    public static Intake intake;
    public static Climber climber;
    public static Climb manualclimber;
    public static ClimberLatch unLatchClimber;
    public static LiftElevator liftElevator;
    public static ManuallyCube m_manuallyCube;
       
    Command autoCommand;
	
	private enum Sides {
        Left,
        Right,
    }

    private enum Autos {
        SwitchOnly,
        ScaleOnly,
        SwitchToScale,
        ScaleToSwitch,
    }

    String gameData;
    SendableChooser sideSelector;
    SendableChooser autoSelector;
    Command leftSideCloseScaleAuto;
	Command leftSideCloseSwitchAuto; 
	Command rightSideCloseScaleAuto;
	Command rightSideCloseSwitchAuto;
	Command driveStraightOnly;
    
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		RobotMap.init();
		
		intake = new Intake();
		//driveTrain = new DriveTrain();
		climber = new Climber();
		unLatchClimber = new ClimberLatch();
	//	liftElevator = new LiftElevator();
		manualclimber = new Climb();
		m_manuallyCube = new ManuallyCube();
        SmartDashboard.putData(liftElevator);
               
        //Setup Usb camera connection      
        UsbCamera cam0 = CameraServer.getInstance().startAutomaticCapture(0);
        cam0.setFPS(20);
        
	    // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();
		
        //Add commands to autonomous Sendable chooser
        sideSelector = new SendableChooser();
	    autoSelector = new SendableChooser();
	    sideSelector.addDefault("Left", Sides.Left); // Left Side
	    sideSelector.addObject("Right", Sides.Right); // Right Side
	    autoSelector.addDefault("Switch Only", Autos.SwitchOnly);
	    autoSelector.addObject("Scale Only", Autos.ScaleOnly);
	    SmartDashboard.putData("Side Selector", sideSelector);
	    SmartDashboard.putData("Auto Selector", autoSelector);
	    //Create all the auto commands
	    leftSideCloseScaleAuto = new LeftSideCloseScaleAuto();
	    leftSideCloseSwitchAuto = new LeftSideCloseSwitchAuto();
	    rightSideCloseScaleAuto = new RightSideCloseScaleAuto();
	    rightSideCloseSwitchAuto = new RightSideCloseSwitchAuto();
	    driveStraightOnly = new autoDefaultDriveForward();
        
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		try {
			String gameDataTemp = DriverStation.getInstance().getGameSpecificMessage();

	        if (gameDataTemp != null) {
	          //  gameData = DriverStation.getInstance().getGameSpecificMessage().substring(0, 2);
	            gameData = gameDataTemp.substring(0, 2);
	        } 
	        
	        else {
	            System.out.println("NO GAME DATA!");
	            gameData = "";
	            
	        }

	        switch (gameData) {
	            case "LR": // Check to see if robot is placed left or right 
	                if (sideSelector.getSelected().equals(Sides.Left)) {
	                   // Left side switch
	                	autoCommand = leftSideCloseSwitchAuto;
	                } else {
	                   // Right side scale
	                	autoCommand = rightSideCloseScaleAuto;
	                }
	                break;

	            case "RL": // Check to see if robot is placed left or right 
	                if (sideSelector.getSelected().equals(Sides.Left)) {
	                	// Left side Scale
	                	autoCommand = leftSideCloseScaleAuto;
	                } else {
	                	// Right side switch
	                	autoCommand = rightSideCloseSwitchAuto;
	                }
	                break;

	            case "LL": // Check to see if robot is placed left or right 
	                if (sideSelector.getSelected().equals(Sides.Left)) {
	                    if (autoSelector.getSelected().equals(Autos.SwitchOnly)) {
	                        autoCommand = leftSideCloseSwitchAuto;
	                    }
	                    if (autoSelector.getSelected().equals(Autos.ScaleOnly)) {
	                        autoCommand = leftSideCloseScaleAuto;
	                    }
	                } else {
	                	// Right side drive straight
	                	autoCommand = driveStraightOnly;
	                }
	                break;

	            case "RR": // Check to see if robot is placed left or right 
	                if (sideSelector.getSelected().equals(Sides.Left)) {
	                	// Left side drive straight
	                	autoCommand = driveStraightOnly;
	                } else {
	                    if (autoSelector.getSelected().equals(Autos.SwitchOnly)) {
	                        autoCommand = rightSideCloseSwitchAuto;
	                    }
	                    if (autoSelector.getSelected().equals(Autos.ScaleOnly)) {
	                        autoCommand = rightSideCloseScaleAuto;
	                    }
	                }
	                break;

	            default:
	                autoCommand = driveStraightOnly;				
	                SmartDashboard.putString("Chosen Auto", "Drive Straight");
	                break;
	        }

	       
		}
		
		catch(NullPointerException e) {
			autoCommand = driveStraightOnly;				
            SmartDashboard.putString("Chosen Auto", "Drive Straight Null"); 
		}
		 catch(Exception e) {
			 autoCommand = driveStraightOnly;				
	         SmartDashboard.putString("Chosen Auto", "Drive Straight" + e.toString()); 
		 } 
		
		 autoCommand.start();
	     SmartDashboard.putString("Chosen Auto", autoCommand.toString());
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
		// Sets the auto mode we will run
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if ( autoCommand != null) {
			autoCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
