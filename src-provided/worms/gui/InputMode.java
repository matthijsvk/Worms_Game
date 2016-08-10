package worms.gui;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputMode<ScreenType extends Screen> implements KeyListener, MouseListener,
		MouseMotionListener {

	private final ScreenType screen;
	private final InputMode<ScreenType> previous;

	public InputMode(ScreenType screen, InputMode<ScreenType> previous) {
		this.screen = screen;
		this.previous = previous;
	}
	
	public ScreenType getScreen() {
		return screen;
	}
	
	public void leaveInputMode() {
		if (previous == null) {
			getScreen().switchInputMode(getScreen().createDefaultInputMode());
		} else {
			getScreen().switchInputMode(previous);
		}
	}

	public void paintOverlay(Graphics2D g) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}