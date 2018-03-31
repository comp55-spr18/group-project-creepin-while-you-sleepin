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
		setLocation(new GPoint(getGame().WINDOW_WIDTH, y));
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("sprites/enemy1.png", getLocation().getX(), getLocation().getY()));
		setBulletColor(Color.yellow);
		getSprite().setSize(200, 200);
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		setyDir(0);
		setSpeed(6);
		setPoints(100);
		setTrail(new FireTrail(this));
	}
	@Override
	public void move() {
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		setLocation(getSprite().getLocation());
		double x = getLocation().getX();
		double y = getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+17.5)});
		if(getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}
	@Override
	public void shoot() {
		if(canShoot()) {
			Projectile newProj = new Bullet(getGame(), false, getGunLocation()[0], -1, 0, 14, getBulletColor(), 300);
			newProj.setxDir((getGame().player.getLocation().getX()+25) - newProj.getLocation().getX()-newProj.getSprite().getWidth()/2);
			newProj.setyDir((getGame().player.getLocation().getY()+25) - newProj.getLocation().getY()-newProj.getSprite().getWidth()/2);
			getGame().add(newProj.getSprite());
			setCanShoot(false);
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}
}


