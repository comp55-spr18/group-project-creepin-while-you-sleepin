import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.graphics.GOval;
import acm.graphics.GPoint;

public class HomingBullet extends Projectile {
	private int gracePeriod = 0;		// Initial timer on the bullet
	private int maxGracePeriod = 20;	// After gracePeriod passes this value, the projectile can hit enemies
	private int disengage = 150;		// After gracePeriod passes this value, the missile will stop homing
	public HomingBullet(MainApplication game, boolean isPlayerProj, GPoint gunLoc, double xD, double yD, int spd, Color bulletColor, int size) {
		setGame(game);
		setTimer(new Timer(1000/game.fps, this));
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
		double angle = Math.atan(getyDir()/getxDir());
		getSprite().move(Math.cos(angle)*getSpeed()*dx, Math.sin(angle)*getSpeed()*dx);
		setLocation(getSprite().getLocation());
		if(gracePeriod < disengage) {
			// The two lines below update the movement vector to point at the player
			setxDir((getGame().player.getLocation().getX()+25) - getLocation().getX());
			setyDir((getGame().player.getLocation().getY()+25) - getLocation().getY());
		}
		if(getGame() != null && (getLocation().getX() < -50 || getLocation().getX() > getGame().WINDOW_WIDTH)) {
			getGame().remove(getSprite());
			getTimer().stop();
		}
	}
	
	public void onCollision(Ship target) {
		if(!target.isInvincible()) {
			if(target instanceof PlayerShip) {
				target.setInvincible(true);
			}
			if(target instanceof PlayerShip || gracePeriod >= maxGracePeriod) {
				getGame().remove(getSprite());
				getTimer().stop();
				target.setHealth(target.getHealth() - 1);
				if(!(target instanceof PlayerShip)) {
					target.setHealth(0);
					getGame().updateScoreBoard(500);
				} else {
					getGame().updateHealthBoard();
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(getGame().runGame) {
			move();
			checkCollision();
			gracePeriod++;
		} else if(getGame().lose || getGame().win) {
			getGame().remove(getSprite());
			getTimer().stop();
		}
	}
}
