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
	FireTrail(Ship s) {
		ship = s;
		if(ship instanceof PlayerShip) {
			xDir = -1;
			location = new GPoint(ship.getLocation().getX() - ship.getSprite().getWidth()/4, ship.getLocation().getY()+ship.getSprite().getHeight()/2-size/2);
			length = 4;
		} else {
			xDir = 1;
			length = 0.2;
			location = new GPoint(ship.getLocation().getX()+ship.getSprite().getWidth() - ship.getSprite().getWidth()/3, ship.getLocation().getY()+ship.getSprite().getHeight()/2-size/2);
		}
		size = 25;
		trail = new ArrayList<Projectile>();
		timer = new Timer(5, this);
		timer.start();
	}
	public void move() {
		if(!ship.isDestroyed()) {
			Projectile trailProj = new Emitter(ship.getGame(), true, location, xDir, 0, length, Color.RED, size);
			trail.add(trailProj);
			ship.getGame().add(trailProj.getSprite());
		}
		for(Projectile tr : trail) {
			tr.move();
			tr.getSprite().setSize(tr.getSprite().getWidth()-0.5, tr.getSprite().getWidth()-0.5);
			tr.getSprite().setLocation(tr.getSprite().getX(), tr.getSprite().getY()+0.25);
			if(tr.getSprite().getColor().getGreen()+15 <= 255) {
				tr.getSprite().setColor(new Color(tr.getSprite().getColor().getRed(), tr.getSprite().getColor().getGreen() + 10, tr.getSprite().getColor().getBlue()));
				tr.getSprite().setFillColor(tr.getSprite().getColor());
			}
		}
		for(Projectile tr : trail) {
			if(tr.getSprite().getWidth() <= ship.getGame().rgen.nextInt()%26 || tr.getSprite().getWidth() <= 3) {
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
