import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Asteroid extends Ship {
	public Asteroid(MainApplication game, double x) {
		super(game);
		setHealth(2);
		setCooldown(0);
		setMaxCooldown(75);
		setCanShoot(false);
		setGunLocation(new GPoint[] {});
		setSprite(new GImage("sprites/asteroid.gif", getGame().WINDOW_WIDTH/(1920/x), -100));
		setBulletColor(Color.RED);
		setSize(100, 100);
		setxDir(-1);
		setyDir(1);
		setSpeed(8);
		setPoints(100);
		setBulletDamage(1);
		setBulletSpeed(20);
		setBulletSize(15);
		setTrail(new FireTrail(this));
		//game.audio.playSound("sounds", "a");
		
	}
	@Override
	public void move() {
		getSprite().move(getxDir() * getSpeed(), getyDir() * getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		
		setGunLocation(new GPoint[] { new GPoint(x, y + getSprite().getHeight() / 2) });
		if (getSprite().getLocation().getX() < -300) {
			setDestroyed(true);
		}
	}
	
	

}
