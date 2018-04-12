import java.awt.Color;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class Beam extends Projectile {
	private GRect sprite;		// The rectangle that is drawn
	private double rate;		// The rate at which the rectangle gains height
	private double rateChange;	// The rate of change of rate
	private double duration;	// The duration the beam freezes at maxHeight
	private double counter;		// The counter that counts up to the duration
	private double maxHeight;	// The maximum height of the beam
	private GPoint location;
	Beam(Ship ship, GPoint gunLoc) {
		super(ship, gunLoc, 0, 0);
		location = gunLoc;
		maxHeight = ship.getBeamHeight();
		counter = 0;
		duration = ship.getBeamDur();
		rateChange = maxHeight/120;
		rate = 15*rateChange;
		ship.getGame().remove(getSprite());
		if(ship instanceof PlayerShip) {
			sprite = new GRect(gunLoc.getX(), gunLoc.getY(), 2000, 1);
		} else {
			sprite = new GRect(gunLoc.getX() - 2000, gunLoc.getY(), 2000, 1);
		}
		ship.getGame().add(sprite);
		sprite.setColor(Color.RED);
		sprite.setFilled(true);
		sprite.setFillColor(sprite.getColor());
	}
	
	public void move() {
		if(sprite.getHeight() + rate > 0) {
			if(counter >= duration || rate > 0) {
				sprite.setSize(sprite.getWidth(), sprite.getHeight() + rate);
				rate -= rateChange;
			} else {
				counter++;
			}
		} else {
			setDestroyed(true);
			getShip().getGame().remove(sprite);
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
				target.setInvincible(true);
				target.dealDamage(getDamage());
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
		if(getGame() != null) {
			GRectangle hitbox = sprite.getBounds();
			for(int i = getGame().enemies.size() - 1;i >= 0;i--) {
				Ship enemy = getGame().enemies.get(i);
				if(enemy instanceof Boss && isPlayerProjectile() && !enemy.isDestroyed()) {
					GRectangle enemyHitbox = enemy.getSprite().getBounds();
					enemyHitbox.setSize(2*(enemy.getSprite().getWidth()/3), enemy.getSprite().getHeight());
					enemyHitbox.setLocation(enemy.getSprite().getX() + enemy.getSprite().getWidth()/3, enemy.getSprite().getY());
					if(enemyHitbox.intersects(hitbox)) {
						onCollision(enemy);
					}
				} else if(enemy.getSprite().getBounds().intersects(hitbox) && !enemy.isDestroyed()) {
					onCollision(enemy);
				}
			}
			if(getGame().player.getSprite().getBounds().intersects(hitbox)) {
				onCollision(getGame().player);
			}
			for(Projectile proj : getGame().projectiles) {
				if(proj.isDestructable() && proj.getSprite().getBounds().intersects(hitbox)) {
					onCollision(proj);
				}
			}
		}
	}
}
