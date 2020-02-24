/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Auto.*;
import frc.robot.SpecificFunctions.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private OI oi;
  private DriveTrain driveTrain;
  //private Intake intake;
  private Outtake outtake;

  private WPI_TalonSRX a,b,c;

  Auto auto;

  //private PixyCam pixy;
  //CANCoder e = new CANCoder(15);
 //R 31,2,1   L 13,14,15
   // Compressor c;
    //DoubleSolenoid s = new DoubleSolenoid(0, 2);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    oi = new OI(new int[]{0,1}, 0, OI.TANKDRIVE);
    driveTrain = new DriveTrain(new int[]{3,5,4}, new int[]{0,1,2}, oi, 1);
   // intake = new Intake(new int[]{3}, new int[]{5});
    //outtake = new Outtake(3, -.3, oi, 5, 0);
    
    //a = new WPI_TalonSRX(6);
    //b = new WPI_TalonSRX(1);
  //  c = new WPI_TalonSRX(2);

   // auto = new Auto();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
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
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);


  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
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
    if(oi.getJoysticks()[0].getRawButtonReleased(5))
    
      oi.switchDirections();
    driveTrain.moveMotors();
    //intake.intakePeriodic();
    //outtake.outTakePeriodic();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    //a.set(oi.getJoysticks()[0].getRawAxis(OI.JOY_Y));
    //b.set(oi.getJoysticks()[0].getRawAxis(OI.JOY_Y));
    //c.set(oi.getJoysticks()[0].getRawAxis(OI.JOY_Y));
  }
}
