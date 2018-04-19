package misc;

import java.awt.Color;

/* File: GraphicsPane.java
 * -----------------------
 * Like you did with your own graphics programs, simply
 * extend from GraphicsPane and implement
 * as little or as much of the mouse listeners that you need
 * for your own programs.  Notice however that in this situation
 * There is no access to the GraphicsProgram window.
 * Make sure to distinguish between your constructor
 * and using showContents and hideContents
 */

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import game.Game;

public abstract class GraphicsPane implements Interfaceable {
	public Game program;
	public GButton selected;
	public Color savedColor;
	@Override
	public abstract void showContents();

	@Override
	public abstract void hideContents();

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj instanceof GButton) {
			if(obj != selected) {
				if(selected != null) {
					selected.setFillColor(savedColor);
				}
				selected = (GButton) obj;
				savedColor = selected.getFillColor();
				int red = selected.getFillColor().getRed();
				int green = selected.getFillColor().getGreen();
				int blue = selected.getFillColor().getBlue();
				red -= 50;
				blue -= 50;
				green -= 50;
				if(red < 0) {
					red = 0;
				}
				if(green < 0) {
					green = 0;
				}
				if(blue < 0) {
					blue = 0;
				}
				selected.setFillColor(new Color(red, green, blue));
			}
		} else if(selected != null) {
			selected.setFillColor(savedColor);
			selected = null;
			savedColor = null;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}
