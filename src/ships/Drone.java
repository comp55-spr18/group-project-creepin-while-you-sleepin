package ships;
import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.Bullet;
import projectiles.FireTrail;

public class Drone extends Ship {
	private int lifetime = 0;	//They get removed based on lifetime being incremented
	private double topBot;		//Decides if it's curving up or down
	private double droneCurve;	//Set in constructor to determine curve amount
	private double curveLimit;	//Used to determine the curve peak
	
	public Drone(Game game, double y, double curvy) {
		super(game);
		setMaxHealth(1);				//They're weak enemies
		setCooldown(920);				//I want them to fire once then never again, dealt with by long cd
		setSprite(new GImage("sprites/enemy2.png", getGame().WINDOW_WIDTH, y));
		setSize(40, 40);				//small
		setSpeed(10);					//kinda speedy
		setGunLocation(new GPoint[] {new GPoint()});
		setTrail(new FireTrail(this));
		setMaxCooldown(1000);
		setPoints(25);					//Not worth many points since they come in swarms
		setBulletSize(20);
		setBulletSpeed(12);
		setBulletDamage(1);
		getGame().add(getSprite());
		//sets where the drone will start to curve back at
		curveLimit = curvy;
		//This sets the arc of the drones based on the height of the player's screen.
		//Can also be altered to change the drone's curve (specifically the 43.2)
		droneCurve = 43.2/getGame().WINDOW_HEIGHT;
		//Sets the initial y direction of the drone based on whether it's in the top
		//or bottom half of the screen. Also scaled to screen size
		if (y <= getGame().WINDOW_HEIGHT/2) {
			setyDir(droneCurve*7.5);
		}
		else {
			setyDir(-droneCurve*7.5);
		}
		topBot = y;						
		setCanShoot(false);		
		setxDir(-1);
		//Buffs based on the level the player is on
		if(game.currLevel >= 2) {
			setBulletDamage(2);
			setShielded(true);
		}

		if(game.currLevel >= 3) {
			setCooldown(20);
			setMaxCooldown(50);
		}
	}
	// Shoots at player's location
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			Bullet newProj = new Bullet(this, getGunLocation()[0], -1, 0);
			newProj.aimAtPlayer();
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}

	//moves the drone until part-way down the screen, where it curves back the way it came.
	//Removes drone if it's been alive for long enough.
	@Override
	public void move() {
		if(getSprite().getLocation().getX() < curveLimit) {
			if (getxDir() != 1) {
				if(topBot <= getGame().WINDOW_HEIGHT/2) {
					if(getxDir() <= 0) {
						setyDir(getyDir() + droneCurve);
					}
					else {
						setyDir(getyDir() - droneCurve);
					}
				}
				
				else {
					if(getxDir() <= 0) {
						setyDir(getyDir() - droneCurve);
					}
					else {
						setyDir(getyDir() + droneCurve);
					}
				}
				
				setxDir(getxDir() + droneCurve);
			}
		}
		lifetime++;
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		getGunLocation()[0].setLocation(x,y+getSprite().getHeight()/2);
		if(lifetime > 200) {
			setDestroyed(true);
		}
		
	}
}
