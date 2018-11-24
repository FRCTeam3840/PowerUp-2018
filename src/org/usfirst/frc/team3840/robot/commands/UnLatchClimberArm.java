package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UnLatchClimberArm extends Command {
	Timer m_timer = new Timer();
	
    public UnLatchClimberArm() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.unLatchClimber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_timer.reset();
    	m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.unLatchClimber.UnlatchArm_CCW();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       if(m_timer.get() > 0.6) {
    	   m_timer.stop();
    	   return true;
       }
       return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	m_timer.stop();
    	Robot.unLatchClimber.StopMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
