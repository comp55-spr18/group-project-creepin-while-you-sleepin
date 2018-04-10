import java.awt.Color;
import java.util.ArrayList;
import acm.graphics.GLabel;
import acm.graphics.GLine;

public class Wave {
	private MainApplication game;			// The game
	private int counter;					// Counter to keep track of time between waves
	private int enemyToSpawn;				// The next enemy the wave wants to spawn
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
				selectedWave = Math.abs(game.rgen.nextInt()%7);			// Randomly select one of the easy waves (currently hard1() and Drone())
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
			for(int i = game.enemies.size() - 1;i >= 0;i--) {					// Check to see if any enemy explosions are still visible (they are hidden when the ship is destroyed)
				Ship enemy = game.enemies.get(i);
				if(enemy.getExplosion().isVisible()) {			// If an enemy explosion is not hidden (still alive)
					counter = 1;								// Set the counter to 1 to freeze it
					return;										// Exit the function
				}
			}
			for(Projectile proj : game.projectiles) {
				if(!proj.isPlayerProjectile()) {
					counter = 1;
					return;
				}
			}
			if(!game.powers.isEmpty()) {
				counter = 1;
				return;
			}
			game.remove(upgradeLine);
			game.remove(upgradeLabel);
			if(waveCount == totalWaves) {
				if(level == maxLevel) {
					game.win = true;
				}
				level++;
				waveCount = 0;
				game.playerControl = false;
			} else if(!game.lose){
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
				game.enemies.add(new BasicEnemy(game, 500));		// This is the first enemy it spawns
				delay = 100;											// Sets the new delay between enemy spawns to be 100
				break;
			case 2:
				game.enemies.add(new BasicEnemy(game, 250));		// The second and so on
				break;
			case 3:
				game.enemies.add(new BasicEnemy(game, 300));
				break;
			case 4:
				game.enemies.add(new BasicEnemy(game, 500));
				break;
			case 5:
				game.enemies.add(new BasicEnemy(game, 250));
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
				game.enemies.add(new BasicEnemy(game, 500));
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
				game.enemies.add(new SprayBall(game, 450, 1920/3));
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
				game.enemies.add(new BasicEnemy(game, 500));
				delay = 50;
				break;
			case 22:
				game.enemies.add(new BasicEnemy(game, 450));
				break;
			case 23:
				game.enemies.add(new BasicEnemy(game, 500));
				break;
			case 24:
				game.enemies.add(new BasicEnemy(game, 550));
				break;
			case 25:
				game.enemies.add(new BasicEnemy(game, 500));
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
				size = 5;
				delay = 100;
				break;
			default:
				switch(enemyToSpawn%2) {
				case 0:
					game.enemies.add(new Bouncer(game, game.WINDOW_HEIGHT - 300));
					if(enemyToSpawn == 20) {
						delay = 200;
					}
					break;
				case 1:
					game.enemies.add(new Bouncer(game, 300));
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
				game.enemies.add(new BasicEnemy(game, 1080/2));
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
				game.enemies.add(new SprayBall(game, 1080/2, 1920/2));
				delay = 400;
				break;
			case 2:
				game.enemies.add(new BasicEnemy(game, 200));
				game.enemies.add(new BasicEnemy(game, 800));
				delay = 50;
				break;
			case 3:
				game.enemies.add(new BasicEnemy(game, 400));
				game.enemies.add(new BasicEnemy(game, 600));
				break;
			case 4:
				game.enemies.add(new BasicEnemy(game, 500));
				break;
			case 5:
				game.enemies.add(new BasicEnemy(game, 500));
				break;
			case 6:
				game.enemies.add(new BasicEnemy(game, 400));
				game.enemies.add(new BasicEnemy(game, 600));
				break;
			case 7:
				game.enemies.add(new BasicEnemy(game, 200));
				game.enemies.add(new BasicEnemy(game, 800));
				break;
		}
	}
	
	void easy7() {
		switch(enemyToSpawn) {
			case 0:
				size = 20;
				delay = 15;
			default:
				switch(enemyToSpawn%2) {
					case 0:
						game.enemies.add(new BasicEnemy(game, 250));
						break;
					case 1:
						game.enemies.add(new BasicEnemy(game, 850));
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
			game.enemies.add(new SawedOff(game, 1080/2));
			break;
		case 2:
			game.enemies.add(new Bouncer(game, 400));
			break;
		case 3:
			game.enemies.add(new TestHomingEnemy(game, 700));
			break;
		case 4:
			game.enemies.add(new Kamikazi(game, 200));
			break;
		case 5:
			game.enemies.add(new SprayBall(game, 1080/2, 1920/2));
			break;
		}
	}

	public void hard1() {			// Generates a basic hard wave
		switch(enemyToSpawn) {
			case 0:
				size = 1;
				delay = 50;
				break;
			case 1:
				game.enemies.add(new SwarmCaller(game, 200));
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
				game.enemies.add(new TestHomingEnemy(game, 800));
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
						game.enemies.add(new Drone(game, game.WINDOW_HEIGHT - 200));
						break;
					case 1:					// Otherwise spawn this one (note that this one gets called first)
						game.enemies.add(new Drone(game, 100));
						if(enemyToSpawn%5 == 0) {
							game.enemies.add(new Kamikazi(game, 1080/2));
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
				game.enemies.add(new SawedOff(game, 200));
				break;
			case 2:
				game.enemies.add(new Bouncer(game, 100));
				break;
			case 3:
				game.enemies.add(new Bouncer(game, 500));
				break;
		}
	}

	public void fakeBossWave() {			// Just a pseudo-boss wave until we have a boss
		switch(enemyToSpawn) {
			case 0:
				size = 1;
				delay = 1;
				break;
			default:
				game.enemies.add(new Boss(game, 200));
		}
	}

	public void upgradeWave() {
		delay = 1;
		size = 0;
		if(game.player.getSprite().getX() < game.WINDOW_WIDTH/(1920/1000.0)) {
			game.powers.add(new FireRateUp(game, 1500, 50));
			game.powers.add(new BulletDamageUp(game, 1500, 150));
			game.powers.add(new BulletSpeedUp(game, 1500, 250));
			game.powers.add(new SpreadShot(game, 1500, 350));
			game.powers.add(new DoubleShot(game, 1500, 450));
			game.powers.add(new BulletSizeUp(game, 1500, 550));
			game.powers.add(new HealthUp(game, 1500, 650));
			game.powers.add(new ShieldUp(game, 1500, 750));
			game.remove(upgradeLine);
			game.remove(upgradeLabel);
			enemyToSpawn = 0;
		} else {
			enemyToSpawn = -2;
			game.add(upgradeLine);
			game.add(upgradeLabel);
		}
	}
	// Getters and setters
	public MainApplication getGame() {
		return game;
	}

	public int getCounter() {
		return counter;
	}

	public int getEnemyToSpawn() {
		return enemyToSpawn;
	}

	public int getDelay() {
		return delay;
	}

	public int getSize() {
		return size;
	}

	public int getSelectedDifficulty() {
		return selectedDifficulty;
	}

	public int getSelectedWave() {
		return selectedWave;
	}

	public int getTotalWaves() {
		return totalWaves;
	}

	public int getWaveCount() {
		return waveCount;
	}

	public int getPrevWave() {
		return prevWave;
	}

	public int getUpgradeMod() {
		return upgradeMod;
	}

	public int getLevel() {
		return level;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public GLine getUpgradeLine() {
		return upgradeLine;
	}

	public GLabel getUpgradeLabel() {
		return upgradeLabel;
	}

	public int getPrevSize() {
		return prevSize;
	}

	public int getCurrSize() {
		return currSize;
	}

	public void setGame(MainApplication game) {
		this.game = game;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public void setEnemyToSpawn(int enemyToSpawn) {
		this.enemyToSpawn = enemyToSpawn;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSelectedDifficulty(int selectedDifficulty) {
		this.selectedDifficulty = selectedDifficulty;
	}

	public void setSelectedWave(int selectedWave) {
		this.selectedWave = selectedWave;
	}

	public void setTotalWaves(int totalWaves) {
		this.totalWaves = totalWaves;
	}

	public void setWaveCount(int waveCount) {
		this.waveCount = waveCount;
	}

	public void setPrevWave(int prevWave) {
		this.prevWave = prevWave;
	}

	public void setUpgradeMod(int upgradeMod) {
		this.upgradeMod = upgradeMod;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public void setUpgradeLine(GLine upgradeLine) {
		this.upgradeLine = upgradeLine;
	}

	public void setUpgradeLabel(GLabel upgradeLabel) {
		this.upgradeLabel = upgradeLabel;
	}

	public void setPrevSize(int prevSize) {
		this.prevSize = prevSize;
	}

	public void setCurrSize(int currSize) {
		this.currSize = currSize;
	}
}
