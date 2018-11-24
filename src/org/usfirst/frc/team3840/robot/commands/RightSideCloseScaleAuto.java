package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Right Side to the scale auto
 */
public class RightSideCloseScaleAuto extends CommandGroup {

    public RightSideCloseScaleAuto() {
    	// A command group will require all of the subsystems that each member would require.
	    requires(Robot.driveTrain);
	    requires(Robot.liftElevator);
	    requires(Robot.intake);
	    
	    addParallel(new autoHoldCubeIn(true));
  		addSequential(new LiftToTravel());
  		
  		// To the switch is 26380 cts
  		addSequential(new autoDriveStraight(52760));   
  		addSequential(new autoTurnLeft());
  		addSequential(new LiftToSwitch());
  		addSequential(new autoDummyDelay());
  		addSequential(new autoDriveStraight(5000));
  		addSequential(new autoDummyDelay());
  	  	addParallel(new autoHoldCubeIn(false));
  		addSequential(new autoEjectCube());
    }
}
