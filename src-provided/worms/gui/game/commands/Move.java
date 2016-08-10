package worms.gui.game.commands;

import worms.gui.GUIConstants;
import worms.gui.game.PlayGameScreen;
import worms.gui.game.sprites.WormSprite;
import worms.gui.messages.MessageType;
import worms.model.IFacade;
import worms.model.ModelException;
import worms.model.Worm;

public class Move extends Command {

	private double startX;
	private double startY;

	private double finalX;
	private double finalY;

	private boolean isFalling;
	private double fallingStartTime = -1;

	private final Worm worm;

	public Move(IFacade facade, Worm worm, PlayGameScreen screen) {
		super(facade, screen);
		this.worm = worm;
	}

	public Worm getWorm() {
		return worm;
	}

	@Override
	protected boolean canStart() {
		return getWorm() != null && getFacade().canMove(getWorm());
	}

	private double getDuration() {
		return GUIConstants.MOVE_DURATION;
	}

	protected boolean canFall() {
		return getFacade().canFall(getWorm());
	}

	@Override
	protected void doUpdate(double dt) {
		WormSprite sprite = getScreen().getWormSprite(getWorm());
		if (sprite != null) {
			sprite.setIsMoving(true);
			if (getElapsedTime() < getDuration()) {
				double t = getElapsedTime() / getDuration();
				t = t * t * (3 - 2 * t); // smooth-step interpolation
				double x = (1.0 - t) * startX + t * finalX;
				double y = (1.0 - t) * startY + t * finalY;
				sprite.setCenterLocation(x, y);
			} else {
				fall(dt);
			}
		} else {
			cancelExecution();
		}
	}

	protected boolean isFalling() {
		return isFalling;
	}

	protected void ensureFalling() {
		if (fallingStartTime == -1) {
			fallingStartTime = getElapsedTime();
		}
		isFalling = true;
	}

	protected void fall(double dt) {
		if (!isFalling) {
			startFalling();
		} else {
			updateFalling();
		}
	}

	protected void updateFalling() {
		WormSprite sprite = getScreen().getWormSprite(worm);
		if (sprite != null) {
			double duration = getScreen().screenToWorldDistance(
					Math.abs(finalY - startY))
					/ GUIConstants.FALL_VELOCITY;
			double timeElapsedFalling = getElapsedTime() - fallingStartTime;
			if (timeElapsedFalling <= duration) {
				double t = timeElapsedFalling / duration;
				t = t * t;
				double x = (1.0 - t) * startX + t * finalX;
				double y = (1.0 - t) * startY + t * finalY;
				sprite.setCenterLocation(x, y);
			} else {
				sprite.setCenterLocation(finalX, finalY);
				completeExecution();
			}
		} else {
			cancelExecution();
		}
	}

	protected void startFalling() {
		this.startX = getScreen().getScreenX(getObjectX());
		this.startY = getScreen().getScreenY(getObjectY());

		if (canFall()) {
			ensureFalling();
			getFacade().fall(getWorm());
			if (isObjectStillActive()) {
				this.finalX = getScreen().getScreenX(getObjectX());
				this.finalY = getScreen().getScreenY(getObjectY());
			} else {
				this.finalX = startX;
				try {
					this.finalY = getScreen().getScreenY(getObjectY());
				} catch (ModelException e) {
					this.finalY = getScreen().getScreenY(0);
				}
			}
		} else {
			completeExecution();
		}
		WormSprite sprite = getScreen().getWormSprite(worm);
		if (sprite != null) {
			sprite.setCenterLocation(startX, startY);
		} else {
			cancelExecution();
		}
	}
	
	@Override
	protected void afterExecutionCompleted() {
		WormSprite sprite = getScreen().getWormSprite(getWorm());
		if (sprite != null) {
			sprite.setIsMoving(false);
		}
	}

	@Override
	protected void afterExecutionCancelled() {
		WormSprite sprite = getScreen().getWormSprite(getWorm());
		if (sprite != null) {
			sprite.setIsMoving(false);
		}
		getScreen().addMessage("This worm cannot move like that :(",
				MessageType.ERROR);
	}

	@Override
	protected void doStartExecution() {
		try {
			this.startX = getScreen().getScreenX(getFacade().getX(getWorm()));
			this.startY = getScreen().getScreenY(getFacade().getY(getWorm()));
			getFacade().move(getWorm());
			this.finalX = getScreen().getScreenX(getFacade().getX(getWorm()));
			this.finalY = getScreen().getScreenY(getFacade().getY(getWorm()));
		} catch (ModelException e) {
			e.printStackTrace();
			cancelExecution();
		}
	}

	protected boolean isObjectStillActive() {
		return getFacade().isAlive(getWorm());
	}

	protected double getObjectX() {
		return getFacade().getX(getWorm());
	}

	protected double getObjectY() {
		return getFacade().getY(getWorm());
	}
}