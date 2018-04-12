import java.awt.Color;
import java.util.ArrayList;
import acm.graphics.GLabel;
import acm.graphics.GLine;

public class Wave {
	private MainApplication game;			// The game
	private int counter;					// Counter to keep track of time between waves
	private int enemyToSpawn;				// The next enemy the wave wants to spawn
	private int asteroidToSpawn;
	private boolean asteroidWave;
	private int asteroidDelay;
	private int delay;						// The delay before the next enemy spawns
	private int size;						// The size of the current wave
	private int selectedDifficulty;			// The difficulty of the current wave
	private int selectedWave;				// The rgen value of the current wave
	private int totalWaves;					// The total number of waves the player must fight (including the boss wave)
	private int waveCount;					// The current wave the player is on
	private int prevWave;					// The previous wave that was generated
	private int upgradeMod;					// How often upgrade waves occur
	private int level;						// The current level the player is on
	private int maxLevel;					// The max number of levels in the game
	private GLine upgradeLine;				// The line that appears if the player is on the right side of the screen at the start of an upgrade wave
	private GLabel upgradeLabel;			// The label telling the player to fly past the line to spawn the upgrades
	private int prevSize;					// The previous size of game.enemies
	private int currSize;					// The current size of game.enemies

	public Wave(MainApplication g) {
		game = g;
		asteroidDelay = 50;
		asteroidToSpawn = 0;
		asteroidWave = false;
		level = 1;
		maxLevel = 2;
		waveCount = 0;
		upgradeMod = 3;
		totalWaves = 7;			// For now there are 4 regular waves, 2 upgrade waves and 1 boss wave
		selectedWave = -1;
		prevWave = -1;
		upgradeLine = new GLine(game.WINDOW_WIDTH/(1920/1000.0), 0, game.WINDOW_WIDTH/(1920/1000.0), game.WINDOW_HEIGHT);
		upgradeLine.setColor(Color.RED);
		upgradeLabel = new GLabel("Fly behind this line to see the upgrades");
		upgradeLabel.setFont("arial-22-bold");
		upgradeLabel.setLocation(game.WINDOW_WIDTH/(1920/1050.0), game.WINDOW_HEIGHT/2 - upgradeLabel.getHeight()/2);
		upgradeLabel.setColor(Color.RED);
		if(game.easy) {				// If the game is on easy, set selectedDifficulty to 0
			selectedDifficulty = 0;
		} else {					// If the game is on hard, set selectedDifficulty to 1
			selectedDifficulty = 1;
		}
		getNewWave();				// Start the first wave
	}

	public void getNewWave() {
		if(game.enemies != null) {	// If enemies is not null
			game.enemies.clear();	// Clear it
		}
		game.enemies = new ArrayList<Ship>();	// Make enemies a new arraylist
		prevSize = 0;
		currSize = 0;
		counter = 0;							// Reset the counter
		enemyToSpawn = 0;						// Reset the enemy to spawn (we set it to -1 so that it reads the delay and size of the wave but doesn't spawn anything)
		if(selectedDifficulty == 0) {								// If the difficulty of the new wave is hard
			while(selectedWave == prevWave) {
				selectedWave = Math.abs(game.rgen.nextInt()%10);			// Randomly select one of the easy waves (currently hard1() and Drone())
			}
		} else {													// If the wave is easy
			while(selectedWave == prevWave) {
				selectedWave = Math.abs(game.rgen.nextInt()%3);			// Randomly select one of the hard waves (currently only easy1())
			}
		}
		waveCount++;												// Increment wave count
		if(waveCount%upgradeMod != 0) {
			prevWave = selectedWave;
		}
		getNextEnemy();												// Get the next enemy (must be called here to initialize delay and size)
	}

	public void getNextEnemy() {				// Generates the next enemy in the wave
		if(waveCount < totalWaves && waveCount%upgradeMod != 0) {			// If it is not the final wave
			if(selectedDifficulty == 0) {		// If the wave difficulty is easy
				switch(selectedWave) {			// Switch statement for all the easy waves
				case 0:
					easy1();
					break;
				case 1:
					easy2();
					break;
				case 2:
					easy3();
					break;
				case 3:
					easy4();
					break;
				case 4:
					easy5();
					break;
				case 5:
					easy6();
					break;
				case 6:
					easy7();
					break;
				case 7:
					easy8();
					break;
				case 8:
					easy9();
					break;
				case 9:
					easy10();
					break;
				}
			} else {							// If the wave difficulty is hard
				switch(selectedWave) {			// Switch statement for all the hard waves
				case 0:
					hard1();
					break;
				case 1:
					hard2();
					break;
				case 2:
					hard3();
					break;
				}
			}
		} else if(waveCount%upgradeMod == 0 && waveCount != totalWaves) {
			upgradeWave();						// Call the upgrade wave
		} else {								// If it is the final wave
			firstBossWave();						// Call the boss wave
		}
		enemyToSpawn++;							// Increment the enemyToSpawn
	}

	public void addEnemies() {
		currSize = game.enemies.size();					// Get current size of enemies
		if(currSize > prevSize) {						// If an enemy was created
			for(int i = prevSize;i < currSize;i++) {	// Add the sprite of all new enemies
				game.add(game.enemies.get(i).getSprite());
			}
			prevSize = currSize;						// Update prevSize
		}
	}
	
	public boolean isClear() {				// This function checks to see if all enemies/projectiles/powerups are gone from the screen (so the game knows when to start the next wave)
		for(int i = game.enemies.size() - 1;i >= 0;i--) {	// Check to see if any enemy explosions are still visible (they are hidden when the ship is destroyed)
			Ship enemy = game.enemies.get(i);
			if(enemy.getExplosion().isVisible()) {			// If an enemy explosion is not hidden (still alive)
				return false;								// Return false
			}
		}
		for(Projectile proj : game.projectiles) {			// For all projectiles in the game
			if(!proj.isPlayerProjectile()) {				// If it is hostile
				return false;								// Return false
			}
		}
		if(!game.powers.isEmpty()) {						// If there are powerups in the game
			return false;									// Return false
		}
		return true;										// Otherwise it is empty and returns true
	}

	public void update() {
		counter++;												// Increment counter
		if(counter%delay == 0 && enemyToSpawn <= size) {		// After counter advances 'delay' number of frames, and if there are more enemies to spawn
			getNextEnemy();										// call getNextEnemy() to add the next enemy to game.enemies
		}
		addEnemies();											// Add any new enemy sprites
		if(asteroidWave && counter%asteroidDelay == 0) {		// If an asteroid wave is triggered, and the delay satisfies
			asteroidBelt();										// Call asteroidBelt to spawn the next asteroid
			asteroidToSpawn++;									// Increment asteroidToSpawn
		}
		if (enemyToSpawn > size && isClear()) {					// If all enemies have been spawned and the screen is clear
			game.remove(upgradeLine);							// Remove any labels that may have shown during a powerup wave
			game.remove(upgradeLabel);
			if(waveCount == totalWaves) {						// If it is the final wave (as in the player beat the boss)
				if(level == maxLevel) {							// If this was the last level
					game.win = true;							// The player won the game
				}
				level++;										// Increment the level
				waveCount = 0;									// Reset the waveCount
				game.playerControl = false;						// Take control away from player to trigger next level
			} else if(!game.lose){								// If it was not the final wave (and the player is not dead), get the next wave
				getNewWave();									// Get a new wave
			}
		}
		if(game.rgen.nextInt()%600 == 0 && game.powers.isEmpty()) {						// Every time update() is called, there is a 1/600 chance of triggering an asteroidWave
			asteroidWave = true;
		}
	}

	public void easy1() {			// Generates a basic easy wave
		switch(enemyToSpawn) {
			case 0:
				size = 6;
				delay = 50;
				break;
			case 1:
				new BasicEnemy(game, 500);		// This is the first enemy it spawns
				delay = 100;											// Sets the new delay between enemy spawns to be 100
				break;
			case 2:
				new BasicEnemy(game, 250);		// The second and so on
				break;
			case 3:
				new BasicEnemy(game, 300);
				break;
			case 4:
				new BasicEnemy(game, 500);
				break;
			case 5:
				new BasicEnemy(game, 250);
				break;
			case 6:
				new SprayBall(game, 450, game.WINDOW_WIDTH/3);
				break;
		}
	}

	public void easy2() {
		switch(enemyToSpawn) {
			case 0:
				size = 6;
				delay = 50;
				break;
			case 1:
				new BasicEnemy(game, 500);
				delay = 100;
				break;
			case 2:
				new Kamikazi(game, 900);
				break;
			case 3:
				new SprayBall(game, 300, 1000);
				delay = 300;
				break;
			case 4:
				new HomingEnemy(game, 500);
				delay = 100;
				break;
			case 5:
				new HomingEnemy(game, 200);
				break;
			case 6:
				new SprayBall(game, 450, 1920/3);
				break;
		}
	}

	public void easy3() {
		switch(enemyToSpawn) {
			case 0:
				size = 25;
				delay = 20;
				break;
			case 21:
				new BasicEnemy(game, 500);
				delay = 50;
				break;
			case 22:
				new BasicEnemy(game, 450);
				break;
			case 23:
				new BasicEnemy(game, 500);
				break;
			case 24:
				new BasicEnemy(game, 550);
				break;
			case 25:
				new BasicEnemy(game, 500);
				break;
			default:
				switch(enemyToSpawn%2) {
					case 0:
						new Drone(game, game.WINDOW_HEIGHT - 200);
						if(enemyToSpawn == 20) {
							delay = 200;
						}
						break;
					case 1:
						new Drone(game, 100);
						break;
				}
		}
	}

	public void easy4() {
		switch(enemyToSpawn) {
			case 0:
				size = 5;
				delay = 100;
				break;
			default:
				switch(enemyToSpawn%2) {
				case 0:
					new Bouncer(game, game.WINDOW_HEIGHT - 300);
					if(enemyToSpawn == 20) {
						delay = 200;
					}
					break;
				case 1:
					new Bouncer(game, 300);
					break;
			}
		}
	}

	public void easy5() {
		switch(enemyToSpawn) {
		case 0:
			size = 20;
			delay = 25;
			break;
		default:
			switch(enemyToSpawn%3) {
			case 0:
				new Drone(game, game.WINDOW_HEIGHT - 200);
				break;
			case 1:
				new Drone(game, 100);
				break;
			case 2:
				new BasicEnemy(game, 1080/2);
				break;
			}
		}
	}
	
	public void easy6() {
		switch(enemyToSpawn) {
			case 0:
				size = 7;
				delay = 50;
				break;
			case 1:
				new SprayBall(game, 1080/2, 1920/2);
				delay = 400;
				break;
			case 2:
				new BasicEnemy(game, 200);
				new BasicEnemy(game, 800);
				delay = 50;
				break;
			case 3:
				new BasicEnemy(game, 400);
				new BasicEnemy(game, 600);
				break;
			case 4:
				new BasicEnemy(game, 500);
				break;
			case 5:
				new BasicEnemy(game, 500);
				break;
			case 6:
				new BasicEnemy(game, 400);
				new BasicEnemy(game, 600);
				break;
			case 7:
				new BasicEnemy(game, 200);
				new BasicEnemy(game, 800);
				break;
		}
	}
	
	void easy7() {
		switch(enemyToSpawn) {
			case 0:
				size = 20;
				delay = 15;
				break;
			default:
				switch(enemyToSpawn%2) {
					case 0:
						new BasicEnemy(game, 250);
						break;
					case 1:
						new BasicEnemy(game, 850);
						break;
				}
		}
	}
	
	void easy8() {
		switch(enemyToSpawn) {
		case 0:
			size = 5;
			delay = 100;
			break;
		case 1:
			new SawedOff(game, 1080/2);
			break;
		case 2:
			new Bouncer(game, 400);
			break;
		case 3:
			new HomingEnemy(game, 700);
			break;
		case 4:
			new Kamikazi(game, 200);
			break;
		case 5:
			new SprayBall(game, 1080/2, 1920/2);
			break;
		}
	}
	
	void easy9() {
		switch(enemyToSpawn) {
		case 0:
			size = 3;
			delay = 100;
			break;
		case 1:
			new SwarmCaller(game, 500);
			break;
		case 2:
			new BasicEnemy(game, 300);
			break;
		case 3:
			new BasicEnemy(game, 800);
			break;
		}
	}
	
	void easy10() {
		switch(enemyToSpawn) {
		case 0:
			size = 5;
			delay = 150;
			break;
		case 1:
			new Seeker(game, 500);
			break;
		case 2:
			new Seeker(game, 300);
			break;
		case 3:
			new SawedOff(game, 400);
			new SawedOff(game, 600);
			break;
		case 4:
			new SprayBall(game, 500, 100);
			delay = 300;
			break;
		case 5:
			new HomingEnemy(game, 300);
			new HomingEnemy(game, 800);
			break;
		}
	}

	public void hard1() {			// Generates a basic hard wave
		switch(enemyToSpawn) {
			case 0:
				size = 5;
				delay = 50;
				break;
			case 1:
				new Asteroid(game, 1000);
				break;
			case 2:
				new Seeker(game, 400);
				break;
			case 3:
				new Seeker(game, 100);
				break;
			case 4:
				new Seeker(game, 700);
				delay = 32;
				break;
			case 5:
				new SprayBall(game, 1080/2, 1920/2);
				break;
		}
	}

	public void hard2() {			// Generates a drone wave
		switch(enemyToSpawn) {		// Creates a switch for enemyToSpawn, using 0 (the first call) as the initiator for the wave
			case 0:					// Initiate the wave
				size = 25;
				delay = 10;
				break;
			default:						// This means that if enemyToSpawn is anything other than 0, this will trigger
				switch(enemyToSpawn%2) {	// Mod enemyToSpawn by 2 to turn it into a 0 or 1
					case 0:					// If enemyToSpawn is even, spawn this one
						new Drone(game, game.WINDOW_HEIGHT - 200);
						break;
					case 1:					// Otherwise spawn this one (note that this one gets called first)
						new Drone(game, 100);
						if(enemyToSpawn%5 == 0) {
							new Kamikazi(game, 1080/2);
						}
						break;
				}
		}
	}
	public void hard3() {			// Generates a basic bouncy wave
		switch(enemyToSpawn) {
			case 0:
				size = 3;
				delay = 100;
				break;
			case 1:
				new SawedOff(game, 200);
				break;
			case 2:
				new Bouncer(game, 100);
				break;
			case 3:
				new Bouncer(game, 500);
				break;
		}
	}

	public void asteroidBelt() {
		switch (asteroidToSpawn) {
		case 0:
			new Asteroid(game,2000);
			break;
		case 1:
			new Asteroid(game,900);
			break;
		case 2:
			new Asteroid(game,1200);
			break;
		case 3:
			new Asteroid(game,1800);
			break;
		case 4:
			new Asteroid(game,1900);
			break;
		case 5:
			new Asteroid(game,1000);
			asteroidWave = false;
			asteroidToSpawn = 0;
			break;
		}
	}

	public void firstBossWave() {			// Implements easy mode of a boss wave into the program
		switch(enemyToSpawn) {
			case 0:
				size = 1;
				delay = 1;
				break;
			default:
				new Boss(game, 200);
		}
	}
	
	public void secondBossWave() {          // Implements hard mode of the boss wave into the program
		switch(enemyToSpawn) {
		case 0:
			size = 1;
			delay = 1;
		default:
			new Boss2(game,200);
		}
	}

	public void upgradeWave() {
		delay = 1;
		size = 0;
		if(game.player.getSprite().getX() < game.WINDOW_WIDTH/(1920/1000.0)) {
			new FireRateUp(game, 1500, 50);
			new BulletDamageUp(game, 1500, 150);
			new BulletSpeedUp(game, 1500, 250);
			new BulletSizeUp(game, 1500, 350);
			new HealthUp(game, 1500, 450);
			new ShieldUp(game, 1500, 550);
			new BulletUp(game, 1500, 650);
			new Exit(game, 1500, 750);
			game.remove(upgradeLine);
			game.remove(upgradeLabel);
			enemyToSpawn = 0;
		} else {
			enemyToSpawn = -2;
			game.add(upgradeLine);
			game.add(upgradeLabel);
		}
	}
	// Getters
	public int getLevel() {
		return level;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
}
