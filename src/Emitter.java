import java.awt.Color;

import javax.swing.Timer;

import acm.graphics.GOval;
import acm.graphics.GPoint;
// This class exists just for fancy effects, it is a bullet that does no damage
public class Emitter extends Projectile {
	public Emitter(MainApplication game, boolean isPlayerProj, GPoint gunLoc, double xD, double yD, int spd, Color bulletColor, int size) {
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
	
	public void checkCollision() {
	}
	
	public void onCollision(Ship target) {
	}
}
