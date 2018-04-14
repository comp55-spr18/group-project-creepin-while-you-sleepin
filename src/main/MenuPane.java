package main;
import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GImage;
import acm.graphics.GObject;
import game.Game;

public class MenuPane extends GraphicsPane {
	private Game program; // you will use program to get access to
	// all of the GraphicsProgram calls
	private GButton rect;
	private GButton rect2;
	private GButton rect3;
	private GImage background;

	public MenuPane(Game app) {
		super();
		program = app;
		double scaleX = program.WINDOW_WIDTH/(1920/100.0);
		double scaleY = program.WINDOW_HEIGHT/(1080/100.0);
		rect = new GButton("EASY", 4*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		rect3 = new GButton("QUIT", 9*scaleX, 6.5*scaleY, scaleX, scaleY);
		rect2 = new GButton("HARD", 13*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		rect.setFillColor(Color.RED);
		rect2.setFillColor(Color.RED);
		rect3.setFillColor(Color.RED);
		background = new GImage("mainmenu.jpg");
		background.setSize(program.getWidth(), program.getHeight());
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(rect);
		program.add(rect2);
		program.add(rect3);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(rect);
		program.remove(rect2);
		program.remove(rect3);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.easy = true;
			program.startGame();
			program.player.move(e);
		}
		if (obj == rect2) {
			program.easy = false;
			program.startGame();
			program.player.move(e);
		}
		if (obj == rect3) {
			System.exit(0);
		}
	}
}
