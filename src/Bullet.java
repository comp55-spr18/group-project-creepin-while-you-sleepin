import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.graphics.GOval;
import acm.graphics.GPoint;

public class Bullet extends Projectile {
	public Bullet(MainApplication game, boolean isPlayerProj, GPoint gunLoc, double xD, double yD, double spd, Color bulletColor, int size) {
		super(game, isPlayerProj, gunLoc, xD, yD, spd, bulletColor, size);
	}
	
	// Notice that I didn't redefine any functions here so it inherits move(), checkCollision(), and onCollision() from Projectile
}
