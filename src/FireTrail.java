import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GPoint;

public class FireTrail implements ActionListener {
	private Timer timer;					// The timer used by the fire trail
	private ArrayList<Projectile> trail;	// The ArrayList the projectiles are stored in
	private Ship ship;						// The ship the trail belongs to
	private int xDir;						// The x direction of the trail
	private int size;						// The initial size of the trail
	private GPoint location;				// The location the trail begins at
	private double length;					// The length of the trail
	private double speed;					// The speed of the projectiles in the trail
	private double xOffset;					// The offset of the trail relative to the ship
	
	FireTrail(Ship s) {
		ship = s;
		if(ship instanceof PlayerShip) {
			xDir = -1;
			xOffset = ship.getSprite().getWidth()/4;
			length = 1;
			speed = 2;
		} else {
			xDir = 1;
			length = 0.25;
			speed = 1;
			xOffset = ship.getSprite().getWidth()/3 - ship.getSprite().getWidth();
		}
		size = 25;
		trail = new ArrayList<Projectile>();
		timer = new Timer(5, this);
		timer.start();
	}
	// This function moves all the projectiles in the arraylist as well as updates the color and size each time
	public void move() {
		if(!ship.isDestroyed()) {	// If the ship is not destroyed
			location = new GPoint(ship.getLocation().getX() - xOffset, ship.getLocation().getY()+ship.getSprite().getHeight()/2-size/2);	// Set the location relative to the ship
			Projectile trailProj = new Emitter(ship.getGame(), true, location, xDir, 0, speed, Color.RED, size);	// Create an emitter with the proper values
			trail.add(trailProj);							// Add the new emitter to the arraylist
			ship.getGame().add(trailProj.getSprite());		// Add the emitter's sprite to the game
		}
		for(Projectile tr : trail) {	// For each projectile in the arraylist
			tr.move();					// Move it
			tr.getSprite().setSize(tr.getSprite().getWidth()-(0.5/length), tr.getSprite().getWidth()-(0.5/length));		// Set the size to be smaller
			tr.getSprite().setLocation(tr.getSprite().getX(), tr.getSprite().getY()+(0.25/length));						// Set the position to be a little lower so it's centered
			if(tr.getSprite().getColor().getGreen()+(10/length) <= 255) {												// If more green can be added to the sprite
				tr.getSprite().setColor(new Color(tr.getSprite().getColor().getRed(), (int) (tr.getSprite().getColor().getGreen() + (10/length)), tr.getSprite().getColor().getBlue()));
				tr.getSprite().setFillColor(tr.getSprite().getColor()); // Add green to the sprite (turning it from red to yellow gradually)
			}
		}
		for(Projectile tr : trail) {						// For each projectile in the arraylist
			if(tr.getSprite().getWidth() <= 3) {			// If the sprite's size is small enough
				ship.getGame().remove(tr.getSprite());		// Remove the sprite
				tr.getTimer().stop();
				trail.remove(tr);							// Remove the projectile from the arraylist
				break;
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		if(trail.isEmpty() || ship.getGame().win) {
			timer.stop();
		}
	}
	// Getters and setters
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public ArrayList<Projectile> getTrail() {
		return trail;
	}
	public void setTrail(ArrayList<Projectile> trail) {
		this.trail = trail;
	}
	public Ship getShip() {
		return ship;
	}
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	public int getxDir() {
		return xDir;
	}
	public void setxDir(int xDir) {
		this.xDir = xDir;
	}
}
