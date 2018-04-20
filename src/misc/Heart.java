package misc;

import acm.graphics.GImage;
import game.Game;

public class Heart extends Object {
	public Heart(Game game, double x) {
		setGame(game);
		setSprite(new GImage("sprites/heart.gif", getGame().WINDOW_WIDTH/(1920/x), -100));
		setxDir(-1);
		setyDir(1);
		setSpeed(3);
		getGame().add(getSprite());
		setSize(50,50);
		getGame().objects.add(this);
	}
	
	@Override
	public void checkCollision() {
		if(getSprite().getBounds().intersects(game.player.getSprite().getBounds())) {
			game.player.setHealth(game.player.getHealth() + 1);
			setDestroyed(true);
			game.playSound("heart", 1);
			if(isDestroyed) {
				getGame().remove(getSprite());
				getGame().objects.remove(this);
			}
			
			
		}
	}
	
	@Override
	public void move() {
		getSprite().move(getxDir() * getSpeed(), getyDir() * getSpeed());
		if (getSprite().getLocation().getY() > getGame().WINDOW_HEIGHT + 100) {
			setDestroyed(true);
		}	
	}

	public GImage getSprite() {
		return (GImage) sprite;
	}

	public void setSprite(GImage sprite) {
		this.sprite = sprite;
	}
}


