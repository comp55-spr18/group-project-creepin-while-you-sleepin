import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.graphics.GOval;
import acm.graphics.GPoint;

public class Bullet extends Projectile {
	public Bullet(MainApplication game, boolean isPlayerProj, GPoint gunLoc, double xD, double yD, int spd, Color bulletColor, int size) {
		setGame(game);
		setTimer(new Timer(1000/game.fps, this));
		setPlayerProjectile(isPlayerProj);
		setLocation(new GPoint(gunLoc));
		setSprite(new GOval(15,15));
		getSprite().setFillColor(bulletColor);
		getSprite().setColor(bulletColor);
		getSprite().setFilled(true);
		setxDir(xD);
		setyDir(yD);
		getSprite().setSize(size, size);
		setSpeed(spd);
		getSprite().setLocation(gunLoc);
		getTimer().start();
	}
	
	// Notice that I didn't redefine any functions here so it inherits move(), checkCollision(), and onCollision() from Projectile
}
