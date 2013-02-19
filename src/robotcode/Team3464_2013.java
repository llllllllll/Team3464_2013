/* Team 3464 2013 robot code
 * @Authors: Joe Jevnik, Jack Pugmire, Zayd Salah, Trevor Aron, Tomas Bravo.
 * @Version: YoloBot4000
 */
package robotcode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;


public class Team3464_2013 extends SimpleRobot {
    
    int forwardDriveTime = 1500;
    double autoSpeed = .5;
    RobotDrive drive = new RobotDrive(1,2);
    DigitalInput topSensor = new DigitalInput(1);
    DigitalInput lowSensor = new DigitalInput(2);

    Joystick leftJoy = new Joystick(2);
    Joystick rightJoy = new Joystick(1);
    Joystick shooterJoy = new Joystick(3);

    Jaguar shooter = new Jaguar(3);
    Victor feedme = new Victor(4);
    DigitalInput feederFront = new DigitalInput(3);
    DigitalInput feederBack = new DigitalInput(4);

    boolean dropperOpen = false;
    double joyDeadZone = 0.15;
    
    long correctionTime = 50;
    /**
     * The robot drives forward for forwardDriveTime, then spin clockwise until 
     * the robot finds the left side of the bottom goal. Once it finds the tape, 
     * the robot will drive forward until it is within a set distance from the 
     * wall and then deliver the Frisbees to the goal.
     */
    public void autonomous() {
        long startTime = System.currentTimeMillis();
        while(topSensor.get() && isAutonomous()){
            drive.tankDrive(-.8*autoSpeed, .8*autoSpeed);
        }
        firstTime = System.currentTimeMillis();
        while(!topSensor.get()){
            drive.tankDrive(-.8*autoSpeed, .8*autoSpeed);
        }
        lastTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - lastTime < (lastTime-firstTime)){
            drive.tankDrive(.85*autoSpeed, -.85*autoSpeed);
        }
    }

    /**
     * 
     */
    public void operatorControl() {
	double shooterSpeed = 0.0;
	double triggerSpeed = 0.0;

	boolean top = false, bot = false, fast_deploy= false;
	boolean feed = false;

        while(isOperatorControl()) {
            drive.tankDrive(leftJoy.getMagnitude() < joyDeadZone ? 0 : leftJoy.getAxis(Joystick.AxisType.kY),
                    rightJoy.getMagnitude() < joyDeadZone ? 0 : rightJoy.getAxis(Joystick.AxisType.kY));

	    if(!top && joy.getRawButton(adjustUp)) {
		shooterSpeed += 0.05;
		if(shooterSpeed > 1.0) shooterSpeed = 1.0;
		printMsg("Shooter speed" + shooterSpeed;
	    }
	    if(!bot && joy.getRawButton(adjustDown)) {
		shooterSpeed -= 0.05;
		if(shooterSpeed < 0.0) shooterSpeed = 0.0;
		printMsg("Shooter speed" + shooterSpeed;
	    }
	    if(!fast_deploy && joy.getRawButton(start)) {
		shooterSpeed += 0.05;
		if(shooterSpeed > 1.0) shooterSpeed = 1.0;
		printMsg("Shooter speed" + shooterSpeed;
	    }

	    top = top != joy.getRawButton(adjustUp) ? joy.getRawButton(adjustUp) : top;
	    bot = bot != joy.getRawButton(adjustDown) ? joy.getRawButton(adjustDown) : bot;
	    fast_deploy = fast_deploy != joy.getRawButton(start) ? joy.getRawButton(start) : fast_deploy;

	    if(joy.getRawButton(stop) && shooterSpeed != 0.0) {
		shooterSpeed = 0.0;
		printMsg("Shooter speed" + shooterSpeed;
	    }

	    shooter.set(shooterSpeed);

	    feed = (joy.getRawButton(feedDisk) || feed) && !triggerFront.get();
	    if(feed) {
		triggerSpeed = 0.5;
	    }
	    else {
		triggerSpeed = !triggerBack.get() ? -0.5 : 0.0;
	    }

	    feedme.set(triggerSpeed);
	}
    }
   
    public void printMsg(String msg) {

    }
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    }
    
    private void autoClimb(){
        //>climbing
    }
}
