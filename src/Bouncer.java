import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Bouncer extends Ship {
	
	public Bouncer(MainApplication game, double y) {
		setGame(game);
		setInvincible(false);
		setShots(2);
		setHealth(5);
		setCooldown(0);
		setMaxCooldown(70);
		setCanShoot(false);
		setGunLocation(new GPoint[] {});
		setSprite(new GImage("sprites/enemy2.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setBulletColor(Color.yellow);
		setSize(50, 50);
		setExplosion(new GImage("explosion.png"));
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		setyDir(-6);
		setSpeed(6);
		setPoints(100);
		setBulletDamage(1);
		setBulletSpeed(10);
		setBulletSize(15);
		setCollisionDamage(1);
		setTrail(new FireTrail(this));
		
	}
	@Override
	public void move() {
		
		getSprite().move(getxDir(), getyDir());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y + getSprite().getHeight()/2)});
		if(getSprite().getY() >= getGame().WINDOW_HEIGHT || getSprite().getY() <= 0) {
			
			setyDir(getyDir()*-1);
		}
		if(getSprite().getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}

}
