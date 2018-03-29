
import java.awt.Color;

import javax.swing.Timer;


import acm.graphics.GOval;
import acm.graphics.GPoint;
// This class exists just for fancy effects, it is a bullet that does no damage
public class Emitter extends Projectile {
	public Emitter(MainApplication game, boolean isPlayerProj, GPoint gunLoc, double xD, double yD, double spd, Color bulletColor, int size) {
		super(game, isPlayerProj, gunLoc, xD, yD, spd, bulletColor, size);
	}
	
	// I had to redefine checkCollision and onCollision here since this projectile type does nothing but add aesthetics
	public void checkCollision() {
	}
	
	public void onCollision(Ship target) {
	}
}
