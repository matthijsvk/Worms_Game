package worms.gui.game.modes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import worms.gui.InputMode;
import worms.gui.game.PlayGameScreen;

public class EnteringNameMode extends InputMode<PlayGameScreen> {

	public static interface Callback {
		public void onNameEntered(String newName);
	}
	
	private final String message;
	private final Callback callback;

	/**
	 * @param playGameScreen
	 */
	public EnteringNameMode(String message, PlayGameScreen playGameScreen, InputMode<PlayGameScreen> previous, Callback callback) {
		super(playGameScreen, previous);
		this.message = message;
		this.callback = callback;
	}

	private String enteredName = "";

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			if (callback != null) {
				callback.onNameEntered(enteredName);
			}
			leaveInputMode();
			break;
		case KeyEvent.VK_ESCAPE:
			leaveInputMode();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == '\b') {
			enteredName = enteredName.substring(0,
					Math.max(0, enteredName.length() - 1));
		} else if (!Character.isISOControl(e.getKeyChar())
				&& e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
			enteredName += e.getKeyChar();
		}
		getScreen().repaint();
	}

	@Override
	public void paintOverlay(Graphics2D g) {
		super.paintOverlay(g);
		getScreen().paintTextEntry(g, message, enteredName);
	}

}