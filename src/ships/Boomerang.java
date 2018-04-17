package ships;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.BoomerangBullet;

public class Boomerang extends Ship {
	public Boomerang(Game game, double y) {
		super(game);
		setHealth(2);
		setCooldown(0);
		setMaxCooldown(75);
		setCanShoot(false);
		setGunLocation(new GPoint[] { new GPoint(50, 15) });
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT / (1080 / y)));
		setSize(50, 50);
		setxDir(-1);
		setyDir(0);
		setSpeed(5);
		setPoints(100);
		setBulletDamage(1);
		setBulletSpeed(20);
		setBulletSize(15);
	}

	@Override
	public void move() {
		getSprite().move(getxDir() * getSpeed(), getyDir() * getSpeed());
		getTrail().setLocation(getSprite().getX(), getSprite().getY());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] { new GPoint(x, y + getSprite().getHeight() / 2) });
		if (getSprite().getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}

	@Override
	public void shoot() {
		if (canShoot()) {
			setCanShoot(false);
			new BoomerangBullet(this, getGunLocation()[0], -1, 0);
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
		} else {
			setCooldown(getCooldown() + 1);
			if (getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}
}
