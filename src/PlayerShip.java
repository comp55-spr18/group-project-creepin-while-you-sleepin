import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class PlayerShip extends Ship {
	private ArrayList<Projectile> trail = new ArrayList<Projectile>();

	public PlayerShip(MainApplication game) {
		setTimer(new Timer(5, this));
		setGame(game);
		setInvincible(false);
		setIframe(100);
		setHealth(5);
		setCooldown(0);
		setMaxCooldown(25);
		setCanShoot(true);
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
		setGunLocation(new GPoint[] {new GPoint(x+50,y+17.5)});
		getSprite().setLocation(getLocation());
	}
	@Override
	public void shoot() {		// Returns the projectile type and iterates to the next gun location (or the same one if only one)
		if(canShoot() && getGame().isShooting) {
			Projectile newProj = new Bullet(getGame(), true, getGunLocation()[0], 1, 0, 25, getBulletColor(), 15);
			getGame().add(newProj.getSprite());
			setCanShoot(false);
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}
	
	public void onCollision() {
		setHealth(getHealth() - 1);
		if(getHealth() <= 0) {
			getGame().runGame = false;
		}
		setInvincible(true);
	}
	
	public void fireTrail() {
		Projectile trailProj = new Bullet(null, true, new GPoint(getLocation().getX()-10,getLocation().getY()+12.5), -1, 0, 4, Color.RED, 25);
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
			if(tr.getSprite().getWidth() <= getGame().rgen.nextInt()%26 || tr.getSprite().getWidth() <= 3) {
				getGame().remove(tr.getSprite());
				trail.remove(tr);
				break;
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		fireTrail();
		shoot();
		// If the player is invincible, increment their invincibility timer
		if(isInvincible()) {
			if(getIframe() == 0) {
				getSprite().setImage("truck.png");
				getSprite().setSize(50, 50);
			}
			setIframe(getIframe() + 1);
		}
		// If the player's iframe count hits 100, make them vulnerable again
		if(getIframe() == 100) {
			getSprite().setImage("auto.png");
			getSprite().setSize(50, 50);
			setInvincible(false);
			setIframe(0);
		}
	}
}
