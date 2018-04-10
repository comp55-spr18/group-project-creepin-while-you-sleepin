import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class GamePane extends GraphicsPane {
	private MainApplication program;
	private GImage background;

	public GamePane(MainApplication app) {
		this.program = app;
		program.scoreBoard.setFont("Arial-Bold-22");
		program.scoreBoard.setColor(Color.WHITE);
		program.alreadyHave.setFont("arial-22-bold");
		program.alreadyHave.setColor(Color.RED);
		program.alreadyHave.setLocation(program.WINDOW_WIDTH/2 - program.alreadyHave.getWidth()/2, program.WINDOW_HEIGHT/2 - program.getHeight()/2);
		background = new GImage("levels/test.gif");
		background.setSize(program.getWidth()+ 500, program.getHeight());
	}

	@Override
	public void showContents() {
		program.add(background);
		for(GImage heart : program.healthBar) {
			program.add(heart);
		}
		program.add(program.scoreBoard);
		program.add(program.player.getSprite());
		if(program.player.isShielded()) {
			program.add(program.player.getShield());
		}
		background.sendToBack();
	}

	@Override
	public void hideContents() {
		program.remove(program.scoreBoard);
		program.remove(background);
		for(Ship enemy : program.enemies) {						// Remove all enemy sprites
			program.remove(enemy.getSprite());
			program.remove(enemy.getExplosion());
			if(enemy.isShielded()) {
				program.remove(enemy.getShield());
			}
		}
		for(Projectile proj : program.projectiles) {			// Remove all projectile sprites
			program.remove(proj.getSprite());
		}
		program.remove(program.player.getSprite());	// Remove the playership sprite
		program.remove(program.player.getExplosion());
		if(program.player.isShielded()) {
			program.remove(program.player.getShield());
		}
		program.remove(program.scoreBoard);
		for(GImage heart : program.healthBar) {
			program.remove(heart);
		}
		program.enemies.clear();								// Clear the enemies arraylist
		program.projectiles.clear();							// Clear the projectiles arraylist
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!program.player.isDestroyed() && program.playerControl) {
			program.player.getSprite().setLocation(new GPoint(e.getX() - program.player.getSprite().getWidth()/2, e.getY() - program.player.getSprite().getHeight()/2));
			program.player.move();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e) && !program.player.isDestroyed() && program.playerControl) {
			program.player.getSprite().setLocation(new GPoint(e.getX() - program.player.getSprite().getWidth()/2, e.getY() - program.player.getSprite().getHeight()/2));
			program.player.move();
			program.isShooting = true;
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(!program.player.isDestroyed() && program.playerControl) {
			program.player.getSprite().setLocation(new GPoint(e.getX() - program.player.getSprite().getWidth()/2, e.getY() - program.player.getSprite().getHeight()/2));
			program.player.move();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e) && !program.player.isDestroyed() && program.playerControl) {
			program.player.getSprite().setLocation(new GPoint(e.getX() - program.player.getSprite().getWidth()/2, e.getY() - program.player.getSprite().getHeight()/2));
			program.player.move();
			program.isShooting = false;
		}
	}
}