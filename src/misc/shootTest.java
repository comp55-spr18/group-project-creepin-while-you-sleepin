package misc;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
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
	private int selected;
	
	public shootTest(Game app) {
		super();
		program = app;
		double scaleX = program.WINDOW_WIDTH/(1920/100.0);
		double scaleY = program.WINDOW_HEIGHT/(1080/100.0);
		previous = new GButton("PREV", 4*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		next = new GButton("NEXT", 13*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		enemyFire = new GButton("SHOOT", 8.5*scaleX, 6*scaleY, 2*scaleX, 1*scaleY);
		returnToMenu = new GButton("EXIT", .5*scaleX, .5*scaleY, scaleX, scaleY);
		previous.setFillColor(Color.LIGHT_GRAY);
		next.setFillColor(Color.LIGHT_GRAY);
		enemyFire.setFillColor(Color.RED);
		returnToMenu.setFillColor(Color.WHITE);
		spaceBackground = new GImage("levels/betweenbackground.jpg");
		spaceBackground.setSize(program.getWidth(), program.getHeight());
		selected = 0;
	}
	public void showContents() {
		program.add(previous);
		program.add(next);
		program.add(enemyFire);
		program.add(returnToMenu);
		program.add(spaceBackground);
		
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
		ships.add(new Trishot(program, 0));  //Tank, swarm, and spray are not called because they do not fire.
		for(int i = 0;i < ships.size();i++) {
			ships.get(i).getSprite().setLocation(program.WINDOW_WIDTH/2, program.WINDOW_HEIGHT/2);
			program.remove(ships.get(i).getSprite());
		}
	}
	
	public void enemyBlaster(){  //has enemy fire when shoot button is pressed
		ships.get(selected).shoot();
	}
	
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == returnToMenu) {
			program.switchToMenu();
		}
		if (obj == previous) {
			selected--;
			if (selected == 0) {
				System.out.println("You are at the beginning of the ships list!");
			}
		}
		if (obj == next) {
			selected++;
			if (selected > 13) {
				System.out.println("You are at the end of the ships list!");
			}
		}
		if (obj == enemyFire) {
			enemyBlaster();
		}
	}
	
}