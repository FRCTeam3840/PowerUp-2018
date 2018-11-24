/********************************************************************************************************************
 *  FRC 2018 PowerUP
 *  Team 3840 (TNT)
 *  Command Group: AutonomousCommand
 *  Created 02/15/18
 *  Description:
 *  AutonomousCommand...three commands calls for auto selection.  Called from Robot Main.
 *  0 & 1 = Drive Straight  2 = Left Switch, 3 = Right Switch
 * *****************************************************************************************************************
*/

package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Autonomous Command code
 */
public class AutonomousCommand extends CommandGroup {

    public AutonomousCommand(int Selection) {
    	// A command group will require all of the subsystems that each member would require.
    	requires(Robot.driveTrain);
    	requires(Robot.liftElevator);
    	requires(Robot.intake);
    	
    	SmartDashboard.putNumber("AutoSelected", Selection);
    	/**
    	 * Select the auto commands
    	 */
    	switch(Selection) {
	  	case 0: 
	  	//	addSequential(new autoDriveStraight(AutoDistances.driveToSwitch));
	  		break;
	  	case 1: 
	  	//	addSequential(new autoDriveStraight(AutoDistances.driveToSwitch));
	  		break; 
	  	case 2:    //drive to switch and put cube
	  		addParallel(new autoHoldCubeIn(true));
	  		addSequential(new LiftToTravel());
	  		addSequential(new autoDriveStraight(26380));   
	  		addSequential(new autoTurnRight());
	  		addSequential(new LiftToSwitch());
	  		addSequential(new autoDummyDelay());
	  		addSequential(new autoDriveStraight(5000));
	  		addSequential(new autoDummyDelay());
	  	  	addParallel(new autoHoldCubeIn(false));
	  		addSequential(new autoEjectCube());
	 
	  		break;
	  	case 3:
	  	
	  	default:  
		 
	  }
    	
    	
    }

  
}
