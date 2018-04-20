package ships;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;

public class Tank extends Ship {
	
	public Tank(Game game, double y) {
		super(game);
		setMaxHealth(30);		// They soak damage for other ships
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(100, 100);
		setSpeed(5);			//slowish
		setTrail(null);
		setPoints(150);			//worth a decent amount of points, but hard to kill					
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint()});		
		setxDir(-1);
		setyDir(0);
		getGame().add(getSprite());
		//level buffs
		if(game.currLevel >= 2) {
			setMaxHealth(60);
		}

		if(game.currLevel >= 3) {
			setMaxHealth(100);
		}
	}

	// Can't shoot
	@Override
	public void shoot() {}

	//Simple right to left movement
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
