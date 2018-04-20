package ships;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.FireTrail;
import projectiles.HomingBullet;

public class HomingEnemy extends Ship {
	public HomingEnemy(Game game, double y) {
		super(game);
		setMaxHealth(4);
		setCooldown(500);
		setMaxCooldown(575);
		setCanShoot(false);
		setSprite(new GImage("sprites/enemy12.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setGunLocation(new GPoint[] {new GPoint()});
		setSize(75,75);
		setxDir(-1);
		setyDir(0);
		setBulletSize(40);
		setBulletDamage(2);
		setBulletSpeed(8);
		setSpeed(4);
		setPoints(200);
		getGame().add(getSprite());
		setTrail(new FireTrail(this));

		if(game.currLevel >= 2) {
			setMaxHealth(8);
			setCooldown(300);
			setMaxCooldown(375);
			setBulletSpeed(10);
		}

		if(game.currLevel >= 3) {
			setBulletDamage(3);
			setMaxHealth(16);
			setCooldown(100);
			setMaxCooldown(175);
		}
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
