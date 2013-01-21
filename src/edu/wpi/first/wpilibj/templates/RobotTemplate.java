/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        int turnTime = 500;
        long startTime;
        DigitalInput frontSensor = new DigitalInput(5);
        RobotDrive drive = new RobotDrive(1,2,3,4);
        while(frontSensor.get()){
            drive.tankDrive(1, 1);
        }
        startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < turnTime){
            drive.tankDrive(1, -1);
        }
        while(frontSensor.get()){
            drive.tankDrive(1, 1);
        }
        startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < turnTime/3){
            drive.tankDrive(1, -1);
        }
            
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        
        //DigitalInput test = new DigitalInput(1);
        //while(true){
            //System.out.println(test.get());
        //}
    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
