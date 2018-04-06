import java.awt.Color;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class TestEnemy extends Ship {
	double i = 0;
	double j = 0.1;
	public TestEnemy(MainApplication game, double y) {
		setGame(game);
		setInvincible(false);
		setHealth(2);
		setCooldown(100);
		setMaxCooldown(175);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
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
		setBulletDamage(1);
		setBulletSpeed(14);
		setBulletSize(15);
		setTrail(new FireTrail(this));
	}
	@Override
	public void move() {
		i = i+j;
		setyDir(i);
		if(Math.abs(i) >= 5) {
			j *= -1;
		}
		getSprite().move(getxDir()*getSpeed(), getyDir());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+getSprite().getHeight()/2)});
		if(getSprite().getLocation().getX() < -100) {
			setDestroyed(true);
		}
	}
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			Projectile newProj = new Bullet(this, getGunLocation()[0], -1, 0);
			newProj.aimAtPlayer();
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}
}
