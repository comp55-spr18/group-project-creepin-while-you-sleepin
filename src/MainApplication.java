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
	boolean runGame = false;
	Random rgen = new Random();
	ArrayList<Ship> enemies = new ArrayList<Ship>();
	PlayerShip player = new PlayerShip(this);
	int score = 0;
	GLabel scoreBoard = new GLabel("SCORE: " + score, 10, 25);
	boolean isShooting = false;
	int globalTimer = 0;
	Timer timer = new Timer(1000/fps, this);

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		System.out.println("Hello, world!");
		somePane = new SomePane(this);
		menu = new MenuPane(this);
		switchToMenu();
	}

	public void switchToMenu() {
		playRandomSound();
		count++;
		switchToScreen(menu);
	}

	public void switchToSome() {
		playRandomSound();
		switchToScreen(somePane);
		player.getTimer().start();
		player.getTrail().getTimer().start();
		runGame = true;
		timer.start();
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
	
	public void actionPerformed(ActionEvent e) {
		if(runGame) {
			globalTimer++;
			if(globalTimer % 200 == 0) {
				TestEnemy addEnemy = new TestEnemy(this);
				addEnemy.setLocation(new GPoint(WINDOW_WIDTH, WINDOW_HEIGHT/2));
				addEnemy.getSprite().setLocation(WINDOW_WIDTH, WINDOW_HEIGHT/2);
				add(addEnemy.getSprite());
				enemies.add(addEnemy);
				addEnemy.getTimer().start();
			}
		}
	}
}
