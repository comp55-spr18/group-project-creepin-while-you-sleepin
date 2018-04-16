package ships;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.Bullet;
import projectiles.FireTrail;

public class Trishot extends BasicEnemy {
	
	public Trishot(Game game, double y) {
		super(game, y);
		setMaxHealth(8);		// fairly tanky
		setSprite(new GImage("sprites/enemy4.png", getGame().WINDOW_WIDTH, y));
		setSize(80, 80);
		setSpeed(5);			//Normal speed
		setTrail(new FireTrail(this));
		setPoints(150);	
		setCooldown(5); 
		setMaxCooldown(100);
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
