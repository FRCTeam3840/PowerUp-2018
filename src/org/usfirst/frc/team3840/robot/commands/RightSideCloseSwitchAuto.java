package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * Right position to switch auto
 */
public class RightSideCloseSwitchAuto extends CommandGroup {

    public RightSideCloseSwitchAuto() {
    	// A command group will require all of the subsystems that each member would require.
	    requires(Robot.driveTrain);
	    requires(Robot.liftElevator);
	    requires(Robot.intake);
	    
	    addParallel(new autoHoldCubeIn(true));
  		addSequential(new LiftToTravel());
  		addSequential(new autoDriveStraight(26380));   
  		addSequential(new autoTurnLeft());
  		addSequential(new LiftToSwitch());
  		addSequential(new autoDummyDelay());
  		addSequential(new autoDriveStraight(5000));
  		addSequential(new autoDummyDelay());
  	  	addParallel(new autoHoldCubeIn(false));
  		addSequential(new autoEjectCube());
    }
}
