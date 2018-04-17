package ships;
import acm.graphics.GImage;
import acm.graphics.GPoint;
import game.Game;
import projectiles.Bullet;
import projectiles.FireTrail;

//Very similar to BasicEnemy, except I can tweak it without breaking other premade waves
public class SimpleEnemy extends Ship {
	public SimpleEnemy(Game game, double y) {
		super(game);
		setCooldown(50);
		setMaxCooldown(125);
		setSpeed(4); //A little slower than normal		
		setMaxHealth(2);
		setCanShoot(false);
		setGunLocation(new GPoint[] {new GPoint()});
		setSprite(new GImage("sprites/enemy1.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(50, 50);
		setxDir(-1);
		setyDir(0);
		setPoints(100);
		setBulletDamage(1);
		setBulletSpeed(10);
		setBulletSize(15);
		setTrail(new FireTrail(this));
	}
	//Most basic move and shoot
	@Override
	public void move() {
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		getGunLocation()[0].setLocation(x,y+getSprite().getHeight()/2);
		if(getSprite().getLocation().getX() < -100) {
			setDestroyed(true);
		}
	}
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			Bullet newProj = new Bullet(this, getGunLocation()[0], -1, 0);
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