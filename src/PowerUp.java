import acm.graphics.*;

public class PowerUp {
	MainApplication game;
	GButton sprite;
	
	public void checkCollision() {
		if(game != null) {
			if(game.player.getSprite().getBounds().intersects(getSprite().getBounds())) {
				onCollision();
				getGame().remove(getSprite());
				getSprite().setVisible(false);
				return;
			}
		}
	}
	
	public void onCollision() {
	}

	public MainApplication getGame() {
		return game;
	}

	public void setGame(MainApplication game) {
		this.game = game;
	}

	public GButton getSprite() {
		return sprite;
	}

	public void setSprite(GButton sprite) {
		this.sprite = sprite;
	}
}
