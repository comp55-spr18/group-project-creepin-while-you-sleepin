package ships;

import acm.graphics.GImage;
import game.Game;
import projectiles.FireTrail;

public class SwarmCaller extends Ship {
	
	private int waveCall;		//Determines when the next swarmBot wave will be spawned
	private double playery;		//Used to save player's position when a wave is spawned so it doesn't track
								//the player mid-spawn	
	public SwarmCaller(Game game, double y) {
		super(game);
		setMaxHealth(20);		//very tough			
		setCooldown(0);			
		setSprite(new GImage("sprites/enemy5.png", getGame().WINDOW_WIDTH, getGame().WINDOW_HEIGHT/(1080/y)));
		setSize(80, 80);		//kinda big
		setTrail(new FireTrail(this));
		setMaxCooldown(5);		//spawns two more swarmBots when called
		waveCall = 0;			//Calls another swarm when big enough
		setPoints(300);			//worth a fair amount as they're a rarer, harder enemy
		setCanShoot(false);
		setxDir(-1);
		setyDir(0);
		setSpeed(5);			//not that fast
		getGame().add(getSprite());
		//level buffs
		if(game.currLevel >= 2) {
			setMaxHealth(40);
		}

		if(game.currLevel >= 3) {
			setMaxHealth(80);
		}
	}
	//Waits for waveCall to get big enough, then makes two more swarmBots where the player was
	//at the time waveCall triggered every 5 delay until waveCall is large enough. Then resets waveCall
	@Override
	public void shoot() {
		if(canShoot() && getGame().player != null) {
			setCanShoot(false);
			if (waveCall == 150) {
				getGame().r2dCount = getGame().playSound("r2d", getGame().r2dCount);
				playery = getGame().player.getSprite().getY() + getGame().player.getSprite().getHeight()/2;
			}
			if (waveCall >= 150) {
				//spawns enemies targeting the player's location
				new SwarmBot(getGame(), playery-getGame().player.getSprite().getHeight()/2, 1);
				new SwarmBot(getGame(), playery+getGame().player.getSprite().getHeight()/2, 2);
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