package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.gui.messages.MessageType;
import worms.model.IFacade;
import worms.model.ModelException;

public class AddNewTeam extends InstantaneousCommand {

	private final String name;

	public AddNewTeam(IFacade facade, String name, PlayGameScreen screen) {
		super(facade, screen);
		this.name = name;
	}

	@Override
	protected boolean canStart() {
		return true;
	}

	@Override
	protected void doStartExecution() {
		try {
			getFacade().addEmptyTeam(getWorld(), name);
			getScreen().addMessage("Team " + name + " created.", MessageType.NORMAL);
		} catch (ModelException e) {
			getScreen().addMessage(
					"Could not create team " + name + ": " + e.getMessage(),
					MessageType.ERROR);
		}
	}

	@Override
	protected void afterExecutionCompleted() {
	}

}
