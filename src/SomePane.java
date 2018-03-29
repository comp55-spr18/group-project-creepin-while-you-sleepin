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
	private GParagraph para;

	public SomePane(MainApplication app) {
		this.program = app;
		program.scoreBoard.setFont("Arial-Bold-22");
		program.healthBoard.setFont("Arial-Bold-22");
	}

	@Override
	public void showContents() {
		program.add(program.scoreBoard);
		program.add(program.healthBoard);
	}

	@Override
	public void hideContents() {
		program.remove(program.scoreBoard);
		program.remove(program.healthBoard);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		program.player.setLocation(new GPoint(e.getX()-25, e.getY()-25));
		program.player.move();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		program.player.setLocation(new GPoint(e.getX()-25, e.getY()-25));
		program.player.move();
		program.isShooting = true;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		program.player.setLocation(new GPoint(e.getX()-25, e.getY()-25));
		program.player.move();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		program.player.setLocation(new GPoint(e.getX()-25, e.getY()-25));
		program.player.move();
		program.isShooting = false;
	}
}