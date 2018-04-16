package ships;

import acm.graphics.GImage;
import acm.graphics.GObject;
import game.Game;
import projectiles.FireTrail;

public class Seeker extends BasicEnemy {
	private int seek = 0;
	private double playerx;
	private double playery;
	
	public Seeker(Game game, double y) {
		super(game, y);
		setMaxHealth(6);				// They're tough enemies			
		setSprite(new GImage("sprites/enemy2.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(100, 100);
		setSpeed(15);
		setTrail(new FireTrail(this));
		setPoints(200);
		setxDir(0);
		setyDir(0);		
	}
	// tweaked bullet speed
	@Override
	public void shoot() {}

	//moves the drone until part-way down the screen, where it curves back the way it came
	@Override
	public void move() {
		//if the delay is over, get new target coordinates, start moving towards them, and reset the timer
		if(seek == 0) {
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