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
	long startTime;
	long URDTimeMillis;
	long waitTime;
	long totalTime;
	
	int frameCount = 0;
	int maxFrameCount = 60;
	long targetTime = 1000 / maxFrameCount;
	double avgFPS;
	boolean runGame = false;
	Random rgen = new Random();
	ArrayList<Projectile> bullets = new ArrayList<Projectile>();
	ArrayList<Ship> enemies = new ArrayList<Ship>();
	PlayerShip player = new PlayerShip(this);
	int score = 0;
	GLabel scoreBoard = new GLabel("SCORE: " + score, 10, 25);
	GLabel framerate = new GLabel("FPS: " + avgFPS, 10, 50);
	boolean isShooting = false;
	Timer trailTimer = new Timer(1000/240, this);

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
			if(runGame) {
				trailTimer.start();
				startTime = System.nanoTime();
				globalTimer++;
				// If the player has shot, increment the cooldown
				if(!player.canShoot()) {
					player.setCooldown(player.getCooldown() + 1);
				}
				// If the cooldown matches the maxCooldown, reset cooldown and let the player be able to shoot again
				if(player.getCooldown() == player.getMaxCooldown()) {
					player.setCanShoot(true);
					player.setCooldown(0);
				}
				// If the player is clicking, and can shoot, call the player's shoot function
				if(isShooting && player.canShoot()) {
					player.setCanShoot(false);
					player.shoot();
				}
				// If the player is invincible, increment their invincibility timer
				if(player.isInvincible()) {
					if(player.getIframe() == 0) {
						player.getSprite().setImage("truck.png");
						player.getSprite().setSize(50, 50);
					}
					player.setIframe(player.getIframe() + 1);
				}
				// If the player's iframe count hits 100, make them vulnerable again
				if(player.getIframe() == 100) {
					player.getSprite().setImage("auto.png");
					player.getSprite().setSize(50, 50);
					player.setInvincible(false);
					player.setIframe(0);
				}
				checkCollision();
				moveBullets();
				moveEnemies();
				shootEnemies();
				globalTimer++;
				if(globalTimer % 500 == 0) {
					TestEnemy addEnemy = new TestEnemy(this);
					addEnemy.setLocation(new GPoint(WINDOW_WIDTH, WINDOW_HEIGHT/2));
					addEnemy.getSprite().setLocation(WINDOW_WIDTH, WINDOW_HEIGHT/2);
					add(addEnemy.getSprite());
					enemies.add(addEnemy);
				}				
				// Framerate stuff
				URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
				waitTime = targetTime - URDTimeMillis;
				if(waitTime <= 0) {
					waitTime = 1;
				}
				pause(waitTime);
				totalTime += System.nanoTime() - startTime;
				frameCount++;
				if(frameCount == maxFrameCount) {
					avgFPS = 1000.0/((totalTime / frameCount) / 1000000);
					frameCount = 0;
					totalTime = 0;
					framerate.setLabel("FPS: " + avgFPS);
				}
			} else {
				pause(5);
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
		runGame = true;
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
	
	// This function moves all bullets, and then checks to see if any need to be removed because they are off-screen
	void moveBullets() {
		for(Projectile bullet : bullets) {
			bullet.move();
		}
		for(Projectile bullet : bullets) {
			if(bullet.getLocation().getX() > WINDOW_WIDTH || bullet.getLocation().getX() < -50) {
				remove(bullet.getSprite());
				bullets.remove(bullet);
				break;
			}
		}
	}
	
	// This function moves all enemy ships, then removes them if they go too far off-screen
	void moveEnemies() {
		for(Ship ship : enemies) {
			ship.move();
		}
		for(Ship ship : enemies) {
			if(ship.getLocation().getX() <= -100) {
				remove(ship.getSprite());
				enemies.remove(ship);
				break;
			}
		}
	}
	
	void shootEnemies() {
		for(Ship ship : enemies) {
			if(ship.canShoot()) {
				ship.shoot();
				ship.setCanShoot(false);
			} else {
				ship.setCooldown(ship.getCooldown() + 1);
				if(ship.getCooldown() == ship.getMaxCooldown()) {
					ship.setCooldown(0);
					ship.setCanShoot(true);
				}
			}
		}
	}
	
	// Adds points to the scoreboard
	void updateScoreBoard(int toAdd) {
		score += toAdd;
		scoreBoard.setLabel("SCORE: " + score);
	}
	
	// Checks to see if any bullets have collided with the collision points on each ship
	void checkCollision() {
		Iterator<Projectile> bulletIter = bullets.iterator();
		while(bulletIter.hasNext()) {
			Projectile bullet = bulletIter.next();
			Iterator<Ship> shipIter = enemies.iterator();
			while(shipIter.hasNext()) {
				Ship ship = shipIter.next();
				// Check for collision player bullet -> enemies
				if(bullet.isPlayerProjectile()) {
					GRectangle hitpath = bullet.getSprite().getBounds();
					hitpath.setSize(hitpath.getWidth() + bullet.getSpeed(), hitpath.getHeight());
					for(GPoint point : ship.getShipPoints()) {
						if(hitpath.contains(point)) {
							remove(bullet.getSprite());
							bulletIter.remove();
							ship.setHealth(ship.getHealth() - 1);
							if(ship.getHealth() <= 0) {
								remove(ship.getSprite());
								shipIter.remove();
							}
							updateScoreBoard(100);
							break;
						}
					}
				}
				// Check for collision player ship -> enemies
				for(GPoint point : player.getShipPoints()) {
					if(ship.getSprite().contains(point) && !player.isInvincible()) {
						player.setHealth(player.getHealth() - 1);
						if(player.getHealth() <= 0) {
							runGame = false;
						}
						player.setInvincible(true);
						break;
					}
				}
			}
			// Check for collision enemy bullets -> player
			if(!bullet.isPlayerProjectile()) {
				for(GPoint point : player.getShipPoints()) {
					if(bullet.getSprite().contains(point) && !player.isInvincible()) {
						remove(bullet.getSprite());
						bulletIter.remove();
						player.setHealth(player.getHealth() - 1);
						if(player.getHealth() <= 0) {
							runGame = false;
						}
						player.setInvincible(true);
						break;
					}
				}
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		player.fireTrail();
	}
}
