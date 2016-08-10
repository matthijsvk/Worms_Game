package worms.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import worms.gui.messages.Message;
import worms.gui.messages.MessageDisplay;
import worms.gui.messages.MessagePainter;
import worms.gui.messages.MessageType;

public abstract class Screen {

	private MessageDisplay messageDisplay = new MessageDisplay();

	private final WormsGUI gui;
	private final JComponent contents;
	private final MessagePainter messagePainter;

	protected Screen(WormsGUI gui) {
		this.gui = gui;

		this.contents = createContents();
		contents.setFocusable(true);
		contents.setFocusTraversalKeysEnabled(false);

		this.messagePainter = createMessagePainter();

		switchInputMode(createDefaultInputMode());
	}

	protected MessagePainter createMessagePainter() {
		return new MessagePainter(this);
	}

	public JComponent getContents() {
		return contents;
	}

	protected JComponent createContents() {
		@SuppressWarnings("serial")
		JComponent result = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D graphics = (Graphics2D) g;
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				Screen.this.paintScreen(graphics);

				Screen.this.paintMessage(graphics);

				InputMode<? extends Screen> inputMode = getCurrentInputMode();
				if (inputMode != null)
					inputMode.paintOverlay(graphics);
			}
		};
		result.setBackground(Color.BLACK);
		return result;
	}

	public WormsGUI getGUI() {
		return gui;
	}

	protected abstract InputMode<? extends Screen> createDefaultInputMode();

	private InputMode<? extends Screen> currentInputMode;

	public InputMode<? extends Screen> getCurrentInputMode() {
		return currentInputMode;
	}

	public <ST extends Screen> void switchInputMode(InputMode<ST> newMode) {
		if (currentInputMode != null) {
			contents.removeKeyListener(currentInputMode);
			contents.removeMouseListener(currentInputMode);
			contents.removeMouseMotionListener(currentInputMode);
		}
		currentInputMode = newMode;
		if (newMode != null) {
			contents.addKeyListener(newMode);
			contents.addMouseListener(newMode);
			contents.addMouseMotionListener(newMode);
		}
	}

	protected void paintScreen(Graphics2D g) {
	}

	protected void paintMessage(Graphics2D g) {
		Message message = messageDisplay.getMessage();
		if (message != null && messagePainter != null) {
			messagePainter.paintMessage(g, message);
		}
	}

	public void addMessage(String message, MessageType type) {
		messageDisplay.addMessage(message, type);
		repaint();
	}

	public void screenStarted() {
	}

	public int getScreenHeight() {
		return getContents().getHeight();
	}

	public int getScreenWidth() {
		return getContents().getWidth();
	}

	public void repaint() {
		if (SwingUtilities.isEventDispatchThread()) {
			getContents().paintImmediately(getContents().getVisibleRect());
		} else {
			getContents().repaint();
		}

	}

	public void screenStopped() {
		switchInputMode(null);
	}

}
