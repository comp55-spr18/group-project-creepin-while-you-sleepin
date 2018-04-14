
import java.awt.Color;


import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Bouncer extends Ship {

	public Bouncer(Game game, double y) {
		super(game);
		setShots(2);
		setMaxHealth(5);
		setCooldown(0);
		setMaxCooldown(150);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint()});
		setSprite(new GImage("sprites/enemy2.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT / (1080 / y)));
		setBulletColor(Color.yellow);
		setSize(100, 100);
		setxDir(-2);
		setyDir(-4);
		setSpeed(10);
		setPoints(100);
		setBulletDamage(1);
		setBulletSpeed(10);
		setBulletSize(20);
		setCollisionDamage(1);
		setTrail(new FireTrail(this));

	}

	@Override
	public void move() {
		getSprite().move(getxDir(), getyDir());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		getGunLocation()[0].setLocation(x,y+getSprite().getHeight()/2);
		if (getSprite().getY() <= 0 || getSprite().getY() + getSprite().getHeight() >= getGame().WINDOW_HEIGHT-100) {
			setyDir(getyDir() * -1);
		}
		if (getSprite().getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}

	@Override
	public void shoot() {
		if (canShoot()) {
			setCanShoot(false);
			Projectile newProj = new Bullet(this, getGunLocation()[0], -1, 0);
			newProj.aimAtPlayer();
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
