package projectiles;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;
import ships.Boss;
import ships.PlayerShip;
import ships.Ship;

public class Bullet extends Projectile {
	public Bullet(Ship ship, GPoint gunLoc, double xD, double yD) {
		super(ship, gunLoc, xD, yD);
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
		setCollisionDamage(ship.getBulletDamage());
		getSprite().setLocation(gunLoc.getX() - getSprite().getWidth()/2, gunLoc.getY() - getSprite().getHeight()/2);
		getGame().projectiles.add(this);
		getGame().add(getSprite());
	}

	// The default function for move() moves the projectile in a straight line given an x and y direction and velocity
	public void move() {
		int dx = 1;
		if(getxDir() < 0) dx = -1;
		getSprite().move(Math.cos(Math.atan(getyDir()/getxDir()))*getSpeed()*dx, Math.sin(Math.atan(getyDir()/getxDir()))*getSpeed()*dx);
		if(getGame() != null && (getSprite().getX() < -getSprite().getWidth() || getSprite().getX() > getGame().WINDOW_WIDTH || getSprite().getY() < -getSprite().getHeight() || getSprite().getY() > getGame().WINDOW_HEIGHT)) {
			setDestroyed(true);
		}
		getSprite().sendToFront();
	}

	// The default function for onCollision checks to see if the projectile and the thing it collides with are enemies of eachother and makes sure it is not invincible
	// Then it subtracts 1 health from whatever it hits and removes the projectile and stops its timer
	public void onCollision(Ship target) {
		if((isPlayerProjectile() && !(target instanceof PlayerShip)) || (!isPlayerProjectile() && target instanceof PlayerShip)) {
			setDestroyed(true);
			target.dealDamage(getCollisionDamage());
		}
	}

	// This function is called when a bullet hits another bullet with collision on
	public void onCollision(Projectile missile) {
		if(isPlayerProjectile() && !missile.isPlayerProjectile()) {
			setDestroyed(true);
			missile.setDestroyed(true);
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
		if(getGame() != null) {
			GRectangle hitbox = getSprite().getBounds();
			hitbox.setSize(getSpeed(), hitbox.getHeight());
			for(int i = getGame().enemies.size() - 1;i >= 0;i--) {
				Ship enemy = getGame().enemies.get(i);
				if(enemy instanceof Boss && isPlayerProjectile() && !enemy.isDestroyed()) {
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
			if(isColliding(getSprite(), getGame().player.getSprite())) {
				onCollision(getGame().player);
				return;
			}
			for(Projectile proj : getGame().projectiles) {
				if(proj.isDestructable() && proj.getSprite().getBounds().intersects(hitbox)) {
					onCollision(proj);
				}
			}
		}
	}

	// This is a helper function that returns true if the circular sprite of the projectile collides with the rectangular sprite of the ship
	public boolean isColliding(GObject projectile, GObject ship) {
		double width = 4*ship.getWidth()/5.0;
		double height = 2*(ship.getHeight()/5.0);
		double xC = ship.getX() + ship.getWidth()/2;
		double yC = ship.getY() + ship.getHeight()/2;
		double circleDistanceX = Math.abs(projectile.getX() + projectile.getWidth()/2 - xC);
		double circleDistanceY = Math.abs(projectile.getY() + projectile.getHeight()/2 - yC);

		if (circleDistanceX > (width/2 + projectile.getWidth()/2)) { return false; }
		if (circleDistanceY > (height/2 + projectile.getWidth()/2)) { return false; }

		if (circleDistanceX <= (width/2)) { return true; }
		if (circleDistanceY <= (height/2)) { return true; }

		double cornerDistance_sq = Math.pow(circleDistanceX - width/2, 2) +
				Math.pow(circleDistanceY - height/2, 2);
		return (cornerDistance_sq <= Math.pow(projectile.getWidth()/2, 2));
	}

	public void setSize(double x, double y) {
		getSprite().setSize(getGame().WINDOW_WIDTH/(1920/x), getGame().WINDOW_HEIGHT/(1080/y));
	}

	public GOval getSprite() {
		return (GOval) sprite;
	}

	public void setSprite(GOval sprite) {
		this.sprite = sprite;
	}
}
