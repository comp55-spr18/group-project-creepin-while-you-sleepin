import java.awt.event.ActionEvent;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Drone extends TestEnemy {
	private int lifetime = 0;
	private double topBot;
	
	public Drone(MainApplication game, double y) {
		super(game, y);
		setHealth(1);				//They're weak enemies
		setCooldown(915);			//I want them to fire once then never again, dealt with by long cd
		setMaxCooldown(1000);
		setPoints(25);
		if (y <= getGame().WINDOW_HEIGHT/2) {
			setyDir(.3);
		}
		else {
			setyDir(-.3);
		}
		//setLocation(new GPoint(getGame().WINDOW_WIDTH/1.1, y));
		topBot = y;
		//setSprite(new GImage("sprites/enemy1.png", getLocation().getX(), getLocation().getY()));		
		
	}
	// tweaked bullet speed
	@Override
	public void shoot() {
		if(canShoot()) {
			Projectile newProj = new Bullet(getGame(), false, getGunLocation()[0], -1, 0, 12, getBulletColor(), 20);
			newProj.setxDir((getGame().player.getLocation().getX()+25) - newProj.getLocation().getX());
			newProj.setyDir((getGame().player.getLocation().getY()+25) - newProj.getLocation().getY());
			getGame().add(newProj.getSprite());
			setCanShoot(false);
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
		if(getLocation().getX() < (getGame().WINDOW_WIDTH) / 1.5) {
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
		setLocation(getSprite().getLocation());
		double x = getLocation().getX();
		double y = getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+17.5)});
		if(lifetime > 500) {
			setDestroyed(true);
		}
		
	}
}
