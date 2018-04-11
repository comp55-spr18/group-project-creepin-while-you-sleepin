import java.awt.Color;

public class PowerUp {
	MainApplication game;
	GButton sprite;
	int height = 75;
	int width = 300;
	
	public boolean checkCollision() {
		if(game != null) {
			if(getGame().player.getSprite().getBounds().intersects(getSprite().getBounds())) {
				if(this instanceof BulletUp && game.player.getShots() == 5) {
					getGame().add(getGame().alreadyHave);
					return false;
				}
				onCollision();
				for(PowerUp power : getGame().powers) {
					getGame().remove(power.getSprite());
				}
				getGame().remove(getGame().alreadyHave);
				getGame().powers.clear();
				getGame().audio.playSound("sounds", "pickup.mp3");
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
		double scaleX = game.WINDOW_WIDTH/(1920/width);
		double scaleY = game.WINDOW_HEIGHT/(1080/height);
		setSprite(new GButton("Fire Rate", xPos, yPos, scaleX, scaleY));
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
		double scaleX = game.WINDOW_WIDTH/(1920/width);
		double scaleY = game.WINDOW_HEIGHT/(1080/height);
		setSprite(new GButton("Bullet Size", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.BLUE);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		getGame().player.setBulletSize(getGame().player.getBulletSize() + 15);
	}
}

class BulletSpeedUp extends PowerUp {
	BulletSpeedUp(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/width);
		double scaleY = game.WINDOW_HEIGHT/(1080/height);
		setSprite(new GButton("Bullet Speed", xPos, yPos, scaleX, scaleY));
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
		double scaleX = game.WINDOW_WIDTH/(1920/width);
		double scaleY = game.WINDOW_HEIGHT/(1080/height);
		setSprite(new GButton("Bullet Damage", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.YELLOW);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		getGame().player.setBulletDamage(getGame().player.getBulletDamage() + 1);
	}
}

class BulletUp extends PowerUp {
	BulletUp(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/width);
		double scaleY = game.WINDOW_HEIGHT/(1080/height);
		setSprite(new GButton("+1 Bullet", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.CYAN);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		getGame().player.setShots(getGame().player.getShots() + 1);
	}
}

class HealthUp extends PowerUp {
	HealthUp(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/width);
		double scaleY = game.WINDOW_HEIGHT/(1080/height);
		setSprite(new GButton("+1 Max HP", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.PINK);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		int damageTaken = getGame().player.getMaxHealth() - getGame().player.getHealth();
		getGame().player.setMaxHealth(getGame().player.getMaxHealth() + 1);
		getGame().player.setHealth(getGame().player.getMaxHealth() - damageTaken + 1);
	}
}

class ShieldUp extends PowerUp {
	ShieldUp(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/width);
		double scaleY = game.WINDOW_HEIGHT/(1080/height);
		setSprite(new GButton("Regen Shield", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.LIGHT_GRAY);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
		if(!getGame().player.isShielded()) {
			getGame().player.setShielded(true);
		} else {
			getGame().player.setShieldCooldown(0);
			getGame().player.setShieldMaxCooldown(getGame().player.getShieldMaxCooldown() - 50);
		}
	}
}

class Exit extends PowerUp {
	Exit(MainApplication game, double x, double y) {
		setGame(game);
		double xPos = game.WINDOW_WIDTH/(1920/x);
		double yPos = game.WINDOW_HEIGHT/(1080/y);
		double scaleX = game.WINDOW_WIDTH/(1920/width);
		double scaleY = game.WINDOW_HEIGHT/(1080/height);
		setSprite(new GButton("Exit", xPos, yPos, scaleX, scaleY));
		getSprite().setFillColor(Color.DARK_GRAY);
		getGame().powers.add(this);
		getGame().add(getSprite());
	}
	
	public void onCollision() {
	}
}
