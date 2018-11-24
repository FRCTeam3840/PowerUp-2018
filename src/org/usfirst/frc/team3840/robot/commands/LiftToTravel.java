
package org.usfirst.frc.team3840.robot.commands;
import org.usfirst.frc.team3840.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


/**
 *  Command to run lift to travel position.
 *  Calls lift elevator subsystem
 */
public class LiftToTravel extends Command {
	final String travelLocation = "LiftToTravel";
	
    public LiftToTravel() {

        requires(Robot.liftElevator);

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	Robot.liftElevator.LiftToPosition(travelLocation);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
    	if(Robot.liftElevator.intSensorPosition > Robot.liftElevator.intLowwerRange || Robot.liftElevator.intSensorPosition >= Robot.liftElevator.intUpperRange || true) { 
    		return true;
    	}
    	
		return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }
}
