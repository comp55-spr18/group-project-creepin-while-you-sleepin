import acm.graphics.GImage;
import acm.graphics.GPoint;

public class SprayBall extends TestEnemy {
	private int firing = 0;
	private int delay = 0;
	private double ballDet = getGame().WINDOW_WIDTH/3; //recommended detonation point
	
	public SprayBall(MainApplication game, double y, double detonation) {
		super(game, y);
		getTrail().getTimer().stop();
		setHealth(999);				// Effectively invincible 
		setCooldown(700);			// 
		setSprite(new GImage("sprites/enemy4.png", getGame().WINDOW_WIDTH, y));
		//pretty sure this line is pointless
		setGunLocation(new GPoint[] {}); 
		setSize(120, 120);
		setSpeed(6);
		setTrail(new FireTrail(this));
		setBulletSpeed(12);
		setBulletSize(20);
		setBulletDamage(1);
		setMaxCooldown(1000);
		setPoints(1000);
		ballDet = game.WINDOW_WIDTH/(1920/detonation);
	}
	// Once the ship has paused, fires bullets from 4 cannons turning 180 degrees (hopefully)
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			Projectile newProj = new Bullet(this, getGunLocation()[0], -1, 0);
			Projectile newProj1 = new Bullet(this, getGunLocation()[1], 0, -1);
			Projectile newProj2 = new Bullet(this, getGunLocation()[2], 1, 0);
			Projectile newProj3 = new Bullet(this, getGunLocation()[3], 0, 1);			
			if (firing < 10) {
				newProj.setxDir(-1+firing*(.1));
				newProj.setyDir(0-firing*(.1));
				newProj1.setxDir(0+firing*(.1));
				newProj1.setyDir(-1+firing*(.1));
				newProj2.setxDir(1-firing*(.1));
				newProj2.setyDir(0+firing*(.1));
				newProj3.setxDir(0-firing*(.1));
				newProj3.setyDir(1-firing*(.1));
			}
			else {
				newProj.setxDir(-1+firing*(.1));
				newProj.setyDir(-2+firing*(.1));
				newProj1.setxDir(2-firing*(.1));
				newProj1.setyDir(-1+firing*(.1));
				newProj2.setxDir(1-firing*(.1));
				newProj2.setyDir(2-firing*(.1));
				newProj3.setxDir(-2+firing*(.1));
				newProj3.setyDir(1-firing*(.1));
			}
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
			firing++;
			if (firing == 21) {
				setCanShoot(false);
				setCooldown(0);
			}
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(getMaxCooldown() - 5);
				setCanShoot(true);
			}
		}
	}

	//moves 2/3 of map, pauses for firing, then leaves
	@Override
	public void move() {
		if (getSprite().getLocation().getX() < ballDet && (firing != 21)) {
			delay++;
			if (delay == 50) {
				setCanShoot(true);
				setCooldown(getMaxCooldown() - 5);
			}
			
		}
		else {
			getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		}
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		//moving all 4 gun locations, 1=front 2=top, 3=back, 4=bot
		setGunLocation(new GPoint[] {new GPoint(x,y+getSprite().getHeight()/2), new GPoint(x+getSprite().getWidth()/2,y), new GPoint(x+getSprite().getWidth(),y+getSprite().getHeight()/2), new GPoint(x+getSprite().getWidth()/2,y+getSprite().getHeight())});
		if(getSprite().getLocation().getX() < -100) {
			setDestroyed(true);
		}
	}
}
