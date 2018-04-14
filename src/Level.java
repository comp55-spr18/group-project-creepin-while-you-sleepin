public class Level {
	MainApplication game;
	private int totalWaves;					// The total number of waves the player must fight (including the boss wave)
	private int waveCount;					// The current wave the player is on
	private int prevWave;					// The previous wave that was generated
	private int upgradeMod;					// How often upgrade waves occur
	private Wave wave;						// The wave object generating waves
	private boolean finished;
	public Level(MainApplication g) {
		game = g;
		waveCount = 0;
		totalWaves = 7;
		prevWave = -1;
		upgradeMod = 3;
	}

	public void update() {
		if(wave == null || wave.isFinished()) {					// If all enemies have been spawned and the screen is clear
			if(waveCount == totalWaves) {						// If it is the final wave (as in the player beat the boss)
				setFinished(true);
			} else if(!game.lose || wave == null){								// If it was not the final wave (and the player is not dead), get the next wave
				waveCount++;
				wave = new Wave(this);
				if(waveCount%upgradeMod == 0) {
					wave.setUpgradeWave(true);
				} else {
					wave.setUpgradeWave(false);
					prevWave = wave.getSelectedWave();
				}
				if(waveCount == totalWaves) {
					wave.setUpgradeWave(false);
					wave.setBossWave(true);
				}
			}
		}
		wave.update();
	}

	public MainApplication getGame() {
		return game;
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

	public Wave getWave() {
		return wave;
	}

	public void setGame(MainApplication game) {
		this.game = game;
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
