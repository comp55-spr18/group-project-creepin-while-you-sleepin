package misc;
import acm.graphics.GImage;
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
	public void checkCollision() {
		if(getSprite().getBounds().intersects(game.player.getSprite().getBounds())) {
			game.player.dealDamage(getCollisionDamage());
		}
	}
	public void onCollision() {}
	
	public void update() {
			move();
			checkCollision();
		}
	
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
	public void setSize(double x, double y) {
		((GImage) getSprite()).setSize(getGame().WINDOW_WIDTH/(1920/x), getGame().WINDOW_HEIGHT/(1080/y));
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
