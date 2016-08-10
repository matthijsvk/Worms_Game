package worms.gui.game.modes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import worms.gui.InputMode;
import worms.gui.game.PlayGameScreen;

public class ShootingMode extends InputMode<PlayGameScreen> {

	/**
	 * @param playGameScreen
	 */
	ShootingMode(PlayGameScreen playGameScreen,
			InputMode<PlayGameScreen> previous) {
		super(playGameScreen, previous);
	}

	private static final int MIN_PROPULSION = 0;
	private static final int MAX_PROPULSION = 100;
	private static final int PROPULSION_STEP = 5;

	private int propulsion = MIN_PROPULSION + (MAX_PROPULSION - MIN_PROPULSION)
			/ 2;

	@Override
	public void mouseDragged(MouseEvent e) {
		getScreen().switchInputMode(new TurningMode(getScreen(), this));
		getScreen().getCurrentInputMode().mouseDragged(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			getScreen().switchInputMode(new TurningMode(getScreen(), this));
			getScreen().getCurrentInputMode().keyPressed(e);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			leaveInputMode();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 's':
		case 'S':
			getScreen().shoot(propulsion);
			break;
		case 'w':
		case 'W':
			getScreen().selectNextWeapon();
			break;
		case '+':
			propulsion = Math.min(MAX_PROPULSION, propulsion + PROPULSION_STEP);
			break;
		case '-':
			propulsion = Math.max(MIN_PROPULSION, propulsion - PROPULSION_STEP);
			break;
		}
	}

	@Override
	public void paintOverlay(Graphics2D g) {
		getScreen().showInstructions(g, "Shooting mode\nPress 'W' to toggle weapon\nPress 'S' to shoot\nPress 'ESC' to exit shooting mode");
		getScreen().paintShootingInfoForSelectedWorm(
				g,
				(double) (propulsion - MIN_PROPULSION)
						/ (MAX_PROPULSION - MIN_PROPULSION));
	}

}