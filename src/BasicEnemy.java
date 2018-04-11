import java.awt.Color;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class BasicEnemy extends Ship {
	double i = -1.5;
	double j = -0.05;
	public BasicEnemy(MainApplication game, double y) {
		super(game);
		setHealth(2);
		setCooldown(500);
		setMaxCooldown(575);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint(50,15)});
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setBulletColor(Color.RED);
		setSize(50, 50);
		getSprite().setLocation(getSprite().getX(), getSprite().getY() - getSprite().getHeight()/2);
		setxDir(-1);
		setyDir(0);
		setSpeed(5);
		setPoints(100);
		setBulletDamage(1);
		setBulletSpeed(10);
		setBulletSize(15);
		setTrail(new FireTrail(this));
		switch(game.wave.getLevel()) {
			case 2:
				setMaxHealth(3);
				setCooldown(300);
				setMaxCooldown(375);
		}
	}
	@Override
	public void move() {
		i = i+j;
		setyDir(i);
		setyDir(getGame().WINDOW_HEIGHT/(1080/getyDir()));
		if(Math.abs(i) >= 3) {
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
