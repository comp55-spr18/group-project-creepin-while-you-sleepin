import java.awt.*;
import acm.graphics.*;

public abstract class Ship {
	private MainApplication game;		// Reference to the pane the game runs on so that the ship is aware of other variables in the game
	private GImage sprite;				// The image that will be displayed for the ship
	private GPoint[] gunLocation;		// The points that will be used to fire projectiles, if any
	private int selectedGun;			// Integer switch that selects which point in gunLocation array to fire from
	private Color bulletColor;			// The color of the projectiles fired by this ship
	private boolean canShoot;			// Trigger that toggles on/off on cooldown in the main loop
	private boolean invincible;			// Trigger that toggles on/off on cooldown in the main loop
	private int iframe;					// Number of frames that the ship will be invincible for if at all
	private int health;					// The number of hits the ship can take before being destroyed
	private int cooldown;				// The initial value of cooldown (Set to 0 if the ship can fire as soon as it spawns)
	private int maxCooldown;			// The number of frames between each call of the Shoot() function
	private boolean isDestroyed;		// Toggles the destruction sequence of the ship
	private int destroyedCounter;		// Counter for how long the death sprite lasts
	private int points;					// The points the ship is worth
	private GImage explosion;
	private FireTrail trail;
	private int collisionDamage;
	private double bulletSize;
	private double bulletSpeed;
	private int bulletDamage;
	private int shots;
	
	// These attributes only apply to enemy ships
	private double xDir;			// Since each move() is different for each ship, these do whatever you make them do
	private double yDir;
	private int speed;
	
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
			if(getHealth() <= 0) {					// If the ship's health is below 0
				getGame().updateScoreBoard(points);	// Add points to the score board
				setDestroyed(true);					// Destroy the ship
			}
			if (getGame().lose || getGame().win) {	// If the game is over
				getGame().remove(getSprite());		// Remove the ship sprite
				setDestroyed(true);
			}
		}
		if(isDestroyed()) {									// If the ship is destroyed
			if(getDestroyedCounter() == 0) {
				explosion.setLocation(getSprite().getLocation());
				getGame().remove(getSprite());		// Remove the ship sprite
				game.add(explosion);
				if(explosion.getX() > 0 && explosion.getX() < getGame().WINDOW_WIDTH && !getGame().lose && !getGame().win) {
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

	// Getters and setters, nothing important down here
	public void setSize(double x, double y) {
		getSprite().setSize(getGame().WINDOW_WIDTH/(1920/x), getGame().WINDOW_HEIGHT/(1080/y));
	}

	public GImage getSprite() {
		return sprite;
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		if(this instanceof PlayerShip) {
			updateHealthBar();
		}
	}

	public void dealDamage(int damage) {
		health -= damage;
		if(this instanceof PlayerShip) {					// If the player's health is being updated, the healthbar should reflect it
			setInvincible(true);
			updateHealthBar();
			getGame().playerHitCount = getGame().playSound("playerhit", getGame().playerHitCount);
		} else {
			getGame().enemyHitCount = getGame().playSound("enemyhit",  getGame().enemyHitCount);
		}
	}

	private void updateHealthBar() {
		for(int i = 0;i < game.healthBar.size();i++) {	// Remove the current hearts from the screen
			game.remove(game.healthBar.get(i));
		}
		game.healthBar.clear();							// Clear the arraylist containing the heart images
		for(int i = 0;i < health;i++) {					// For each point of health the player's ship has
			GImage toAdd = new GImage("heart.png", 10 + 25*i, 30);	// Add a heart to the arraylist
			toAdd.setSize(20, 20);						// Set the size
			game.healthBar.add(toAdd);					// Add the heart to the arraylist
			game.add(toAdd);							// Add the heart to the screen
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = getGame().WINDOW_WIDTH/(1920/speed);
	}

	public int getSelectedGun() {
		return selectedGun;
	}

	public void setSelectedGun(int selectedGun) {
		this.selectedGun = selectedGun;
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
		this.explosion.setSize(sprite.getSize());		// Set the size of the explosion sprite to the size of the ship's sprite
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
		this.bulletSpeed = bulletSpeed;
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
}