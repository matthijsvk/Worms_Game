package worms.gui.game.modes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import worms.gui.InputMode;
import worms.gui.game.PlayGameScreen;

public class SetupInputMode extends InputMode<PlayGameScreen> {

	public SetupInputMode(PlayGameScreen screen,
			InputMode<PlayGameScreen> previous) {
		super(screen, previous);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 't':
		case 'T':
			getScreen().addEmptyTeam();
			break;
		case 'w':
		case 'W':
			getScreen().addPlayerControlledWorm();
			break;
		case 'c':
		case 'C':
			getScreen().addComputerControlledWorm();
			break;
		case 'f':
		case 'F':
			getScreen().addFood();
			break;
		case 's':
		case 'S':
			getScreen().startGame();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			getScreen().getGUI().exit();
			break;
		}
	}

	@Override
	public void paintOverlay(Graphics2D g) {
		getScreen()
				.showInstructions(
						g,
						"Press\n'T' to create a new team\n'W' to add a new player-controlled worm\n'C' to add a new computer-controlled worm\n'F' to add food\n'S' to start the game");
	}

}
