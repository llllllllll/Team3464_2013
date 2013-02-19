/* Team 3464 2013 robot code
 * @Authors: Jack Pugmire, Joe Jevnik, Trevor Aron
 * @Version: BlazerBot 3000
 */
package robotcode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DriverStationLCD;


public class Team3464_2013 extends SimpleRobot {

    DriverStationLCD dslcd = DriverStationLCD.getInstance();
    
    int forwardDriveTime = 1500;
    double autoSpeed = .5;
    RobotDrive drive = new RobotDrive(1,2);
    DigitalInput topSensor = new DigitalInput(1);
    DigitalInput lowSensor = new DigitalInput(2);

    Joystick leftJoy = new Joystick(2);
    Joystick rightJoy = new Joystick(1);
    Joystick shooterJoy = new Joystick(3);

    Jaguar shooter = new Jaguar(3);
    double defaultSpeed = 0.5;
    Victor feedme = new Victor(4);
    DigitalInput feederFront = new DigitalInput(3);
    DigitalInput feederBack = new DigitalInput(4);

    boolean dropperOpen = false;
    double shooterJoyDeadZone = 0.15;
    
    long firstTime, lastTime;
    long correctionTime = 50;
    /**
     * The robot drives forward for forwardDriveTime, then spin clockwise until 
     * the robot finds the left side of the bottom goal. Once it finds the tape, 
     * the robot will drive forward until it is within a set distance from the 
     * wall and then deliver the Frisbees to the goal.
     */
    public void autonomous() {
	dslcd.println(DriverStationLCD.Line.kUser2, 1, "Searching for goal.");
	dslcd.println(DriverStationLCD.Line.kUser3, 1, "Systems > Mech");
	dslcd.println(DriverStationLCD.Line.kUser4, 1, "Systems > Mech");
	dslcd.println(DriverStationLCD.Line.kUser5, 1, "Systems > Mech");
	dslcd.println(DriverStationLCD.Line.kUser6, 1, "Systems > Mech");

        long startTime = System.currentTimeMillis();
        while(topSensor.get() && isAutonomous()){
            drive.tankDrive(-.8*autoSpeed, .8*autoSpeed);
        }
        firstTime = System.currentTimeMillis();
        while(!topSensor.get()){
            drive.tankDrive(-.8*autoSpeed, .8*autoSpeed);
        }
	dslcd.println(DriverStationLCD.Line.kUser2, 1, "Found goal.");
	dslcd.println(DriverStationLCD.Line.kUser3, 1, "Aligning to goal.");
        lastTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - lastTime < (lastTime-firstTime)){
            drive.tankDrive(.85*autoSpeed, -.85*autoSpeed);
        }
	dslcd.println(DriverStationLCD.Line.kUser2, 1, "Shooting.");
	dslcd.println(DriverStationLCD.Line.kUser3, 1, "<3");

	shooter.set(defaultSpeed);

	for(int i = 0; i < 4; ++i) {
		while(!feederFront.get()) {
			feedme.set(0.5);
		}
		while(!feederBack.get()) {
			feedme.set(-0.5);
		}
	}
    }

    public void operatorControl() {
	double shooterSpeed = 0.0;
	double triggerSpeed = 0.0;

	boolean top = false, bot = false, fast_deploy= false;
	boolean feed = false;
        boolean chmod = false;
        boolean arcade = false;

        dslcd.println(DriverStationLCD.Line.kUser3, 1, "Tank mode");

        while(isOperatorControl()) {
            if(arcade) {
                drive.arcadeDrive(rightJoy);
            } else
                drive.tankDrive(leftJoy.getMagnitude() < shooterJoyDeadZone ? 0 : leftJoy.getAxis(Joystick.AxisType.kY),
                    rightJoy.getMagnitude() < shooterJoyDeadZone ? 0 : rightJoy.getAxis(Joystick.AxisType.kY));

	    if(!top && shooterJoy.getRawButton(Keybinds.adjustUp)) {
		shooterSpeed += 0.05;
		if(shooterSpeed > 1.0) shooterSpeed = 1.0;
		dslcd.println(DriverStationLCD.Line.kUser2, 1, "Shooter speed" + shooterSpeed);
	    }
	    if(!bot && shooterJoy.getRawButton(Keybinds.adjustDown)) {
		shooterSpeed -= 0.05;
		if(shooterSpeed < 0.0) shooterSpeed = 0.0;
		dslcd.println(DriverStationLCD.Line.kUser2, 1, "Shooter speed" + shooterSpeed);
	    }
	    if(!fast_deploy && shooterJoy.getRawButton(Keybinds.start)) {
		shooterSpeed = defaultSpeed;
		dslcd.println(DriverStationLCD.Line.kUser2, 1, "Shooter speed" + shooterSpeed);
	    }
            if(!chmod && rightJoy.getRawButton(Keybinds.chmod)) {
		arcade = !arcade;
		dslcd.println(DriverStationLCD.Line.kUser3, 1, (arcade ? "Arcade" : "Tank") + " mode");
	    }

	    top = (top != shooterJoy.getRawButton(Keybinds.adjustUp)) ? shooterJoy.getRawButton(Keybinds.adjustUp) : top;
	    bot = (bot != shooterJoy.getRawButton(Keybinds.adjustDown)) ? shooterJoy.getRawButton(Keybinds.adjustDown) : bot;
	    fast_deploy = (fast_deploy != shooterJoy.getRawButton(Keybinds.start)) ? shooterJoy.getRawButton(Keybinds.start) : fast_deploy;
	    chmod = (chmod != rightJoy.getRawButton(Keybinds.chmod)) ? shooterJoy.getRawButton(Keybinds.chmod) : chmod;

	    if(shooterJoy.getRawButton(Keybinds.kill) && shooterSpeed != 0.0) {
		shooterSpeed = 0.0;
		dslcd.println(DriverStationLCD.Line.kUser2, 1, "Shooter speed" + shooterSpeed);
	    }

	    shooter.set(shooterSpeed);

	    feed = (shooterJoy.getRawButton(Keybinds.feedDisk) || feed) && !feederFront.get();
	    if(feed) {
		triggerSpeed = 0.5;
		dslcd.println(DriverStationLCD.Line.kUser3, 1, "Fire in the hole.");
	    }
	    else {
		triggerSpeed = !feederBack.get() ? -0.5 : 0.0;
		dslcd.println(DriverStationLCD.Line.kUser3, 1, "Ready to fire.");
	    }

	    feedme.set(triggerSpeed);
	}
    }
   
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    }
    
    private void climb(){
        //>climbing
    }
}
