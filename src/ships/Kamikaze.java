package ships;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.FireTrail;

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
		getGame().add(getSprite());
		setTrail(new FireTrail(this));

		if(game.currLevel >= 2) {
			setMaxHealth(4);
		}

		if(game.currLevel >= 3) {
			setCollisionDamage(3);
			setMaxHealth(8);
			setSpeed(10);
		}
	}
	@Override
	public void move() {
		aimAtPlayer();
		vectorMove();
		if(getSprite().getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}

	public void checkCollision() {
		if(getSprite().getBounds().intersects(game.player.getSprite().getBounds())) {
			game.player.dealDamage(getCollisionDamage());
			setDestroyed(true);
		}
	}
}
