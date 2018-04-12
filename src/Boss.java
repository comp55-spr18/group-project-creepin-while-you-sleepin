import java.awt.Color;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Boss extends Ship {
	private int counter;
	public Boss(MainApplication game, double y) {
		super(game);
		counter = 0;
		setMaxHealth(150);
		setCooldown(0);
		setMaxCooldown(50);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint(), new GPoint(), new GPoint()});
		setSprite(new GImage("boss 1.png", game.WINDOW_WIDTH, game.WINDOW_HEIGHT/(1080/y)));
		setBulletColor(Color.white);
		setSize(500, 500);
		setBulletColor(Color.RED);
		setExplosion(new GImage("explosion.png"));
		setDestroyed(false);
		setDestroyedCounter(0);
		setxDir(-1);
		setyDir(0);
		setSpeed(4);
		setPoints(100);
		setCollisionDamage(2);
		setBulletDamage(2);
		setBulletSize(40);
		setBulletSpeed(8);
		setBeamHeight(250);
		setBeamDur(65);
		setBeamDamage(1);
		setSelectedGun(0);
		switch(game.wave.getLevel()) {
			case 2:
				setMaxHealth(250);
				setBulletDamage(3);
				setBulletSize(80);
				setMaxCooldown(20);
		}
		setTrail(new FireTrail(this));
	}
	
	public void move() { //boss will spawn in and then bounce up and down on the screen
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		if(getSprite().getX() <= 1000 && getyDir() == 0) {
			setxDir(0);
			setyDir(-1);
		}
		if(getSprite().getY() <= 0 || getSprite().getY() + getSprite().getHeight() >= getGame().WINDOW_HEIGHT - 100) {
			setyDir(getyDir() * -1);
		}
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		getGunLocation()[0].setLocation(x, y + getSprite().getHeight()/5);
		getGunLocation()[1].setLocation(x, y + (4*getSprite().getHeight()/5));
		getGunLocation()[2].setLocation(x + getSprite().getWidth()/2, y + getSprite().getHeight()/2);
		if(getSprite().getLocation().getX() < -100) {
			setDestroyed(true);
		}
	}
	
	public void shoot() { //shoot is constructed to shoot multiple straight bullets at the player
		if(canShoot()) {
			setCanShoot(false);
			Projectile newProj = new Bullet(this, getGunLocation()[getSelectedGun()], -1, 0);
			newProj.aimAtPlayer();
			setSelectedGun((getSelectedGun() + 1)%2);
			if(getHealth() < 50 && counter%5 == 0) {
				new Beam(this, getGunLocation()[2]);
			}
			counter++;
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
