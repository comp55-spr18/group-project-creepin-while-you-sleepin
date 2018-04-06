import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Kamikazi extends Ship {
	public Kamikazi(MainApplication game, double y) {
		setGame(game);
		setInvincible(false);
		setHealth(3);
		setCooldown(325);
		setMaxCooldown(400);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("sprites/enemy3.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(50, 50);
		setExplosion(new GImage("explosion.png"));
		setBulletColor(Color.yellow);
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		setyDir(0);
		setSpeed(8);
		setPoints(100);
		setCollisionDamage(2);
		setTrail(new FireTrail(this));
	}
	@Override
	public void move() {
		//Problem is here: ship will not spawn, goal is to get ship to move directly at player ship no shots fired, fast movement
		aimAtPlayer();
		vectorMove();
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+17.5)});
		if(getSprite().getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}
}




