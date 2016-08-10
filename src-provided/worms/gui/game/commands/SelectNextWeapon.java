package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.gui.messages.MessageType;
import worms.model.IFacade;
import worms.model.ModelException;
import worms.model.Worm;

public class SelectNextWeapon extends InstantaneousCommand {
	private final Worm worm;

	public SelectNextWeapon(IFacade facade, Worm worm, PlayGameScreen screen) {
		super(facade, screen);
		this.worm = worm;
	}

	@Override
	protected boolean canStart() {
		return worm != null;
	}

	@Override
	protected void doStartExecution() {
		try {
			getFacade().selectNextWeapon(worm);
		} catch (ModelException e) {
			getScreen().addMessage("Cannot select next weapon :(",
					MessageType.ERROR);
		}
	}
}