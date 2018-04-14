package misc;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import game.Game;

public class BetweenPane extends GraphicsPane {
	private Game program;
	private GButton rect;
	private GButton rect2;
	private GLabel levelLabel;
	private GImage background;

	public BetweenPane(Game app) {
		super();
		program = app;
		double scaleX = program.WINDOW_WIDTH/(1920/100.0);
		double scaleY = program.WINDOW_HEIGHT/(1080/100.0);
		rect = new GButton("CONTINUE", 4*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		rect2 = new GButton("QUIT", 13*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		rect.setFillColor(Color.RED);
		rect2.setFillColor(Color.RED);
		levelLabel = new GLabel("");
		levelLabel.setFont("Arial-Bold-100");
		levelLabel.setColor(Color.WHITE);
		background = new GImage("levels/testspacelevel.jpg");
		background.setSize(program.getWidth(), program.getHeight());
	}

	@Override
	public void showContents() {
		program.add(background);
		levelLabel.setLabel("LEVEL " + program.currLevel);
		levelLabel.setLocation(program.WINDOW_WIDTH/2 - levelLabel.getWidth()/2, program.WINDOW_HEIGHT/2 - levelLabel.getHeight()/2);
		program.add(levelLabel);
		program.add(rect);
		program.add(rect2);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(levelLabel);
		program.remove(rect);
		program.remove(rect2);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.switchToGame();
			program.player.move(e);
			program.timer.start();
		}
		if (obj == rect2) {
			System.exit(0);
		}
	}
}