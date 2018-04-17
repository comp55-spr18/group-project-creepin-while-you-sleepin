package misc;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import game.Game;

public class Heart extends Object {
	public Heart(Game game, double x) {
		setSprite(new GImage("sprites/Heart.gif", getGame().WINDOW_WIDTH/(1920/x), -100));
		setxDir(-1);
		setyDir(1);
		setSpeed(3);
		
	}
	public void checkCollision() {
		if(getSprite().getBounds().intersects(game.player.getSprite().getBounds())) {
			game.player.dealDamage(-1);
		}
	}
	@Override
	public void move() {
			getSprite().move(getxDir() * getSpeed(), getyDir() * getSpeed());
			double x = getSprite().getLocation().getX();
			double y = getSprite().getLocation().getY();
			if (getSprite().getLocation().getY() > getGame().WINDOW_HEIGHT + 100) {
				setDestroyed(true);
			}
			
		}
	}


