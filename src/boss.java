import java.awt.Color;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class boss extends Ship {
	public boss(MainApplication game, double y) {
		setGame(game);
		setInvincible(false);
		setHealth(50);
		setCooldown(100);
		setMaxCooldown(175);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("boss 1.png", MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT/(1080/y)));
		setBulletColor(Color.white);
		setSize(500, 500);
		setExplosion(new GImage("explosion.png"));
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		setyDir(0);
		setSpeed(7);
		setPoints(100);
		setCollisionDamage(2);
		setTrail(new FireTrail(this));
	}
	
	public void move() {
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+17.5)});
		if(getSprite().getLocation().getX() < -100) {
			setDestroyed(true);
		}
	}
	
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			Projectile newProj = new Bullet(getGame(), false, getGunLocation()[0], -1, 0, 14, getBulletColor(), 350);
			newProj.setxDir((getGame().player.getSprite().getLocation().getX()+25) - newProj.getSprite().getLocation().getX()-newProj.getSprite().getWidth()/2);
			newProj.setyDir((getGame().player.getSprite().getLocation().getY()+25) - newProj.getSprite().getLocation().getY()-newProj.getSprite().getWidth()/2);
			getGame().add(newProj.getSprite());
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

