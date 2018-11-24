package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Auto Drive up to switch
 */
public class autoDriveToSwitch extends Command {
	private double m_distance;
	Timer m_timer = new Timer();
	
    public autoDriveToSwitch(double xDistance) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.zeroDistanceTraveled();
    	m_timer.reset();
    	m_timer.start();
	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.DriveToDistance();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putNumber("Switch Distance", Robot.driveTrain.getDistanceTraveled());
    	return (Robot.driveTrain.getDistanceTraveled() >= m_distance);  
    }

    // Called once after isFinished returns true
    protected void end() {
    	SmartDashboard.putNumber("TimeSwitch", m_timer.get());
    	Robot.driveTrain.StopMotion();
    	m_timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
