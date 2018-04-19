package game;
public class Level {
	Game game;
	private int totalWaves;					// The total number of waves the player must fight (including the boss wave)
	private int currWave;					// The current wave the player is on
	private int prevWave;					// The previous wave that was generated
	private int upgradeMod;					// How often upgrade waves occur
	private Wave wave;						// The wave object generating waves
	private boolean finished;				// Boolean to check if the level is completed
	public Level(Game g) {
		game = g;
		currWave = 1;
		totalWaves = 7;
		prevWave = -1;
		upgradeMod = 3;
		getNewWave();
	}

	public void update() {
		if(wave.isFinished()) {					// If all enemies have been spawned and the screen is clear
			if(currWave == totalWaves) {		// If it is the final wave (as in the player beat the boss)
				setFinished(true);				// Flag this wave as finished
			} else if(!game.lose){				// If it was not the final wave (and the player is not dead), get the next wave
				currWave++;						// Increment the current wave
				getNewWave();					// Get a new wave
			}
		}
		wave.update();							// Update the wave
	}

	void getNewWave() {								// This function generates a new wave
		wave = new Wave(this);						// Create a new wave object
		if(currWave%upgradeMod == 0) {				// If this is an upgrade wave
			wave.setUpgradeWave(true);				// Set upgradeWave to true
		} else {									// Otherwise
			prevWave = wave.getSelectedWave();		// Set the previous wave to the current selected wave
		}
		if(currWave == totalWaves) {				// If this is the last wave
			wave.setBossWave(true);					// Set bossWave to true
		}
	}

	public Game getGame() {
		return game;
	}

	public int getTotalWaves() {
		return totalWaves;
	}

	public int getWaveCount() {
		return currWave;
	}

	public int getPrevWave() {
		return prevWave;
	}

	public int getUpgradeMod() {
		return upgradeMod;
	}

	public Wave getWave() {
		return wave;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setTotalWaves(int totalWaves) {
		this.totalWaves = totalWaves;
	}

	public void setWaveCount(int waveCount) {
		this.currWave = waveCount;
	}

	public void setPrevWave(int prevWave) {
		this.prevWave = prevWave;
	}

	public void setUpgradeMod(int upgradeMod) {
		this.upgradeMod = upgradeMod;
	}

	public void setWave(Wave wave) {
		this.wave = wave;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
