import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Wave {
	MainApplication game;
	ArrayList<Ship> currentWave;
	Timer timer;
	int counter;
	int enemyToSpawn;
	int delay;
	boolean finished;
	public Wave(MainApplication g) {
		game = g;
		finished = false;
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
		int weight;
		if(game.easy) {
			weight = 100;
		} else {
			weight = 25;
		}
		if(game.rgen.nextInt()%weight < 20) {
			Drone();
		} else {
			Drone();
		}
	}
	public void easy1() {			// Generates a basic easy wave
		
		delay = 100;
		currentWave.add(new Drone(game, 100));
		currentWave.add(new Drone(game, 200));
		currentWave.add(new Drone(game, 300));
		currentWave.add(new Drone(game, 400));
		currentWave.add(new Drone(game, 500));
		
	}
	
	public void hard1() {			// Generates a basic hard wave
		delay = 2;
		currentWave.add(new TestHomingEnemy(game, 500));
		currentWave.add(new TestHomingEnemy(game, 100));
		currentWave.add(new TestHomingEnemy(game, 300));
		currentWave.add(new TestHomingEnemy(game, 500));
		currentWave.add(new TestHomingEnemy(game, 100));
		
	}
	
	public void Drone() {			// Generates a drone wave
		delay = 5;
		currentWave.add(new Drone(game, game.WINDOW_HEIGHT - 200));		
		currentWave.add(new Drone(game, 100));
		currentWave.add(new Drone(game, game.WINDOW_HEIGHT - 200));
		currentWave.add(new Drone(game, 100));
		currentWave.add(new Drone(game, game.WINDOW_HEIGHT - 200));
		currentWave.add(new Drone(game, 100));
		currentWave.add(new Drone(game, game.WINDOW_HEIGHT - 200));
		currentWave.add(new Drone(game, 100));
		currentWave.add(new Drone(game, game.WINDOW_HEIGHT - 200));
		currentWave.add(new Drone(game, 100)); 
		currentWave.add(new Drone(game, game.WINDOW_HEIGHT - 200));		
		currentWave.add(new Drone(game, 100));
		currentWave.add(new Drone(game, game.WINDOW_HEIGHT - 200));
		currentWave.add(new Drone(game, 100));
		currentWave.add(new Drone(game, game.WINDOW_HEIGHT - 200));
		currentWave.add(new Drone(game, 100));
		currentWave.add(new Drone(game, game.WINDOW_HEIGHT - 200));
		currentWave.add(new Drone(game, 100));
		currentWave.add(new Drone(game, game.WINDOW_HEIGHT - 200));
		currentWave.add(new Drone(game, 100)); 
		currentWave.add(new HeavyWeightEnemy(game, 400));
	}
	
	public void update() {
		if(game.lose || game.win) {
			finished = true;
		}
		counter++;														// Increment counter
		if(counter%delay == 0 && enemyToSpawn < currentWave.size()) {	// On a 150 frame interval, spawn next enemy
			game.add(currentWave.get(enemyToSpawn).getSprite());		// Add the sprite of the enemy
			game.enemies.add(currentWave.get(enemyToSpawn++));
		} else if (enemyToSpawn >= currentWave.size()) {				// If all enemies have been spawned
			for(Ship enemy : game.enemies) {							// Check to see if any enemy timers are running
				if(!enemy.isDestroyed()) {						// If an enemy timer is still running (still alive)
					counter = 1;										// Decrement the counter (effectively freezing it)
					return;												// Exit the function
				}
			}
			finished = true;												// Reaching this point means all enemies are dead, stop wave timer
		}
	}
}
