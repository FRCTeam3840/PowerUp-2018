package org.usfirst.frc.team3840.robot.commands;

import org.usfirst.frc.team3840.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Autonomous Drive Straight
 */
public class autoDriveStraight extends Command {
	 private double m_distance;
	 Timer m_timer = new Timer();

    public autoDriveStraight(double xDistance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        m_distance = xDistance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.zeroDistanceTraveled();
    	m_timer.reset();
    	m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.print("Drive Started");
    	Robot.driveTrain.DriveToDistance();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putNumber("Switch Distance", Robot.driveTrain.getDistanceTraveled());
    	return (Robot.driveTrain.getDistanceTraveled() >= m_distance);  
    }

    // Called once after isFinished returns true
    protected void end() {
    	SmartDashboard.putNumber("Time", m_timer.get());
    	Robot.driveTrain.StopMotion();
    	m_timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
