package ships;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.Bullet;
import projectiles.FireTrail;
import projectiles.Projectile;

public class Boss2 extends Ship {
	public Boss2(Game game, double y) {
		super(game);
		setInvincible(false);
		setMaxHealth(150);
		setCooldown(0);
		setMaxCooldown(50);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("boss 1.png", game.WINDOW_WIDTH, game.WINDOW_HEIGHT/(1080/y)));
		setBulletColor(Color.white);
		setSize(350, 350);
		setBulletColor(Color.RED);
		setExplosion(new GImage("explosion.png"));
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		setyDir(0);
		setSpeed(7);
		setPoints(100);
		setCollisionDamage(2);
		setBulletDamage(2);
		setBulletSize(80);
		setBulletSpeed(10);
		setSelectedGun(0);
		setTrail(new FireTrail(this));
	}
	
	public void move() { //boss again will spawn in and bounce up and down on screen
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		if(getSprite().getX() <= 1000 && getyDir() == 0) {
			setxDir(0);
			setyDir(-1);
		}
		if(getSprite().getY() <= 0 || getSprite().getY() + getSprite().getHeight() >= getGame().WINDOW_HEIGHT - 100) {
			setyDir(getyDir() * -1);
		}
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+getSprite().getHeight()/5), new GPoint(x,y+(4*getSprite().getHeight()/5))});
		if(getSprite().getLocation().getX() < -100) {
			setDestroyed(true);
		}
	}
	
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			Projectile newProj = new Bullet(this, getGunLocation()[getSelectedGun()], -1, 0);
			setSelectedGun((getSelectedGun() + 1)%2);
			newProj.setxDir((getGame().player.getSprite().getLocation().getX()+25) - newProj.getSprite().getLocation().getX()-newProj.getSprite().getWidth()/2);
			newProj.setyDir((getGame().player.getSprite().getLocation().getY()+25) - newProj.getSprite().getLocation().getY()-newProj.getSprite().getWidth()/2);
			getGame().add(newProj.getSprite());
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