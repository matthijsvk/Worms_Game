package worms.gui.messages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.StringTokenizer;

import worms.gui.AbstractPainter;
import worms.gui.GUIUtils;
import worms.gui.Screen;

public class MessagePainter extends AbstractPainter<Screen> {

	public MessagePainter(Screen screen) {
		super(screen);
	}

	protected static final Color ERROR_MESSAGE_BACKGROUND_COLOR = new Color(
			0x60a7130e, true);
	protected static final Color NORMAL_MESSAGE_BACKGROUND_COLOR = new Color(
			0x600e13a7, true);
	protected static final Color INFO_MESSAGE_BACKGROUND_COLOR = new Color(
			0x60565656, true);
	protected static final Color MESSAGE_TEXT_COLOR = Color.WHITE;

	private static final int LINE_HEIGHT = 30;

	public void paintMessage(Graphics2D g, Message message) {
		switch (message.getType()) {
		case ERROR:
			g.setColor(ERROR_MESSAGE_BACKGROUND_COLOR);
			break;
		case INFO:
			g.setColor(INFO_MESSAGE_BACKGROUND_COLOR);
			break;
		default:
			g.setColor(NORMAL_MESSAGE_BACKGROUND_COLOR);
		}

		StringTokenizer tok = new StringTokenizer(message.getText(), "\n");
		int nbLines = tok.countTokens();

		int height = LINE_HEIGHT * (nbLines + 2);
		int top = (getScreen().getScreenHeight() - height) / 2;

		g.fillRect(0, top, getScreen().getScreenWidth(), height);
		Font oldFont = g.getFont();
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 2 * LINE_HEIGHT / 3));
		g.setColor(MESSAGE_TEXT_COLOR);

		int y = top + 2 * LINE_HEIGHT;
		while (tok.hasMoreTokens()) {
			String line = tok.nextToken();
			GUIUtils.drawCenteredString(g, line, getScreen().getScreenWidth(),
					y);
			y += LINE_HEIGHT;
		}

		g.setFont(oldFont);
	}
}
