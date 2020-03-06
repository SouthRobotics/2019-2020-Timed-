/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Arduino.ArduinoCommunication;
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
  private Intake intake;
  private Outtake outtake;
  private OuttakeLift lift;
  private BallSystem ballSystem;
  private Camera camera;

  private WPI_TalonSRX a,b,c;

  private Auto auto;

  private DigitalOutput d0;
  private PWM p0;
  private boolean d = false;
  private Servo servo;

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
    driveTrain = new DriveTrain(new int[]{3,4,10}, new int[]{0,1,2}, oi, 1);
    intake = new Intake(new int[]{7}, new int[]{8});
    intake.setBackupButtons(oi, 0, 4);
    outtake = new Outtake(6, -.3, oi, 5, 0);
    d0 = new DigitalOutput(1);
    lift = new OuttakeLift(0);
    ballSystem = new BallSystem(intake, outtake, lift);
   // camera = new Camera();
   // a = new WPI_TalonSRX(6);
    //b = new WPI_TalonSRX(1);
  //  c = new WPI_TalonSRX(2);
    //p0 = new PWM(0);
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
   // if(oi.getJoysticks()[0].getRawButtonReleased(5))
   //   oi.switchDirections();
   // driveTrain.moveMotors();
   driveTrain.moveMotors(0, 0, false);
    intake.intakePeriodic();
    //outtake.outTakePeriodic();

    if(oi.getJoysticks()[0].getTriggerPressed())
      d=!d;

    if(d)
     // t.start();
      lift.set(true);
    else
     // t.stop();
      lift.set(false);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    //if(oi.getJoysticks()[0].getTriggerReleased())
    //  camera.toggleCrosshairs();
    driveTrain.moveMotors();
    ballSystem.ballSystemPeriodic();
  }
}
