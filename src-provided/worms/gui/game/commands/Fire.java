package worms.gui.game.commands;

import worms.gui.GUIConstants;
import worms.gui.game.PlayGameScreen;
import worms.gui.game.sprites.ProjectileSprite;
import worms.gui.messages.MessageType;
import worms.model.IFacade;
import worms.model.ModelException;
import worms.model.Projectile;
import worms.model.Worm;

public class Fire extends Command {
	private final Worm worm;

	private final int propulsionYield;
	private Projectile projectile;
	private double totalDuration;
	private boolean hasJumped;

	public Fire(IFacade facade, Worm worm, int propulsionYield,
			PlayGameScreen screen) {
		super(facade, screen);
		this.worm = worm;
		this.propulsionYield = propulsionYield;
	}

	@Override
	protected boolean canStart() {
		return worm != null;
	}

	@Override
	protected void doStartExecution() {
		try {
			getFacade().shoot(worm, propulsionYield);
			projectile = getFacade().getActiveProjectile(getWorld());
			if (projectile != null) {
				totalDuration = getFacade().getJumpTime(projectile,
						GUIConstants.JUMP_TIME_STEP);
				ProjectileSprite sprite = new ProjectileSprite(getScreen(),
						projectile);
				sprite.setCenterLocation(
						getScreen().getScreenX(getFacade().getX(projectile)),
						getScreen().getScreenY(getFacade().getY(projectile)));
				sprite.setSize(getScreen().worldToScreenDistance(
						getFacade().getRadius(projectile)));
				getScreen().addSprite(sprite);
			} else {
				cancelExecution();
			}
		} catch (ModelException e) {
			e.printStackTrace();
			cancelExecution();
		}
	}

	@Override
	protected void afterExecutionCancelled() {
		getScreen().addMessage("This worm cannot shoot :(", MessageType.ERROR);
	}

	@Override
	protected void doUpdate(double dt) {
		try {
			if (getElapsedTime() >= totalDuration) {
				if (!hasJumped) {
					hasJumped = true;
					getFacade().jump(projectile, GUIConstants.JUMP_TIME_STEP);
					completeExecution();
				}
			} else {
				ProjectileSprite sprite = getScreen().getSpriteOfTypeFor(
						ProjectileSprite.class, projectile);

				double[] xy = getFacade().getJumpStep(projectile,
						getElapsedTime());

				sprite.setCenterLocation(getScreen().getScreenX(xy[0]),
						getScreen().getScreenY(xy[1]));
			}
		} catch (ModelException e) {
			e.printStackTrace();
			cancelExecution();
		}
	}
}