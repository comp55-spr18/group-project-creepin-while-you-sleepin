import java.util.ArrayList;
import java.util.Random;

import acm.graphics.GLabel;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

	private SomePane somePane;
	private MenuPane menu;
	private int count;
	// Variables for game loop
	Random rgen = new Random();
	ArrayList<Projectile> bullets = new ArrayList<Projectile>();
	ArrayList<Ship> enemies = new ArrayList<Ship>();
	ArrayList<Projectile> trail = new ArrayList<Projectile>();
	PlayerShip player = new PlayerShip();
	int score = 0;
	GLabel scoreBoard = new GLabel("SCORE: " + score, 10, 25);
	boolean isShooting = false;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		System.out.println("Hello, world!");
		somePane = new SomePane(this);
		menu = new MenuPane(this);
		switchToMenu();
		int globalTimer = 0;
		while(true) {
			globalTimer++;
			pause(1);
			// If the player has shot, increment the cooldown
			if(!player.canShoot()) {
				player.setCooldown(player.getCooldown() + 1);
			}
			// If the cooldown matches the maxCooldown, reset cooldown and let the player be able to shoot again
			if(player.getCooldown() == player.getMaxCooldown()) {
				player.setCanShoot(true);
				player.setCooldown(0);
			}
			if(isShooting && player.canShoot()) {
				player.setCanShoot(false);
				player.shoot();
			}
		}
	}

	public void switchToMenu() {
		playRandomSound();
		count++;
		switchToScreen(menu);
	}

	public void switchToSome() {
		playRandomSound();
		switchToScreen(somePane);
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
}
