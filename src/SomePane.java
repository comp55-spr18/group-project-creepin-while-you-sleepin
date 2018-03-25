import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GPoint;

public class SomePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage img;
	private GParagraph para;
	// Variables for game loop
	Random rgen = new Random();
	ArrayList<Projectile> bullets = new ArrayList<Projectile>();
	ArrayList<Ship> enemies = new ArrayList<Ship>();
	ArrayList<Projectile> trail = new ArrayList<Projectile>();
	PlayerShip player = new PlayerShip();
	int score = 0;
	GLabel scoreBoard = new GLabel("SCORE: " + score, 10, 25);
	boolean isShooting = false;
	int count = 0;

	public SomePane(MainApplication app) {
		this.program = app;
		img = new GImage("robot head.jpg", 100, 100);
		para = new GParagraph("welcome\nto my\nsecret room!", 150, 300);
		para.setFont("Arial-24");
	}

	@Override
	public void showContents() {
		program.add(player.getSprite());
	}

	@Override
	public void hideContents() {
		program.remove(player.getSprite());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		player.setLocation(new GPoint(e.getX()-25, e.getY()-25));
		player.move();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		player.setLocation(new GPoint(e.getX()-25, e.getY()-25));
		player.move();
		isShooting = true;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		player.setLocation(new GPoint(e.getX()-25, e.getY()-25));
		player.move();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		player.setLocation(new GPoint(e.getX()-25, e.getY()-25));
		player.move();
		isShooting = false;
	}
}