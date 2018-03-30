import java.awt.event.ActionEvent;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class TestDroneEnemy extends TestEnemy {
	private int lifetime = 0;
	private double topBot;
	
	public TestDroneEnemy(MainApplication game, double y) {
		super(game, y);
		setHealth(1);				//They're weak enemies
		setCooldown(915);			//I want them to fire once then never again, dealt with by long cd
		setMaxCooldown(1000);
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
	//had to override this so I could make the score different. Maybe revise how we do score in the future?
	@Override
	public void actionPerformed(ActionEvent e) {	// This is the default loop that a ship will use
		if(!isDestroyed()) {						// If the ship is not destroyed
			move();									// Move the ship
			shoot();								// Tell the ship to shoot
			if(getHealth() <= 0) {					// If the ship's health is below 0
				getGame().updateScoreBoard(25);	    // Add points to the score board
				setDestroyed(true);					// Destroy the ship
			}
			if (getGame().lose || getGame().win) {	// If the game is over
				getGame().remove(getSprite());		// Remove the ship sprite
				getTimer().stop();					// Stop the ship timer
			}
		} else {									// If the ship is destroyed
			getSprite().setImage("explosion.png");	// Change the sprite to an explosion
			getSprite().setSize(50,50);				// Set the image size
			setDestroyedCounter(getDestroyedCounter() + 1);		// Increment the destroyed counter
			if(getDestroyedCounter() == 50) {		// When the counter hits 50
				getGame().remove(getSprite());		// Remove the ship sprite
				getTimer().stop();					// Stop the ship timer
			}
		}
	}
}
