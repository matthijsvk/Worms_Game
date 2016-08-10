package worms.gui.menu;

import java.awt.event.KeyEvent;

import worms.gui.InputMode;

public class MenuInputMode<ST extends AbstractMenuScreen<Choice>, Choice> extends
		InputMode<ST> {

	public MenuInputMode(ST screen,
			InputMode<ST> previous) {
		super(screen, previous);
	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			getScreen().getGUI().exit();
			break;
		case KeyEvent.VK_DOWN:
			getScreen().selectNext();
			
			break;
		case KeyEvent.VK_UP:
			getScreen().selectPrevious();
			break;
		case KeyEvent.VK_ENTER:
			getScreen().selectCurrent();
			break;
		}
	}
}