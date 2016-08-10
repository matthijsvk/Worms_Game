package worms.model.programs;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import worms.model.programs.parser.WormsParserLexer;
import worms.model.programs.parser.WormsParserMyListener;
import worms.model.programs.parser.WormsParserParser;

public class ProgramParser<E, S, T> {
	private ProgramFactory<E, S, T> factory;
	private Map<String, T> globals = null;
	private List<String> errors = new ArrayList<String>();
	private S statement = null;

	public ProgramParser(ProgramFactory<E, S, T> factory) {
		this.factory = factory;
	}

	/**
	 * Parse the given program text.
	 * This method calls the ProgramFactory methods of the factory given at construction time.
	 * 
	 * After parsing,
	 * <ul>
	 * <li> the declared global variables can be accessed with getGlobals()
	 * <li> the main program statement (which is usually a sequence of statements) can be accessed with getStatement() -
	 * <li> errors encountered while parsing can be accessed via getErrors;
	 * if this collection is empty, parsing was successful
	 * </ul>
	 * 
	 * <p>
	 * For example, when the following program is parsed: <code>
	 *   double a;
	 *   double b := 3.14;
	 *   a := 1.0 - sin(b);
	 *   jump;
	 * </code>
	 * this results in the following calls on the factory
	 * (simplified pseudocode):
	 * <code>
	 * createDoubleType(  )
     * createDoubleType(  )
     * createSequence(
     *   createAssignment(
     *     "b",
     *     createDoubleLiteral( 3.14 )
     *   ),
     *   createSequence(
     *     createAssignment(
     *       "a",
     *       createSubtraction(
     *         createDoubleLiteral( 1.0 ),
     *         createSin( createVariableAccess( "b" ) )
     *       )
     *     ),
     *     createJump(  )
     *   )
     * )
	 * </code>
	 */
	public void parse(String text) throws RecognitionException {
		globals = null;
		statement = null;
		CharStream cs = new ANTLRInputStream(text);
		WormsParserLexer lexer = new WormsParserLexer(cs);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		WormsParserParser parser = new WormsParserParser(tokens);
		parser.setBuildParseTree(true);
		parser.addErrorListener(new ANTLRErrorListener() {

			@Override
			public void syntaxError(Recognizer<?, ?> recognizer,
					Object offendingSymbol, int line, int column, String msg,
					RecognitionException e) {
				ProgramParser.this.errors.add(line + ":" + column
						+ " syntax error: " + msg);

			}

			@Override
			public void reportContextSensitivity(Parser arg0, DFA arg1,
					int arg2, int arg3, int arg4, ATNConfigSet arg5) {

			}

			@Override
			public void reportAttemptingFullContext(Parser arg0, DFA arg1,
					int arg2, int arg3, BitSet arg4, ATNConfigSet arg5) {

			}

			@Override
			public void reportAmbiguity(Parser arg0, DFA arg1, int arg2,
					int arg3, boolean arg4, BitSet arg5, ATNConfigSet arg6) {

			}
		});
		ParserRuleContext tree = parser.eval();
		if (!errors.isEmpty())
			return;
		WormsParserMyListener<E, S, T> listener = new WormsParserMyListener<E, S, T>(
				factory);
		this.listener = listener; //TODO added extra by us
		ParseTreeWalker.DEFAULT.walk(listener, tree);
		errors = listener.getErrors();
		globals = listener.getGlobals();
		statement = listener.getStatement();
	}
	
	//TODO added extra by us
	WormsParserMyListener<E, S, T> listener = null;
	public 	WormsParserMyListener<E, S, T> getListener(){
		return this.listener;
	}

	public List<String> getErrors() {
		return errors;
	}

	/**
	 * This method returns a non-null result provided <code>getErrors()</code>
	 * is empty.
	 */
	public Map<String, T> getGlobals() {
		return globals;
	}

	/**
	 * This method returns a non-null result provided <code>getErrors()</code>
	 * is empty.
	 */
	public S getStatement() {
		return statement;
	}
}
