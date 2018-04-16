package game;
public class Level {
	Game game;
	private int totalWaves;					// The total number of waves the player must fight (including the boss wave)
	private int currWave;					// The current wave the player is on
	private int prevWave;					// The previous wave that was generated
	private int upgradeMod;					// How often upgrade waves occur
	private Wave wave;						// The wave object generating waves
	private boolean finished;
	public Level(Game g) {
		game = g;
		currWave = 1;
		totalWaves = 7;
		prevWave = -1;
		upgradeMod = 3;
	}

	public void update() {
		if(wave == null) {
			wave = new Wave(this);
			prevWave = wave.getSelectedWave();
		}
		if(wave.isFinished()) {					// If all enemies have been spawned and the screen is clear
			if(currWave == totalWaves) {		// If it is the final wave (as in the player beat the boss)
				setFinished(true);
			} else if(!game.lose){				// If it was not the final wave (and the player is not dead), get the next wave
				currWave++;
				wave = new Wave(this);
				if(currWave%upgradeMod == 0) {
					wave.setUpgradeWave(true);
				} else {
					wave.setUpgradeWave(false);
					prevWave = wave.getSelectedWave();
				}
				if(currWave == totalWaves) {
					wave.setUpgradeWave(false);
					wave.setBossWave(true);
				}
			}
		}
		wave.update();
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
