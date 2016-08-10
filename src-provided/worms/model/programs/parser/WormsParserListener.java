// Generated from WormsParser.g4 by ANTLR 4.2.2
 package worms.model.programs.parser; 
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link WormsParserParser}.
 */
public interface WormsParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link WormsParserParser#foreach}.
	 * @param ctx the parse tree
	 */
	void enterForeach(@NotNull WormsParserParser.ForeachContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#foreach}.
	 * @param ctx the parse tree
	 */
	void exitForeach(@NotNull WormsParserParser.ForeachContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#whiledo}.
	 * @param ctx the parse tree
	 */
	void enterWhiledo(@NotNull WormsParserParser.WhiledoContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#whiledo}.
	 * @param ctx the parse tree
	 */
	void exitWhiledo(@NotNull WormsParserParser.WhiledoContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(@NotNull WormsParserParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(@NotNull WormsParserParser.AssignContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#ifthenelse}.
	 * @param ctx the parse tree
	 */
	void enterIfthenelse(@NotNull WormsParserParser.IfthenelseContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#ifthenelse}.
	 * @param ctx the parse tree
	 */
	void exitIfthenelse(@NotNull WormsParserParser.IfthenelseContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#unop}.
	 * @param ctx the parse tree
	 */
	void enterUnop(@NotNull WormsParserParser.UnopContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#unop}.
	 * @param ctx the parse tree
	 */
	void exitUnop(@NotNull WormsParserParser.UnopContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#eval}.
	 * @param ctx the parse tree
	 */
	void enterEval(@NotNull WormsParserParser.EvalContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#eval}.
	 * @param ctx the parse tree
	 */
	void exitEval(@NotNull WormsParserParser.EvalContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#action}.
	 * @param ctx the parse tree
	 */
	void enterAction(@NotNull WormsParserParser.ActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#action}.
	 * @param ctx the parse tree
	 */
	void exitAction(@NotNull WormsParserParser.ActionContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#namedconst}.
	 * @param ctx the parse tree
	 */
	void enterNamedconst(@NotNull WormsParserParser.NamedconstContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#namedconst}.
	 * @param ctx the parse tree
	 */
	void exitNamedconst(@NotNull WormsParserParser.NamedconstContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull WormsParserParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(@NotNull WormsParserParser.ExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull WormsParserParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull WormsParserParser.TypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#ctrl}.
	 * @param ctx the parse tree
	 */
	void enterCtrl(@NotNull WormsParserParser.CtrlContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#ctrl}.
	 * @param ctx the parse tree
	 */
	void exitCtrl(@NotNull WormsParserParser.CtrlContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#entityspec}.
	 * @param ctx the parse tree
	 */
	void enterEntityspec(@NotNull WormsParserParser.EntityspecContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#entityspec}.
	 * @param ctx the parse tree
	 */
	void exitEntityspec(@NotNull WormsParserParser.EntityspecContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#binop}.
	 * @param ctx the parse tree
	 */
	void enterBinop(@NotNull WormsParserParser.BinopContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#binop}.
	 * @param ctx the parse tree
	 */
	void exitBinop(@NotNull WormsParserParser.BinopContext ctx);

	/**
	 * Enter a parse tree produced by {@link WormsParserParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(@NotNull WormsParserParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link WormsParserParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(@NotNull WormsParserParser.DeclContext ctx);
}