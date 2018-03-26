import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import acm.graphics.*;
import acm.program.*;

public class Ship {
	private MainApplication game;		// Reference to the pane the game runs on so that the ship is aware of other variables in the game
	private GPoint[] shipPoints;		// The points on the ship that will be checked for collision
	private GImage sprite;				// The image that will be displayed for the ship
	private GPoint[] gunLocation;		// The points that will be used to fire projectiles, if any
	private int selectedGun;			// Integer switch that selects which point in gunLocation array to fire from
	private GPoint location;			// The top-left most point of the ship (like a GRectangle's location)
	private Color bulletColor;			// The color of the projectiles fired by this ship
	private boolean canShoot;			// Trigger that toggles on/off on cooldown in the main loop
	private boolean invincible;			// Trigger that toggles on/off on cooldown in the main loop
	private int iframe;					// Number of frames that the ship will be invincible for if at all
	private int health;					// The number of hits the ship can take before being destroyed
	private int cooldown;				// The initial value of cooldown (Set to 0 if the ship can fire as soon as it spawns)
	private int maxCooldown;			// The number of frames between each call of the Shoot() function
	
	// These attributes only apply to enemy ships
	private int xDir;			// Since each move() is different for each ship, these do whatever you make them do
	private int yDir;
	private int speed;

	public void move() {
		System.out.println("Needs to be accessed by child class");
	}
	public void shoot() {
		System.out.println("Needs to be accessed by child class");
	}
	// Getters and setters, nothing important down here
	public GPoint[] getShipPoints() {
		return shipPoints;
	}
	public void setShipPoints(GPoint[] shipPoints) {
		this.shipPoints = shipPoints;
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
	public GPoint getLocation() {
		return location;
	}
	public void setLocation(GPoint location) {
		this.location = location;
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
	public int getxDir() {
		return xDir;
	}
	public void setxDir(int xDir) {
		this.xDir = xDir;
	}
	public int getyDir() {
		return yDir;
	}
	public void setyDir(int yDir) {
		this.yDir = yDir;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
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
}