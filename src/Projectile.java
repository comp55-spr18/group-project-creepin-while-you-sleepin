import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.Timer;
import acm.graphics.*;
import acm.program.*;

public abstract class Projectile implements ActionListener {
	private Timer timer;
	private MainApplication game;
	private boolean isPlayerProjectile;			// Check if the projectile belongs to player
	private GPoint location;					// Top-left-most point of projectile
	private double xDir;						// Using the x and y Dir variables creates a vector with speed as its magnitude, indicating the movement of the projectile
	private double yDir;
	private int speed;
	private GOval sprite;						// The sprite that will be used for the projectile, currently just a circle for simplicity
	
	public void move() {
		int dx = 1;
		if(getxDir() < 0) dx = -1;
		getSprite().move(Math.cos(Math.atan(getyDir()/getxDir()))*getSpeed()*dx, Math.sin(Math.atan(getyDir()/getxDir()))*getSpeed()*dx);
		setLocation(getSprite().getLocation());
		if(getGame() != null && (getLocation().getX() < -50 || getLocation().getX() > getGame().WINDOW_WIDTH)) {
			getGame().remove(getSprite());
			getTimer().stop();
		}
	}

	public void onCollision(Ship target) {
		if((isPlayerProjectile() && !(target instanceof PlayerShip)) || (!isPlayerProjectile() && target instanceof PlayerShip)) {
			if(!target.isInvincible()) {
				if(target instanceof PlayerShip) {
					target.setInvincible(true);
				}
				getGame().remove(getSprite());
				getTimer().stop();
				target.setHealth(target.getHealth() - 1);
				if(target instanceof PlayerShip) {
					getGame().updateHealthBoard();
				}
			}
		}
	}

	public void checkCollision() {
		if(game != null) {
			GPoint top = new GPoint(location.getX() + sprite.getWidth()/2, location.getY());
			GPoint mid = new GPoint(location.getX() + sprite.getWidth()/2, location.getY() + sprite.getHeight()/2);
			GPoint bot = new GPoint(location.getX() + sprite.getWidth()/2, location.getY() + sprite.getHeight());
			GPoint[] testPoints = new GPoint[] {top, mid, bot};
			for(GPoint point : testPoints) {
				for(Ship enemy : game.enemies) {
					if(enemy.getSprite().contains(point)) {
						onCollision(enemy);
						return;
					}
				}
				if(game.player.getSprite().contains(point)) {
					onCollision(game.player);
					return;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		checkCollision();
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
	public MainApplication getGame() {
		return game;
	}
	public void setGame(MainApplication game) {
		this.game = game;
	}
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}