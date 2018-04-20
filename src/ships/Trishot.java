package ships;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.Bullet;
import projectiles.FireTrail;

public class Trishot extends Ship {
	
	public Trishot(Game game, double y) {
		super(game);
		setMaxHealth(8);		// fairly tanky
		setSprite(new GImage("sprites/enemy4.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(80, 80);
		setSpeed(5);			//Normal speed
		setTrail(new FireTrail(this));
		setPoints(150);	
		setCooldown(5); 
		setMaxCooldown(100);				
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint()});
		setxDir(-1);
		setyDir(0);
		setBulletDamage(1);
		setBulletSpeed(10);
		setBulletSize(15);
		getGame().add(getSprite());
		//level buffs
		if(game.currLevel >= 2) {
			setMaxHealth(16);
			setMaxCooldown(80);
			setBulletDamage(2);
		}

		if(game.currLevel >= 3) {
			setMaxHealth(32);
			setMaxCooldown(60);
		}
	}

	// shoots three at medium speed
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			new Bullet(this, getGunLocation()[0], -1, -.2);
			new Bullet(this, getGunLocation()[0], -1, 0);
			new Bullet(this, getGunLocation()[0], -1, .2);
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}		
	}

	//stops at right side of map, meant to just pump out bullets
	@Override
	public void move() {
		if (getSprite().getLocation().getX() > getGame().WINDOW_WIDTH/1.1) {
			getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		}
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] { new GPoint(x, y + getSprite().getHeight() / 2) });
		
	}
}
