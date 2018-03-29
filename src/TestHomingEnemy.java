
public class TestHomingEnemy extends TestEnemy {
	public TestHomingEnemy(MainApplication game) {
		super(game);
		setSpeed(4);
	}
	
	@Override
	public void shoot() {
		if(canShoot()) {
			Projectile newProj = new HomingBullet(getGame(), false, getGunLocation()[0], -1, 0, 8, getBulletColor(), 40);
			getGame().add(newProj.getSprite());
			setCanShoot(false);
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}
}
