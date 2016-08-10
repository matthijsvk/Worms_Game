package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.model.IFacade;

public class AddNewFood extends InstantaneousCommand {

	public AddNewFood(IFacade facade, PlayGameScreen screen) {
		super(facade, screen);
	}

	@Override
	protected boolean canStart() {
		return true;
	}

	@Override
	protected void doStartExecution() {
		getFacade().addNewFood(getWorld());
	}

}
