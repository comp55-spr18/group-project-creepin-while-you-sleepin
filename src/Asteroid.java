import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Asteroid extends Ship {
	public Asteroid(Game game, double x) {
		super(game);
		setHealth(1);
		setCooldown(0);
		setMaxCooldown(75);
		setCanShoot(false);
		setSprite(new GImage("sprites/asteroid.gif", getGame().WINDOW_WIDTH/(1920/x), -100));
		setBulletColor(Color.RED);
		setSize(100, 100);
		setxDir(-1);
		setyDir(1);
		setSpeed(8);
		setTrail(new FireTrail(this));
//		getGame().fallCount = getGame().playSound("fall", getGame().fallCount);
	}	

	@Override
	public void shoot() {}

	@Override
	public void move() {
		getSprite().move(getxDir() * getSpeed(), getyDir() * getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] { new GPoint(x, y + getSprite().getHeight() / 2) });
		if (getSprite().getLocation().getY() > getGame().WINDOW_HEIGHT + 100) {
			setDestroyed(true);
		}
	}
}
