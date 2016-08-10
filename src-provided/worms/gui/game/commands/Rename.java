package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.gui.messages.MessageType;
import worms.model.IFacade;
import worms.model.ModelException;
import worms.model.Worm;

public class Rename extends InstantaneousCommand {
	private final String newName;
	private final Worm worm;

	public Rename(IFacade facade, Worm worm, String newName,
			PlayGameScreen screen) {
		super(facade, screen);
		this.worm = worm;
		this.newName = newName;
	}

	@Override
	protected boolean canStart() {
		return worm != null;
	}

	@Override
	protected void doStartExecution() {
		try {
			getFacade().rename(worm, newName);
		} catch (ModelException e) {
			// an invalid name
			getScreen().addMessage("Invalid name: " + newName, MessageType.ERROR);
		}
	}
}