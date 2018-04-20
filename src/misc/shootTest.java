package misc;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import game.Game;
import ships.*;

public class shootTest extends GraphicsPane implements ActionListener {
	private GButton previous;
	private GButton next;
	private GButton enemyFire;
	private GButton returnToMenu;
	private GImage spaceBackground;
	public ArrayList<Ship> ships;
	public ArrayList<GLabel> descriptions;
	private int selected;
	public Timer timer;
	public GLabel asteroid;
	public GLabel basicEnemy;
	public GLabel boomerang;
	public GLabel boss;
	public GLabel bouncer;
	public GLabel drone;
	public GLabel heavyWeightEnemy;
	public GLabel homingEnemy;
	public GLabel kamikaze;
	public GLabel sawedOff;
	public GLabel seeker;
	public GLabel simpleEnemy;
	public GLabel sprayBall;
	public GLabel squeeze;
	public GLabel swarmBot;
	public GLabel tank;
	public GLabel trishot;
	
	public shootTest(Game app) {
		super();
		ships = new ArrayList<Ship>();
		descriptions = new ArrayList<GLabel>();
		program = app;
		timer = new Timer(1000/program.fps, this);		// The timer for the game
		setShips();
		setGlabels();
		double scaleX = program.WINDOW_WIDTH/(1920/100.0);
		double scaleY = program.WINDOW_HEIGHT/(1080/100.0);
		previous = new GButton("PREV", 4*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		next = new GButton("NEXT", 13*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		enemyFire = new GButton("SHOOT", 8.5*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		returnToMenu = new GButton("EXIT", .5*scaleX, .5*scaleY, scaleX, scaleY);
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
	}
	
	public void hideContents() {
		program.remove(previous);
		program.remove(next);
		program.remove(enemyFire);
		program.remove(returnToMenu);
		program.remove(spaceBackground);
	}
	
	public void setShips() {  //sets the ships into the array list
		ships.add(new Asteroid(program,0));
		ships.add(new BasicEnemy(program, 0));
		ships.add(new Boomerang(program, 0));
		ships.add(new Boss(program,0));
		ships.add(new Bouncer(program,0));
		ships.add(new Drone(program, 0));
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
		ships.add(new SwarmBot(program, 0, 0));
		for(int i = 0;i < ships.size();i++) {
			ships.get(i).getSprite().setLocation(program.WINDOW_WIDTH/2 - ships.get(i).getSprite().getWidth()/2, program.WINDOW_HEIGHT/4 - ships.get(i).getSprite().getHeight()/2);
			program.remove(ships.get(i).getSprite());
		}
	}
	
	public void setGlabels() {
		descriptions.add(asteroid = new GLabel("Asteroids: A field of asteroids that will damage anything it touches."));
		descriptions.add(basicEnemy = new GLabel("Basic Enemy: Pretty self explanatory."));
		descriptions.add(boomerang = new GLabel("Boomerang: Enemy that moves in a boomerang like arc."));
		descriptions.add(boss = new GLabel("???: ?????????"));
		descriptions.add(bouncer = new GLabel("Bouncer: Enemy type that pinballs on the vertical axis of the screen."));
		descriptions.add(drone = new GLabel("Drone: A drone that hovers on top attacking the player."));
		descriptions.add(heavyWeightEnemy = new GLabel("Heavy Weight Enemy: Enemy that is massive and does extra damage then normal."));
		descriptions.add(homingEnemy = new GLabel("Homing Enemy: Enemy that homes onto player."));
		descriptions.add(kamikaze = new GLabel("Kamikaze: Enemy type that will home onto player till destruction."));
		descriptions.add(sawedOff = new GLabel("Sawed-Off: Enemy type that has an attack spread that spirals like a saw."));
		descriptions.add(seeker = new GLabel("Seeker: Enemy that tries to home onto the player."));
		descriptions.add(simpleEnemy = new GLabel("Simple Enemy: Generic enemy type."));
		descriptions.add(sprayBall = new GLabel("Sprayball: An enemy with a constant attack spray."));
		descriptions.add(squeeze = new GLabel("Squeeze: An enemy type that attempts to corner the player."));
		descriptions.add(swarmBot = new GLabel("Swarmbots: A DNA shaped sequence of enemies that attacks the player."));
		descriptions.add(tank = new GLabel("Tank: Bigger enemy that has a large amount of health and damaging spread."));
		descriptions.add(trishot = new GLabel("Trishot: Enemy that has a bullet with a triple spread."));
	}

	public void enemyBlaster(){  //has enemy fire when shoot button is pressed
		ships.get(selected).shoot();
	}
	
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == returnToMenu) {
			hideContents();
			program.switchToMenu();
		}
		if (obj == previous) {
			program.remove(ships.get(selected).getSprite());
			program.remove(descriptions.get(selected));
			selected--;
			if (selected < 0) {
				selected = 15;
			}
			program.add(ships.get(selected).getSprite());
			program.add(descriptions.get(selected));
		}
		if (obj == next) {
			program.remove(ships.get(selected).getSprite());
			program.remove(descriptions.get(selected));
			selected++;
			if (selected > 15) {
				selected = 0;
			}
			program.add(ships.get(selected).getSprite());
			program.add(descriptions.get(selected));
		}
		if (obj == enemyFire) {
			enemyBlaster();
		}
	}
	
}