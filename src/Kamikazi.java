import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Kamikazi extends Ship {
	public Kamikazi(MainApplication game, double y) {
		setGame(game);
		setInvincible(false);
		setHealth(5);
		setCooldown(325);
		setMaxCooldown(400);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setExplosion(new GImage("explosion.png"));
		setBulletColor(Color.yellow);
		setSize(500, 500);
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		setyDir(0);
		setSpeed(10);
		setPoints(100);
		setTrail(new FireTrail(this));
	}
	@Override
	public void move() {
		//Problem is here: ship will not spawn, goal is to get ship to move directly at player ship no shots fired, fast movement
		getSprite().move(getGame().player.getSprite().getX()*getSpeed(),getGame().player.getSprite().getY()*getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+17.5)});


		if(getSprite().getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}

		
	}




