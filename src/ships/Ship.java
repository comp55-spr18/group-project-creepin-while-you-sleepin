package ships;
import java.awt.*;

import acm.graphics.*;
import game.Game;
import misc.Object;
import projectiles.FireTrail;

public abstract class Ship extends Object {
	private GPoint[] gunLocation;		// The points that will be used to fire projectiles, if any
	private int selectedGun;			// Integer switch that selects which point in gunLocation array to fire from
	private Color bulletColor;			// The color of the projectiles fired by this ship
	private boolean canShoot;			// Trigger that toggles on/off on cooldown in the main loop
	private boolean invincible;			// Trigger that toggles on/off on cooldown in the main loop
	private int iframe;					// Number of frames that the ship will be invincible for if at all
	private int maxIframe;				// Maximum number of iframes the ship will go through before becoming vulnerable
	private int health;					// The number of hits the ship can take before being destroyed
	private int maxHealth;
	private int cooldown;				// The initial value of cooldown (Set to 0 if the ship can fire as soon as it spawns)
	private int maxCooldown;			// The number of frames between each call of the Shoot() function
	private int destroyedCounter;		// Counter for how long the death sprite lasts
	private int points;					// The points the ship is worth
	private boolean shielded = false;
	private GOval shield;
	private int shieldCooldown;
	private int shieldMaxCooldown;
	private GImage explosion;
	private FireTrail trail;
	private double bulletSize;
	private double bulletSpeed;
	private int bulletDamage;
	private double beamHeight;
	private int beamDuration;
	private int beamWarningDuration;
	private int beamDamage;
	private int shots;
	private boolean eventEnemy;
	
	Ship(Game game) {
		setGame(game);
		setEventEnemy(false);
		if(!(this instanceof PlayerShip)) {
			game.enemies.add(this);
		}
		setDestroyed(false);
		setInvincible(false);
		setIframe(0);				// Iframes for any ship but the player only effect beam damage detection
		setMaxIframe(20);
		setDestroyedCounter(0);
		setShielded(false);
		setCollisionDamage(1);
		setExplosion(new GImage("explosion.png"));
	}
	
	//vector move does the angle math for you.
	public void vectorMove () {
		int dx = 1;
		if(getxDir() < 0) dx = -1;
		getSprite().move(Math.cos(Math.atan(getyDir()/getxDir()))*getSpeed()*dx, Math.sin(Math.atan(getyDir()/getxDir()))*getSpeed()*dx);
	}

	// Use this if you want to aim at the player with your ship path.
	public void aimAtPlayer() {
		GObject shipSprite = getGame().player.getSprite();
		setxDir((shipSprite.getX()+shipSprite.getWidth()/2) - getSprite().getX() - getSprite().getWidth()/2);
		setyDir((shipSprite.getY()+shipSprite.getHeight()/2) - getSprite().getY() - getSprite().getHeight()/2);
	}

	public void move() {		// Declare a new move() in each new Ship subclass
//		System.out.println("Needs to be accessed by child class");
	}
	public void shoot() {		// Declare a new shoot() in each new Ship subclass
//		System.out.println("Needs to be accessed by child class");
	}
	
	public void update() {	// This is the default loop that a ship will use
		if(!isDestroyed()) {
			move();									// Move the ship
			shoot();								// Tell the ship to shoot
			checkCollision();
			if (getGame().lose || getGame().win) {	// If the game is over
				getGame().remove(getSprite());		// Remove the ship sprite
				setDestroyed(true);
			}
			if(isInvincible()) {
				setIframe(getIframe() + 1);
				// If the ship's iframe count hits 30, make them vulnerable again
				if(getIframe() == getMaxIframe()) {
					setInvincible(false);
					setIframe(0);
				}
			}
			if(isShielded()) {
				getShield().setLocation(sprite.getLocation());
				if(!getShield().isVisible()) {
					setShieldCooldown(getShieldCooldown() + 1);
					if(getShieldCooldown() == getShieldMaxCooldown()) {
						getGame().shieldRegenCount = getGame().playSound("shieldregen", getGame().shieldRegenCount);
						getShield().setVisible(true);
						setShieldCooldown(0);
					}
				}
			}
		}
		if(isDestroyed()) {									// If the ship is destroyed
			if(getDestroyedCounter() == 0) {
				explosion.setLocation(getSprite().getLocation());
				getGame().remove(getSprite());		// Remove the ship sprite
				game.add(explosion);
				if(explosion.getX() > 0 && explosion.getX() < getGame().WINDOW_WIDTH && !getGame().lose && !getGame().win && explosion.getY() <= getGame().WINDOW_HEIGHT) {
					getGame().shipDeathCount = getGame().playSound("shipdeath", getGame().shipDeathCount);
				}
			}
			setDestroyedCounter(getDestroyedCounter() + 1);		// Increment the destroyed counter
			if(getDestroyedCounter() == 50) {		// When the counter hits 50
				getGame().remove(explosion);
				explosion.setVisible(false);
			}
		}
	}

	public void checkCollision() {
		if(getSprite().getBounds().intersects(game.player.getSprite().getBounds())) {
			game.player.dealDamage(getCollisionDamage());
		}
	}

	// Getters and setters, nothing important down here
	public void setSize(double x, double y) {
		getSprite().setSize(getGame().WINDOW_WIDTH/(1920/x), getGame().WINDOW_HEIGHT/(1080/y));
		getExplosion().setSize(getSprite().getSize());
	}

	public GImage getSprite() {
		return (GImage) sprite;
	}

	public void setSprite(GImage sprite) {
		this.sprite = sprite;
	}

	public GPoint[] getGunLocation() {
		return gunLocation;
	}

	public void setGunLocation(GPoint[] gunLocation) {
		this.gunLocation = gunLocation;
	}

	public Color getBulletColor() {
		return bulletColor;
	}

	public void setBulletColor(Color bulletColor) {
		this.bulletColor = bulletColor;
	}

	public boolean canShoot() {
		return canShoot;
	}

	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public int getIframe() {
		return iframe;
	}

	public void setIframe(int iframe) {
		this.iframe = iframe;
	}

	public int getMaxIframe() {
		return maxIframe;
	}

	public void setMaxIframe(int maxIframe) {
		this.maxIframe = maxIframe;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if(this instanceof PlayerShip) {
			updateHealthBar(health - getHealth());
		}
		this.health = health;
	}

	public void dealDamage(int damage) {
		if(isShielded()) {
			if(!getShield().isVisible() && !isInvincible()) {
				calculateDamage(damage);
			} else {
				getGame().shieldHitCount = getGame().playSound("shieldhit", getGame().shieldHitCount);
				getShield().setVisible(false);
			}
		} else if(!isInvincible() || !(this instanceof PlayerShip)) {
			calculateDamage(damage);
		}
	}
	
	public void calculateDamage(int damage) {
		health -= damage;
		if(this instanceof PlayerShip) {					// If the player's health is being updated, the healthbar should reflect it
			setInvincible(true);
			updateHealthBar(-damage);
			getGame().playerHitCount = getGame().playSound("playerhit", getGame().playerHitCount);
		} else {
			getGame().enemyHitCount = getGame().playSound("enemyhit",  getGame().enemyHitCount);
		}
		if(getHealth() <= 0) {								// If the ship's health is below 0
			getGame().updateScoreBoard(points);				// Add points to the score board
			setDestroyed(true);								// Destroy the ship
		}
	}

	private void updateHealthBar(int healthChange) {
		int size = game.healthBar.size();
		if(healthChange < 0) {
			for(int i = 0;i < Math.abs(healthChange);i++) {
				if(size - 1 - i >= 0) {
					game.remove(game.healthBar.get(size - 1 - i));
					game.healthBar.remove(size - 1 - i);
				}
			}
		} else {
			for(int i = size;i < healthChange + size;i++) {
				if(i >= 0) {
					GImage toAdd = new GImage("heart.png", 10 + 25*i, 30);
					toAdd.setSize(20, 20);
					game.healthBar.add(toAdd);
					game.add(toAdd);
				}
			}
		}
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public int getMaxCooldown() {
		return maxCooldown;
	}

	public void setMaxCooldown(int maxCooldown) {
		this.maxCooldown = maxCooldown;
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

	public int getSelectedGun() {
		return selectedGun;
	}

	public void setSelectedGun(int selectedGun) {
		this.selectedGun = selectedGun;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public int getDestroyedCounter() {
		return destroyedCounter;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public void setDestroyedCounter(int destroyedCounter) {
		this.destroyedCounter = destroyedCounter;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public GImage getExplosion() {
		return explosion;
	}

	public void setExplosion(GImage explosion) {
		this.explosion = explosion;
	}

	public FireTrail getTrail() {
		return trail;
	}

	public void setTrail(FireTrail trail) {
		this.trail = trail;
	}

	public int getCollisionDamage() {
		return collisionDamage;
	}

	public void setCollisionDamage(int collisionDamage) {
		this.collisionDamage = collisionDamage;
	}

	public double getBulletSize() {
		return bulletSize;
	}

	public void setBulletSize(double bulletSize) {
		this.bulletSize = bulletSize;
	}

	public double getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(double bulletSpeed) {
		this.bulletSpeed = getGame().WINDOW_WIDTH/(1920/bulletSpeed);
	}

	public int getBulletDamage() {
		return bulletDamage;
	}

	public void setBulletDamage(int bulletDamage) {
		this.bulletDamage = bulletDamage;
	}
	
	public int getShots() {  
		return shots;
	}
	
	public void setShots(int shots) {
		this.shots = shots;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		setHealth(maxHealth);
	}

	public GOval getShield() {
		return shield;
	}

	public int getShieldCooldown() {
		return shieldCooldown;
	}

	public void setShieldCooldown(int shieldCooldown) {
		this.shieldCooldown = shieldCooldown;
	}

	public int getShieldMaxCooldown() {
		return shieldMaxCooldown;
	}

	public void setShieldMaxCooldown(int shieldMaxCooldown) {
		this.shieldMaxCooldown = shieldMaxCooldown;
	}

	public boolean isShielded() {
		return shielded;
	}

	public void setShielded(boolean shielded) {
		this.shielded = shielded;
		if(shielded) {
			shield = new GOval(getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());
			shield.setFilled(true);
			shield.setFillColor(Color.CYAN);
			game.add(shield);
		} else {
			shield = null;
		}
	}

	public double getBeamHeight() {
		return beamHeight;
	}

	public double getBeamDuration() {
		return beamDuration;
	}

	public void setBeamHeight(double beamHeight) {
		this.beamHeight = getGame().WINDOW_HEIGHT/(1080/beamHeight);
	}

	public void setBeamDuration(int beamDuration) {
		this.beamDuration = beamDuration;
	}

	public int getBeamDamage() {
		return beamDamage;
	}

	public void setBeamDamage(int beamDamage) {
		this.beamDamage = beamDamage;
	}

	public int getBeamWarningDuration() {
		return beamWarningDuration;
	}

	public void setBeamWarningDuration(int beamWarningDuration) {
		this.beamWarningDuration = beamWarningDuration;
	}

	public boolean isEventEnemy() {
		return eventEnemy;
	}

	public void setEventEnemy(boolean eventEnemy) {
		this.eventEnemy = eventEnemy;
	}
}