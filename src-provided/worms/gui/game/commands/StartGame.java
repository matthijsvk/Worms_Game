package worms.gui.game.commands;

import java.util.Collection;

import worms.gui.game.PlayGameScreen;
import worms.gui.messages.MessageType;
import worms.model.IFacade;
import worms.model.Worm;

public class StartGame extends InstantaneousCommand {

	public StartGame(IFacade facade, PlayGameScreen screen) {
		super(facade, screen);
	}

	@Override
	protected boolean canStart() {
		Collection<Worm> worms = getFacade().getWorms(getWorld());
		return worms != null && !worms.isEmpty();
	}
	
	@Override
	protected void afterExecutionCancelled() {
		getScreen().addMessage("Cannot start the game without worms", MessageType.ERROR);
	}

	@Override
	protected void doStartExecution() {
		getScreen().gameStarted();		
		getFacade().startGame(getWorld());
		if (getFacade().isGameFinished(getWorld())) {
			getScreen().gameFinished();
		}
	}

}
