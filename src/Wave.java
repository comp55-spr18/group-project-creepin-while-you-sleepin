import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Wave {
	MainApplication game;
	int counter;				// Counter to keep track of time between waves
	int enemyToSpawn;			// The next enemy the wave wants to spawn
	int delay;					// The delay before the next enemy spawns
	int size;					// The size of the current wave
	int selectedDifficulty;		// The difficulty of the current wave
	int selectedWave;			// The rgen value of the current wave
	int totalWaves;				// The total number of waves the player must fight (including the boss wave)
	int waveCount;				// The current wave the player is on
	
	public Wave(MainApplication g) {
		game = g;
		waveCount = 0;
		totalWaves = 3;				// For now there are only 2 regular waves and 1 boss wave
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
		counter = 0;							// Reset the counter
		enemyToSpawn = 0;						// Reset the enemy to spawn (we set it to -1 so that it reads the delay and size of the wave but doesn't spawn anything)
		if(selectedDifficulty == 0) {								// If the difficulty of the new wave is hard
			selectedWave = Math.abs(game.rgen.nextInt()%1);			// Randomly select one of the easy waves (currently hard1() and Drone())
		} else {													// If the wave is easy
			selectedWave = Math.abs(game.rgen.nextInt()%2);			// Randomly select one of the hard waves (currently only easy1())
		}
		waveCount++;												// Increment wave count
		getNextEnemy();												// Get the next enemy (must be called here to initialize delay and size)
	}
	
	public void getNextEnemy() {			// Generates the next enemy in the wave
		if(waveCount < totalWaves) {			// If it is not the final wave
			if(selectedDifficulty == 0) {		// If the wave difficulty is easy
				switch(selectedWave) {			// Switch statement for all the easy waves
				case 0:
					easy1();
					break;
				}
			} else {							// If the wave difficulty is hard
				switch(selectedWave) {			// Switch statement for all the hard waves
				case 0:
					hard1();
					break;
				case 1:
					Drone();
					break;
				}
			}
		} else {								// If it is the final wave
			fakeBossWave();						// Call the boss wave
		}
		if(enemyToSpawn > 0) {					// If an enemy was created
			game.add(game.enemies.get(game.enemies.size() - 1).getSprite());	// Add the sprite of the latest enemy added to enemies
		}
		enemyToSpawn++;						// Increment the enemyToSpawn
	}
	
	public void easy1() {			// Generates a basic easy wave
		switch(enemyToSpawn) {
			case 0:
				size = 6;
				delay = 50;
				break;
			case 1:
			game.enemies.add(new HeavyWeightEnemy(game, 500));		// This is the first enemy it spawns
			delay = 100;											// Sets the new delay between enemy spawns to be 100
			break;
			case 2:
			game.enemies.add(new TestEnemy(game, 100));		// The second and so on
			break;
			case 3:
			game.enemies.add(new TestEnemy(game, 300));
			break;
			case 4:
			game.enemies.add(new TestEnemy(game, 500));
			break;
			case 5:
			game.enemies.add(new TestEnemy(game, 100));
			break;
			case 6:
			game.enemies.add(new SprayBall(game, 450, game.WINDOW_WIDTH/3));
			break;
		}
	}
	
	public void hard1() {			// Generates a basic hard wave
		switch(enemyToSpawn) {
			case 0:
				size = 5;
				delay = 200;
				break;
			case 1:
			game.enemies.add(new TestHomingEnemy(game, 500));
			break;
			case 2:
			game.enemies.add(new TestHomingEnemy(game, 100));
			break;
			case 3:
			game.enemies.add(new TestHomingEnemy(game, 300));
			break;
			case 4:
			game.enemies.add(new TestHomingEnemy(game, 500));
			break;
			case 5:
			game.enemies.add(new TestHomingEnemy(game, 100));
			break;
		}
		
	}
	
	public void Drone() {			// Generates a drone wave
		switch(enemyToSpawn) {		// Creates a switch for enemyToSpawn, using 0 (the first call) as the initiator for the wave
			case 0:					// Initiate the wave
				size = 20;
				delay = 10;
				break;
			default:						// This means that if enemyToSpawn is anything other than 0, this will trigger
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
				size = 10;
				delay = 5;
				break;
			default:
				game.enemies.add(new SprayBall(game, 50*enemyToSpawn, enemyToSpawn*(1920/10)));
				break;
		}
	}
	
	public void update() {
		counter++;												// Increment counter
		if(counter%delay == 0 && enemyToSpawn <= size) {		// After counter advances 'delay' number of frames, and if there are more enemies to spawn
			getNextEnemy();										// call getNextEnemy() to add the next enemy to game.enemies
		} else if (enemyToSpawn > size) {						// If all enemies have been spawned
			for(Ship enemy : game.enemies) {					// Check to see if any enemy explosions are still visible (they are hidden when the ship is destroyed)
				if(enemy.getExplosion().isVisible()) {			// If an enemy explosion is not hidden (still alive)
					counter = 1;								// Set the counter to 1 to freeze it
					return;										// Exit the function
				}
			}
			if(waveCount == totalWaves) {
				game.win = true;
			} else {
				getNewWave();										// Reaching this point means all enemies are dead, so get a new wave to spawn
			}
		}
	}
}
