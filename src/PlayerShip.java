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
		setInvincible(true);
		setIframe(0);
		setHealth(5);
		setCooldown(0);
		setMaxCooldown(20);
		setCanShoot(false);
		setSprite(new GImage("sprites/playermodel.png", 0, 0));
		setBulletColor(Color.GREEN);
		setSize(50, 50);
		setDestroyed(false);
		setDestroyedCounter(0);
		getGame().add(getSprite());
		setTrail(new FireTrail(this));
	}
	@Override
	public void move() {		// Moves the player's ship hitbox to the location of the ship
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x + getSprite().getWidth(), y + getSprite().getHeight()/2)});
	}
	@Override
	public void shoot() {		// Returns the projectile type and iterates to the next gun location (or the same one if only one)
		if(canShoot() && getGame().isShooting) {
			setCanShoot(false);
			Projectile newProj = new Bullet(getGame(), true, getGunLocation()[0], 1, 0, 25, getBulletColor(), 15);
			getGame().add(newProj.getSprite());
			getGame().playerShootCount = getGame().playSound("playershoot", getGame().playerShootCount);
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
				onCollision(enemy);
			}
		}
	}

	public void onCollision(Ship enemy) {
		if(!isInvincible()) {
			setHealth(getHealth() - enemy.getCollisionDamage());
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
					setSize(50, 50);
				}
				setIframe(getIframe() + 1);
			}
			// If the player's iframe count hits 100, make them vulnerable again
			if(getIframe() == 50) {
				getSprite().setImage("sprites/playermodel.png");
				setSize(50, 50);
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
