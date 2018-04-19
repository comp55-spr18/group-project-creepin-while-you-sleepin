package ships;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.Beam;
import projectiles.Bullet;
import projectiles.FireTrail;

public class Boss extends Ship {
	private int counter;
	private int currentAttack;
	public Boss(Game game, double y) {
		super(game);
		counter = 0;
		setMaxHealth(150);
		setGunLocation(new GPoint[] {new GPoint(), new GPoint(), new GPoint()});
		setSprite(new GImage("boss 1.png", game.WINDOW_WIDTH, game.WINDOW_HEIGHT/(1080/y)));
		setBulletColor(Color.white);
		setSize(500, 500);
		setBulletColor(Color.RED);
		setxDir(-1);
		setyDir(0);
		setSpeed(4);
		setPoints(100);
		setCollisionDamage(2);
		setBulletDamage(2);
		setBulletSize(40);
		setBulletSpeed(14);
		setBeamHeight(250);
		setBeamDuration(65);
		setBeamWarningDuration(60);
		setBeamDamage(1);
		setSelectedGun(0);
		getGame().add(getSprite());
		setTrail(new FireTrail(this));

		if(game.currLevel >= 2) {
			setMaxHealth(400);
			setMaxCooldown(35);
			setSpeed(6);
			setBulletSpeed(11);
		}

		if(game.currLevel >= 3) {
			setMaxHealth(1500);
			setMaxCooldown(20);
			setBeamDuration(30);
			setBeamDamage(2);
		}
	}

	public void move() { 			//boss will spawn in and then bounce up and down on the screen
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		if(getSprite().getX() <= getGame().WINDOW_WIDTH/(1920/1400.0) && getyDir() == 0) {
			setxDir(0);
			setyDir(-1);
		}
		if(getSprite().getY() <= 0 || getSprite().getY() + getSprite().getHeight() >= getGame().WINDOW_HEIGHT - 100) {
			setyDir(getyDir() * -1);
		}
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		getGunLocation()[0].setLocation(x, y + getSprite().getHeight()/5);
		getGunLocation()[1].setLocation(x, y + (4*getSprite().getHeight()/5));
		getGunLocation()[2].setLocation(x + getSprite().getWidth()/2, y + getSprite().getHeight()/2);
		if(getSprite().getLocation().getX() < -100) {
			setDestroyed(true);
		}
	}

	public void shoot() { 			//shoot is constructed to shoot multiple straight bullets at the player
		switch(currentAttack) {
		case 0:
			primary();
			break;
		case 1:
			beam();
			break;
		case 2:
			primary();
			break;
		case 3:
			squeeze();
			break;
		}
		if(currentAttack == 4) {
			currentAttack = 0;
		}
	}

	public void primary() {
		if(counter%20 == 0) {
			Bullet newProj = new Bullet(this, getGunLocation()[getSelectedGun()], -1, 0);
			newProj.aimAtPlayer();
			setSelectedGun((getSelectedGun() + 1)%2);
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
		}
		counter++;
		if(counter%200 == 0) {
			counter = 0;
			currentAttack++;
		}
	}

	public void beam() {
		if(counter == 0) {
			new Beam(this, getGunLocation()[2]);
		}
		counter++;
		if(counter == 160) {
			counter = 0;
			currentAttack++;
		}
	}

	public void squeeze() {
		if(counter == 0) {
			new Beam(this, getGunLocation()[0], 20);
			new Beam(this, getGunLocation()[1], 20);
		}
		if(counter%5 == 0) {
			new Bullet(this, getGunLocation()[0], -1, -0.5);
			new Bullet(this, getGunLocation()[1], -1, 0.5);
			new Bullet(this, getGunLocation()[0], -1, -0.2);
			new Bullet(this, getGunLocation()[1], -1, 0.2);
		}
		counter++;
		if(counter == 160) {
			counter = 0;
			currentAttack++;
		}
	}
}
