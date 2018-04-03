import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GPoint;
public class HeavyWeightEnemy extends Ship {
	public HeavyWeightEnemy(MainApplication game, double y) {
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
		setSize(200, 200);
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		setyDir(0);
		setSpeed(6);
		setPoints(100);
		setCollisionDamage(1);
		setTrail(new FireTrail(this));
	}
	@Override
	public void move() {
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+17.5)});


		if(getSprite().getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			Projectile newProj = new Bullet(getGame(), false, getGunLocation()[0], -1, 0, 14, getBulletColor(), 300);
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


