package worms.gui.game.commands;

import worms.gui.game.PlayGameScreen;
import worms.gui.messages.MessageType;
import worms.model.IFacade;
import worms.model.Worm;

public class Turn extends InstantaneousCommand {
	private final Worm worm;
	private final double angle;

	public Turn(IFacade facade, Worm worm, double angle, PlayGameScreen screen) {
		super(facade, screen);
		this.worm = worm;
		this.angle = angle;
	}

	@Override
	protected boolean canStart() {
		return getFacade().canTurn(worm, angle);
	}

	@Override
	protected void afterExecutionCancelled() {
		getScreen().addMessage("This worm cannot perform that turn :(",
				MessageType.ERROR);
	}

	@Override
	protected void doStartExecution() {
		getFacade().turn(worm, angle);
	}
}