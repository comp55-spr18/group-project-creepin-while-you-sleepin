import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class TestEnemy extends Ship {
	public TestEnemy() {
		setInvincible(false);
		setHealth(3);
		setCooldown(100);
		setMaxCooldown(175);
		setCanShoot(false);
		setShipPoints(new GPoint[] {});
		setLocation(new GPoint(1000,300));
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("enemy.jpg", getLocation().getX(), getLocation().getY()));
		setBulletColor(Color.RED);
		getSprite().setSize(50, 50);
		setxDir(-1);
		setyDir(0);
		setSpeed(2);
	}
	@Override
	public void move() {
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		setLocation(getSprite().getLocation());
		double x = getLocation().getX();
		double y = getLocation().getY();
		setShipPoints(new GPoint[]{new GPoint(x,y), new GPoint(x,y+10), new GPoint(x,y+20), new GPoint(x,y+30), new GPoint(x,y+40), new GPoint(x,y+50), new GPoint(x+10,y+50), new GPoint(x+20,y+50), new GPoint(x+30,y+50), new GPoint(x+40,y+50), new GPoint(x+50,y+50),new GPoint(x+50,y+40), new GPoint(x+50,y+30), new GPoint(x+50,y+20), new GPoint(x+50,y+10), new GPoint(x+50, y), new GPoint(x+40, y), new GPoint(x+30, y), new GPoint(x+20, y), new GPoint(x+10, y)});
		setGunLocation(new GPoint[] {new GPoint(x,y+17.5)});
		getSprite().setLocation(getLocation());
	}
	@Override
	public void shoot() {
		Projectile newProj = new Projectile(false, getGunLocation()[0], -1, 0, 4, getBulletColor(), 15);
		getGame().bullets.add(newProj);
		getGame().add(newProj.getSprite());
	}
}
