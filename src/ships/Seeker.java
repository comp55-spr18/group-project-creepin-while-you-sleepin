package ships;

import acm.graphics.GImage;
import acm.graphics.GObject;
import game.Game;
import projectiles.FireTrail;

public class Seeker extends Ship {
	private int seek = 0;
	private double playerx;
	private double playery;
	
	public Seeker(Game game, double y) {
		super(game);
		setMaxHealth(6);				//they're medium tankiness		
		setSprite(new GImage("sprites/enemy2.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(100, 100);				//kinda large
		setSpeed(15);					//pretty fast
		getGame().add(getSprite());
		setTrail(new FireTrail(this));
		setPoints(200);					//worth a decent amount
		setxDir(0);
		setyDir(0);							
		setCanShoot(false);
		//level buffs
		if(game.currLevel >= 2) {
			setMaxHealth(12);
			setShielded(true);
			setShieldCooldown(0);
			setShieldMaxCooldown(200);
			setCollisionDamage(2);
		}

		if(game.currLevel >= 3) {
			setMaxHealth(24);
			setCollisionDamage(3);
			setSpeed(18);
		}
	}
	//No shoot function
	@Override
	public void shoot() {}

	//Dashes towards the player's previous position, then delays before going to the player's new position. 
	@Override
	public void move() {
		//if the delay is over, get new target coordinates, start moving towards them, and reset the timer
		if(seek == 0 && getGame().player != null) {
			GObject shipSprite = getGame().player.getSprite();
			playerx = shipSprite.getX() + shipSprite.getWidth()/2;
			playery = shipSprite.getY() + shipSprite.getHeight()/2;
			aimAtPlayer();
		}
		//checks if seeker is within one move(speed) of target coordinates. If it is, stops seeker
		if (((Math.abs(playerx - getSprite().getX() - getSprite().getWidth()/2)) <= (getSpeed()/2+1)) && (seek < 0) && ((Math.abs(playery - getSprite().getY() - getSprite().getHeight()/2) <= (getSpeed()/2+1))) ) {
			setxDir(0);
			setyDir(0);
			seek = 50;
		}
		if (seek <= 0) {
			vectorMove();
		}
		seek--;
	}
}