import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GPoint;

public class GamePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GParagraph para;
	private GImage background;

	public GamePane(MainApplication app) {
		this.program = app;
		program.scoreBoard.setFont("Arial-Bold-22");
		program.scoreBoard.setColor(Color.WHITE);
		background = new GImage("levels/testspacelevel.jpg");
		background.setSize(program.getWidth()+ 500, program.getHeight());
	}

	@Override
	public void showContents() {
		program.add(program.scoreBoard);
		program.add(background);
		new AttackSpeedUp(program, 500, 500);
		background.sendToBack();
	}

	@Override
	public void hideContents() {
		program.remove(program.scoreBoard);
		program.remove(background);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!program.player.isDestroyed()) {
			program.player.getSprite().setLocation(new GPoint(e.getX() - program.player.getSprite().getWidth()/2, e.getY() - program.player.getSprite().getHeight()/2));
			program.player.move();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(!program.player.isDestroyed()) {
			program.player.getSprite().setLocation(new GPoint(e.getX() - program.player.getSprite().getWidth()/2, e.getY() - program.player.getSprite().getHeight()/2));
			program.player.move();
			program.isShooting = true;
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(!program.player.isDestroyed()) {
			program.player.getSprite().setLocation(new GPoint(e.getX() - program.player.getSprite().getWidth()/2, e.getY() - program.player.getSprite().getHeight()/2));
			program.player.move();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(!program.player.isDestroyed()) {
			program.player.getSprite().setLocation(new GPoint(e.getX() - program.player.getSprite().getWidth()/2, e.getY() - program.player.getSprite().getHeight()/2));
			program.player.move();
			program.isShooting = false;
		}
	}
}