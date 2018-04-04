import java.awt.Color;

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

class AttackSpeedUp extends PowerUp {
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

class BulletSizeUp extends PowerUp {
	BulletSizeUp(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/100);
		double scaleY = game.WINDOW_HEIGHT/(1080/100);
		setSprite(new GButton("Bullet Size Up", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.BLUE);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		getGame().player.setBulletSize(getGame().player.getBulletSize() + 10);
	}
}

class BulletSpeedUp extends PowerUp {
	BulletSpeedUp(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/100);
		double scaleY = game.WINDOW_HEIGHT/(1080/100);
		setSprite(new GButton("Bullet Speed Up", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.GREEN);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		getGame().player.setBulletSpeed(getGame().player.getBulletSpeed() + 15);
	}
}

class BulletDamageUp extends PowerUp {
	BulletDamageUp(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/100);
		double scaleY = game.WINDOW_HEIGHT/(1080/100);
		setSprite(new GButton("Bullet Damage Up", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.YELLOW);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		getGame().player.setBulletSpeed(getGame().player.getBulletSpeed() + 1);
	}
}
