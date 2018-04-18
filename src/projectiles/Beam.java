package projectiles;
import java.awt.Color;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import ships.PlayerShip;
import ships.Ship;

public class Beam extends Projectile {
	private double rate;		// The rate at which the rectangle gains height
	private double rateChange;	// The rate of change of rate
	private double duration;	// The duration the beam freezes at maxHeight
	private double counter;		// The counter that counts up to the duration
	private double maxHeight;	// The maximum height of the beam
	private GPoint location;
	private int warningDuration;
	public Beam(Ship ship, GPoint gunLoc) {
		super(ship, gunLoc, 0, 0);
		warningDuration = ship.getBeamWarningDuration();
		setCollisionDamage(ship.getBeamDamage());
		location = gunLoc;
		maxHeight = ship.getBeamHeight();
		counter = 0;
		duration = ship.getBeamDuration();
		rateChange = maxHeight/120;
		rate = 15*rateChange;
		if(ship instanceof PlayerShip) {
			setSprite(new GRect(gunLoc.getX(), gunLoc.getY(), 2000, 1));
		} else {
			setSprite(new GRect(gunLoc.getX() - 2000, gunLoc.getY(), 2000, 1));
		}
		getGame().projectiles.add(this);
		getGame().add(sprite);
		getBeamSprite().setColor(Color.CYAN);
		getBeamSprite().setFilled(true);
		getBeamSprite().setFillColor(sprite.getColor());
	}
	
	public void move() {
		if(getShip().isDestroyed() || getGame().getLevel().isFinished()) {
			setDestroyed(true);
			getGame().remove(sprite);
		}
		if(warningDuration < 0) {
			if(sprite.getHeight() + rate > 0) {
				if(counter >= duration || rate > 0) {
					getBeamSprite().setSize(sprite.getWidth(), sprite.getHeight() + rate);
					rate -= rateChange;
				} else {
					counter++;
				}
			} else {
				setDestroyed(true);
				getGame().remove(sprite);
			}
		} else {
			warningDuration--;
		}
		if(getShip() instanceof PlayerShip) {
			sprite.setLocation(location.getX(), location.getY() - sprite.getHeight()/2);
		} else {
			sprite.setLocation(location.getX() - 2000, location.getY() - sprite.getHeight()/2);
		}
	}
	
	public void onCollision(Ship target) {
		if((isPlayerProjectile() && !(target instanceof PlayerShip)) || (!isPlayerProjectile() && target instanceof PlayerShip)) {
			if(!target.isInvincible()) {
				target.dealDamage(getCollisionDamage());
				target.setInvincible(true);
			}
		}
	}
	
	public void onCollision(Projectile missile) {
		if(isPlayerProjectile() && !missile.isPlayerProjectile()) {
			missile.setDestroyed(true);
			getGame().remove(missile.getSprite());
			getGame().enemyHitCount = getGame().playSound("enemyhit", getGame().enemyHitCount);
		}
	}

	public void checkCollision() {
		if(getGame() != null && warningDuration < 0) {
			GRectangle hitbox = sprite.getBounds();
			for(int i = getGame().enemies.size() - 1;i >= 0;i--) {
				Ship enemy = getGame().enemies.get(i);
				if(enemy.getSprite().getBounds().intersects(hitbox) && !enemy.isDestroyed()) {
					onCollision(enemy);
				}
			}
			GRectangle playerHitbox = getGame().player.getSprite().getBounds();
			playerHitbox.setSize(playerHitbox.getWidth(), (4.0/5.0)*playerHitbox.getHeight());
			playerHitbox.setLocation(playerHitbox.getX(), getGame().player.getSprite().getY() + (0.5/5.0)*getGame().player.getSprite().getHeight());
			if(playerHitbox.intersects(hitbox)) {
				onCollision(getGame().player);
			}
			for(Projectile proj : getGame().projectiles) {
				if(proj.isDestructable() && proj.getSprite().getBounds().intersects(hitbox)) {
					onCollision(proj);
				}
			}
		}
	}

	// Getters
	public GRect getBeamSprite() {
		return (GRect) sprite;
	}

	public void setSprite(GRect sprite) {
		this.sprite = sprite;
	}
}
