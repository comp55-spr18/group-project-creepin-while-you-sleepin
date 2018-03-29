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
	boolean win = false;
	boolean lose = false;
	Random rgen = new Random();
	ArrayList<Ship> enemies = new ArrayList<Ship>();
	PlayerShip player;
	int score = 0;
	GLabel afterMessage = new GLabel("", 10, 25);
	GLabel scoreBoard = new GLabel("SCORE: " + score, 10, 25);
	GLabel healthBoard = new GLabel("", 10, 50);
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
//		playRandomSound();
		enemies.clear();
		count++;
		switchToScreen(menu);
	}

	public void switchToSome() {
//		playRandomSound();
		player = new PlayerShip(this);
		score = 0;
		updateHealthBoard();
		updateScoreBoard(0);
		player.getTimer().start();
		player.getTrail().getTimer().start();
		runGame = true;
		lose = false;
		win = false;
		timer.start();
		switchToScreen(somePane);
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
	
	void updateHealthBoard() {
		String health = "";
		for(int i = 0;i < player.getHealth();i++) {
			health += "X";
		}
		healthBoard.setLabel(health);
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
			if(score == 1000) {
				runGame = false;
				win = true;
			}
		} else if(win) {
			switchToMenu();
			afterMessage.setLabel("You win!");
		} else if(lose) {
			switchToMenu();
			afterMessage.setLabel("You lose!");
		}
	}
}
