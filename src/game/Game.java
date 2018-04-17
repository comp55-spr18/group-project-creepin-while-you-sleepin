package game;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.Toolkit;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import misc.AudioPlayer;
import misc.BetweenPane;
import misc.EndPane;
import misc.GamePane;
import misc.GraphicsApplication;
import misc.MenuPane;
import projectiles.Projectile;
import ships.PlayerShip;
import ships.Ship;
import misc.Object;

@SuppressWarnings("serial")
public class Game extends GraphicsApplication {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final int WINDOW_WIDTH = (int) screenSize.getWidth();
	public final int WINDOW_HEIGHT = (int) screenSize.getHeight();
	private GamePane gamePane;
	private MenuPane menu;
	private BetweenPane betweenPane;
	private EndPane endPane;
	
	// Variables for game loop
	public int lowShootCount;			// These variables are just telling the audio player which sound to play
	public int playerShootCount;
	public int shipDeathCount;
	public int enemyHitCount;
	public int playerHitCount;
	public int shieldHitCount;
	public int shieldRegenCount;
	public int fallCount;
	public int fps = 65;				// How many updates are called per second
	public boolean win = false;		// Notice that we have both win and lose booleans; default state is that both are false (the player hasn't won or lost but is playing)
	public boolean lose = false;		// this means we need to be explicit and can't assume that because win = false that the player lost
	public boolean easy = false;
	public boolean paused = false;
	public Random rgen = new Random();
	public AudioPlayer audio;
	public ArrayList<Ship> enemies = new ArrayList<Ship>();
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public ArrayList<PowerUp> powers = new ArrayList<PowerUp>();
	public ArrayList<Object> objects = new ArrayList<Object>();
	public PlayerShip player;
	public int score = 0;
	public GLabel scoreBoard = new GLabel("SCORE: " + score, 10, 25);
	public GLabel alreadyHave = new GLabel("You have maxed that upgrade");
	public ArrayList<GImage> healthBar;
	public Timer timer = new Timer(1000/fps, this);
	public Level level;
	public int currLevel;
	public int maxLevel = 3;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT - 100);
	}

	public void run() {
		gamePane = new GamePane(this);
		menu = new MenuPane(this);
		endPane = new EndPane(this);
		betweenPane = new BetweenPane(this);
		audio = AudioPlayer.getInstance();
		playRandomSound();						// The audio player needs time to "wake up" when it gets used the first time
		pause(2000);							// Give the audio player time to wake up
		switchToMenu();							// Then switch to the menu screen
	}

	public void switchToMenu() {
		switchToScreen(menu);
	}
	
	public void switchToBetween() {
		switchToScreen(betweenPane);
	}
	
	public void switchToGame() {
		switchToScreen(gamePane);
	}

	public void startGame() {
		paused = false;
		healthBar = new ArrayList<GImage>();
		player = new PlayerShip(this);			// Initiate the game with a new player ship
		level = new Level(this);
		score = 0;								// Reset score
		updateScoreBoard(0);					// Initialize score board
		currLevel = 1;
		lowShootCount = 0;
		playerShootCount = 0;
		shipDeathCount = 0;
		enemyHitCount = 0;
		shieldHitCount = 0;
		shieldRegenCount = 0;
		fallCount = 0;
		lose = false;							// Reset the lose/win booleans
		win = false;
		timer.start();							// Start the game
		switchToScreen(gamePane);				// Switch to the game screen
	}

	private void playRandomSound() {
		audio.playSound("sounds", "r2d2.mp3");
	}
	
	public int playSound(String sound, int count) {
		audio.playSound("sounds", sound + count + ".mp3");
		count++;
		if(sound == "lowshoot") {
			if(count == 20) {
				count = 0;
			}
		} else if(count == 5) {
			count = 0;
		}
		return count;
	}
	
	// Adds points to the scoreboard
	public void updateScoreBoard(int toAdd) {
		score += toAdd;
		scoreBoard.setLabel("SCORE: " + score);
	}

	// Main game loop
	public void actionPerformed(ActionEvent e) {
		if(!paused) {
			player.update();									// These lines just call the update function of the player
			for(int i = enemies.size() - 1;i >= 0;i--) {							// and all of the enemy ships and projectiles
				enemies.get(i).update();
			}
			for(PowerUp power : powers) {
				if(power.checkCollision()) {
					break;
				}
			}
			for(Projectile proj : projectiles) {
				proj.update();
			}
			for(int i = objects.size() - 1;i >= 0;i--) {
				objects.get(i).update();
			}
			for(int i = projectiles.size() - 1;i >= 0;i--) {	// This for loop iterates backwards thru the projectiles arraylist to avoid exceptions
				if(projectiles.get(i).isDestroyed()) {			// If the projectile is destroyed
					projectiles.remove(i);						// Remove it from the arraylist
				}
			}
			if(!level.isFinished()) {
				level.update();										// Update the level
			} else {
				player.setShooting(false);
				player.setShootingAlt(false);
				player.move();
				if(player.getSprite().getX() > WINDOW_WIDTH + 300) {
					if(currLevel == maxLevel) {
						win = true;
						switchToScreen(endPane);
						timer.stop();
						return;
					} else {
						currLevel++;
						level = new Level(this);
						player.setHealth(player.getMaxHealth());
						switchToScreen(betweenPane);
						timer.stop();
						return;
					}
				}
			}
			if(lose) {										// If you lost, print it at the menu screen and stop the game timer
				switchToScreen(endPane);
				timer.stop();
				return;
			}
		}
	}

	public Level getLevel() {
		return level;
	}
}
