import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.Toolkit;

import acm.graphics.GImage;
import acm.graphics.GLabel;

@SuppressWarnings("serial")
public class MainApplication extends GraphicsApplication {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final int WINDOW_WIDTH = (int) screenSize.getWidth();
	public final int WINDOW_HEIGHT = (int) screenSize.getHeight();
	public GamePane gamePane;
	private MenuPane menu;
	private BetweenPane betweenPane;
	private EndPane endPane;
	
	// Variables for game loop
	public int lowShootCount;
	public int playerShootCount;
	public int shipDeathCount;
	public int enemyHitCount;
	public int playerHitCount;
	public int shieldHitCount;
	public int shieldRegenCount;
	int fps = 65;
	boolean win = false;		// Notice that we have both win and lose booleans; default state is that both are false (the player hasn't won or lost but is playing)
	boolean lose = false;		// this means we need to be explicit and can't assume that because win = false that the player lost
	boolean easy = false;
	boolean playerControl = true;
	Random rgen = new Random();
	AudioPlayer audio;
	ArrayList<Ship> enemies = new ArrayList<Ship>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<PowerUp> powers = new ArrayList<PowerUp>();
	PlayerShip player;
	int score = 0;
	GLabel scoreBoard = new GLabel("SCORE: " + score, 10, 25);
	GLabel alreadyHave = new GLabel("You currently have that upgrade, pick another");
	ArrayList<GImage> healthBar = new ArrayList<GImage>();
	boolean isShooting = false;
	Timer timer = new Timer(1000/fps, this);
	Wave wave;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT - 100);
	}

	public void run() {
		gamePane = new GamePane(this);
		menu = new MenuPane(this);
		endPane = new EndPane(this);
		betweenPane = new BetweenPane(this);
		audio = AudioPlayer.getInstance();
		playRandomSound();					// The audio player needs time to "wake up" when it gets used the first time
		pause(2000);						// Give the audio player time to wake up
		switchToMenu();						// Then switch to the menu screen
	}

	public void switchToMenu() {
		switchToScreen(menu);
	}

	public void switchToGame() {
		player = new PlayerShip(this);			// Initiate the game with a new player ship
		wave = new Wave(this);
		score = 0;								// Reset score
		updateScoreBoard(0);					// Initialize score board
		playerControl = true;
		lowShootCount = 0;
		playerShootCount = 0;
		shipDeathCount = 0;
		enemyHitCount = 0;
		shieldHitCount = 0;
		shieldRegenCount = 0;
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
	void updateScoreBoard(int toAdd) {
		score += toAdd;
		scoreBoard.setLabel("SCORE: " + score);
	}
	
	// Main game loop
	public void actionPerformed(ActionEvent e) {
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
		for(int i = projectiles.size() - 1;i >= 0;i--) {	// This for loop iterates backwards thru the projectiles arraylist to avoid exceptions
			if(projectiles.get(i).isDestroyed()) {			// If the projectile is destroyed
				projectiles.remove(i);						// Remove it from the arraylist
			}
		}
		if(playerControl) {
			wave.update();										// Update the wave
		} else {
			player.getSprite().move(10, 0);
			if(player.isShielded()) {
				player.getShield().move(10, 0);
			}
			if(player.getSprite().getX() > WINDOW_WIDTH + 300) {
				if(win) {										// If you won, print it at the menu screen and stop the game timer
					switchToScreen(endPane);
					timer.stop();
					return;
				}
				switchToScreen(betweenPane);
			}
			isShooting = false;
		}
		if(lose) {										// If you lost, print it at the menu screen and stop the game timer
			switchToScreen(endPane);
			timer.stop();
		}
	}
}
