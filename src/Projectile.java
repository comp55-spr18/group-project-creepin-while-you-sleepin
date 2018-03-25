import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import acm.graphics.*;
import acm.program.*;

public class Projectile {
	private boolean isPlayerProjectile;	// Check if the projectile belongs to player
	private GPoint location;					// Top-left-most point of projectile
	private double xDir;						// Using the x and y Dir variables creates a vector with speed as its magnitude, indicating the movement of the projectile
	private double yDir;
	private int speed;
	private GOval sprite;						// The sprite that will be used for the projectile, currently just a circle for simplicity
	public Projectile(boolean isPlayerProj, GPoint gunLoc, double xD, double yD, int spd, Color bulletColor, int size) {
		setPlayerProjectile(isPlayerProj);
		setLocation(new GPoint(gunLoc));
		setSprite(new GOval(15,15));
		getSprite().setFillColor(bulletColor);
		getSprite().setFilled(true);
		setxDir(xD);
		setyDir(yD);
		getSprite().setSize(size,size);
		setSpeed(spd);
		getSprite().setLocation(gunLoc);
	}
	public void move() {
		int dx = 1;
		if(xDir < 0) dx = -1;
		sprite.move(Math.cos(Math.atan(yDir/xDir))*speed*dx, Math.sin(Math.atan(yDir/xDir))*speed*dx);
		location.setLocation(sprite.getLocation());
	}
	// Getters and setters
	public boolean isPlayerProjectile() {
		return isPlayerProjectile;
	}
	public void setPlayerProjectile(boolean isPlayerProjectile) {
		this.isPlayerProjectile = isPlayerProjectile;
	}
	public GPoint getLocation() {
		return location;
	}
	public void setLocation(GPoint location) {
		this.location = location;
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
		this.speed = speed;
	}
	public GOval getSprite() {
		return sprite;
	}
	public void setSprite(GOval sprite) {
		this.sprite = sprite;
	}
}