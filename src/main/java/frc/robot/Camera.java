/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * This class is used to control the Camera
 */
public class Camera {

    private Mat source, output;
    private boolean crosshairs = false;


    public Camera()
    {
        new Thread(() -> {
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
            camera.setResolution(640, 480);
        
            CvSink cvSink = CameraServer.getInstance().getVideo();
            CvSource outputStream = CameraServer.getInstance().putVideo("Processed Video", 640, 480);
        
            source = new Mat();
            output = new Mat();
        
            while(!Thread.interrupted()) {
              if (cvSink.grabFrame(source) == 0) {
                continue;
              }
              Imgproc.cvtColor(source, output, Imgproc.COLOR_RGB2RGBA);

              if(crosshairs)
              {
                Imgproc.circle(output, new Point(source.width() / 2, source.height()/2), 50, new Scalar(0, 0, 255), 5);
                Imgproc.line(output, new Point(source.width() / 2, source.height()/2 - 100), new Point(source.width() / 2, source.height()/2 - 20), new Scalar(0,0,255), 5);
                Imgproc.line(output, new Point(source.width() / 2, source.height()/2 + 100), new Point(source.width() / 2, source.height()/2 + 20), new Scalar(0,0,255), 5);
                Imgproc.line(output, new Point(source.width() / 2 - 100, source.height()/2), new Point(source.width() / 2 - 20, source.height()/2), new Scalar(0,0,255), 5);
                Imgproc.line(output, new Point(source.width() / 2 + 100, source.height()/2), new Point(source.width() / 2 + 20, source.height()/2), new Scalar(0,0,255), 5);
              }
              else
              {
                Imgproc.circle(output, new Point(source.width() / 2, source.height()/2), 10, new Scalar(0, 0, 255), 10); 
              }
              outputStream.putFrame(output);
            }

        }).start();
    }

    public void toggleCrosshairs()
    {
        crosshairs = !crosshairs;
    }
}
