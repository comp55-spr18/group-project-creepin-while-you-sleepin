import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Drone extends BasicEnemy {
	private int lifetime = 0;
	private double topBot;
	
	public Drone(MainApplication game, double y) {
		super(game, y);
		setMaxHealth(1);				// They're weak enemies
		setCooldown(920);			// I want them to fire once then never again, dealt with by long cd
		setSprite(new GImage("sprites/enemy2.png", getGame().WINDOW_WIDTH, y));
		setSize(40, 40);
		setSpeed(10);
		setTrail(new FireTrail(this));
		setMaxCooldown(1000);
		setPoints(25);
		setBulletSize(20);
		setBulletSpeed(12);
		setBulletDamage(1);
		if (y <= getGame().WINDOW_HEIGHT/2) {
			setyDir(.3);
		}
		else {
			setyDir(-.3);
		}
		topBot = y;
		switch(game.wave.getLevel()) {
			case 2:
				setBulletDamage(2);
				setShielded(true);
				setShieldCooldown(0);
				setShieldMaxCooldown(100000);
		}
	}
	// tweaked bullet speed
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

	//moves the drone until part-way down the screen, where it curves back the way it came
	@Override
	public void move() {
		if(getSprite().getLocation().getX() < (getGame().WINDOW_WIDTH) / 1.5) {
			if (getxDir() != 1) {
				if(topBot <= getGame().WINDOW_HEIGHT/2) {
					if(getxDir() <= 0) {
						setyDir(getyDir() + .04);
					}
					else {
						setyDir(getyDir() - .04);
					}
				}
				
				else {
					if(getxDir() <= 0) {
						setyDir(getyDir() - .04);
					}
					else {
						setyDir(getyDir() + .04);
					}
				}
				
				setxDir(getxDir() + .04);
			}
		}
		lifetime++;
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+getSprite().getHeight()/2)});
		if(lifetime > 200) {
			setDestroyed(true);
		}
		
	}
}
