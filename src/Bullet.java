import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.graphics.GOval;
import acm.graphics.GPoint;

public class Bullet extends Projectile {
	public Bullet(MainApplication game, boolean isPlayerProj, GPoint gunLoc, double xD, double yD, int spd, Color bulletColor, int size) {
		setTimer(new Timer(16, this));
		setGame(game);
		setPlayerProjectile(isPlayerProj);
		setLocation(new GPoint(gunLoc));
		setSprite(new GOval(15,15));
		getSprite().setFillColor(bulletColor);
		getSprite().setColor(bulletColor);
		getSprite().setFilled(true);
		setxDir(xD);
		setyDir(yD);
		getSprite().setSize(size, size);
		setSpeed(spd);
		getSprite().setLocation(gunLoc);
		getTimer().start();
	}
	public void move() {
		int dx = 1;
		if(getxDir() < 0) dx = -1;
		getSprite().move(Math.cos(Math.atan(getyDir()/getxDir()))*getSpeed()*dx, Math.sin(Math.atan(getyDir()/getxDir()))*getSpeed()*dx);
		setLocation(getSprite().getLocation());
		if(getGame() != null && (getLocation().getX() < -50 || getLocation().getX() > getGame().WINDOW_WIDTH)) {
			getGame().remove(getSprite());
			getGame().bullets.remove(this);
			getTimer().stop();
		}
	}
	public void onCollision(Ship target) {
		if((isPlayerProjectile() && !(target instanceof PlayerShip)) || (!isPlayerProjectile() && target instanceof PlayerShip)) {
			if(!target.isInvincible()) {
				if(target instanceof PlayerShip) {
					target.setInvincible(true);
				}
				getGame().remove(getSprite());
				getGame().bullets.remove(this);
				getTimer().stop();
				target.setHealth(target.getHealth() - 1);
				if(target.getHealth() <= 0) {
					getGame().remove(target.getSprite());
					getGame().enemies.remove(target);
					getGame().updateScoreBoard(100);
					target.getTimer().stop();
				}
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		checkCollision();
	}
}
