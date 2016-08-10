package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.model.IFacade;

public class SelectNextWorm extends InstantaneousCommand {

	public SelectNextWorm(IFacade facade, PlayGameScreen screen) {
		super(facade, screen);
	}

	@Override
	protected boolean canStart() {
		return true;
	}

	@Override
	protected void doStartExecution() {
		getFacade().startNextTurn(getWorld());
	}

}
