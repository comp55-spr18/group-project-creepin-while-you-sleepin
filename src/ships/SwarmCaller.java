package ships;

import acm.graphics.GImage;
import game.Game;
import projectiles.FireTrail;

public class SwarmCaller extends BasicEnemy {
	
	private int waveCall;
	private double playery;
	
	public SwarmCaller(Game game, double y) {
		super(game, y);
		setMaxHealth(20);		//very tough			
		setCooldown(0);			
		setSprite(new GImage("sprites/enemy5.png", getGame().WINDOW_WIDTH, y));
		setSize(80, 80);
		setTrail(new FireTrail(this));
		setMaxCooldown(5); //spawns two more swarmBots when called
		waveCall = 0;//Calls another swarm when big enough
		setPoints(300);
	}
	
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			if (waveCall == 150) {
				getGame().playSound("r2d", 2);
				playery = getGame().player.getSprite().getY() + getGame().player.getSprite().getHeight()/2;
			}
			if (waveCall >= 150) {
				//spawns enemies targeting the player's location
				getGame().enemies.add(new SwarmBot(getGame(), playery-getGame().player.getSprite().getHeight()/2, 1));
				getGame().enemies.add(new SwarmBot(getGame(), playery+getGame().player.getSprite().getHeight()/2, 2));
			}
			if(waveCall >= 205) {
				waveCall = 0;
			}
			
		} else {
			setCooldown(getCooldown() + 1);
			waveCall++;
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}

	//moves onto screen, then stops.
	@Override
	public void move() {
		if (getSprite().getLocation().getX() > getGame().WINDOW_WIDTH/1.2) {
			getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		}
	}
}