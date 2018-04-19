package misc;
import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import game.Game;

public class shootTest extends GraphicsPane {
	private GButton previous;
	private GButton next;
	private GButton enemyFire;
	private GButton returnToMenu;
	
	
	
	
	
	
	public void showContents() {
		program.add(previous);
		program.add(next);
		program.add(enemyFire);
		program.add(returnToMenu);
		
	}
	public void hideContents() {
		program.add(previous);
		program.add(next);
		program.add(enemyFire);
		program.add(returnToMenu);
	}
	
	
	
	
	
	
}