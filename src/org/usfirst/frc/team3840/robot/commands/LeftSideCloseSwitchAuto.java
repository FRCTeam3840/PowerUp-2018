package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives to left side switch.
 */
public class LeftSideCloseSwitchAuto extends CommandGroup{

	 public LeftSideCloseSwitchAuto() {
		
		// A command group will require all of the subsystems that each member would require.
		    requires(Robot.driveTrain);
		    requires(Robot.liftElevator);
		    requires(Robot.intake);
		    
		    addParallel(new autoHoldCubeIn(true));
	  		addSequential(new LiftToTravel());
	  		addSequential(new autoDriveStraight(26380));   
	  		addSequential(new autoTurnRight());
	  		addSequential(new LiftToSwitch());
	  		addSequential(new autoDummyDelay());
	  		addSequential(new autoDriveStraight(3000));
	  		addSequential(new autoDummyDelay());
	  	  	addParallel(new autoHoldCubeIn(false));
	  		addSequential(new autoEjectCube());
	  		
	 }
}
