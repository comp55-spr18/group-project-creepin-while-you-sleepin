import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import acm.graphics.*;
import acm.program.*;

public class Ship {
	public GPoint[] shipPoints;		// The points on the ship that will be checked for collision
	public GImage sprite;			// The image that will be displayed for the ship
	public GPoint[] gunLocation;	// The points that will be used to fire projectiles, if any 
	public GPoint location;			// The top-left most point of the ship (like a GRectangle's location)
	public Color bulletColor;		// The color of the projectiles fired by this ship
	boolean canShoot;				// Trigger that toggles on/off on cooldown in the main loop
	boolean invincible;				// Trigger that toggles on/off on cooldown in the main loop
	int iframe;						// Number of frames that the ship will be invincible for if at all
	int health;						// The number of hits the ship can take before being destroyed
	int cooldown;					// The initial value of cooldown (Set to 0 if the ship can fire as soon as it spawns)
	int maxCooldown;				// The number of frames between each call of the Shoot() function
	
	// These attributes only apply to enemy ships
	int xDir;						// The number of pixels the ship will move on the x-axis per frame
	int yDir;						// The number of pixels the ship will move on the y-axis per frame
	int speed;						// Multiplicative speed modifier to x/yDir

	public void move() {
		System.out.println("Needs to be accessed by child class");
	}
	public Projectile shoot() {
		System.out.println("Needs to be accessed by child class");
		return null;
	}
}