import java.awt.Color;

import acm.graphics.*;

public class PowerUp {
	MainApplication game;
	GButton sprite;
	
	public boolean checkCollision() {
		if(game != null) {
			if(game.player.getSprite().getBounds().intersects(getSprite().getBounds())) {
				onCollision();
				for(PowerUp power : getGame().powers) {
					getGame().remove(power.getSprite());
				}
				getGame().powers.clear();
				return true;
			}
		}
		return false;
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

class FireRateUp extends PowerUp {
	FireRateUp(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/100);
		double scaleY = game.WINDOW_HEIGHT/(1080/100);
		setSprite(new GButton("Fire Rate Up", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.RED);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		getGame().player.setCanShoot(true);
		getGame().player.setCooldown(0);
		getGame().player.setMaxCooldown(getGame().player.getMaxCooldown() - getGame().player.getMaxCooldown()/4);
		if(getGame().player.getMaxCooldown() <= 0) {
			getGame().player.setMaxCooldown(1);
		}
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
		getGame().player.setBulletDamage(getGame().player.getBulletDamage() + 1);
	}
}

class SpreadShot extends PowerUp {
	SpreadShot(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/100);
		double scaleY = game.WINDOW_HEIGHT/(1080/100);
		setSprite(new GButton("SpreadShot", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.YELLOW);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		getGame().player.setShots(3);
	}
}

class DoubleShot extends PowerUp {
	DoubleShot(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/100);
		double scaleY = game.WINDOW_HEIGHT/(1080/100);
		setSprite(new GButton("DoubleShot", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.YELLOW);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		getGame().player.setShots(2);
	}
}
