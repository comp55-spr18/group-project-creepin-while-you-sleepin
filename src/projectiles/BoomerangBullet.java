package projectiles;
import acm.graphics.GPoint;
import ships.Ship;

public class BoomerangBullet extends Bullet {
	public BoomerangBullet(Ship ship, GPoint gunLoc, double xD, double yD) {
		super(ship, gunLoc, xD, yD);

	}

	@Override
	public void move() {
		getSprite().move(getxDir() * getSpeed(), getyDir() * getSpeed());
		setSpeed(getSpeed() - .3);
		if (getGame() != null && (getSprite().getX() < -50 || getSprite().getX() > getGame().WINDOW_WIDTH
				|| getSprite().getY() < -50 || getSprite().getY() > getGame().WINDOW_HEIGHT)) {
			setDestroyed(true);
			getGame().remove(getSprite());
		}
	}
}
