package worms.model.programs.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.antlr.v4.runtime.RecognitionException;

import worms.model.programs.ProgramParser;

public class Listener {

	public static void main(String[] args) throws IOException,
			RecognitionException {
		if (args.length == 0) {
			new Listener().processInteractive();
		} else if (args.length == 1) {
			new Listener().processFile(args[0]);
		} else {
			System.err.println("usage: java Listener [file-name]");
		}
	}

	private void processFile(String filePath) throws IOException,
			RecognitionException {

		InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath));
		BufferedReader reader = new BufferedReader(isr);
		StringBuilder builder = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			builder.append(line);
			builder.append('\n');
			line = reader.readLine();
		}

		DummyProgramFactoryImpl factory = new DummyProgramFactoryImpl();
		ProgramParser<DummyExpression, DummyStatement, DummyType> parser = new ProgramParser<DummyExpression, DummyStatement, DummyType>(
				factory);
		parser.parse(builder.toString());
		
		isr.close();
	}

	private void processInteractive() throws IOException, RecognitionException {

	}
}
