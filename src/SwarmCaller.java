import acm.graphics.GImage;
import acm.graphics.GPoint;

public class SwarmCaller extends BasicEnemy {
	
	public SwarmCaller(MainApplication game, double y) {
		super(game, y);
		getTrail().getTimer().stop();
		setMaxHealth(20);		//very tough			
		setCooldown(10);			
		setSprite(new GImage("sprites/enemy2.png", getGame().WINDOW_WIDTH, y));
		setSize(80, 80);
		setExplosion(new GImage("explosion.png"));
		setTrail(new FireTrail(this));
		setMaxCooldown(40); //Their firing is calling a swarm
		setPoints(300);
	}
	
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
			getGame().enemies.add(new Drone(getGame(), 500));
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}

	//moves onto screen, then stops.
	@Override
	public void move() {
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		if (getSprite().getLocation().getX() > getGame().WINDOW_WIDTH/1.2) {
			getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
			setGunLocation(new GPoint[] {new GPoint(x,y+getSprite().getHeight()/2)});
		}
		
	}
}