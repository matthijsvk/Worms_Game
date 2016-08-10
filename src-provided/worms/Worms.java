package worms;

import worms.gui.GUIOptions;
import worms.gui.WormsGUI;
import worms.model.Facade;

public class Worms {

	public static void main(String[] args) {
		new WormsGUI(new Facade(), parseOptions(args)).start();
	}

	private static GUIOptions parseOptions(String[] args) {
		GUIOptions options = new GUIOptions();

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if ("-window".equals(arg)) {
				options.disableFullScreen = true;
			} else if ("-seed".equals(arg)) {
				long randomSeed = Long.parseLong(args[++i]);
				options.randomSeed = randomSeed;
			} else if ("-clickselect".equals(arg)) {
				options.enableClickToSelect = true;
			} else if ("-program".equals(arg)) {
				String program = args[++i];
				options.programFile = program;
			}
		}

		return options;
	}
}
