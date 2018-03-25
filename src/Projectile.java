import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import acm.graphics.*;
import acm.program.*;

public class Projectile {
	public boolean isPlayerProjectile;
	GPoint location;
	double xDir;
	double yDir;
	int speed;
	GOval sprite;
	public Projectile(boolean isPlayerProj, GPoint gunLoc, double xD, double yD, int spd, Color bulletColor, int size) {
		isPlayerProjectile = isPlayerProj;
		location = new GPoint(gunLoc);
		sprite = new GOval(15,15);
		sprite.setFillColor(bulletColor);
		sprite.setFilled(true);
		xDir = xD;
		yDir = yD;
		sprite.setSize(size,size);
		speed = spd;
		sprite.setLocation(gunLoc);
	}
}