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
	private int projCount;
	private int colorScale;
	private double shrinkScale;
	
	FireTrail(Ship s) {
		ship = s;
		size = (int) ship.getSprite().getWidth()/2;
		shrinkScale = ship.getSprite().getWidth()/100.0;
		if(ship instanceof PlayerShip) {
			xDir = -1;
			xOffset = 0;
			length = 0.5;
			speed = 2;
		} else {
			xDir = 1;
			length = 0.35*(size/50.0);
			speed = 1;
			xOffset = size;
		}
		projCount = (int) (((size/shrinkScale)*length) - 3/(shrinkScale/length));
		colorScale = (int) ((int) 255/(projCount/2.0));
		trail = new ArrayList<Projectile>();
		timer = new Timer(5, this);
		timer.start();
	}
	// This function moves all the projectiles in the arraylist as well as updates the color and size each time
	public void move() {
		if(!ship.isDestroyed()) {	// If the ship is not destroyed
			location = new GPoint(ship.getSprite().getX() + xOffset, ship.getSprite().getY()+ship.getSprite().getHeight()/2);	// Set the location relative to the ship
			Projectile trailProj = new Emitter(ship.getGame(), true, location, xDir, 0, speed, Color.RED, size);	// Create an emitter with the proper values
			trail.add(trailProj);							// Add the new emitter to the arraylist
			ship.getGame().add(trailProj.getSprite());		// Add the emitter's sprite to the game
		}
		for(Projectile tr : trail) {	// For each projectile in the arraylist
			tr.move();					// Move it
			tr.getSprite().setSize(tr.getSprite().getWidth()-(shrinkScale/length), tr.getSprite().getWidth()-(shrinkScale/length));		// Set the size to be smaller
			tr.getSprite().setLocation(tr.getSprite().getX(), tr.getSprite().getY()+((shrinkScale/2)/length));						// Set the position to be a little lower so it's centered
			if(tr.getSprite().getColor().getGreen() + colorScale <= 255) {												// If more green can be added to the sprite
				tr.getSprite().setColor(new Color(tr.getSprite().getColor().getRed(), (int) (tr.getSprite().getColor().getGreen() + colorScale), tr.getSprite().getColor().getBlue()));
				tr.getSprite().setFillColor(tr.getSprite().getColor()); // Add green to the sprite (turning it from red to yellow gradually)
			}
		}
		for(Projectile tr : trail) {						// For each projectile in the arraylist
			if(tr.getSprite().getWidth() <= 6*shrinkScale) {			// If the sprite's size is small enough
				ship.getGame().remove(tr.getSprite());		// Remove the sprite
				tr.setDestroyed(true);
				trail.remove(tr);							// Remove the projectile from the arraylist
				break;
			}
		}
		ship.getSprite().sendToFront();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		if(trail.isEmpty() || ship.getGame().win) {
			for(Projectile proj : trail) {
				ship.getGame().remove(proj.getSprite());
			}
			trail.clear();
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
