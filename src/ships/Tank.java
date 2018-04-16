package ships;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.FireTrail;

public class Tank extends BasicEnemy {
	
	public Tank(Game game, double y) {
		super(game, y);
		setMaxHealth(30);		// They soak damage for other ships
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, y));
		setSize(100, 100);
		setSpeed(5);			//slowish
		setTrail(new FireTrail(this));
		setPoints(150);			//worth no points
	}

	// Can't shoot
	@Override
	public void shoot() {}

	//DNA helix movement with other SwarmBots
	@Override
	public void move() {		
		getSprite().move(getxDir() * getSpeed(), getyDir() * getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] { new GPoint(x, y + getSprite().getHeight() / 2) });
		if (getSprite().getLocation().getX() < -200) {
			setDestroyed(true);
		}
		
	}
}
