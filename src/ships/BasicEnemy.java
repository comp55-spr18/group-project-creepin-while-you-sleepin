package ships;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.Bullet;
import projectiles.FireTrail;

public class BasicEnemy extends Ship {
	double i = -1.5;
	double j = -0.05;
	public BasicEnemy(Game game, double y) {
		super(game);
		setMaxHealth(2);
		setCooldown(500);
		setMaxCooldown(575);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint()});
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(50, 50);
		setxDir(-1);
		setyDir(0);
		setSpeed(5);
		setPoints(100);
		setBulletDamage(1);
		setBulletSpeed(10);
		setBulletSize(15);
		getGame().add(getSprite());
		setTrail(new FireTrail(this));

		if(game.currLevel >= 2) {
			setMaxHealth(4);
			setCooldown(300);
			setMaxCooldown(375);
			setSpeed(6);
			setBulletSpeed(11);
		}

		if(game.currLevel >= 3) {
			setMaxHealth(8);
			setCooldown(100);
			setMaxCooldown(175);
		}
	}
	@Override
	public void move() {
		i = i+j;
		setyDir(i);
		setyDir(getGame().WINDOW_HEIGHT/(1080/getyDir()));
		if(Math.abs(i) >= 3) {
			j *= -1;
		}
		getSprite().move(getxDir()*getSpeed(), getyDir());
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
			Bullet newProj = new Bullet(this, getGunLocation()[0], -1, 0);
			newProj.aimAtPlayer();
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
