package projectiles;
import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.GPoint;
import ships.Asteroid;
import ships.PlayerShip;
import ships.Ship;

public class FireTrail {
	private ArrayList<Emitter> trail;	// The ArrayList the projectiles are stored in
	private Ship ship;						// The ship the trail belongs to
	private double xDir;						// The x direction of the trail
	private double yDir;
	private int size;						// The initial size of the trail
	private GPoint location;				// The location the trail begins at
	private double length;					// The length of the trail
	private double speed;					// The speed of the projectiles in the trail
	private double xOffset;					// The offset of the trail relative to the ship
	private int projCount;
	private int colorScale;
	private double shrinkScale;
	
	public FireTrail(Ship s) {
		ship = s;
		size = (int) (ship.getSprite().getWidth()/2);
		shrinkScale = ship.getSprite().getWidth()/100.0;
		yDir = 0;
		if(ship instanceof PlayerShip) {
			xDir = -1;
			xOffset = -size/2;
			length = 0.25;
			speed = 2;
		} else if(ship instanceof Asteroid) {
			xDir = 1;
			yDir = -1;
			xOffset = 0;
			length = 0.115;
			speed = 1;
			size = (int) (ship.getSprite().getWidth());
		} else {
			xDir = 1;
			length = 0.22;
			speed = 1;
			xOffset = size/2;
		}
		projCount = 10;
		colorScale = 25;
		trail = new ArrayList<Emitter>();
	}
	// This function moves all the projectiles in the arraylist as well as updates the color and size each time
	public void move() {
		if(!ship.isDestroyed()) {							// If the ship is not destroyed
			location = new GPoint(ship.getSprite().getX() + ship.getSprite().getWidth()/2 + xOffset, ship.getSprite().getY()+ship.getSprite().getHeight()/2);	// Set the location relative to the ship
			Emitter trailProj = new Emitter(ship, location, xDir, yDir, speed, Color.RED, size);	// Create an emitter with the proper values
			trail.add(trailProj);							// Add the new emitter to the arraylist
			ship.getGame().add(trailProj.getSprite());		// Add the emitter's sprite to the game
		}
		for(Emitter tr : trail) {						// For each projectile in the arraylist
			tr.move();										// Move it
			tr.getSprite().setSize(tr.getSprite().getWidth()-(shrinkScale/length), tr.getSprite().getWidth()-(shrinkScale/length));		// Set the size to be smaller
			tr.getSprite().setLocation(tr.getSprite().getX(), tr.getSprite().getY()+((shrinkScale/2)/length));							// Set the position to be a little lower so it's centered
			if(tr.getSprite().getColor().getGreen() + colorScale <= 255) {																// If more green can be added to the sprite
				tr.getSprite().setColor(new Color(tr.getSprite().getColor().getRed(), (int) (tr.getSprite().getColor().getGreen() + colorScale), tr.getSprite().getColor().getBlue()));
				tr.getSprite().setFillColor(tr.getSprite().getColor()); 		// Add green to the sprite (turning it from red to yellow gradually)
			}
		}
		if(trail.size() > projCount) {
			Projectile tr = trail.get(0);
			ship.getGame().remove(tr.getSprite());
			tr.setDestroyed(true);
			trail.remove(tr);
		}
		ship.getSprite().sendToFront();
	}
	
	public void update() {
		move();
		if(ship.isDestroyed()) {
			for(Projectile proj : trail) {
				ship.getGame().remove(proj.getSprite());
			}
			trail.clear();
		}
	}
}