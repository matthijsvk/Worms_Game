package worms.model.programs.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import worms.model.programs.ProgramFactory;

// ------------------------------------------------------------------------
// This class implement a recursive AST inspector that generates a Program.
// The global variables and the main statement may be retrieved by calling getGlobals() and getStatement().
// ------------------------------------------------------------------------
public class WormsParserMyListener<E, S, T> implements WormsParserListener {

	// ------------------------------------------------------------------------
	// --- Internal Attributes and Methods ------------------------------------
	// ------------------------------------------------------------------------
	// This is the core of the AST inspector. The entry point is
	// StatementOfEval(), which returns a (seyence of) statement(s) for a given
	// WormsParserParser.EvalContext while updating the list of declared
	// variables.
	// ------------------------------------------------------------------------

	private Map<String, T> globals = new HashMap<String, T>();
	private S statement = null;
	private List<String> errors = new ArrayList<String>();
	private ProgramFactory<E, S, T> factory;

	private Boolean evalVisited = false; // hack!

	public WormsParserMyListener(ProgramFactory<E, S, T> factory) {
		if (!canHaveAsFactory(factory))
			throw new IllegalArgumentException("bad factory");
		this.factory = factory;
	}

	public boolean canHaveAsFactory(ProgramFactory<E, S, T> factory) {
		return factory != null;
	}

	private S StatementOfEval(WormsParserParser.EvalContext eval) {
		if (eval == null)
			return null;
		int line = eval.getStart().getLine();
		int column = eval.getStart().getCharPositionInLine();
		S statements = null;

		// assignment
		if (eval.assign() != null) {
			statements = StatementOfAssign(eval.assign().IDENTIFIER(), eval
					.assign().expr());
		}

		// if, while, etc.
		if (eval.ctrl() != null) {
			statements = StatementOfCtrl(eval.ctrl());
		}

		// fire, turn, etc.
		if (eval.action() != null) {
			statements = StatementOfAction(eval.action());
		}

		// declarations
		if (eval.decl() != null) {
			addDeclaration(eval.decl());

			// handle assignment in declaration
			if (eval.decl().ASSIGN() != null && eval.decl().expr() != null) {
				statements = StatementOfAssign(eval.decl().IDENTIFIER(), eval
						.decl().expr());
			}
		}

		if (eval.PRINT() != null) {
			statements = factory.createPrint(line, column,
					ExpressionOfExpr(eval.expr()));
		}

		// recurse to process sequence of statements
		if (eval.eval() != null) {
			if (statements == null) {
				statements = StatementOfEval(eval.eval());
			} else {
				java.util.List<S> l = new java.util.ArrayList<S>();
				l.add(statements);
				l.add(StatementOfEval(eval.eval()));
				statements = factory.createSequence(line, column, l);
			}
		}

		// return something sensible for empty bodies
		if (statements != null) {
			return (statements);
		} else {
			return (factory.createSequence(line, column,
					Collections.<S> emptyList()));
		}
	}

	private S StatementOfAssign(TerminalNode identifier,
			WormsParserParser.ExprContext expr) {
		if (expr == null)
			return null;
		int line = identifier.getSymbol().getLine();
		int column = identifier.getSymbol().getCharPositionInLine();
		return (factory.createAssignment(line, column, identifier.getText(),
				ExpressionOfExpr(expr)));
	}

	private S StatementOfAction(WormsParserParser.ActionContext action) {
		if (action == null)
			return null;
		int line = action.getStart().getLine();
		int column = action.getStart().getCharPositionInLine();
		if (action.SKIP() != null) {
			return (factory.createSkip(line, column));
		}
		if (action.JUMP() != null) {
			return (factory.createJump(line, column));
		}
		if (action.FIRE() != null) {
			E e = ExpressionOfExpr(action.expr());
			return (factory.createFire(line, column, e));
		}
		if (action.TOGGLEWEAP() != null) {
			return (factory.createToggleWeap(line, column));
		}
		if (action.TURN() != null) {
			E e = ExpressionOfExpr(action.expr());
			return (factory.createTurn(line, column, e));
		}
		if (action.MOVE() != null) {
			return factory.createMove(line, column);
		}

		assert (false);
		return (null);
	}

	void addDeclaration(WormsParserParser.DeclContext d) {
		if (d == null)
			return;

		TerminalNode identifier = d.IDENTIFIER();
		WormsParserParser.TypeContext type = d.type();

		if (type.BOOL() != null) {
			globals.put(identifier.getText(), factory.createBooleanType());
			return;
		}
		if (type.DOUBLE() != null) {
			globals.put(identifier.getText(), factory.createDoubleType());
			return;
		}
		if (type.ENTITY() != null) {
			globals.put(identifier.getText(), factory.createEntityType());
			return;
		}

		assert (false);
		return;
	}

	private S StatementOfCtrl(WormsParserParser.CtrlContext ctrl) {
		if (ctrl == null)
			return null;

		if (ctrl.ifthenelse() != null) {
			return (StatementOfIfthenelse(ctrl.ifthenelse()));
		}
		if (ctrl.whiledo() != null) {
			return (StatementOfWhiledo(ctrl.whiledo()));
		}
		if (ctrl.foreach() != null) {
			return (StatementOfForeach(ctrl.foreach()));
		}

		assert (false);
		return (null);
	}

	private S StatementOfIfthenelse(WormsParserParser.IfthenelseContext ctx) {
		if (ctx == null)
			return null;
		int line = ctx.getStart().getLine();
		int column = ctx.getStart().getCharPositionInLine();
		WormsParserParser.ExprContext c = ctx.expr();
		java.util.List<WormsParserParser.EvalContext> e = ctx.eval();

		switch (e.size()) {
		case 0: {
			break;
		}
		case 1: {
			return (factory.createIf(
					line,
					column,
					ExpressionOfExpr(c),
					StatementOfEval(e.get(0)),
					factory.createSequence(line, column,
							Collections.<S> emptyList())));
		}
		case 2: {
			return (factory.createIf(line, column, ExpressionOfExpr(c),
					StatementOfEval(e.get(0)), StatementOfEval(e.get(1))));
		}
		default: {
		}
		}

		assert (false);
		return (null);
	}

	private S StatementOfWhiledo(WormsParserParser.WhiledoContext w) {
		if (w == null)
			return null;
		int line = w.getStart().getLine();
		int column = w.getStart().getCharPositionInLine();
		WormsParserParser.ExprContext c = w.expr();
		WormsParserParser.EvalContext e = w.eval();

		return (factory.createWhile(line, column, ExpressionOfExpr(c),
				StatementOfEval(e)));
	}

	private S StatementOfForeach(WormsParserParser.ForeachContext ctx) {
		if (ctx == null)
			return null;
		int line = ctx.getStart().getLine();
		int column = ctx.getStart().getCharPositionInLine();
		ProgramFactory.ForeachType type = null;
		if (ctx.entityspec().ANY() != null) {
			type = ProgramFactory.ForeachType.ANY;
		}
		if (ctx.entityspec().WORM() != null) {
			type = ProgramFactory.ForeachType.WORM;
		}
		if (ctx.entityspec().FOOD() != null) {
			type = ProgramFactory.ForeachType.FOOD;
		}
		assert (type != null);

		return (factory.createForeach(line, column, type, ctx.IDENTIFIER()
				.getText(), StatementOfEval(ctx.eval())));
	}

	private E ExpressionOfNamedConst(WormsParserParser.NamedconstContext naconst) {
		if (naconst == null)
			return null;
		int line = naconst.getStart().getLine();
		int column = naconst.getStart().getCharPositionInLine();

		if (naconst.FALSE() != null) {
			return (factory.createBooleanLiteral(line, column, false));
		}
		if (naconst.TRUE() != null) {
			return (factory.createBooleanLiteral(line, column, true));
		}
		if (naconst.SELF() != null) {
			return (factory.createSelf(line, column));
		}
		if (naconst.NULL() != null) {
			return (factory.createNull(line, column));
		}

		assert (false);
		return (null);
	}

	private E ExpressionOfNumber(TerminalNode n) {
		if (n == null)
			return null;
		int line = n.getSymbol().getLine();
		int column = n.getSymbol().getCharPositionInLine();
		return (factory.createDoubleLiteral(line, column,
				Double.parseDouble(n.getText())));
	}

	private E ExpressionOfBinop(WormsParserParser.BinopContext op,
			WormsParserParser.ExprContext e1, WormsParserParser.ExprContext e2) {
		if (op == null || e1 == null)
			return null;
		int line = e1.getStart().getLine();
		int column = e1.getStart().getCharPositionInLine();
		if (op.GT() != null) {
			return factory.createGreaterThan(line, column,
					ExpressionOfExpr(e1), ExpressionOfExpr(e2));
		}
		if (op.LT() != null) {
			return factory.createLessThan(line, column, ExpressionOfExpr(e1),
					ExpressionOfExpr(e2));
		}
		if (op.SUB() != null) {
			return factory.createSubtraction(line, column,
					ExpressionOfExpr(e1), ExpressionOfExpr(e2));
		}
		if (op.NEQ() != null) {
			return factory.createInequality(line, column, ExpressionOfExpr(e1),
					ExpressionOfExpr(e2));
		}
		if (op.GEQ() != null) {
			return factory.createGreaterThanOrEqualTo(line, column,
					ExpressionOfExpr(e1), ExpressionOfExpr(e2));
		}
		if (op.EQ() != null) {
			return (factory.createEquality(line, column, ExpressionOfExpr(e1),
					ExpressionOfExpr(e2)));
		}
		if (op.DIV() != null) {
			return factory.createDivision(line, column, ExpressionOfExpr(e1),
					ExpressionOfExpr(e2));
		}
		if (op.MUL() != null) {
			return (factory.createMul(line, column, ExpressionOfExpr(e1),
					ExpressionOfExpr(e2)));
		}
		if (op.OR() != null) {
			return (factory.createOr(line, column, ExpressionOfExpr(e1),
					ExpressionOfExpr(e2)));
		}
		if (op.AND() != null) {
			return (factory.createAnd(line, column, ExpressionOfExpr(e1),
					ExpressionOfExpr(e2)));
		}
		if (op.LEQ() != null) {
			return (factory.createLessThanOrEqualTo(line, column,
					ExpressionOfExpr(e1), ExpressionOfExpr(e2)));
		}
		if (op.ADD() != null) {
			return (factory.createAdd(line, column, ExpressionOfExpr(e1),
					ExpressionOfExpr(e2)));
		}

		assert (false);
		return (null);
	}

	private E ExpressionOfUnop(WormsParserParser.UnopContext op) {
		if (op == null)
			return null;
		int line = op.getStart().getLine();
		int column = op.getStart().getCharPositionInLine();
		if (op.GETRADIUS() != null) {
			return (factory.createGetRadius(line, column,
					ExpressionOfExpr(op.expr())));
		}
		if (op.GETDIR() != null) {
			return (factory.createGetDir(line, column,
					ExpressionOfExpr(op.expr())));
		}
		if (op.GETY() != null) {
			return (factory.createGetY(line, column,
					ExpressionOfExpr(op.expr())));
		}
		if (op.GETX() != null) {
			return (factory.createGetX(line, column,
					ExpressionOfExpr(op.expr())));
		}
		if (op.GETAP() != null) {
			return (factory.createGetAP(line, column,
					ExpressionOfExpr(op.expr())));
		}
		if (op.GETMAXAP() != null) {
			return (factory.createGetMaxAP(line, column,
					ExpressionOfExpr(op.expr())));
		}
		if (op.GETHP() != null) {
			return (factory.createGetHP(line, column,
					ExpressionOfExpr(op.expr())));
		}
		if (op.GETMAXHP() != null) {
			return (factory.createGetMaxHP(line, column,
					ExpressionOfExpr(op.expr())));
		}
		if (op.NOT() != null) {
			return (factory
					.createNot(line, column, ExpressionOfExpr(op.expr())));
		}
		if (op.SQRT() != null) {
			return factory
					.createSqrt(line, column, ExpressionOfExpr(op.expr()));
		}
		if (op.SIN() != null) {
			return factory.createSin(line, column,
					(ExpressionOfExpr(op.expr())));
		}
		if (op.COS() != null) {
			return factory.createCos(line, column,
					(ExpressionOfExpr(op.expr())));
		}
		if (op.SAMETEAM() != null) {
			return factory.createSameTeam(line, column,
					ExpressionOfExpr(op.expr()));
		}
		if (op.SEARCHOBJ() != null) {
			return factory.createSearchObj(line, column,
					ExpressionOfExpr(op.expr()));
		}
		if (op.ISWORM() != null) {
			return factory.createIsWorm(line, column,
					ExpressionOfExpr(op.expr()));
		}
		if (op.ISFOOD() != null) {
			return factory.createIsFood(line, column,
					ExpressionOfExpr(op.expr()));
		}

		assert (false);
		return (null);
	}

	private E ExpressionOfExpr(WormsParserParser.ExprContext expr) {
		if (expr == null)
			return null;
		int line = expr.getStart().getLine();
		int column = expr.getStart().getCharPositionInLine();
		java.util.List<WormsParserParser.ExprContext> e = expr.expr();

		switch (e.size()) {
		case 0: {
			if (expr.namedconst() != null) {
				return (ExpressionOfNamedConst(expr.namedconst()));
			}
			if (expr.NUMBER() != null) {
				return (ExpressionOfNumber(expr.NUMBER()));
			}
			if (expr.IDENTIFIER() != null) {
				String varName =  expr
						.IDENTIFIER().getText();
				T type = globals.get(varName);
				E varExpr = factory.createVariableAccess(line, column, varName, type);
				if (varExpr == null) {
					varExpr = factory.createVariableAccess(line, column, varName);
				}
				return varExpr;
			}
			if (expr.unop() != null) {
				return (ExpressionOfUnop(expr.unop()));
			}
			break;
		}
		case 1: {
			return (ExpressionOfExpr(e.get(0)));
		}
		case 2: {
			if (expr.binop() != null) {
				return (ExpressionOfBinop(expr.binop(), e.get(0), e.get(1)));
			}
			break;
		}
		default: {
		}
		}

		assert (false);
		return (null);
	}

	// ------------------------------------------------------------------------
	// --- Interface Methods --------------------------------------------------
	// ------------------------------------------------------------------------
	// Most of this section is auto-generated by Antlr: This is a modified copy
	// of WormsParserBaseListener.java. Exceptions are getProgram() and the
	// implementations of method bodies below.
	// ------------------------------------------------------------------------

	public Map<String, T> getGlobals() {
		if (globals == null) {
			return Collections.emptyMap();
		} else {
			return globals;
		}
	}

	public S getStatement() {
		if (statement == null) {
			return factory.createSequence(0, 0, Collections.<S> emptyList());
		}
		return statement;
	}

	@Override
	public void enterForeach(WormsParserParser.ForeachContext ctx) {
	}

	@Override
	public void exitForeach(WormsParserParser.ForeachContext ctx) {
	}

	@Override
	public void enterWhiledo(WormsParserParser.WhiledoContext ctx) {
	}

	@Override
	public void exitWhiledo(WormsParserParser.WhiledoContext ctx) {
	}

	@Override
	public void enterAssign(WormsParserParser.AssignContext ctx) {
	}

	@Override
	public void exitAssign(WormsParserParser.AssignContext ctx) {
	}

	@Override
	public void enterIfthenelse(WormsParserParser.IfthenelseContext ctx) {
	}

	@Override
	public void exitIfthenelse(WormsParserParser.IfthenelseContext ctx) {
	}

	@Override
	public void enterUnop(WormsParserParser.UnopContext ctx) {
	}

	@Override
	public void exitUnop(WormsParserParser.UnopContext ctx) {
	}

	@Override
	public void enterEval(WormsParserParser.EvalContext ctx) {
		if (!evalVisited) {
			evalVisited = true;
			statement = StatementOfEval(ctx);
			// if(statement == null) {
			// errors.add("final statement null");
			// }
		}
	}

	@Override
	public void exitEval(WormsParserParser.EvalContext ctx) {
	}

	@Override
	public void enterAction(WormsParserParser.ActionContext ctx) {
	}

	@Override
	public void exitAction(WormsParserParser.ActionContext ctx) {
	}

	@Override
	public void enterNamedconst(WormsParserParser.NamedconstContext ctx) {
	}

	@Override
	public void exitNamedconst(WormsParserParser.NamedconstContext ctx) {
	}

	@Override
	public void enterExpr(WormsParserParser.ExprContext ctx) {
	}

	@Override
	public void exitExpr(WormsParserParser.ExprContext ctx) {
	}

	@Override
	public void enterType(WormsParserParser.TypeContext ctx) {
	}

	@Override
	public void exitType(WormsParserParser.TypeContext ctx) {
	}

	@Override
	public void enterCtrl(WormsParserParser.CtrlContext ctx) {
	}

	@Override
	public void exitCtrl(WormsParserParser.CtrlContext ctx) {
	}

	@Override
	public void enterEntityspec(WormsParserParser.EntityspecContext ctx) {
	}

	@Override
	public void exitEntityspec(WormsParserParser.EntityspecContext ctx) {
	}

	@Override
	public void enterBinop(WormsParserParser.BinopContext ctx) {
	}

	@Override
	public void exitBinop(WormsParserParser.BinopContext ctx) {
	}

	@Override
	public void enterDecl(WormsParserParser.DeclContext ctx) {
	}

	@Override
	public void exitDecl(WormsParserParser.DeclContext ctx) {
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
	}

	@Override
	public void visitTerminal(TerminalNode node) {
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		errors.add(node.getSymbol().getLine() + "."
				+ node.getSymbol().getCharPositionInLine() + ": "
				+ node.getText());
	}

	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
}
