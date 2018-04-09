import java.awt.*;
import acm.graphics.*;

// This is an abstract class so all functions declared in this class can be overwritten in a subclass if you'd like
// If you want a new projectile to behave like this one but interact differently when it hits something, just
// Make a constructor for the new class and define a new onCollision() since move() and checkCollision() will be inherited

public abstract class Projectile {
	private Ship ship;
	private MainApplication game;
	private boolean isPlayerProjectile;			// Check if the projectile belongs to player
	private double xDir;						// Using the x and y Dir variables creates a vector with speed as its magnitude, indicating the movement of the projectile
	private double yDir;
	private double speed;
	private GOval sprite;						// The sprite that will be used for the projectile, currently just a circle for simplicity
	private boolean isDestroyed;
	private boolean isDestructable;				// Check if this projectile can be destroyed by the player's projectile
	private int damage;

	public Projectile(Ship ship, GPoint gunLoc, double xD, double yD) {
		setShip(ship);
		setGame(ship.getGame());
		setDestroyed(false);
		setDestructable(false);
		setPlayerProjectile(ship instanceof PlayerShip);
		setSprite(new GOval(15,15));
		getSprite().setFillColor(ship.getBulletColor());
		getSprite().setColor(ship.getBulletColor());
		getSprite().setFilled(true);
		setxDir(xD);
		setyDir(yD);
		setSize(ship.getBulletSize(), ship.getBulletSize());
		setSpeed(ship.getBulletSpeed());
		setDamage(ship.getBulletDamage());
		getSprite().setLocation(gunLoc.getX(), gunLoc.getY() - getSprite().getHeight()/2);
		getGame().projectiles.add(this);
		getGame().add(getSprite());
	}
	

	// The default function for move() moves the projectile in a straight line given an x and y direction and velocity
	public void move() {
		int dx = 1;
		if(getxDir() < 0) dx = -1;
		getSprite().move(Math.cos(Math.atan(getyDir()/getxDir()))*getSpeed()*dx, Math.sin(Math.atan(getyDir()/getxDir()))*getSpeed()*dx);
		if(getGame() != null && (getSprite().getX() < -50 || getSprite().getX() > getGame().WINDOW_WIDTH || getSprite().getY() < -50 || getSprite().getY() > getGame().WINDOW_HEIGHT)) {
			setDestroyed(true);
			getGame().remove(getSprite());
		}
	}
	
	// The default function for onCollision checks to see if the projectile and the thing it collides with are enemies of eachother and makes sure it is not invincible
	// Then it subtracts 1 health from whatever it hits and removes the projectile and stops its timer
	public void onCollision(Ship target) {
		if((isPlayerProjectile() && !(target instanceof PlayerShip)) || (!isPlayerProjectile() && target instanceof PlayerShip)) {
			setDestroyed(true);
			getGame().remove(getSprite());
			if(!target.isInvincible()) {
				target.dealDamage(getDamage());
			}
		}
	}
	
	// This function is called when a bullet hits another bullet with collision on
	public void onCollision(Projectile missile) {
		if(isPlayerProjectile() && !missile.isPlayerProjectile()) {
			setDestroyed(true);
			missile.setDestroyed(true);
			getGame().remove(getSprite());
			getGame().remove(missile.getSprite());
			getGame().enemyHitCount = getGame().playSound("enemyhit", getGame().enemyHitCount);
		}
	}
	
	// This can be called by any projectile subclass, it just tells the projectile to set it's move vector towards the player
	public void aimAtPlayer() {
		GObject shipSprite = getGame().player.getSprite();
		setxDir((shipSprite.getX()+shipSprite.getWidth()/2) - getSprite().getX() - getSprite().getWidth()/2);
		setyDir((shipSprite.getY()+shipSprite.getHeight()/2) - getSprite().getY() - getSprite().getHeight()/2);
	}
	
	// The default checkCollision creates three GPoints at the top, center, and bottom of the projectile
	// If these points collide with an enemy or player, onCollision() is called
	public void checkCollision() {
		if(game != null) {
			GRectangle hitbox = getSprite().getBounds();
			hitbox.setSize(getSpeed(), hitbox.getHeight());
			for(Ship enemy : game.enemies) {
				if(enemy instanceof Boss && isPlayerProjectile()) {
					GRectangle enemyHitbox = enemy.getSprite().getBounds();
					enemyHitbox.setSize(2*(enemy.getSprite().getWidth()/3), enemy.getSprite().getHeight());
					enemyHitbox.setLocation(enemy.getSprite().getX() + enemy.getSprite().getWidth()/3, enemy.getSprite().getY());
					if(enemyHitbox.intersects(hitbox)) {
						onCollision(enemy);
						return;
					}
				} else if(enemy.getSprite().getBounds().intersects(hitbox) && !enemy.isDestroyed()) {
					onCollision(enemy);
					return;
				}
			}
			if(isColliding(getSprite(), game.player.getSprite())) {
				onCollision(game.player);
				return;
			}
			for(Projectile proj : game.projectiles) {
				if(proj.isDestructable() && proj.getSprite().getBounds().intersects(hitbox)) {
					onCollision(proj);
				}
			}
		}
	}
	
	// This is a helper function that returns true if the circular sprite of the projectile collides with the rectangular sprite of the ship
	public boolean isColliding(GObject projectile, GObject ship) {
		double circleDistanceX = Math.abs(projectile.getX() + projectile.getWidth()/2 - ship.getX());
		double circleDistanceY = Math.abs(projectile.getY() + projectile.getHeight()/2 - ship.getY());

		if (circleDistanceX > (ship.getWidth()/2 + projectile.getWidth()/2)) { return false; }
		if (circleDistanceY > (ship.getHeight()/2 + projectile.getWidth()/2)) { return false; }

		if (circleDistanceX <= (ship.getWidth()/2)) { return true; } 
		if (circleDistanceY <= (ship.getHeight()/2)) { return true; }

		double cornerDistance_sq = Math.pow(circleDistanceX - ship.getWidth()/2, 2) +
				Math.pow(circleDistanceY - ship.getHeight()/2, 2);
		return (cornerDistance_sq <= Math.pow(projectile.getWidth()/2, 2));
	}

	// The default timer loop calls move() and checkCollision() on each pass and removes the projectile and stops its timer if the game is over
	public void update() {
		move();
		checkCollision();
		if(getGame().lose || getGame().win) {
			getGame().remove(getSprite());
			setDestroyed(true);
		}
	}
	
	// Getters and setters
	public void setSize(double x, double y) {
		getSprite().setSize(getGame().WINDOW_WIDTH/(1920/x), getGame().WINDOW_HEIGHT/(1080/y));
	}
	public boolean isPlayerProjectile() {
		return isPlayerProjectile;
	}
	public void setPlayerProjectile(boolean isPlayerProjectile) {
		this.isPlayerProjectile = isPlayerProjectile;
	}
	public double getxDir() {
		return xDir;
	}
	public void setxDir(double xDir) {
		this.xDir = xDir;
	}
	public double getyDir() {
		return yDir;
	}
	public void setyDir(double yDir) {
		this.yDir = yDir;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = getGame().WINDOW_WIDTH/(1920/speed);
	}
	public GOval getSprite() {
		return sprite;
	}
	public void setSprite(GOval sprite) {
		this.sprite = sprite;
	}
	public MainApplication getGame() {
		return game;
	}
	public void setGame(MainApplication game) {
		this.game = game;
	}
	public boolean isDestroyed() {
		return isDestroyed;
	}
	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	public boolean isDestructable() {
		return isDestructable;
	}
	public void setDestructable(boolean isDestructable) {
		this.isDestructable = isDestructable;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}


	public Ship getShip() {
		return ship;
	}


	public void setShip(Ship ship) {
		this.ship = ship;
	}
}