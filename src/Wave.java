import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Wave implements ActionListener {
	MainApplication game;
	Timer timer;
	int counter;
	int enemyToSpawn;
	int delay;
	public Wave(MainApplication g) {
		game = g;
		game.enemies = new ArrayList<Ship>();
		timer = new Timer(1000/game.fps, this);
		counter = 0;
		enemyToSpawn = 0;
		int weight;
		if(game.easy) {
			weight = 100;
		} else {
			weight = 25;
		}
		if(game.rgen.nextInt()%weight < 20) {
			hard1();
		} else {
			easy1();
		}
		timer.start();
	}
	public void easy1() {			// Generates a basic easy wave
		delay = 100;
		game.enemies.add(new TestEnemy(game));
		game.enemies.add(new TestEnemy(game));
		game.enemies.add(new TestEnemy(game));
		game.enemies.add(new TestEnemy(game));
		game.enemies.add(new TestHomingEnemy(game));
	}
	
	public void hard1() {			// Generates a basic hard wave
		delay = 200;
		game.enemies.add(new TestHomingEnemy(game));
		game.enemies.add(new TestHomingEnemy(game));
		game.enemies.add(new TestHomingEnemy(game));
		game.enemies.add(new TestHomingEnemy(game));
		game.enemies.add(new TestHomingEnemy(game));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(game.lose || game.win) {
			timer.stop();
		}
		counter++;														// Increment counter
		if(counter%delay == 0 && enemyToSpawn < game.enemies.size()) {	// On a 150 frame interval, spawn next enemy
			game.add(game.enemies.get(enemyToSpawn).getSprite());		// Add the sprite of the enemy
			game.enemies.get(enemyToSpawn++).getTimer().start();		// Start the timer of the enemy
		} else if (enemyToSpawn >= game.enemies.size()) {				// If all enemies have been spawned
			for(Ship enemy : game.enemies) {							// Check to see if any enemy timers are running
				if(enemy.getTimer().isRunning()) {						// If an enemy timer is still running (still alive)
					counter = 1;										// Decrement the counter (effectively freezing it)
					return;												// Exit the function
				}
			}
			timer.stop();												// Reaching this point means all enemies are dead, stop wave timer
		}
	}
}