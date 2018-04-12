import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class PlayerShip extends Ship {
	private boolean shooting = false;
	public PlayerShip(MainApplication game) {
		super(game);
		setIframe(0);
		setMaxIframe(50);
		setShots(1);
		setMaxHealth(5);
		setCooldown(0);
		setMaxCooldown(20);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint()});
		setSprite(new GImage("sprites/playermodel.png", 0, 0));
		setBulletColor(Color.GREEN);
		setSize(50, 50);
		setExplosion(new GImage("explosion.png"));
		setBulletSize(15);
		setBulletSpeed(25);
		setBulletDamage(1);
		setBeamHeight(40);
		setBeamDur(30);
		setBeamDamage(1);
		setPoints(0);
		getGame().add(getSprite());
		setTrail(new FireTrail(this));
	}
	public void move(MouseEvent e) {		// Moves the player's gun location to the location of the ship
		if(!isDestroyed() && getGame().playerControl) {
			getSprite().setLocation(new GPoint(e.getX() - getSprite().getWidth()/2, e.getY() - getSprite().getHeight()/2));
			double x = getSprite().getX();
			double y = getSprite().getY();
			getGunLocation()[0].setLocation(x + getSprite().getWidth(), y + getSprite().getHeight()/2);
			if(isShielded()) {
				getShield().setLocation(x, y);
			}
		}
	}
	@Override
	public void move() {
		getSprite().move(10, 0);
		if(isShielded()) {
			getShield().move(10, 0);
		}
	}
	@Override
	public void shoot() {		// Returns the projectile type and iterates to the next gun location (or the same one if only one)
		if(canShoot() && isShooting()) {
			setCanShoot(false);
			getGame().playerShootCount = getGame().playSound("playershoot", getGame().playerShootCount);
			switch(getShots()) {
			case 2:
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() + getBulletSize()), 1, 0);
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() - getBulletSize()), 1, 0);
				break;
			case 3:
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() + getBulletSize()), 1, 0.2);
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() - getBulletSize()), 1, -0.2);
				new Bullet(this, getGunLocation()[0], 1, 0);
				break;
			case 4:
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() + getBulletSize()), 1, 0.2);
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() - getBulletSize()), 1, -0.2);
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() + getBulletSize()), 1, 0);
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() - getBulletSize()), 1, 0);
				break;
			case 5:
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() + getBulletSize()/2), 1, 0.1);
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() - getBulletSize()/2), 1, -0.1);
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() + getBulletSize()), 1, 0.2);
				new Bullet(this, new GPoint(getGunLocation()[0].getX(), getGunLocation()[0].getY() - getBulletSize()), 1, -0.2);
			default:
				new Beam(this, getGunLocation()[0]);
			}
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
			dealDamage(enemy.getCollisionDamage());
			if(enemy instanceof Kamikazi) {
				enemy.setDestroyed(true);
				getGame().remove(enemy.getSprite());
			}
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
				// If the player's iframe count hits 100, make them vulnerable again
				if(getIframe() == getMaxIframe()) {
					getSprite().setImage("sprites/playermodel.png");
					setSize(50, 50);
					setInvincible(false);
					setIframe(0);
				}
			}
			if(isShielded() && !getShield().isVisible()) {
				setShieldCooldown(getShieldCooldown() + 1);
				if(getShieldCooldown() == getShieldMaxCooldown()) {
					getGame().shieldRegenCount = getGame().playSound("shieldregen", getGame().shieldRegenCount);
					getShield().setVisible(true);
					setShieldCooldown(0);
				}
			}
		} else {
			if(getDestroyedCounter() == 0) {
				getExplosion().setLocation(getSprite().getLocation());
				getGame().remove(getSprite());		// Remove the ship sprite
				getGame().add(getExplosion());
				if(getExplosion().getX() > 0 && getExplosion().getX() < getGame().WINDOW_WIDTH) {
					getGame().shipDeathCount = getGame().playSound("shipdeath", getGame().shipDeathCount);
				}
			}
			setDestroyedCounter(getDestroyedCounter() + 1);
			if(getDestroyedCounter() == 50) {
				getGame().remove(getExplosion());
				getExplosion().setVisible(false);
				getGame().lose = true;
			}
		}
	}
	// Getters and setters
	public boolean isShooting() {
		return shooting;
	}
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
}