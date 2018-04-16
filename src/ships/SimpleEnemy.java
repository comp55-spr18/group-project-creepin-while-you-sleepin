package ships;
import game.Game;
import projectiles.Bullet;

//Very similar to BasicEnemy, except I can tweak it without breaking other ships
public class SimpleEnemy extends BasicEnemy {
	public SimpleEnemy(Game game, double y) {
		super(game, y);
		setCooldown(50);
		setMaxCooldown(125);
		setSpeed(4); //A little slower than normal
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