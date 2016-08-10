package worms.gui.game.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import worms.gui.GUIUtils;
import worms.gui.WormsGUI;
import worms.gui.game.PlayGameScreen;
import worms.model.IFacade;
import worms.model.Program;
import worms.model.programs.ParseOutcome;
import worms.model.programs.ParseOutcome.Failure;
import worms.model.programs.ParseOutcome.Success;

public class AddNewWorm extends InstantaneousCommand {

	private final boolean withProgram;

	public AddNewWorm(IFacade facade, boolean withProgram, PlayGameScreen screen) {
		super(facade, screen);
		this.withProgram = withProgram;
	}

	@Override
	protected boolean canStart() {
		return true;
	}

	@Override
	protected void doStartExecution() {
		if (withProgram) {
			String programText = readProgramText();
			if (programText != null) {
				ParseOutcome<?> parsed = getFacade().parseProgram(programText,
						getScreen().getProgramActionHandler());
				if (parsed != null) {
					if (parsed.isSuccess()) {
						Program program = ((Success) parsed).getResult();
						if (getFacade().isWellFormed(program)) {
							getFacade().addNewWorm(getWorld(), program);
						} else {
							cancelExecution();
							getGUI().showError("The program is not well-formed");
						}
						return;
					} else {
						List<String> errors = ((Failure) parsed).getResult();
						String msg = "Parsing failed\nwith the following errors:\n";
						for (String error : errors) {
							msg += error + "\n";
						}
						cancelExecution();
						getGUI().showError(msg);
						return;
					}
				}
			} else {
				cancelExecution();
				return;
			}
		} else {
			getFacade().addNewWorm(getWorld(), null);
		}
	}

	private WormsGUI getGUI() {
		return getScreen().getGUI();
	}

	protected String readProgramText() {
		InputStream stream;
		try {
			stream = GUIUtils.openResource(getGUI().getOptions().programFile);
		} catch (IOException e) {
			e.printStackTrace();
			getGUI().showError(e.getMessage());
			return null;
		}

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		StringBuilder programText = new StringBuilder();
		String line;
		try {
			line = reader.readLine();
			while (line != null) {
				programText.append(line);
				programText.append("\n");
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			getGUI().showError("Error while loading program: " + e);
			return null;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// I don't care
			}
		}
		return programText.toString();
	}

}
