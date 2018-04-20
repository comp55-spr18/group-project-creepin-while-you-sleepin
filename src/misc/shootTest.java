package misc;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import game.Game;
import ships.*;

public class shootTest extends GraphicsPane {
	private GButton previous;
	private GButton next;
	private GButton enemyFire;
	private GButton returnToMenu;
	private GImage spaceBackground;
	public ArrayList<Ship> ships;
	public ArrayList<GLabel> glabels;
	private int selected;
	public GLabel asteroid = new GLabel("Asteroids: A field of asteroids that will damage anything it touches.");
	public GLabel basicEnemy = new GLabel("Basic Enemy: Pretty self explanatory.");
	public GLabel boomerang = new GLabel("Boomerang: Enemy that moves in a boomerang like arc.");
	public GLabel boss = new GLabel("???: ?????????");
	public GLabel bouncer = new GLabel("Bouncer: Enemy type that pinballs on the vertical axis of the screen.");
	public GLabel drone = new GLabel("Drone: A drone that hovers on top attacking the player.");
	public GLabel heavyWeightEnemy = new GLabel("Heavy Weight Enemy: Enemy that is massive and does extra damage then normal.");
	public GLabel homingEnemy = new GLabel("Homing Enemy: Enemy that homes onto player.");
	public GLabel kamikaze = new GLabel("Kamikaze: Enemy type that will home onto player till destruction.");
	public GLabel sawedOff = new GLabel("Sawed-Off: Enemy type that has an attack spread that spirals like a saw.");
	public GLabel seeker = new GLabel("Seeker: Enemy that tries to home onto the player.");
	public GLabel simpleEnemy = new GLabel("Simple Enemy: Generic enemy type.");
	public GLabel sprayBall = new GLabel("Sprayball: An enemy with a constant attack spray.");
	public GLabel squeeze = new GLabel("Squeeze: An enemy type that attempts to corner the player.");
	public GLabel swarmBot = new GLabel("Swarmbots: A DNA shaped sequence of enemies that attacks the player.");
	public GLabel tank = new GLabel("Tank: Bigger enemy that has a large amount of health and damaging spread.");
	public GLabel trishot = new GLabel("Trishot: Enemy that has a bullet with a triple spread.");
	
	public shootTest(Game app) {
		super();
		program = app;
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
	}
	
	public void hideContents() {
		program.add(previous);
		program.add(next);
		program.add(enemyFire);
		program.add(returnToMenu);
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
		//ships.add(new SprayBall(program, 0, ??));
		//ships.add(new Tank(program, 0, ??));
		//ships.add(new SwarmBot(program, 0, ???));
		for(int i = 0;i < ships.size();i++) {
			ships.get(i).getSprite().setLocation(program.WINDOW_WIDTH/2, program.WINDOW_HEIGHT/2);
			program.remove(ships.get(i).getSprite());
		}
	}
	
	//public void setGlabels() {
		
		
	//}
	
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
			selected--;
			if (selected < 0) {
				//System.out.println("You are at the beginning of the ships list!");
			}
		}
		if (obj == next) {
			selected++;
			if (selected > 15) {
				//System.out.println("You are at the end of the ships list!");
			}
		}
		if (obj == enemyFire) {
			enemyBlaster();
		}
	}
	
}