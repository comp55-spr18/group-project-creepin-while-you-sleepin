import java.awt.Color;
import java.util.ArrayList;
import acm.graphics.GLabel;
import acm.graphics.GLine;

public class Wave {
	MainApplication game;
	int counter;					// Counter to keep track of time between waves
	int enemyToSpawn;				// The next enemy the wave wants to spawn
	int delay;						// The delay before the next enemy spawns
	int size;						// The size of the current wave
	int selectedDifficulty;			// The difficulty of the current wave
	int selectedWave;				// The rgen value of the current wave
	int totalWaves;					// The total number of waves the player must fight (including the boss wave)
	int waveCount;					// The current wave the player is on
	int upgradeMod;
	GLine upgradeLine;
	GLabel upgradeLabel;
	int prevSize;
	int currSize;

	public Wave(MainApplication g) {
		game = g;
		waveCount = 0;
		upgradeMod = 3;
		totalWaves = 20;			// For now there are 13 regular waves, 6 upgrade waves and 1 boss wave
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
			selectedWave = Math.abs(game.rgen.nextInt()%5);			// Randomly select one of the easy waves (currently hard1() and Drone())
		} else {													// If the wave is easy
			selectedWave = Math.abs(game.rgen.nextInt()%2);			// Randomly select one of the hard waves (currently only easy1())
		}
		waveCount++;												// Increment wave count
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
					fakeBossWave();
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
					fakeBossWave();
					break;
				}
			}
		} else if(waveCount%upgradeMod == 0 && waveCount != totalWaves) {
			upgradeWave();						// Call the upgrade wave
		} else {								// If it is the final wave
			fakeBossWave();						// Call the boss wave
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

	public void update() {
		counter++;												// Increment counter
		if(counter%delay == 0 && enemyToSpawn <= size) {		// After counter advances 'delay' number of frames, and if there are more enemies to spawn
			getNextEnemy();										// call getNextEnemy() to add the next enemy to game.enemies
		}
		addEnemies();
		if (enemyToSpawn > size) {								// If all enemies have been spawned
			for(Ship enemy : game.enemies) {					// Check to see if any enemy explosions are still visible (they are hidden when the ship is destroyed)
				if(enemy.getExplosion().isVisible()) {			// If an enemy explosion is not hidden (still alive)
					counter = 1;								// Set the counter to 1 to freeze it
					return;										// Exit the function
				}
			}
			if(!game.powers.isEmpty()) {
				counter = 1;
				return;
			}
			game.remove(upgradeLine);
			if(waveCount == totalWaves) {
				game.win = true;
			} else {
				getNewWave();										// Reaching this point means all enemies are dead, so get a new wave to spawn
			}
		}
	}

	public void easy1() {			// Generates a basic easy wave
		switch(enemyToSpawn) {
			case 0:
				size = 6;
				delay = 50;
				break;
			case 1:
				game.enemies.add(new TestEnemy(game, 500));		// This is the first enemy it spawns
				delay = 100;											// Sets the new delay between enemy spawns to be 100
				break;
			case 2:
				game.enemies.add(new TestEnemy(game, 200));		// The second and so on
				break;
			case 3:
				game.enemies.add(new TestEnemy(game, 300));
				break;
			case 4:
				game.enemies.add(new TestEnemy(game, 500));
				break;
			case 5:
				game.enemies.add(new TestEnemy(game, 200));
				break;
			case 6:
				game.enemies.add(new SprayBall(game, 450, game.WINDOW_WIDTH/3));
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
				game.enemies.add(new TestEnemy(game, 500));
				delay = 100;
				break;
			case 2:
				game.enemies.add(new Kamikazi(game, 900));
				break;
			case 3:
				game.enemies.add(new SprayBall(game, 300, 1000));
				delay = 300;
				break;
			case 4:
				game.enemies.add(new TestHomingEnemy(game, 500));
				delay = 100;
				break;
			case 5:
				game.enemies.add(new TestHomingEnemy(game, 200));
				break;
			case 6:
				game.enemies.add(new SprayBall(game, 450, game.WINDOW_WIDTH/3));
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
				game.enemies.add(new TestEnemy(game, 500));
				delay = 50;
				break;
			case 22:
				game.enemies.add(new TestEnemy(game, 450));
				break;
			case 23:
				game.enemies.add(new TestEnemy(game, 500));
				break;
			case 24:
				game.enemies.add(new TestEnemy(game, 550));
				break;
			case 25:
				game.enemies.add(new TestEnemy(game, 500));
				break;
			default:
				switch(enemyToSpawn%2) {
					case 0:
						game.enemies.add(new Drone(game, game.WINDOW_HEIGHT - 200));
						if(enemyToSpawn == 20) {
							delay = 200;
						}
						break;
					case 1:
						game.enemies.add(new Drone(game, 100));
						break;
				}
		}
	}

	public void easy4() {
		switch(enemyToSpawn) {
			case 0:
				size = 10;
				delay = 50;
				break;
			default:
				switch(enemyToSpawn%2) {
				case 0:
					game.enemies.add(new TestEnemy(game, game.WINDOW_HEIGHT - 300));
					if(enemyToSpawn == 20) {
						delay = 200;
					}
					break;
				case 1:
					game.enemies.add(new TestEnemy(game, 300));
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
				game.enemies.add(new Drone(game, game.WINDOW_HEIGHT - 200));
				break;
			case 1:
				game.enemies.add(new Drone(game, 100));
				break;
			case 2:
				game.enemies.add(new TestEnemy(game, 1080/2));
				break;
			}
		}
	}

	public void hard1() {			// Generates a basic hard wave
		switch(enemyToSpawn) {
			case 0:
				size = 5;
				delay = 50;
				break;
			case 1:
				game.enemies.add(new TestHomingEnemy(game, 200));
				break;
			case 2:
				game.enemies.add(new TestHomingEnemy(game, 100));
				break;
			case 3:
				game.enemies.add(new TestHomingEnemy(game, 500));
				break;
			case 4:
				game.enemies.add(new TestHomingEnemy(game, 650));
				break;
			case 5:
				game.enemies.add(new SawedOff(game, 800));
				break;
	
		}
		
	}

	public void hard2() {			// Generates a drone wave
		switch(enemyToSpawn) {		// Creates a switch for enemyToSpawn, using 0 (the first call) as the initiator for the wave
			case 0:					// Initiate the wave
				size = 70;
				delay = 5;
				break;
			default:						// This means that if enemyToSpawn is anything other than 0, this will trigger
				if(enemyToSpawn%3 == 0) {
					game.enemies.add(new TestEnemy(game, 1080/2.0));
				}
				switch(enemyToSpawn%2) {	// Mod enemyToSpawn by 2 to turn it into a 0 or 1
					case 0:					// If enemyToSpawn is even, spawn this one
						game.enemies.add(new Drone(game, game.WINDOW_HEIGHT - 200));
						break;
					case 1:					// Otherwise spawn this one (note that this one gets called first)
						game.enemies.add(new Drone(game, 100));
						break;
				}
		}
	}

	public void fakeBossWave() {			// Just a pseudo-boss wave until we have a boss
		switch(enemyToSpawn) {
			case 0:
				size = 50;
				delay = 5;
				break;
			default:
				if(enemyToSpawn%3 == 0) {
					game.enemies.add(new Boss(game, 1080/2.0));
				}
				switch(enemyToSpawn%2) {
				case 0:
					game.enemies.add(new Boss(game, game.WINDOW_HEIGHT - 300));
					break;
				case 1:
					game.enemies.add(new Boss(game, 300));
					break;
			}
		}
	}

	public void upgradeWave() {
		delay = 1;
		size = 0;
		if(game.player.getSprite().getX() < game.WINDOW_WIDTH/(1920/1000.0)) {
			game.powers.add(new FireRateUp(game, 1500, 50));
			game.powers.add(new BulletDamageUp(game, 1500, 200));
			game.powers.add(new BulletSpeedUp(game, 1500, 350));
			game.powers.add(new SpreadShot(game, 1500, 500));
			game.powers.add(new DoubleShot(game, 1500, 650));
			game.powers.add(new BulletSizeUp(game, 1500, 800));
			game.remove(upgradeLine);
			game.remove(upgradeLabel);
			enemyToSpawn = 0;
		} else {
			enemyToSpawn = -2;
			game.add(upgradeLine);
			game.add(upgradeLabel);
		}
	}
}
