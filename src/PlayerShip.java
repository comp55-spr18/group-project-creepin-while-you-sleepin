import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class PlayerShip extends Ship {
	public PlayerShip(MainApplication game) {
		setGame(game);
		setInvincible(false);
		setIframe(100);
		setHealth(5);
		setCooldown(0);
		setMaxCooldown(10);
		setCanShoot(true);
		setGunLocation(new GPoint[] {new GPoint(50,17.5)});
		setSprite(new GImage("auto.png", 0, 0));
		setBulletColor(Color.BLUE);
		getSprite().setSize(50, 50);
		setDestroyed(false);
		setDestroyedCounter(0);
		getGame().add(getSprite());
		setTrail(new FireTrail(this));
	}
	@Override
	public void move() {		// Moves the player's ship hitbox to the location of the ship
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x+50,y+17.5)});
	}
	@Override
	public void shoot() {		// Returns the projectile type and iterates to the next gun location (or the same one if only one)
		if(canShoot() && getGame().isShooting) {
			setCanShoot(false);
			Projectile newProj = new Bullet(getGame(), true, getGunLocation()[0], 1, 0, 25, getBulletColor(), 15);
			getGame().add(newProj.getSprite());
		} else if (!canShoot()) {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}
	
	public void checkCollision() {
		for(Ship enemy : getGame().enemies) {
			if(getSprite().getBounds().intersects(enemy.getSprite().getBounds()) && !enemy.isDestroyed()) {
				onCollision();
			}
		}
	}

	public void onCollision() {
		if(!isInvincible()) {
			setHealth(getHealth() - 1);
			setInvincible(true);
		}
	}

	public void update() {
		if(!isDestroyed()) {
			shoot();
			checkCollision();
			// If the player is invincible, increment their invincibility timer
			if(isInvincible()) {
				if(getIframe() == 0) {
					getSprite().setImage("truck.png");
					getSprite().setSize(50, 50);
				}
				setIframe(getIframe() + 1);
			}
			// If the player's iframe count hits 100, make them vulnerable again
			if(getIframe() == 100) {
				getSprite().setImage("auto.png");
				getSprite().setSize(50, 50);
				setInvincible(false);
				setIframe(0);
			}
			if(getHealth() <= 0) {
				setDestroyed(true);
			}
		} else {
			getSprite().setImage("explosion.png");
			getSprite().setSize(50,50);
			setDestroyedCounter(getDestroyedCounter() + 1);
			if(getDestroyedCounter() == 50) {
				getGame().lose = true;
			}
		}
		if (getGame().lose || getGame().win) {
			getGame().remove(getSprite());
		}
	}
}
