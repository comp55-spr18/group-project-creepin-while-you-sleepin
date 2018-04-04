import java.awt.Color;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class boss extends Ship {
	public boss(MainApplication game, double y) {
		setGame(game);
		setInvincible(false);
		setHealth(2);
		setCooldown(100);
		setMaxCooldown(175);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("boss 1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setBulletColor(Color.RED);
		setSize(50, 50);
		setExplosion(new GImage("explosion.png"));
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		setyDir(0);
		setSpeed(6);
		setPoints(100);
		setCollisionDamage(1);
		setTrail(new FireTrail(this));
	}
}