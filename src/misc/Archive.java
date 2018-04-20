package misc;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import game.Game;
import ships.*;

public class Archive extends GraphicsPane implements ActionListener {
	private GButton previous;
	private GButton next;
	private GButton enemyFire;
	private GButton returnToMenu;
	private GImage spaceBackground;
	public ArrayList<Ship> ships;
	public ArrayList<GButton> descriptions;
	private int selected;
	public Timer timer;
	private double boxSizeX;
	private double boxSizeY;
	
	public Archive(Game app) {
		super();
		ships = new ArrayList<Ship>();
		descriptions = new ArrayList<GButton>();
		program = app;
		timer = new Timer(1000/program.fps, this);		// The timer for the game
		double scaleX = program.WINDOW_WIDTH/(1920/100.0);
		double scaleY = program.WINDOW_HEIGHT/(1080/100.0);
		boxSizeX = 15*scaleX;
		boxSizeY = scaleY;
		setShips();
		setGlabels();
		previous = new GButton("PREV", 4*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		next = new GButton("NEXT", 13*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		enemyFire = new GButton("SHOOT", 8.5*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		returnToMenu = new GButton("MENU", .5*scaleX, 6*scaleY, scaleX, scaleY);
		previous.setFillColor(Color.LIGHT_GRAY);
		next.setFillColor(Color.LIGHT_GRAY);
		enemyFire.setFillColor(Color.RED);
		returnToMenu.setFillColor(Color.WHITE);
		spaceBackground = new GImage("starry_sky_milky_way_stars_glitter_space_118653_1920x1080.jpg");
		spaceBackground.setSize(program.getWidth(), program.getHeight());
		selected = 0;
	}
	
	public void showContents() {
		program.add(spaceBackground);
		program.add(previous);
		program.add(next);
		program.add(enemyFire);
		program.add(returnToMenu);
		program.add(descriptions.get(selected));
		program.add(ships.get(selected).getSprite());
		timer.start();
	}
	
	public void hideContents() {
		program.remove(previous);
		program.remove(next);
		program.remove(enemyFire);
		program.remove(returnToMenu);
		program.remove(spaceBackground);
		program.remove(ships.get(selected).getSprite());
		program.remove(descriptions.get(selected));
		for(int i = program.projectiles.size() - 1;i >= 0;i--) {	// Update all projectiles
			program.remove(program.projectiles.get(i).getSprite());
		}
		timer.stop();
	}
	
	public void setShips() {  //sets the ships into the array list
		ships.add(new Asteroid(program,0));
		ships.add(new BasicEnemy(program, 0));
		ships.add(new Boomerang(program, 0));
		ships.add(new Bouncer(program,0));
		ships.add(new Drone(program, 0, 0));
		ships.add(new HeavyWeightEnemy(program, 0));
		ships.add(new HomingEnemy(program, 0));
		ships.add(new Kamikaze(program, 0));
		ships.add(new SawedOff(program, 0));
		ships.add(new Seeker(program, 0));
		ships.add(new SimpleEnemy(program, 0));
		ships.add(new Squeeze(program, 0));
		ships.add(new Trishot(program, 0)); 
		ships.add(new SprayBall(program, 0, 0));
		ships.add(new Tank(program, 0));
		ships.add(new SwarmCaller(program, 0));
		ships.add(new SwarmBot(program, 0, 0));
		ships.add(new Boss(program,0));
		for(int i = 0;i < ships.size();i++) {
			ships.get(i).getSprite().setLocation(program.WINDOW_WIDTH/2 - ships.get(i).getSprite().getWidth()/2, program.WINDOW_HEIGHT/4 - ships.get(i).getSprite().getHeight()/2);
			program.remove(ships.get(i).getSprite());
			ships.get(i).move();
			ships.get(i).getSprite().setLocation(program.WINDOW_WIDTH/2 - ships.get(i).getSprite().getWidth()/2, program.WINDOW_HEIGHT/4 - ships.get(i).getSprite().getHeight()/2);
		}
	}
	
	public void setGlabels() {
		descriptions.add(new GButton("Asteroids: Asteroid field that will damage anything it touches. (NO FIRE)", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Basic Enemy: Moves up and down and its shot tracks the player.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Boomerang: Enemy that shoot bullets which reverse direction.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Bouncer: Enemy type that pinballs on the vertical axis of the screen.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Drone: Follows a curved path on and off the screen. Fires tracking bullets.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Heavy Weight Enemy: Enemy that shoots a massive bullet at the player.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Homing Enemy: Enemy that fires homing bullets.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Kamikaze: Enemy type that will suicidally collide with the player. (NO FIRE)", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Sawed-Off: Enemy type that has more bullets after it is damaged.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Seeker: Enemy that periodically flies towards the player's location.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Simple Enemy: Moves only from right to left. Fires a tracking bullet.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Squeeze: An enemy type that restricts player movement through lasers.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Trishot: Enemy that has a bullet with a triple spread. Hovers at the edge of the screen.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Sprayball: An enemy that fires a spiraling barrage of bullets.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Tank: Bigger enemy that has a large amount of health but can't shoot. (NO FIRE)", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Swarmcaller: An enemy that spawns Swarmbots continuously until it dies.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("Swarmbot: A DNA shaped sequence of enemies that attacks the player.", 0, 0, boxSizeX, boxSizeY));
		descriptions.add(new GButton("???: ?????????", 0, 0, boxSizeX, boxSizeY));
		for(int i = 0;i < descriptions.size();i++) {
			descriptions.get(i).setColor(Color.WHITE);
			descriptions.get(i).setFillColor(Color.BLACK);
			descriptions.get(i).setLocation(program.WINDOW_WIDTH/2 - descriptions.get(i).getWidth()/2, 3*program.WINDOW_HEIGHT/4);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == returnToMenu) {
			program.switchToMenu();
		}
		if (obj == previous) {
			program.remove(ships.get(selected).getSprite());
			program.remove(descriptions.get(selected));
			selected--;
			if (selected < 0) {
			selected = ships.size() - 1;
			}
			program.add(ships.get(selected).getSprite());
			program.add(descriptions.get(selected));
			for(int i = program.projectiles.size() - 1;i >= 0;i--) {
				program.remove(program.projectiles.get(i).getSprite());
			}
			program.projectiles.clear();
		}
		if (obj == next) {
			program.remove(ships.get(selected).getSprite());
			program.remove(descriptions.get(selected));
			selected++;
			if (selected > ships.size() - 1) {
			selected = 0;
			}
			program.add(ships.get(selected).getSprite());
			program.add(descriptions.get(selected));
			for(int i = program.projectiles.size() - 1;i >= 0;i--) {
				program.remove(program.projectiles.get(i).getSprite());
			}
			program.projectiles.clear();
		}
		if (obj == enemyFire) {
			ships.get(selected).setCanShoot(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!(ships.get(selected) instanceof Boss)) {
			ships.get(selected).shoot();
		}
		for(int i = program.projectiles.size() - 1;i >= 0;i--) {	// Update all projectiles
			program.projectiles.get(i).update();
		}
	}
}