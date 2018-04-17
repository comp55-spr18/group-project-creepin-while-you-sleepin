package projectiles;
import java.awt.Color;

import acm.graphics.GPoint;
import ships.Ship;
// This class exists just for fancy effects, it is a bullet that does no damage
public class Emitter extends Bullet {
	public Emitter(Ship ship, GPoint gunLoc, double xD, double yD, double spd, Color bulletColor, int size) {
		super(ship, gunLoc, xD, yD);
		setSpeed(spd);
		setPlayerProjectile(true);
		getSprite().setColor(bulletColor);
		getSprite().setFillColor(bulletColor);
		getSprite().setSize(size, size);
		getSprite().setLocation(gunLoc.getX() - getSprite().getWidth()/2, gunLoc.getY() - getSprite().getHeight()/2);
		setCollisionDamage(0);
	}
	
	// I had to redefine checkCollision and onCollision here since this projectile type does nothing but add aesthetics
	public void checkCollision() {
	}
	
	public void onCollision(Ship target) {
	}
}