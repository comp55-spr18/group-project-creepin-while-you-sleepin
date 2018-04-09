import java.awt.Color;
import acm.graphics.GPoint;
// This class exists just for fancy effects, it is a bullet that does no damage
public class Emitter extends Projectile {
	public Emitter(Ship ship, GPoint gunLoc, double xD, double yD, double spd, Color bulletColor, int size) {
		super(ship, gunLoc, xD, yD);
		setSpeed(spd);
		setPlayerProjectile(true);
		getSprite().setColor(bulletColor);
		getSprite().setFillColor(bulletColor);
		setSize(size, size);
		getSprite().setLocation(gunLoc.getX(), gunLoc.getY() - getSprite().getHeight()/2);
		setDamage(0);
	}
	
	// I had to redefine checkCollision and onCollision here since this projectile type does nothing but add aesthetics
	public void checkCollision() {
	}
	
	public void onCollision(Ship target) {
	}
}
