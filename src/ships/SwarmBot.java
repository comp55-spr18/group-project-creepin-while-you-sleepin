package ships;

import acm.graphics.GImage;
import game.Game;
import projectiles.FireTrail;

public class SwarmBot extends Ship {
	private double upDown;
	public SwarmBot(Game game, double y, int spawnPos) {
		super(game);
		setMaxHealth(1);		// They're weak enemies, but its a swarm so it doesn't matter
		setSprite(new GImage("sprites/enemy3.png", getGame().WINDOW_WIDTH, y));
		setSize(40, 40);		//small
		setSpeed(30);			//fast
		setTrail(new FireTrail(this));
		setPoints(0);			//worth no points because they're infinitely spawned by swarmCaller
		//Makes them go up or down when they're spawned so they enter into their DNA movement pattern properly
		if (spawnPos == 1) {
			setyDir(.25);
		}
		else {
			setyDir(-.25);
		}
		upDown = spawnPos;							
		setCanShoot(false);		
		setxDir(-1);
		getGame().add(getSprite());		
		//level buffs
		if(game.currLevel >= 2) {
			setMaxHealth(2);
			setCollisionDamage(2);
		}

		if(game.currLevel >= 3) {
			setMaxHealth(4);
			setCollisionDamage(3);
		}
	}

	// Can't shoot
	@Override
	public void shoot() {}

	//DNA helix movement with other SwarmBots. Movement is reversed once they moved far enough to make the pattern
	@Override
	public void move() {		
		if ((getyDir() == 1) || getyDir() == -1) {
			if (upDown == 1) {
				upDown = 2;
			}
			else {
				upDown = 1;
			}
		}
		if (upDown == 1) {
			setyDir(getyDir()+.25);
		}
		else {
			setyDir(getyDir()-.25);
		}
			
		getSprite().move(getxDir()*getSpeed(), getyDir()*getSpeed());
		if(getSprite().getX() < -100) {
			setDestroyed(true);
		}
	}
}
