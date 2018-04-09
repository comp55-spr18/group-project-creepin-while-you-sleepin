import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;

public class Seeker extends BasicEnemy {
	private int seek = 0;
	private double playerx;
	private double playery;
	
	public Seeker(MainApplication game, double y) {
		super(game, y);
		getTrail().getTimer().stop();
		setHealth(6);				// They're tough enemies			
		setSprite(new GImage("sprites/enemy2.png", getGame().WINDOW_WIDTH, y));
		setSize(100, 100);
		setSpeed(12);
		setExplosion(new GImage("explosion.png"));
		setTrail(new FireTrail(this));
		setPoints(200);
		setxDir(0);
		setyDir(0);		
	}
	// tweaked bullet speed
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
		}
	}

	//moves the drone until part-way down the screen, where it curves back the way it came
	@Override
	public void move() {
		//if the delay is over, get new target coordinates, start moving towards them, and reset the timer
		if(seek == 0) {
			GObject shipSprite = getGame().player.getSprite();
			playerx = shipSprite.getX() + shipSprite.getWidth()/2;
			playery = shipSprite.getY() + shipSprite.getHeight()/2;
			aimAtPlayer();
			seek = 100;	
		}
		//checks if seeker is within one move(speed) of target coordinates. If it is, stops seeker
		if ((((Math.abs(playerx - getSprite().getX() + getSprite().getWidth()/2)) <= getSpeed()) && (seek < 99)) || (((Math.abs(playery - getSprite().getY() + getSprite().getHeight()/2) <= getSpeed())) && (seek < 99)) ) {
			setxDir(0);
			setyDir(0);
		}
		else {
			vectorMove();
		}
		seek--;
		double x = getSprite().getLocation().getX();
		double y = getSprite().getLocation().getY();
		setGunLocation(new GPoint[] {new GPoint(x,y+getSprite().getHeight()/2)});
	}
}