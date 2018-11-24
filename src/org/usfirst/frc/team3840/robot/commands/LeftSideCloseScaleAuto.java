package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Left Side Scale Auto
 */
public class LeftSideCloseScaleAuto extends  CommandGroup {

	 public LeftSideCloseScaleAuto() {
		// A command group will require all of the subsystems that each member would require.
	    requires(Robot.driveTrain);
	    requires(Robot.liftElevator);
	    requires(Robot.intake);
	    
	    addParallel(new autoHoldCubeIn(true));
  		addSequential(new LiftToTravel());
  		
  		// To the switch is 26380 cts
  		addSequential(new autoDriveStraight(52760)); 
  		addSequential(new LiftToScale());
  		addSequential(new autoDummyDelay());
  		addSequential(new autoTurnRight());
  		addSequential(new autoDummyDelay());
  		addSequential(new autoDriveStraight(2000));
  		addSequential(new autoDummyDelay());
  	  	addParallel(new autoHoldCubeIn(false));
  		addSequential(new autoEjectCube());
		 
	 }
}
