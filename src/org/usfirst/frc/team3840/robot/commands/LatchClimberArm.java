package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 */
public class LatchClimberArm extends Command {
		
    public LatchClimberArm() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.unLatchClimber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(0.6);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.unLatchClimber.UnlatchArm_CW();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.unLatchClimber.StopMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
