import java.awt.Color;
import acm.graphics.GLabel;

public class AttackSpeedUp extends PowerUp {
	AttackSpeedUp(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/100);
		double scaleY = game.WINDOW_HEIGHT/(1080/100);
		setSprite(new GButton("Attack Speed Up", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.RED);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		getGame().player.setCanShoot(true);
		getGame().player.setCooldown(0);
		getGame().player.setMaxCooldown(getGame().player.getMaxCooldown() - 5);
	}
}
