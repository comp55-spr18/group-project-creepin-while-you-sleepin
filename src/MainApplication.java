import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 1920;
	public static final int WINDOW_HEIGHT = 1080;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

	private SomePane somePane;
	private MenuPane menu;
	private int count;
	// Variables for game loop
	int fps = 60;
	boolean win = false;		// Notice that we have both win and lose booleans; default state is that both are false (the player hasn't won or lost but is playing)
	boolean lose = false;		// this means we need to be explicit and can't assume that because win = false that the player lost
	Random rgen = new Random();
	ArrayList<Ship> enemies = new ArrayList<Ship>();
	PlayerShip player;
	int score = 0;
	GLabel afterMessage = new GLabel("", 10, 25);
	GLabel scoreBoard = new GLabel("SCORE: " + score, 10, 25);
	GLabel healthBoard = new GLabel("", 10, 50);
	boolean isShooting = false;
	int globalCounter = 0;
	Timer timer = new Timer(1000/fps, this);

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
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
		globalCounter = 0;						// Reset the global counter
		score = 0;								// Reset score
		updateHealthBoard();					// Initialize health bar
		updateScoreBoard(0);					// Initialize score board
		player.getTimer().start();				// Start the player timer
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
	
	// Update the health bar
	void updateHealthBoard() {
		String health = "";
		for(int i = 0;i < player.getHealth();i++) {
			health += "X";
		}
		healthBoard.setLabel(health);
	}
	
	// Main game loop
	public void actionPerformed(ActionEvent e) {
		globalCounter++;
		if(globalCounter % 200 == 0) {
			TestEnemy addEnemy = new TestEnemy(this);
			addEnemy.setLocation(new GPoint(WINDOW_WIDTH, WINDOW_HEIGHT/2));
			addEnemy.getSprite().setLocation(WINDOW_WIDTH, WINDOW_HEIGHT/2);
			add(addEnemy.getSprite());
			enemies.add(addEnemy);
			addEnemy.getTimer().start();
		}
		if(score >= 1000) {	// If you get 1000 or more points, you win (for now)
			win = true;		// Set win to true so the game knows you won
		}
		if(win) {			// If you won, print it at the menu screen
			switchToMenu();
			afterMessage.setLabel("You win!");
			timer.stop();
		}
		if(lose) {			// If you lost, print it at the menu screen
			switchToMenu();
			afterMessage.setLabel("You lose!");
			timer.stop();
		}
	}
}
