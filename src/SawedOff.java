import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class SawedOff extends Ship {
	public SawedOff(MainApplication game, double y) {
		setGame(game);
		setInvincible(false);
		setShots(2);
		setHealth(5);
		setCooldown(0);
		setMaxCooldown(70);
		setCanShoot(false);
		setGunLocation(new GPoint[] {});
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setBulletColor(Color.yellow);
		setSize(50, 50);
		setExplosion(new GImage("explosion.png"));
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		setyDir(0);
		setSpeed(2);
		setPoints(100);
		setBulletDamage(1);
		setBulletSpeed(10);
		setBulletSize(15);
		setCollisionDamage(1);
		setTrail(new FireTrail(this));
	}
	@Override
	public void move() {
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y + getSprite().getHeight()/2)});
		if(getSprite().getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}
	@Override
	public void shoot() {
		if(getHealth() < 3) {
			setShots(3);
			setSpeed(5);
			getSprite().setImage("sprites/enemy5.png");
			getSprite().setSize(50, 50);
			setMaxCooldown(50);
			if(getCooldown() > getMaxCooldown()) {
				setCooldown(0);
			}
			
		}
		if(canShoot()) {
			setCanShoot(false);
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
			switch(getShots()) {
			case 2:
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() + getBulletSize()), -1, 0);
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() - getBulletSize()), -1, 0);
				break;
			case 3:
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() + getBulletSize()), -1, 0.2);
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() - getBulletSize()), -1, -0.2);
			default:
				new Bullet(this, getGunLocation()[0], -1, 0);
			}
		} else if (!canShoot()) {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}

	}

