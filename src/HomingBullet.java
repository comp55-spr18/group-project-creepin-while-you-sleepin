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
		super(game, isPlayerProj, gunLoc, xD, yD, spd, bulletColor, size);
		setDestructable(true);
	}
	
	// Notice that I had to redefine how this projectile moves since it homes in on the target
	public void move() {
		int dx = 1;
		if(getxDir() < 0) dx = -1;
		double angle = Math.atan(getyDir()/getxDir());
		getSprite().move(Math.cos(angle)*getSpeed()*dx, Math.sin(angle)*getSpeed()*dx);
		if(gracePeriod < disengage) {
			aimAtPlayer();
		}
		if(getGame() != null && (getSprite().getLocation().getX() < -50 || getSprite().getLocation().getX() > getGame().WINDOW_WIDTH)) {
			getGame().remove(getSprite());
			setDestroyed(true);
		}
	}
	
	// I also had to redefine onCollision since this projectile type can hit any ship after it's launched - including the ship that fired it
	public void onCollision(Ship target) {
		if(!target.isInvincible()) {
			if(target instanceof PlayerShip) {
				target.setInvincible(true);
			}
			if(target instanceof PlayerShip || gracePeriod >= maxGracePeriod) {
				getGame().remove(getSprite());
				setDestroyed(true);
				target.setHealth(target.getHealth() - 1);
				if(!(target instanceof PlayerShip)) {
					target.setHealth(0);
					getGame().updateScoreBoard(500);
				}
			}
		}
	}
	
	// I had to redefine the actionPerformed since I need to increment gracePeriod every time the timer is called
	@Override
	public void update() {
		move();
		checkCollision();
		gracePeriod++;
		if(getGame().lose || getGame().win) {
			getGame().remove(getSprite());
			setDestroyed(true);
		}
	}
}
