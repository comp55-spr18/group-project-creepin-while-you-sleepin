import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Drone extends Ship {
	private int lifetime = 0;
	private double topBot;
	private FireTrail trail;
	
	public Drone(MainApplication game, double y) {
		super(game, y);
		getTrail().getTimer().stop();
		setHealth(1);				// They're weak enemies
		setCooldown(920);			// I want them to fire once then never again, dealt with by long cd
		setSprite(new GImage("sprites/enemy2.png", getGame().WINDOW_WIDTH, y));
		setSize(40, 40);
		setSpeed(10);
		setTrail(new FireTrail(this));
		setMaxCooldown(1000);
		setPoints(25);
		setCanShoot(false);
		setLocation(new GPoint(getGame().WINDOW_WIDTH, y));
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("sprites/enemy1.png", getLocation().getX(), getLocation().getY()));
		setBulletColor(Color.RED);
		getSprite().setSize(50, 50);
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		if (y <= getGame().WINDOW_HEIGHT/2) {
			setyDir(.3);
		}
		else {
			setyDir(-.3);
		}
		setSpeed(6);
		setTrail(new FireTrail(this));
		//setLocation(new GPoint(getGame().WINDOW_WIDTH/1.1, y));
		topBot = y;
<<<<<<< HEAD
		//setSprite(new GImage("sprites/enemy1.png", getLocation().getX(), getLocation().getY()));																			
											
=======
		//setSprite(new GImage("sprites/enemy1.png", getLocation().getX(), getLocation().getY()));		
>>>>>>> branch 'master' of https://github.com/comp55-spr18/group-project-creepin-while-you-sleepin.git
	}
	// tweaked bullet speed
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			Projectile newProj = new Bullet(getGame(), false, getGunLocation()[0], -1, 0, 12, getBulletColor(), 20);
			newProj.aimAtPlayer();
			getGame().add(newProj.getSprite());
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}

	//moves the drone until part-way down the screen, where it curves back the way it came
	@Override
	public void move() {
		if(getSprite().getLocation().getX() < (getGame().WINDOW_WIDTH) / 1.5) {
			if (getxDir() != 1) {
				
				if(topBot <= getGame().WINDOW_HEIGHT/2) {
					if(getxDir() <= 0) {
						setyDir(getyDir() + .04);
					}
					else {
						setyDir(getyDir() - .04);
					}
				}
				
				else {
					if(getxDir() <= 0) {
						setyDir(getyDir() - .04);
					}
					else {
						setyDir(getyDir() + .04);
					}
				}
				
				setxDir(getxDir() + .04);
			}
		}
		lifetime++;
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+17.5)});
		if(lifetime > 200) {
			setDestroyed(true);
		}
		
	}
	public FireTrail getTrail() {
		return trail;
	}
	public void setTrail(FireTrail trail) {
		this.trail = trail;
	}
}
