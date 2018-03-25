import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import acm.graphics.*;
import acm.program.*;

public class Projectile {
	public boolean isPlayerProjectile;	// Check if the projectile belongs to player
	GPoint location;					// Top-left-most point of projectile
	double xDir;						// Using the x and y Dir variables creates a vector with speed as its magnitude, indicating the movement of the projectile
	double yDir;
	int speed;
	GOval sprite;						// The sprite that will be used for the projectile, currently just a circle for simplicity
	public Projectile(boolean isPlayerProj, GPoint gunLoc, double xD, double yD, int spd, Color bulletColor, int size) {
		isPlayerProjectile = isPlayerProj;
		location = new GPoint(gunLoc);
		sprite = new GOval(15,15);
		sprite.setFillColor(bulletColor);
		sprite.setFilled(true);
		xDir = xD;
		yDir = yD;
		sprite.setSize(size,size);
		speed = spd;
		sprite.setLocation(gunLoc);
	}
	public void move() {
		int dx = 1;
		if(xDir < 0) dx = -1;
		sprite.move(Math.cos(Math.atan(yDir/xDir))*speed*dx, Math.sin(Math.atan(yDir/xDir))*speed*dx);
		location.setLocation(sprite.getLocation());
	}
}