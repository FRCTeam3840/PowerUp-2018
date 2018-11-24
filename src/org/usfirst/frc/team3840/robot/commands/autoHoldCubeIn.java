package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class autoHoldCubeIn extends Command {
	boolean isIn;
	boolean isCompleted;
	
    public autoHoldCubeIn(boolean selection) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.intake);
        isIn = selection;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(isIn = true) {
    		Robot.intake.holdCube();
    		isCompleted = false;
    	}
    	
    	if(isIn = false) {
    		isCompleted = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isCompleted;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stopMotion();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
