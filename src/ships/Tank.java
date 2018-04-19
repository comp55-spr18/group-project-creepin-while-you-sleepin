package ships;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;

public class Tank extends Ship {
	
	public Tank(Game game, double y, int height) {
		super(game);
		setMaxHealth(30);		// They soak damage for other ships
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(100, height);
		setSpeed(5);			//slowish
		setTrail(null);
		setPoints(150);			//worth no points						
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint()});		
		setxDir(-1);
		setyDir(0);
		getGame().add(getSprite());

		if(game.currLevel >= 2) {
			setMaxHealth(45);
		}

		if(game.currLevel >= 3) {
			setMaxHealth(70);
		}
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
