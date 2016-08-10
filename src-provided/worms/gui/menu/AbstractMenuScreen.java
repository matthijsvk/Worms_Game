package worms.gui.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import worms.gui.GUIUtils;
import worms.gui.InputMode;
import worms.gui.Screen;
import worms.gui.WormsGUI;

public abstract class AbstractMenuScreen<Choice> extends Screen {

	private static final int INSTRUCTIONS_AREA_HEIGHT = 100;
	private static final int CHOICE_HEIGHT = 30;

	private static final Font DEFAULT_CHOICE_FONT = new Font(Font.SANS_SERIF,
			Font.PLAIN, (CHOICE_HEIGHT * 4) / 6);
	private static final Color DEFAULT_CHOICE_COLOR = Color.WHITE;
	private static final Font SELECTED_CHOICE_FONT = new Font(Font.SANS_SERIF,
			Font.PLAIN, (CHOICE_HEIGHT * 5) / 6);
	private static final Color SELECTED_CHOICE_COLOR = Color.YELLOW;

	final Choice[] choices;

	BlockingQueue<Choice> selection = new ArrayBlockingQueue<Choice>(1);
	int selectedIndex = 0;

	public AbstractMenuScreen(WormsGUI gui) {
		super(gui);
		this.choices = getChoices();
	}

	public void selectNext() {
		selectedIndex = (selectedIndex + 1) % choices.length;
		repaint();
	}

	public void selectPrevious() {
		selectedIndex = (selectedIndex + choices.length - 1) % choices.length;
		repaint();
	}

	public void selectCurrent() {
		if (selection.isEmpty())
			selection.add(choices[selectedIndex]);
	}

	@Override
	protected InputMode<? extends AbstractMenuScreen<Choice>> createDefaultInputMode() {
		return new MenuInputMode<AbstractMenuScreen<Choice>, Choice>(this, null);
	}

	protected abstract Choice[] getChoices();

	protected abstract String getDisplayName(Choice choice);

	protected abstract String getInstructions();

	public Choice select() {
		try {
			return selection.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void paintScreen(Graphics2D g) {
		paintInstructions(g);

		int maxNbChoicesOnScreen = (getScreenHeight() - INSTRUCTIONS_AREA_HEIGHT)
				/ CHOICE_HEIGHT - 1;
		int start = 0;
		if (selectedIndex >= maxNbChoicesOnScreen) {
			start = selectedIndex - maxNbChoicesOnScreen + 1;
		}

		int lastChoiceToDisplay = Math.min(start + maxNbChoicesOnScreen,
				choices.length);
		for (int index = start; index < lastChoiceToDisplay; index++) {
			Choice choice = choices[index];
			String str = getDisplayName(choice);
			if (index == selectedIndex) {
				g.setColor(SELECTED_CHOICE_COLOR);
				g.setFont(SELECTED_CHOICE_FONT);
				str = "\u00bb " + str + " \u00ab";
			} else {
				g.setColor(DEFAULT_CHOICE_COLOR);
				g.setFont(DEFAULT_CHOICE_FONT);
			}
			GUIUtils.drawCenteredString(g, str, getScreenWidth(),
					INSTRUCTIONS_AREA_HEIGHT + CHOICE_HEIGHT * (index - start));
		}
		if (lastChoiceToDisplay < choices.length) {
			g.setFont(DEFAULT_CHOICE_FONT);
			g.setColor(DEFAULT_CHOICE_COLOR);
			GUIUtils.drawCenteredString(g, "...", getScreenWidth(),
					INSTRUCTIONS_AREA_HEIGHT + CHOICE_HEIGHT
							* maxNbChoicesOnScreen);
		}
	}

	private void paintInstructions(Graphics2D g) {
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.setColor(Color.WHITE);
		GUIUtils.drawCenteredString(g, getInstructions(), getScreenWidth(),
				INSTRUCTIONS_AREA_HEIGHT / 2);
	}

}
