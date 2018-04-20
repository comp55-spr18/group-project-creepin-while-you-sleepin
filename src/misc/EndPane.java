package misc;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import game.Game;

public class EndPane extends GraphicsPane {
	private GButton rect;
	private GButton rect2;
	private GLabel endLabel;
	private GImage background;

	public EndPane(Game app) {
		super();
		program = app;
		double scaleX = program.WINDOW_WIDTH/(1920/100.0);
		double scaleY = program.WINDOW_HEIGHT/(1080/100.0);
		rect = new GButton("MENU", 4*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		rect2 = new GButton("QUIT", 13*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		rect.setFillColor(Color.RED);
		rect2.setFillColor(Color.RED);
		endLabel = new GLabel("");
		endLabel.setFont("Arial-Bold-100");
		endLabel.setColor(Color.WHITE);
		background = new GImage("levels/betweenbackground.jpg");
		background.setSize(program.getWidth(), program.getHeight());
	}

	@Override
	public void showContents() {
		program.add(background);
		if(program.lose) {
			endLabel.setLabel("You lose!");
		} else {
			endLabel.setLabel("You win!");
		}
		endLabel.setLocation(program.WINDOW_WIDTH/2 - endLabel.getWidth()/2, program.WINDOW_HEIGHT/2 - endLabel.getHeight()/2);
		program.add(endLabel);
		program.add(rect);
		program.add(rect2);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(endLabel);
		program.remove(rect);
		program.remove(rect2);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.player = null;
			program.switchToMenu();
		}
		if (obj == rect2) {
			System.exit(0);
		}
	}
}