package projectiles;
import acm.graphics.*;
import misc.Object;
import ships.*;

// This is an abstract class so all functions declared in this class can be overwritten in a subclass if you'd like
// If you want a new projectile to behave like this one but interact differently when it hits something, just
// Make a constructor for the new class and define a new onCollision() since move() and checkCollision() will be inherited

public abstract class Projectile extends Object {
	private Ship ship;
	private boolean isPlayerProjectile;			// Check if the projectile belongs to player
	private boolean isDestructable;				// Check if this projectile can be destroyed by the player's projectile

	// Constructor that every projectile should use
	public Projectile(Ship ship, GPoint gunLoc, double xD, double yD) {
		setShip(ship);
		setGame(ship.getGame());
		setDestroyed(false);
		setDestructable(false);
		setPlayerProjectile(ship instanceof PlayerShip);
		setxDir(xD);
		setyDir(yD);
	}
	public void update() {
		move();
		checkCollision();
		if(this instanceof Beam && ship.isDestroyed()) {
			setDestroyed(true);
		}
		if(isDestroyed()) {
			getGame().remove(sprite);
			getGame().projectiles.remove(this);
		}
	}
	// Getters and setters
	public boolean isPlayerProjectile() {
		return isPlayerProjectile;
	}
	public void setPlayerProjectile(boolean isPlayerProjectile) {
		this.isPlayerProjectile = isPlayerProjectile;
	}
	public boolean isDestructable() {
		return isDestructable;
	}
	public void setDestructable(boolean isDestructable) {
		this.isDestructable = isDestructable;
	}
	public Ship getShip() {
		return ship;
	}
	public void setShip(Ship ship) {
		this.ship = ship;
	}
}