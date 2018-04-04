import java.awt.Color;
import acm.graphics.GLabel;

public class AttackSpeedUp extends PowerUp {
	AttackSpeedUp(MainApplication game, double x, double y) {
		super(game, x, y);
		getSprite().setColor(Color.RED);
		getSprite().setFillColor(getSprite().getColor());
		setTag(new GLabel("Attack Up"));
		getTag().setLocation(getSprite().getX() + getSprite().getWidth()/2 - getTag().getWidth(), getSprite().getY() + getSprite().getHeight()/2 - getTag().getHeight());
		getGame().add(getSprite());
		getGame().add(getTag());
	}
}
