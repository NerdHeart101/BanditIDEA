/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1817;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */

// If you rename or move this class, update the build.properties file in the project root
public class Robot extends TimedRobot 
{
    
    private static final String DEFAULT_AUTO = "Default";
    private static final String CUSTOM_AUTO = "My Auto";
    private String autoSelected;
    private SendableChooser<String> chooser = new SendableChooser<>();

    private DifferentialDrive drive;
    private XboxController xboxController;
    /**
     * 0 == right forward channel 1 == left forward channel 6 left reverse    7   right reverse
     *
     */
    private  DoubleSolenoid leftShifter, rightShifter, gearClamp;



    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() 
    {
        chooser.addDefault("Default Auto", DEFAULT_AUTO);
        chooser.addObject("My Auto", CUSTOM_AUTO);
        SmartDashboard.putData("Auto choices", chooser);

        VictorSP victorSPLeft = new VictorSP(0);
        VictorSP victorSPRight = new VictorSP(1);

        victorSPLeft.setInverted(true);
        victorSPRight.setInverted(true);

        xboxController = new XboxController(0);
        leftShifter = new DoubleSolenoid(1, 6);
        rightShifter = new DoubleSolenoid(7, 0);
        gearClamp = new DoubleSolenoid(5,2);

        SmartDashboard.putNumber("Solenoid", 0);
        drive = new DifferentialDrive(victorSPLeft, victorSPRight);
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() 
    {
        autoSelected = chooser.getSelected();
        // autoSelected = SmartDashboard.getString("Auto Selector",
        //      DEFAULT_AUTO);
        System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() 
    {
        switch (autoSelected) 
        {
            case CUSTOM_AUTO:
                // Put custom auto code here
                break;
            case DEFAULT_AUTO:
            default:
                // Put default auto code here
                break;
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        drive();
        shift();
    }

    /**
     * Function to drive bot in arcade mode
     */
    private void drive() {
        double LY = -xboxController.getY(GenericHID.Hand.kLeft);
        double RX = xboxController.getX(GenericHID.Hand.kRight);
        drive.arcadeDrive(LY, RX);
    }

    private void shift() {
        boolean RB = xboxController.getBumper(GenericHID.Hand.kRight);

        if (RB) {
            leftShifter.set(DoubleSolenoid.Value.kForward);
            rightShifter.set(DoubleSolenoid.Value.kReverse);
        } else {
            leftShifter.set(DoubleSolenoid.Value.kReverse);
            rightShifter.set(DoubleSolenoid.Value.kForward);
        }
    }
    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
        
    }
}
