import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
	// all of the GraphicsProgram calls
	private GButton rect;
	private GButton rect2;
	private GImage background;

	public MenuPane(MainApplication app) {
		super();
		program = app;
		double scaleX = program.WINDOW_WIDTH/(1920/100.0);
		double scaleY = program.WINDOW_HEIGHT/(1080/100.0);
		rect2 = new GButton("EASY", 4*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		rect = new GButton("HARD", 13*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		rect.setFillColor(Color.RED);
		rect2.setFillColor(Color.BLUE);
		background = new GImage("mainmenu.jpg");
		background.setSize(program.getWidth(), program.getHeight());
		program.afterMessage.setFont("Arial-Bold-22");
		program.afterMessage.setColor(Color.WHITE);
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(rect);
		program.add(rect2);
		program.add(program.afterMessage);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(rect);
		program.remove(rect2);
		program.remove(program.afterMessage);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.easy = false;
			program.switchToSome();
		}
		if (obj == rect2) {
			program.easy = true;
			program.switchToSome();
		}
	}
}
