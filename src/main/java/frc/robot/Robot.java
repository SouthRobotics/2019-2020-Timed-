/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

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


  /**************Declaring Motors, Motor Groups, and Differential drive****************************** */
  private WPI_TalonSRX RMotor1;
  private WPI_TalonSRX RMotor2;
  private WPI_TalonSRX RMotor3;
  private SpeedControllerGroup RightMotors;

  private WPI_TalonSRX LMotor1;
  private WPI_TalonSRX LMotor2;
  private WPI_TalonSRX LMotor3;
  private SpeedControllerGroup LeftMotors;

  
  private DifferentialDrive dDrive;
  /************************* OI ************************************************************************** */

    Joystick RJoy;
    Joystick LJoy;

  /*********************************************************************************************************/


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //Instantiating talons and the Right motor group
    RMotor1 = new WPI_TalonSRX(0);
    RMotor2 = new WPI_TalonSRX(2);
    RMotor3 = new WPI_TalonSRX(1);
    RightMotors = new SpeedControllerGroup(RMotor1, RMotor2, RMotor3);

    //Instantiating talons and left motor group
    LMotor1 = new WPI_TalonSRX(14);
    LMotor2 = new WPI_TalonSRX(13);
    LMotor3 = new WPI_TalonSRX(15);
    LeftMotors = new SpeedControllerGroup(LMotor1, LMotor2, LMotor3);

    dDrive = new DifferentialDrive(LeftMotors, RightMotors);


    //Making Joysticks
    RJoy = new Joystick(0);
    LJoy = new Joystick(1);
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
    double a = -1;
    dDrive.tankDrive(LJoy.getRawAxis(1) * a, RJoy.getRawAxis(1) * a);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
