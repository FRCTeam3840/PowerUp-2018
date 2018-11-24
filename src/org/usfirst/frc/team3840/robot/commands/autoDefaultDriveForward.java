package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Default Drive Forward
 */
public class autoDefaultDriveForward extends CommandGroup {

    public autoDefaultDriveForward() {
    	requires(Robot.driveTrain);
    	requires(Robot.liftElevator);
    	requires(Robot.intake);
    	//commands the robot performs
    	addParallel(new autoHoldCubeIn(true));
  		addSequential(new LiftToTravel());
  		addSequential(new autoDriveStraight(26380));
  		addParallel(new autoHoldCubeIn(false));
    }
}
