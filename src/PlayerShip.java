import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class PlayerShip extends Ship {
	ArrayList<Projectile> trail = new ArrayList<Projectile>();
	
	public PlayerShip(MainApplication game) {
		setGame(game);
		setInvincible(false);
		setIframe(100);
		setHealth(5);
		setCooldown(0);
		setMaxCooldown(10);
		setCanShoot(true);
		setShipPoints(new GPoint[] {});
		setLocation(new GPoint(0,0));
		setGunLocation(new GPoint[] {new GPoint(50,17.5)});
		setSprite(new GImage("auto.png", getLocation().getX(), getLocation().getY()));
		setBulletColor(Color.BLUE);
		getSprite().setSize(50, 50);
	}
	@Override
	public void move() {	// Moves the player's ship hitbox to the location of the ship
		double x = getLocation().getX();
		double y = getLocation().getY();
		setShipPoints(new GPoint[]{new GPoint(x,y), new GPoint(x,y+10), new GPoint(x,y+20), new GPoint(x,y+30), new GPoint(x,y+40), new GPoint(x,y+50), new GPoint(x+10,y+50), new GPoint(x+20,y+50), new GPoint(x+30,y+50), new GPoint(x+40,y+50), new GPoint(x+50,y+50),new GPoint(x+50,y+40), new GPoint(x+50,y+30), new GPoint(x+50,y+20), new GPoint(x+50,y+10), new GPoint(x+50, y), new GPoint(x+40, y), new GPoint(x+30, y), new GPoint(x+20, y), new GPoint(x+10, y)});
		setGunLocation(new GPoint[] {new GPoint(x+50,y+17.5)});
		getSprite().setLocation(getLocation());
	}
	@Override
	public void shoot() {		// Returns the projectile type and iterates to the next gun location (or the same one if only one)
		Projectile newProj = new Projectile(true, getGunLocation()[0], 1, 0, 25, getBulletColor(), 15);
		getGame().bullets.add(newProj);
		getGame().add(newProj.getSprite());
	}
	
	public void fireTrail() {
		Projectile trailProj = new Projectile(true, new GPoint(getLocation().getX()-10,getLocation().getY()+12.5), -1, 0, 4, Color.RED, 25);
		trail.add(trailProj);
		getGame().add(trailProj.getSprite());
		for(Projectile tr : trail) {
			tr.move();
			tr.getSprite().setSize(tr.getSprite().getWidth()-0.5, tr.getSprite().getWidth()-0.5);
			tr.getSprite().setLocation(tr.getSprite().getX(), tr.getSprite().getY()+0.25);
			if(tr.getSprite().getColor().getGreen()+25 <= 255) {
				tr.getSprite().setColor(new Color(tr.getSprite().getColor().getRed(), tr.getSprite().getColor().getGreen() + 10, tr.getSprite().getColor().getBlue()));
				tr.getSprite().setFillColor(tr.getSprite().getColor());
			}
		}
		for(Projectile tr : trail) {
			if(tr.getSprite().getWidth() <= 3) {
				getGame().remove(tr.getSprite());
				trail.remove(tr);
				break;
			}
		}
	}
}
