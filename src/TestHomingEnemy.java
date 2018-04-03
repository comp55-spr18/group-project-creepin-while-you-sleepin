
public class TestHomingEnemy extends TestEnemy {
	public TestHomingEnemy(MainApplication game, double y) {
		super(game, y);
		setHealth(4);
		setSpeed(4);
		setPoints(200);
	}
	
	@Override
	public void shoot() {
		if(canShoot()) {
			setCanShoot(false);
			new HomingBullet(getGame(), false, getGunLocation()[0], -1, 0, 8, getBulletColor(), 40);
			getGame().lowShootCount = getGame().playSound("lowshoot", getGame().lowShootCount);
		} else {
			setCooldown(getCooldown() + 1);
			if(getCooldown() == getMaxCooldown()) {
				setCooldown(0);
				setCanShoot(true);
			}
		}
	}
}
