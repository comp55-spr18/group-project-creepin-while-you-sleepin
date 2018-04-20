package ships;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.Beam;
import projectiles.FireTrail;

public class Squeeze extends Ship {
	public Squeeze(Game game, double y) {
		super(game);
		setMaxHealth(5);
		setCooldown(0);
		setMaxCooldown(70);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint()});
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(100, 100);
		setxDir(-1);
		setyDir(0);
		setSpeed(6);
		setPoints(100);
		setCollisionDamage(1);
		getGame().add(getSprite());
		setTrail(new FireTrail(this));
		setBeamHeight(100);
		setBeamDuration(80);
		setBeamWarningDuration(40);
		setBeamDamage(1);

		if(game.currLevel >= 2) {
			setMaxHealth(10);
			setBeamHeight(125);
		}

		if(game.currLevel >= 3) {
			setMaxHealth(15);
			setBeamDamage(2);
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
		 new Beam(this, getGunLocation()[0]);
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}

}
