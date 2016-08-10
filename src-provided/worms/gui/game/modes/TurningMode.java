package worms.gui.game.modes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import worms.gui.GUIConstants;
import worms.gui.GUIUtils;
import worms.gui.InputMode;
import worms.gui.game.PlayGameScreen;
import worms.gui.game.sprites.WormSprite;

public class TurningMode extends InputMode<PlayGameScreen> {

	public TurningMode(PlayGameScreen playGameScreen,
			InputMode<PlayGameScreen> previous) {
		super(playGameScreen, previous);
	}

	private double angle = 0;

	private long pressedSince = 0; // 0 if not turning
	private boolean clockwise;

	private void startTurning(boolean clockwise) {
		if (!isTurning()) {
			pressedSince = System.currentTimeMillis();
			this.clockwise = clockwise;
		}
	}

	private void stopTurning() {
		angle = getCurrentAngle();
		pressedSince = 0;
	}

	private boolean isTurning() {
		return pressedSince != 0;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		WormSprite sprite = getScreen().getSelectedWormSprite();
		if (sprite != null) {
			double[] wormXY = sprite.getCenterLocation();
			double currentOrientation = sprite.getOrientation();
			this.angle = Math.PI
					- currentOrientation
					+ Math.atan2((e.getY() - wormXY[1]), (wormXY[0] - e.getX()));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		finishTurn();
	}

	private void finishTurn() {
		if (angle != 0) {
			getScreen().turn(angle);
			leaveInputMode();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			startTurning(true);
			break;
		case KeyEvent.VK_LEFT:
			startTurning(false);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			leaveInputMode();
			break;
		case KeyEvent.VK_ENTER:
			finishTurn();
			break;
		case KeyEvent.VK_LEFT: // no-break
		case KeyEvent.VK_RIGHT:
			stopTurning();
			break;
		}
	}

	private double getCurrentAngle() {
		double delta = 0;
		if (isTurning()) {
			long now = System.currentTimeMillis();
			delta = Math.max(GUIConstants.MIN_TURN_ANGLE, (now - pressedSince)
					/ 1000.0 * GUIConstants.ANGLE_TURNED_PER_SECOND);
			if (clockwise) {
				delta = -delta;
			}
			return GUIUtils.restrictAngle(angle + delta, -Math.PI);
		} else {
			return angle;
		}
	}

	@Override
	public void paintOverlay(Graphics2D g) {
		super.paintOverlay(g);
		WormSprite sprite = getScreen().getSelectedWormSprite();
		if (sprite != null) {
			getScreen().drawTurnAngleIndicator(g, sprite, getCurrentAngle());
		}
	}
}