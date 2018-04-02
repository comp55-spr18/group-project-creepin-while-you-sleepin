import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.Toolkit;

import acm.graphics.GImage;
import acm.graphics.GLabel;

public class MainApplication extends GraphicsApplication {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WINDOW_WIDTH = (int) screenSize.getWidth();
	public static final int WINDOW_HEIGHT = (int) screenSize.getHeight();
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };
	private SomePane somePane;
	private MenuPane menu;
	private int count;
	
	// Variables for game loop
	int fps = 75;
	boolean win = false;		// Notice that we have both win and lose booleans; default state is that both are false (the player hasn't won or lost but is playing)
	boolean lose = false;		// this means we need to be explicit and can't assume that because win = false that the player lost
	boolean easy = false;
	Random rgen = new Random();
	ArrayList<Ship> enemies = new ArrayList<Ship>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	PlayerShip player;
	int score = 0;
	GLabel afterMessage = new GLabel("", 10, 25);
	GLabel scoreBoard = new GLabel("SCORE: " + score, 10, 25);
	ArrayList<GImage> healthBar = new ArrayList<GImage>();
	boolean isShooting = false;
	Timer timer = new Timer(1000/fps, this);
	Wave wave;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT - 100);
	}

	public void run() {
		somePane = new SomePane(this);
		menu = new MenuPane(this);
		switchToMenu();
	}

	public void switchToMenu() {
//		playRandomSound();
		enemies.clear();
		count++;
		switchToScreen(menu);
	}

	public void switchToSome() {
//		playRandomSound();
		player = new PlayerShip(this);			// Initiate the game with a new player ship
		wave = new Wave(this);
		score = 0;								// Reset score
		updateScoreBoard(0);					// Initialize score board
		timer.start();
		lose = false;							// Reset the lose/win booleans
		win = false;
		timer.start();							// Start the game
		switchToScreen(somePane);				// Switch to the game screen
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
	
	// Adds points to the scoreboard
	void updateScoreBoard(int toAdd) {
		score += toAdd;
		scoreBoard.setLabel("SCORE: " + score);
	}
	
	// Main game loop
	public void actionPerformed(ActionEvent e) {
		player.update();									// These lines just call the update function of the player
		for(Ship enemy : enemies) {							// and all of the enemy ships and projectiles
			enemy.update();
		}
		for(Projectile proj : projectiles) {
			proj.update();
		}
		for(int i = projectiles.size() - 1;i >= 0;i--) {	// This for loop iterates backwards thru the projectiles arraylist to avoid exceptions
			if(projectiles.get(i).isDestroyed()) {			// If the projectile is destroyed
				projectiles.remove(i);						// Remove it from the arraylist
			}
		}
		wave.update();										// Update the wave
		if(score >= 1000) {									// If you get 1000 or more points, you win (for now)
			win = true;										// Set win to true so the game knows you won
		}
		if(win || lose) {									// If the game is over
			for(Ship enemy : enemies) {						// Remove all enemy sprites
				remove(enemy.getSprite());
				remove(enemy.getExplosion());
			}
			for(Projectile proj : projectiles) {			// Remove all projectile sprites
				remove(proj.getSprite());
			}
			remove(player.getSprite());						// Remove the playership sprite
			enemies.clear();								// Clear the enemies arraylist
			projectiles.clear();							// Clear the projectiles arraylist
			if(win) {										// If you won, print it at the menu screen and stop the game timer
				switchToMenu();
				afterMessage.setLabel("You win!");
				timer.stop();
			}
			if(lose) {										// If you lost, print it at the menu screen and stop the game timer
				switchToMenu();
				afterMessage.setLabel("You lose!");
				timer.stop();
			}
		}
	}
}
