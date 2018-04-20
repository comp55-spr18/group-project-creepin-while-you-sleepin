package game;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
import misc.LeaderBoardPane;

@SuppressWarnings("serial")
public class Game extends GraphicsApplication {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final int WINDOW_WIDTH = (int) screenSize.getWidth();
	public final int WINDOW_HEIGHT = (int) (9*WINDOW_WIDTH/16);
	private GamePane gamePane;
	private MenuPane menu;
	private BetweenPane betweenPane;
	private EndPane endPane;
	private LeaderBoardPane leaderboard;
	public boolean mute;
	public boolean musicMute;
	
	// Variables for game loop
	public int lowShootCount;			// These variables are just telling the audio player which sound to play
	public int playerShootCount;
	public int shipDeathCount;
	public int enemyHitCount;
	public int playerHitCount;
	public int shieldHitCount;
	public int shieldRegenCount;
	public int beamCount;
	public int fallCount;
	public int r2dCount;
	public int fps = 65;				// How many updates are called per second
	public boolean win = false;			// Notice that we have both win and lose booleans; default state is that both are false (the player hasn't won or lost but is playing)
	public boolean lose = false;		// this means we need to be explicit and can't assume that because win = false that the player lost
	public boolean easy = false;
	public boolean paused = false;		// Initialize the game as unpaused
	public Random rgen = new Random();
	public AudioPlayer audio;
	public ArrayList<Ship> enemies;				// Arraylist for enemies
	public ArrayList<Projectile> projectiles;	// Arraylist for projectiles
	public ArrayList<PowerUp> powers;			// Arraylist for powerups
	public ArrayList<Object> objects;			// Arraylist for non-enemy objects
	public PlayerShip player;					// The player object
	public int score;							// The player's score
	public GLabel scoreBoard = new GLabel("SCORE: " + score, 10, 25);		// The GLabel for the scoreboard
	public GLabel alreadyHave = new GLabel("You have maxed that upgrade");	// The "maxed out" GLabel if a stat can no longer be upgraded
	public ArrayList<GImage> healthBar;					// The healthbar
	public Timer timer = new Timer(1000/fps, this);		// The timer for the game
	public Level level;									// The level object the game uses
	public int currLevel;								// The current level
	public int maxLevel = 3;							// The maximum number of levels
	private File highScores = new File("scores.txt");
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT - WINDOW_HEIGHT/10);
	}

	public void run() {
		gamePane = new GamePane(this);
		menu = new MenuPane(this);
		endPane = new EndPane(this);
		betweenPane = new BetweenPane(this);
		try {
			leaderboard = new LeaderBoardPane(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mute = false;
		audio = AudioPlayer.getInstance();
		switchToMenu();							// Then switch to the menu screen
	}

	public void switchToMenu() {
		if(!musicMute) {
			audio.playSound("music", "menu.mp3", true);
		}
		switchToScreen(menu);
	}
	public void switchToLeaderBoard() {
		switchToScreen(leaderboard);
	}
	
	public void switchToBetween() {
		switchToScreen(betweenPane);
	}
	
	public void switchToGame() {
		if(!musicMute) {
			audio.stopSound("music", "menu.mp3");
			audio.playSound("music", "level" + currLevel + ".mp3", true);
		}
		switchToScreen(gamePane);
	}

	public void startGame() {					// This function initializes all relevant values to start a new game
		paused = false;
		healthBar = new ArrayList<GImage>();
		enemies = new ArrayList<Ship>();
		projectiles = new ArrayList<Projectile>();
		objects = new ArrayList<Object>();
		powers = new ArrayList<PowerUp>();
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
		r2dCount = 0;
		fallCount = 0;
		lose = false;							// Reset the lose/win booleans
		win = false;
		timer.start();							// Start the game
		switchToGame();				// Switch to the game screen
	}
	
	public int playSound(String sound, int count) {			// This sound takes a sound file, plays the file with the "count" number at the end, then returns the next number
		if(!mute) {
			audio.playSound("sounds", sound + count + ".mp3");	// This function was created so we can have the same sound being played multiple times in our game
			count++;
			if(sound == "lowshoot") {	// Lowshoot has 25 variations
				if(count == 25) {
					count = 0;
				}
			} else if(sound == "playershoot") {   // Playershoot has 8 variations
				if(count == 8) {
					count = 0;
				}
			} else if(count == 5) {		// Every other sound has 5 variations
				count = 0;
			}
		}
		return count;				// Return the count so it can be stored in its respective variable
	}
	
	// Adds points to the scoreboard
	public void updateScoreBoard(int toAdd) {
		score += toAdd;
		scoreBoard.setLabel("SCORE: " + score);
	}
	public void saveToLeaderBoard() throws IOException{
		if(lose) {
			FileOutputStream newScore = new FileOutputStream(highScores,true);
			try (Writer write = new BufferedWriter(new OutputStreamWriter(newScore, "utf-8"))) {
				write.write(String.valueOf((score+"\n")));
				write.close();
				
			}
		}
	}
	

	// Main game loop
	public void actionPerformed(ActionEvent e) {
		if(!paused) {											// If the game is not paused
			player.update();									// Update the player
			player.getTrail().update();							// Update the player trail
			for(int i = enemies.size() - 1;i >= 0;i--) {		// Update all enemies and their trails (if they have one)
				if(enemies.get(i).getTrail() != null) {
					enemies.get(i).getTrail().update();
				}
				enemies.get(i).update();
			}
			for(PowerUp power : powers) {						// Check collision on all powerups (if there are any)
				if(power.checkCollision()) {
					break;
				}
			}
			for(int i = projectiles.size() - 1;i >= 0;i--) {	// Update all projectiles
				projectiles.get(i).update();
			}
			for(int i = objects.size() - 1;i >= 0;i--) {		// Update all objects
				objects.get(i).update();
			}
			if(!level.isFinished()) {							// If the level is not finished
				level.update();									// Update the level
			} else {											// If the level is finished
				player.setShooting(false);						// Prevent the player from shooting
				player.setShootingAlt(false);
				player.move();									// Call the player's move() function (they fly to the right)
				if(player.getSprite().getX() > WINDOW_WIDTH + 300) {	// Once they exit the screen to the right
					audio.stopSound("music", "level" + currLevel + ".mp3");		// Stop the music for the level
					if(currLevel == maxLevel) {					// If this was the last level
						win = true;								// Set win to true
						switchToScreen(endPane);				// Switch to endPane for the win screen
						timer.stop();							// Stop the game timer
						return;									// Exit
					} else {									// If this was not the last level
						currLevel++;							// Increment the current level
						level = new Level(this);				// Create a new level object
						player.setHealth(player.getMaxHealth());	// Heal the player to their maximum health
						switchToScreen(betweenPane);			// Switch to betweenPane
						timer.stop();							// Stop the game timer
						return;									// Exit
					}
				}
			}
			if(lose) {											// If lose = true (which happens when PlayerShip is destroyed)
				audio.stopSound("music", "level" + currLevel + ".mp3");
				switchToScreen(endPane);						// Switch to the endPane for the lose screen
				timer.stop();	
				System.out.println(score);// Stop the game timer
				try {
					saveToLeaderBoard();
				}catch(IOException e1) {
					e1.printStackTrace();
				}
				return;											// Exit
			}
		}
	}

	public Level getLevel() {
		return level;
	}
}
