package ships;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;

public class Kamikaze extends Ship {
	public Kamikaze(Game game, double y) {
		super(game);
		setMaxHealth(2);
		setCooldown(325);
		setMaxCooldown(400);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("sprites/enemy3.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(50, 50);
		setBulletColor(Color.yellow);
		setxDir(-1);
		setyDir(0);
		setSpeed(8);
		setPoints(100);
		setCollisionDamage(2);

		if(game.currLevel >= 2) {
			setMaxHealth(4);
		}

		if(game.currLevel >= 3) {
			setMaxHealth(8);
			setSpeed(10);
		}
	}
	@Override
	public void move() {
		aimAtPlayer();
		vectorMove();
		getTrail().setLocation(getSprite().getX(), getSprite().getY());
		if(getSprite().getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}
}




