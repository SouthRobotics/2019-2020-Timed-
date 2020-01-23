/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Arduino.ArduinoCommunication;
import frc.robot.Arduino.PixyCam;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

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

    private PixyCam pixy;

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

    //Instantiating talons and the Right motor group
    RMotor1 = new WPI_TalonSRX(31);
    RMotor2 = new WPI_TalonSRX(2);
    RMotor3 = new WPI_TalonSRX(1);
    RightMotors = new SpeedControllerGroup(RMotor1, RMotor2, RMotor3);

    //Instantiating talons and left motor group
    LMotor1 = new WPI_TalonSRX(14);
    LMotor2 = new WPI_TalonSRX(13);
    LMotor3 = new WPI_TalonSRX(15);
    LeftMotors = new SpeedControllerGroup(LMotor1, LMotor2, LMotor3);

   // dDrive = new DifferentialDrive(LeftMotors, RightMotors);

    //Making Joysticks
    RJoy = new Joystick(0);
    LJoy = new Joystick(1);

    //pixy = new PixyCam(115200);

    //c = new Compressor(0);
  
    //c.setClosedLoopControl(true);
    //c.start();


    //boolean enabled = c.enabled();
    //boolean pressureSwitch = c.getPressureSwitchValue();
    //double current = c.getCompressorCurrent();
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
    double a = .6;
    double b = 1-(RJoy.getRawAxis(3));
    //dDrive.tankDrive(LJoy.getRawAxis(1) * a, RJoy.getRawAxis(1) * a);
    RMotor1.set(-(1-LJoy.getRawAxis(3)) * a);
    RMotor2.set((1-LJoy.getRawAxis(3)) * a/b);
    RMotor3.set((1-LJoy.getRawAxis(3)) * a/b);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    /*
    if(RJoy.getTrigger())
    {
      s.set(kReverse);
      System.out.println("FORWARD");
    }
    else if(LJoy.getTrigger())
    {
      s.set(kForward);
      System.out.println("REVERSE");
    }
    else
      s.set(kOff);

    if(RJoy.getRawAxis(3) < .5)
      c.start();
    else
      c.stop();
    */
    if(RJoy.getTrigger())
    {
      double[] vals =  pixy.getBlocks();
      if(vals[0] != -1)
        dDrive.tankDrive((1-vals[2])*vals[0], (1-vals[2])*(1-vals[0]));
      else
        dDrive.tankDrive(0, 0);
    }
    else
    {
      dDrive.tankDrive(0, 0);
    }
  }
}
