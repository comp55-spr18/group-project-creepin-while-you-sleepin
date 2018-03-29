import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class TestEnemy extends Ship {
	public TestEnemy(MainApplication game) {
		setGame(game);
		setTimer(new Timer(1000/game.fps, this));
		setInvincible(false);
		setHealth(3);
		setCooldown(100);
		setMaxCooldown(175);
		setCanShoot(false);
		setLocation(new GPoint(1900,300));
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("enemy.jpg", getLocation().getX(), getLocation().getY()));
		setBulletColor(Color.RED);
		getSprite().setSize(50, 50);
		setxDir(-1);
		setyDir(0);
		setSpeed(6);
	}
	@Override
	public void move() {
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		setLocation(getSprite().getLocation());
		double x = getLocation().getX();
		double y = getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+17.5)});
		getSprite().setLocation(getLocation());
		if(getLocation().getX() < -50) {
			getGame().remove(getSprite());
			getTimer().stop();
		}
	}
	@Override
	public void shoot() {
		if(canShoot()) {
			Projectile newProj = new Bullet(getGame(), false, getGunLocation()[0], -1, 0, 14, getBulletColor(), 20);
			newProj.setxDir((getGame().player.getLocation().getX()+25) - newProj.getLocation().getX());
			newProj.setyDir((getGame().player.getLocation().getY()+25) - newProj.getLocation().getY());
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
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		shoot();
		if(getHealth() == 0) {
			getGame().remove(getSprite());
			getGame().updateScoreBoard(100);
			getTimer().stop();
		}
		if (getGame().lose || getGame().win) {
			getGame().remove(getSprite());
			getTimer().stop();
		}
	}
}
