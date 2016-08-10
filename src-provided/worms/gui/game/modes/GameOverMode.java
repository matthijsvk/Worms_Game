package worms.gui.game.modes;

import java.awt.event.KeyEvent;

import worms.gui.InputMode;
import worms.gui.game.PlayGameScreen;
import worms.gui.menu.MainMenuScreen;

public class GameOverMode extends InputMode<PlayGameScreen> {

	/**
	 * @param playGameScreen
	 */
	public GameOverMode(PlayGameScreen playGameScreen,
			InputMode<PlayGameScreen> previous) {
		super(playGameScreen, previous);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'r':
		case 'R':
			// need to do this on a new thread, because otherwise the GUI will be blocked on the main menu
			new Thread(new Runnable() {

				@Override
				public void run() {
					getScreen().getGUI().switchToScreen(
							new MainMenuScreen(getScreen().getGUI()));
				}
			}).start();
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

}