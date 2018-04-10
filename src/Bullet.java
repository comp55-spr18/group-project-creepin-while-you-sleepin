import acm.graphics.GPoint;

public class Bullet extends Projectile {
	public Bullet(Ship ship, GPoint gunLoc, double xD, double yD) {
		super(ship, gunLoc, xD, yD);
	}
	// Notice that I didn't redefine any functions here so it inherits move(), checkCollision(), and onCollision() from Projectile
}
