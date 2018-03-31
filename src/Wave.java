import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Wave implements ActionListener{
	MainApplication game;
	ArrayList<Ship> currentWave;
	Timer timer;
	int counter;
	int enemyToSpawn;
	int delay;
	int size;
	boolean finished;
	int weight;
	int selected;
	public Wave(MainApplication g) {
		game = g;
		finished = false;
		timer = new Timer(1000/game.fps, this);
		getNewWave();
		timer.start();
	}
	
	public void getNewWave() {
		if(currentWave != null) {
			currentWave.clear();
		}
		currentWave = new ArrayList<Ship>();
		if(game.enemies != null) {
			game.enemies.clear();
		}
		game.enemies = new ArrayList<Ship>();
		counter = 0;
		enemyToSpawn = 0;
		if(game.easy) {
			weight = 100;
		} else {
			weight = 25;
		}
		selected = game.rgen.nextInt()%weight;
		getWave();
	}
	
	public void getWave() {
		if(selected < 20) {
			Drone();
		} else {
			Drone();
		}
		game.add(game.enemies.get(game.enemies.size() - 1).getSprite());
		enemyToSpawn++;
	}
	
	public void easy1() {			// Generates a basic easy wave
		size = 5;
		delay = 100;
		currentWave.add(new Drone(game, 100));
		currentWave.add(new Drone(game, 200));
		currentWave.add(new Drone(game, 300));
		currentWave.add(new Drone(game, 400));
		currentWave.add(new Drone(game, 500));
	}
	
	public void hard1() {			// Generates a basic hard wave
		size = 5;
		delay = 2;
		currentWave.add(new TestHomingEnemy(game, 500));
		currentWave.add(new TestHomingEnemy(game, 100));
		currentWave.add(new TestHomingEnemy(game, 300));
		currentWave.add(new TestHomingEnemy(game, 500));
		currentWave.add(new TestHomingEnemy(game, 100));
		
	}
	
	public void Drone() {			// Generates a drone wave
		size = 20;
		switch(enemyToSpawn%2) {
			case 0:
				delay = 5;
				game.enemies.add(new Drone(game, game.WINDOW_HEIGHT - 200));
				break;
			case 1:
				delay = 50;
				game.enemies.add(new Drone(game, 100));
				break;
		}
	}
	
	public void update() {
		if(game.lose || game.win) {
			timer.stop();
		}
		counter++;														// Increment counter
		if(counter%delay == 0 && enemyToSpawn < size) {	// On a 150 frame interval, spawn next enemy
			getWave();
			
		} else if (enemyToSpawn >= size) {				// If all enemies have been spawned
			for(Ship enemy : game.enemies) {							// Check to see if any enemy timers are running
				if(enemy.getBlownup().isVisible()) {						// If an enemy timer is still running (still alive)
					counter = 1;										// Decrement the counter (effectively freezing it)
					return;												// Exit the function
				}
			}
			getNewWave();												// Reaching this point means all enemies are dead, stop wave timer
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
	}
}
