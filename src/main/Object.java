package main;
import acm.graphics.GObject;
import game.Game;

public abstract class Object {
	protected Game game;
	protected GObject sprite;
	protected double xDir;
	protected double yDir;
	protected double speed;
	protected int collisionDamage;
	protected boolean isDestroyed;
	
	public void move() {}
	public void checkCollision() {}
	public void onCollision() {}
	public void update() {}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public GObject getSprite() {
		return sprite;
	}
	public void setSprite(GObject sprite) {
		this.sprite = sprite;
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
		this.speed = speed;
	}
	public int getCollisionDamage() {
		return collisionDamage;
	}
	public void setCollisionDamage(int collisionDamage) {
		this.collisionDamage = collisionDamage;
	}
	public boolean isDestroyed() {
		return isDestroyed;
	}
	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
}
