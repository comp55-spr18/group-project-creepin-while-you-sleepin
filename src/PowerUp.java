import acm.graphics.*;

public class PowerUp {
	MainApplication game;
	double xPos;
	double yPos;
	GRect sprite;
	GLabel tag;
	
	PowerUp(MainApplication game, double x, double y) {
		setGame(game);
		setxPos(game.WINDOW_WIDTH/(1920/x));
		setyPos(game.WINDOW_HEIGHT/(1080/y));
		double scaleX = game.WINDOW_WIDTH/(1920/x);
		double scaleY = game.WINDOW_HEIGHT/(1080/x);
		setSprite(new GRect(getxPos(), getyPos(), scaleX, scaleY));
	}
	
	public void checkCollision() {
		if(game != null) {
			if(game.player.getSprite().getBounds().intersects(getSprite().getBounds())) {
				onCollision();
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

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public GRect getSprite() {
		return sprite;
	}

	public void setSprite(GRect sprite) {
		this.sprite = sprite;
	}

	public GLabel getTag() {
		return tag;
	}

	public void setTag(GLabel tag) {
		this.tag = tag;
	}
}
