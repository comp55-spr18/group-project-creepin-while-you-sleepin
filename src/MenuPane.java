import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton rect;

	public MenuPane(MainApplication app) {
		super();
		program = app;
		rect = new GButton("Play", 200, 200, 200, 200);
		rect.setFillColor(Color.RED);
		program.afterMessage.setFont("Arial-Bold-22");
	}

	@Override
	public void showContents() {
		program.add(rect);
		program.add(program.afterMessage);
	}

	@Override
	public void hideContents() {
		program.remove(rect);
		program.remove(program.afterMessage);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.switchToSome();
		}
	}
}
