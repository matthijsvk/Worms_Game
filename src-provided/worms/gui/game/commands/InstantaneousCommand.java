package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.model.IFacade;

public abstract class InstantaneousCommand extends Command {
	protected InstantaneousCommand(IFacade facade, PlayGameScreen screen) {
		super(facade, screen);
	}
	
	@Override
	protected void afterExecutionStarted() {
		completeExecution();
		getScreen().update();
	}

	@Override
	protected final void doUpdate(double dt) {
	}
}