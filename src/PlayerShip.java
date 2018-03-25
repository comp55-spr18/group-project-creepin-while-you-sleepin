import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class PlayerShip extends Ship {
	public PlayerShip() {
		setInvincible(false);
		setIframe(100);
		setHealth(5);
		setCooldown(0);
		setMaxCooldown(1);
		setCanShoot(true);
		setShipPoints(new GPoint[] {});
		setLocation(new GPoint(0,0));
		setGunLocation(new GPoint[] {new GPoint(50,17.5)});
		setSprite(new GImage("auto.png", getLocation().getX(), getLocation().getY()));
		setBulletColor(Color.BLUE);
		getSprite().setSize(50, 50);
	}
	@Override
	public void move() {	// Moves the player's ship hitbox to the location of the ship
		double x = getLocation().getX();
		double y = getLocation().getY();
		setShipPoints(new GPoint[]{new GPoint(x,y), new GPoint(x,y+10), new GPoint(x,y+20), new GPoint(x,y+30), new GPoint(x,y+40), new GPoint(x,y+50), new GPoint(x+10,y+50), new GPoint(x+20,y+50), new GPoint(x+30,y+50), new GPoint(x+40,y+50), new GPoint(x+50,y+50),new GPoint(x+50,y+40), new GPoint(x+50,y+30), new GPoint(x+50,y+20), new GPoint(x+50,y+10), new GPoint(x+50, y), new GPoint(x+40, y), new GPoint(x+30, y), new GPoint(x+20, y), new GPoint(x+10, y)});
		setGunLocation(new GPoint[] {new GPoint(x+50,y+17.5)});
		getSprite().setLocation(getLocation());
	}
	@Override
	public Projectile shoot() {		// Returns the projectile type and iterates to the next gun location (or the same one if only one)
		Projectile returnProj = new Projectile(true, getGunLocation()[getSelectedGun()], 1, 0, 14, getBulletColor(), 15);
		if(getSelectedGun() == getGunLocation().length) {
			setSelectedGun(0);
		} else {
			setSelectedGun(getSelectedGun() + 1);
		}
		return returnProj;
	}
}
