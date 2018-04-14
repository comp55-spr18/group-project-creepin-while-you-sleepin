package ships;

import acm.graphics.GPoint;
import game.Game;
import projectiles.HomingBullet;

public class HomingEnemy extends BasicEnemy {
	public HomingEnemy(Game game, double y) {
		super(game, y);
		setGunLocation(new GPoint[] {new GPoint()});
		setBulletSize(40);
		setBulletDamage(2);
		setBulletSpeed(8);
		setMaxHealth(4);
		setSpeed(4);
		setPoints(200);
	}

	@Override
	public void move() {
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		getGunLocation()[0].setLocation(x,y+getSprite().getHeight()/2);
		if(getSprite().getLocation().getX() < -100) {
			setDestroyed(true);
		}
	}

	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			new HomingBullet(this, getGunLocation()[0], -1, 0);
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}
}
