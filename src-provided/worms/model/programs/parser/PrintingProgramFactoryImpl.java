package worms.model.programs.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import org.antlr.v4.runtime.RecognitionException;

import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramParser;

class PrintingObject {

	private boolean indented = false;

	private final StringBuilder repr = new StringBuilder();

	public PrintingObject(String method, Object... args) {
		if (args.length > 1) {
			indented(method + "(");
			indented = true;
			for (int i = 0; i < args.length; i++) {
				indented(repr(args[i]), args.length > 1 && i < args.length - 1);
			}
			indented = false;
			indented(")");
		} else {
			String msg = method + "( ";
			if (args.length > 0) {
				msg += repr(args[0]).toString().trim();
			}
			msg += " )";
			indented(msg);
		}
	}

	private Object repr(Object object) {
		if (object instanceof String) {
			object = "\"" + object + "\"";
		}
		return object;
	}

	@Override
	public String toString() {
		return repr.toString();
	}

	private void indented(Object object) {
		indented(object, false);
	}

	private void indented(Object object, boolean appendComma) {
		if (object != null) {
			String str = object.toString();
			StringTokenizer tok = new StringTokenizer(str, "\n");
			while (tok.hasMoreTokens()) {
				if (indented)
					repr.append("  ");
				repr.append(tok.nextToken());
				if (!tok.hasMoreTokens() && appendComma) {
					repr.append(",");
				}
				repr.append("\n");
			}
		} else {
			if (indented)
				repr.append("  null");
		}

	}
}

public class PrintingProgramFactoryImpl implements
		ProgramFactory<PrintingObject, PrintingObject, PrintingObject> {

	@Override
	public PrintingObject createDoubleLiteral(int line, int column, double d) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), d);
	}

	@Override
	public PrintingObject createBooleanLiteral(int line, int column, boolean b) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), b);
	}

	@Override
	public PrintingObject createAnd(int line, int column, PrintingObject e1,
			PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createOr(int line, int column, PrintingObject e1,
			PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createNot(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createNull(int line, int column) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName());
	}

	@Override
	public PrintingObject createSelf(int line, int column) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName());
	}

	@Override
	public PrintingObject createGetX(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createGetY(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createGetRadius(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createGetDir(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createGetAP(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createGetMaxAP(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createGetHP(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createGetMaxHP(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createSameTeam(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createSearchObj(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createIsWorm(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createIsFood(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}
	
	@Override
	public PrintingObject createVariableAccess(int line, int column, String name) {
		return null; // we use the other method
	}

	@Override
	public PrintingObject createVariableAccess(int line, int column, String name, PrintingObject type) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), name, type);
	}

	@Override
	public PrintingObject createLessThan(int line, int column,
			PrintingObject e1, PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createGreaterThan(int line, int column,
			PrintingObject e1, PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createLessThanOrEqualTo(int line, int column,
			PrintingObject e1, PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createGreaterThanOrEqualTo(int line, int column,
			PrintingObject e1, PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createEquality(int line, int column,
			PrintingObject e1, PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createInequality(int line, int column,
			PrintingObject e1, PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createAdd(int line, int column, PrintingObject e1,
			PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createSubtraction(int line, int column,
			PrintingObject e1, PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createMul(int line, int column, PrintingObject e1,
			PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createDivision(int line, int column,
			PrintingObject e1, PrintingObject e2) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e1, e2);
	}

	@Override
	public PrintingObject createSqrt(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createSin(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createCos(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createTurn(int line, int column, PrintingObject angle) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), angle);
	}

	@Override
	public PrintingObject createMove(int line, int column) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName());
	}

	@Override
	public PrintingObject createJump(int line, int column) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName());
	}

	@Override
	public PrintingObject createToggleWeap(int line, int column) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName());
	}

	@Override
	public PrintingObject createFire(int line, int column, PrintingObject yield) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), yield);
	}

	@Override
	public PrintingObject createAssignment(int line, int column,
			String variable, PrintingObject rhs) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), variable, rhs);
	}

	@Override
	public PrintingObject createIf(int line, int column,
			PrintingObject condition, PrintingObject then,
			PrintingObject otherwise) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), condition, then, otherwise);
	}

	@Override
	public PrintingObject createWhile(int line, int column,
			PrintingObject condition, PrintingObject body) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), condition, body);
	}

	@Override
	public PrintingObject createForeach(int line, int column, ForeachType type,
			String variableName, PrintingObject body) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), type, variableName, body);
	}

	@Override
	public PrintingObject createSkip(int line, int column) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName());
	}

	@Override
	public PrintingObject createSequence(int line, int column,
			List<PrintingObject> statements) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), statements.toArray());
	}

	@Override
	public PrintingObject createPrint(int line, int column, PrintingObject e) {
		return new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName(), e);
	}

	@Override
	public PrintingObject createDoubleType() {
		PrintingObject po = new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.print(po);
		return po;
	}

	@Override
	public PrintingObject createBooleanType() {
		PrintingObject po = new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.print(po);
		return po;
	}

	@Override
	public PrintingObject createEntityType() {
		PrintingObject po = new PrintingObject(new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.print(po);
		return po;
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		// new PrintingProgramFactoryImpl().process("double a;\ndouble b := 3.14;\na := 1.0 - sin(b);\njump;");

		if (args.length == 0) {
			new PrintingProgramFactoryImpl()
					.processFile("programs/program.txt");
		} else if (args.length == 1) {
			new PrintingProgramFactoryImpl().processFile(args[0]);
		} else {
			System.err
					.println("usage: java PrintingProgramFactory [file-name]");
		}
	}

	private void processFile(String filePath) throws IOException,
			RecognitionException {

		InputStreamReader isr = new InputStreamReader(new FileInputStream(
				filePath));
		BufferedReader reader = new BufferedReader(isr);
		StringBuilder builder = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			builder.append(line);
			builder.append('\n');
			line = reader.readLine();
		}

		process(builder.toString());

		isr.close();
	}

	private void process(String string) {
		ProgramParser<PrintingObject, PrintingObject, PrintingObject> parser = new ProgramParser<PrintingObject, PrintingObject, PrintingObject>(
				this);
		parser.parse(string);
		System.out.println(parser.getStatement());
	}
}
