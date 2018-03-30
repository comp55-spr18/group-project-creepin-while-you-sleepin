import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GPoint;

public class FireTrail implements ActionListener {
	private Timer timer;
	private ArrayList<Projectile> trail;
	private Ship ship;
	private int xDir;
	private int size;
	private GPoint location;
	private double length;
	private double speed;
	private double xOffset;
	FireTrail(Ship s) {
		ship = s;
		if(ship instanceof PlayerShip) {
			xDir = -1;
			xOffset = ship.getSprite().getWidth()/4;
			length = 1;
			speed = 2;
		} else {
			xDir = 1;
			length = 0.25;
			speed = 1;
			xOffset = ship.getSprite().getWidth()/3 - ship.getSprite().getWidth();
		}
		size = 25;
		trail = new ArrayList<Projectile>();
		timer = new Timer(5, this);
		timer.start();
	}
	public void move() {
		if(!ship.isDestroyed()) {
			location = new GPoint(ship.getLocation().getX() - xOffset, ship.getLocation().getY()+ship.getSprite().getHeight()/2-size/2);
			Projectile trailProj = new Emitter(ship.getGame(), true, location, xDir, 0, speed, Color.RED, size);
			trail.add(trailProj);
			ship.getGame().add(trailProj.getSprite());
		}
		for(Projectile tr : trail) {
			tr.move();
			tr.getSprite().setSize(tr.getSprite().getWidth()-(0.5/length), tr.getSprite().getWidth()-(0.5/length));
			tr.getSprite().setLocation(tr.getSprite().getX(), tr.getSprite().getY()+(0.25/length));
			if(tr.getSprite().getColor().getGreen()+(10/length) <= 255) {
				tr.getSprite().setColor(new Color(tr.getSprite().getColor().getRed(), (int) (tr.getSprite().getColor().getGreen() + (10/length)), tr.getSprite().getColor().getBlue()));
				tr.getSprite().setFillColor(tr.getSprite().getColor());
			}
		}
		for(Projectile tr : trail) {
			if(tr.getSprite().getWidth() <= 3) {
				ship.getGame().remove(tr.getSprite());
				trail.remove(tr);
				break;
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		if(trail.isEmpty() || ship.getGame().win) {
			timer.stop();
		}
	}
	// Getters and setters
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public ArrayList<Projectile> getTrail() {
		return trail;
	}
	public void setTrail(ArrayList<Projectile> trail) {
		this.trail = trail;
	}
	public Ship getShip() {
		return ship;
	}
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	public int getxDir() {
		return xDir;
	}
	public void setxDir(int xDir) {
		this.xDir = xDir;
	}
}
