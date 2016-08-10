// Generated from WormsParser.g4 by ANTLR 4.2.2
 package worms.model.programs.parser; 
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class WormsParserParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SELF=1, TRUE=2, FALSE=3, NULL=4, PRINT=5, BOOL=6, DOUBLE=7, ENTITY=8, 
		WORM=9, FOOD=10, TERRAIN=11, ANY=12, GETX=13, GETY=14, GETDIR=15, GETRADIUS=16, 
		GETAP=17, GETMAXAP=18, GETHP=19, GETMAXHP=20, SAMETEAM=21, SEARCHOBJ=22, 
		ISWORM=23, ISFOOD=24, SQRT=25, SIN=26, COS=27, NOT=28, TURN=29, MOVE=30, 
		JUMP=31, TOGGLEWEAP=32, FIRE=33, SKIP=34, IF=35, THEN=36, ELSE=37, WHILE=38, 
		DO=39, FOREACH=40, ASSIGN=41, MUL=42, DIV=43, ADD=44, SUB=45, EQ=46, NEQ=47, 
		LT=48, GT=49, LEQ=50, GEQ=51, AND=52, OR=53, NUMBER=54, FLOAT=55, INTEGER=56, 
		IDENTIFIER=57, LEFT_PAREN=58, RIGHT_PAREN=59, LEFT_BRACE=60, RIGHT_BRACE=61, 
		SEMICOLON=62, COMMA=63, WHITESPACE=64, SINGLE_COMMENT=65;
	public static final String[] tokenNames = {
		"<INVALID>", "'self'", "'true'", "'false'", "'null'", "'print'", "'bool'", 
		"'double'", "'entity'", "'worm'", "'food'", "'terrain'", "'any'", "'getx'", 
		"'gety'", "'getdir'", "'getradius'", "'getap'", "'getmaxap'", "'gethp'", 
		"'getmaxhp'", "'sameteam'", "'searchobj'", "'isworm'", "'isfood'", "'sqrt'", 
		"'sin'", "'cos'", "'!'", "'turn'", "'move'", "'jump'", "'toggleweap'", 
		"'fire'", "'skip'", "'if'", "'then'", "'else'", "'while'", "'do'", "'foreach'", 
		"':='", "'*'", "'/'", "'+'", "'-'", "'=='", "'!='", "'<'", "'>'", "'<='", 
		"'>='", "'&&'", "'||'", "NUMBER", "FLOAT", "INTEGER", "IDENTIFIER", "'('", 
		"')'", "'{'", "'}'", "';'", "','", "WHITESPACE", "SINGLE_COMMENT"
	};
	public static final int
		RULE_eval = 0, RULE_decl = 1, RULE_action = 2, RULE_unop = 3, RULE_ctrl = 4, 
		RULE_ifthenelse = 5, RULE_whiledo = 6, RULE_foreach = 7, RULE_assign = 8, 
		RULE_expr = 9, RULE_namedconst = 10, RULE_type = 11, RULE_entityspec = 12, 
		RULE_binop = 13;
	public static final String[] ruleNames = {
		"eval", "decl", "action", "unop", "ctrl", "ifthenelse", "whiledo", "foreach", 
		"assign", "expr", "namedconst", "type", "entityspec", "binop"
	};

	@Override
	public String getGrammarFileName() { return "WormsParser.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }




	public WormsParserParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class EvalContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(WormsParserParser.PRINT, 0); }
		public TerminalNode SEMICOLON() { return getToken(WormsParserParser.SEMICOLON, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public EvalContext eval() {
			return getRuleContext(EvalContext.class,0);
		}
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public CtrlContext ctrl() {
			return getRuleContext(CtrlContext.class,0);
		}
		public EvalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eval; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterEval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitEval(this);
		}
	}

	public final EvalContext eval() throws RecognitionException {
		EvalContext _localctx = new EvalContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_eval);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			switch (_input.LA(1)) {
			case BOOL:
			case DOUBLE:
			case ENTITY:
				{
				setState(28); decl();
				setState(29); match(SEMICOLON);
				}
				break;
			case TURN:
			case MOVE:
			case JUMP:
			case TOGGLEWEAP:
			case FIRE:
			case SKIP:
				{
				setState(31); action();
				setState(32); match(SEMICOLON);
				}
				break;
			case IDENTIFIER:
				{
				setState(34); assign();
				setState(35); match(SEMICOLON);
				}
				break;
			case PRINT:
				{
				setState(37); match(PRINT);
				setState(38); expr(0);
				setState(39); match(SEMICOLON);
				}
				break;
			case IF:
			case WHILE:
			case FOREACH:
				{
				setState(41); ctrl();
				}
				break;
			case SEMICOLON:
				{
				setState(42); match(SEMICOLON);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(46);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRINT) | (1L << BOOL) | (1L << DOUBLE) | (1L << ENTITY) | (1L << TURN) | (1L << MOVE) | (1L << JUMP) | (1L << TOGGLEWEAP) | (1L << FIRE) | (1L << SKIP) | (1L << IF) | (1L << WHILE) | (1L << FOREACH) | (1L << IDENTIFIER) | (1L << SEMICOLON))) != 0)) {
				{
				setState(45); eval();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(WormsParserParser.ASSIGN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(WormsParserParser.IDENTIFIER, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitDecl(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_decl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48); type();
			setState(49); match(IDENTIFIER);
			setState(52);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(50); match(ASSIGN);
				setState(51); expr(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionContext extends ParserRuleContext {
		public TerminalNode MOVE() { return getToken(WormsParserParser.MOVE, 0); }
		public TerminalNode TOGGLEWEAP() { return getToken(WormsParserParser.TOGGLEWEAP, 0); }
		public TerminalNode FIRE() { return getToken(WormsParserParser.FIRE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SKIP() { return getToken(WormsParserParser.SKIP, 0); }
		public TerminalNode JUMP() { return getToken(WormsParserParser.JUMP, 0); }
		public TerminalNode TURN() { return getToken(WormsParserParser.TURN, 0); }
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitAction(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_action);
		try {
			setState(62);
			switch (_input.LA(1)) {
			case TURN:
				enterOuterAlt(_localctx, 1);
				{
				setState(54); match(TURN);
				setState(55); expr(0);
				}
				break;
			case MOVE:
				enterOuterAlt(_localctx, 2);
				{
				setState(56); match(MOVE);
				}
				break;
			case JUMP:
				enterOuterAlt(_localctx, 3);
				{
				setState(57); match(JUMP);
				}
				break;
			case TOGGLEWEAP:
				enterOuterAlt(_localctx, 4);
				{
				setState(58); match(TOGGLEWEAP);
				}
				break;
			case FIRE:
				enterOuterAlt(_localctx, 5);
				{
				setState(59); match(FIRE);
				setState(60); expr(0);
				}
				break;
			case SKIP:
				enterOuterAlt(_localctx, 6);
				{
				setState(61); match(SKIP);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnopContext extends ParserRuleContext {
		public TerminalNode GETRADIUS() { return getToken(WormsParserParser.GETRADIUS, 0); }
		public TerminalNode ISWORM() { return getToken(WormsParserParser.ISWORM, 0); }
		public TerminalNode SIN() { return getToken(WormsParserParser.SIN, 0); }
		public TerminalNode GETDIR() { return getToken(WormsParserParser.GETDIR, 0); }
		public TerminalNode ISFOOD() { return getToken(WormsParserParser.ISFOOD, 0); }
		public TerminalNode GETMAXAP() { return getToken(WormsParserParser.GETMAXAP, 0); }
		public TerminalNode SEARCHOBJ() { return getToken(WormsParserParser.SEARCHOBJ, 0); }
		public TerminalNode COS() { return getToken(WormsParserParser.COS, 0); }
		public TerminalNode GETAP() { return getToken(WormsParserParser.GETAP, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode NOT() { return getToken(WormsParserParser.NOT, 0); }
		public TerminalNode SAMETEAM() { return getToken(WormsParserParser.SAMETEAM, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(WormsParserParser.RIGHT_PAREN, 0); }
		public TerminalNode GETY() { return getToken(WormsParserParser.GETY, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(WormsParserParser.LEFT_PAREN, 0); }
		public TerminalNode GETX() { return getToken(WormsParserParser.GETX, 0); }
		public TerminalNode GETHP() { return getToken(WormsParserParser.GETHP, 0); }
		public TerminalNode SQRT() { return getToken(WormsParserParser.SQRT, 0); }
		public TerminalNode GETMAXHP() { return getToken(WormsParserParser.GETMAXHP, 0); }
		public UnopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterUnop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitUnop(this);
		}
	}

	public final UnopContext unop() throws RecognitionException {
		UnopContext _localctx = new UnopContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_unop);
		try {
			setState(105);
			switch (_input.LA(1)) {
			case GETX:
				enterOuterAlt(_localctx, 1);
				{
				setState(64); match(GETX);
				setState(65); expr(0);
				}
				break;
			case GETY:
				enterOuterAlt(_localctx, 2);
				{
				setState(66); match(GETY);
				setState(67); expr(0);
				}
				break;
			case GETDIR:
				enterOuterAlt(_localctx, 3);
				{
				setState(68); match(GETDIR);
				setState(69); expr(0);
				}
				break;
			case GETRADIUS:
				enterOuterAlt(_localctx, 4);
				{
				setState(70); match(GETRADIUS);
				setState(71); expr(0);
				}
				break;
			case GETAP:
				enterOuterAlt(_localctx, 5);
				{
				setState(72); match(GETAP);
				setState(73); expr(0);
				}
				break;
			case GETMAXAP:
				enterOuterAlt(_localctx, 6);
				{
				setState(74); match(GETMAXAP);
				setState(75); expr(0);
				}
				break;
			case GETHP:
				enterOuterAlt(_localctx, 7);
				{
				setState(76); match(GETHP);
				setState(77); expr(0);
				}
				break;
			case GETMAXHP:
				enterOuterAlt(_localctx, 8);
				{
				setState(78); match(GETMAXHP);
				setState(79); expr(0);
				}
				break;
			case SAMETEAM:
				enterOuterAlt(_localctx, 9);
				{
				setState(80); match(SAMETEAM);
				setState(81); expr(0);
				}
				break;
			case SEARCHOBJ:
				enterOuterAlt(_localctx, 10);
				{
				setState(82); match(SEARCHOBJ);
				setState(83); expr(0);
				}
				break;
			case ISWORM:
				enterOuterAlt(_localctx, 11);
				{
				setState(84); match(ISWORM);
				setState(85); expr(0);
				}
				break;
			case ISFOOD:
				enterOuterAlt(_localctx, 12);
				{
				setState(86); match(ISFOOD);
				setState(87); expr(0);
				}
				break;
			case SQRT:
				enterOuterAlt(_localctx, 13);
				{
				setState(88); match(SQRT);
				setState(89); match(LEFT_PAREN);
				setState(90); expr(0);
				setState(91); match(RIGHT_PAREN);
				}
				break;
			case SIN:
				enterOuterAlt(_localctx, 14);
				{
				setState(93); match(SIN);
				setState(94); match(LEFT_PAREN);
				setState(95); expr(0);
				setState(96); match(RIGHT_PAREN);
				}
				break;
			case COS:
				enterOuterAlt(_localctx, 15);
				{
				setState(98); match(COS);
				setState(99); match(LEFT_PAREN);
				setState(100); expr(0);
				setState(101); match(RIGHT_PAREN);
				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 16);
				{
				setState(103); match(NOT);
				setState(104); expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CtrlContext extends ParserRuleContext {
		public WhiledoContext whiledo() {
			return getRuleContext(WhiledoContext.class,0);
		}
		public ForeachContext foreach() {
			return getRuleContext(ForeachContext.class,0);
		}
		public IfthenelseContext ifthenelse() {
			return getRuleContext(IfthenelseContext.class,0);
		}
		public CtrlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ctrl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterCtrl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitCtrl(this);
		}
	}

	public final CtrlContext ctrl() throws RecognitionException {
		CtrlContext _localctx = new CtrlContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_ctrl);
		try {
			setState(110);
			switch (_input.LA(1)) {
			case IF:
				enterOuterAlt(_localctx, 1);
				{
				setState(107); ifthenelse();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 2);
				{
				setState(108); whiledo();
				}
				break;
			case FOREACH:
				enterOuterAlt(_localctx, 3);
				{
				setState(109); foreach();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfthenelseContext extends ParserRuleContext {
		public TerminalNode THEN() { return getToken(WormsParserParser.THEN, 0); }
		public TerminalNode IF() { return getToken(WormsParserParser.IF, 0); }
		public EvalContext eval(int i) {
			return getRuleContext(EvalContext.class,i);
		}
		public List<TerminalNode> RIGHT_BRACE() { return getTokens(WormsParserParser.RIGHT_BRACE); }
		public TerminalNode LEFT_BRACE(int i) {
			return getToken(WormsParserParser.LEFT_BRACE, i);
		}
		public TerminalNode ELSE() { return getToken(WormsParserParser.ELSE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<EvalContext> eval() {
			return getRuleContexts(EvalContext.class);
		}
		public TerminalNode RIGHT_BRACE(int i) {
			return getToken(WormsParserParser.RIGHT_BRACE, i);
		}
		public List<TerminalNode> LEFT_BRACE() { return getTokens(WormsParserParser.LEFT_BRACE); }
		public IfthenelseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifthenelse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterIfthenelse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitIfthenelse(this);
		}
	}

	public final IfthenelseContext ifthenelse() throws RecognitionException {
		IfthenelseContext _localctx = new IfthenelseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_ifthenelse);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112); match(IF);
			setState(113); expr(0);
			setState(115);
			_la = _input.LA(1);
			if (_la==THEN) {
				{
				setState(114); match(THEN);
				}
			}

			setState(117); match(LEFT_BRACE);
			setState(119);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRINT) | (1L << BOOL) | (1L << DOUBLE) | (1L << ENTITY) | (1L << TURN) | (1L << MOVE) | (1L << JUMP) | (1L << TOGGLEWEAP) | (1L << FIRE) | (1L << SKIP) | (1L << IF) | (1L << WHILE) | (1L << FOREACH) | (1L << IDENTIFIER) | (1L << SEMICOLON))) != 0)) {
				{
				setState(118); eval();
				}
			}

			setState(121); match(RIGHT_BRACE);
			setState(128);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(122); match(ELSE);
				setState(123); match(LEFT_BRACE);
				setState(125);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRINT) | (1L << BOOL) | (1L << DOUBLE) | (1L << ENTITY) | (1L << TURN) | (1L << MOVE) | (1L << JUMP) | (1L << TOGGLEWEAP) | (1L << FIRE) | (1L << SKIP) | (1L << IF) | (1L << WHILE) | (1L << FOREACH) | (1L << IDENTIFIER) | (1L << SEMICOLON))) != 0)) {
					{
					setState(124); eval();
					}
				}

				setState(127); match(RIGHT_BRACE);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhiledoContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(WormsParserParser.WHILE, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(WormsParserParser.RIGHT_BRACE, 0); }
		public TerminalNode DO() { return getToken(WormsParserParser.DO, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public EvalContext eval() {
			return getRuleContext(EvalContext.class,0);
		}
		public TerminalNode LEFT_BRACE() { return getToken(WormsParserParser.LEFT_BRACE, 0); }
		public WhiledoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whiledo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterWhiledo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitWhiledo(this);
		}
	}

	public final WhiledoContext whiledo() throws RecognitionException {
		WhiledoContext _localctx = new WhiledoContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_whiledo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130); match(WHILE);
			setState(131); expr(0);
			setState(133);
			_la = _input.LA(1);
			if (_la==DO) {
				{
				setState(132); match(DO);
				}
			}

			setState(135); match(LEFT_BRACE);
			setState(137);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRINT) | (1L << BOOL) | (1L << DOUBLE) | (1L << ENTITY) | (1L << TURN) | (1L << MOVE) | (1L << JUMP) | (1L << TOGGLEWEAP) | (1L << FIRE) | (1L << SKIP) | (1L << IF) | (1L << WHILE) | (1L << FOREACH) | (1L << IDENTIFIER) | (1L << SEMICOLON))) != 0)) {
				{
				setState(136); eval();
				}
			}

			setState(139); match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForeachContext extends ParserRuleContext {
		public TerminalNode RIGHT_BRACE() { return getToken(WormsParserParser.RIGHT_BRACE, 0); }
		public TerminalNode DO() { return getToken(WormsParserParser.DO, 0); }
		public TerminalNode IDENTIFIER() { return getToken(WormsParserParser.IDENTIFIER, 0); }
		public EvalContext eval() {
			return getRuleContext(EvalContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(WormsParserParser.COMMA, 0); }
		public EntityspecContext entityspec() {
			return getRuleContext(EntityspecContext.class,0);
		}
		public TerminalNode LEFT_BRACE() { return getToken(WormsParserParser.LEFT_BRACE, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(WormsParserParser.RIGHT_PAREN, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(WormsParserParser.LEFT_PAREN, 0); }
		public TerminalNode FOREACH() { return getToken(WormsParserParser.FOREACH, 0); }
		public ForeachContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_foreach; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterForeach(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitForeach(this);
		}
	}

	public final ForeachContext foreach() throws RecognitionException {
		ForeachContext _localctx = new ForeachContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_foreach);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141); match(FOREACH);
			setState(142); match(LEFT_PAREN);
			setState(143); entityspec();
			setState(144); match(COMMA);
			setState(145); match(IDENTIFIER);
			setState(146); match(RIGHT_PAREN);
			setState(148);
			_la = _input.LA(1);
			if (_la==DO) {
				{
				setState(147); match(DO);
				}
			}

			setState(150); match(LEFT_BRACE);
			setState(152);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRINT) | (1L << BOOL) | (1L << DOUBLE) | (1L << ENTITY) | (1L << TURN) | (1L << MOVE) | (1L << JUMP) | (1L << TOGGLEWEAP) | (1L << FIRE) | (1L << SKIP) | (1L << IF) | (1L << WHILE) | (1L << FOREACH) | (1L << IDENTIFIER) | (1L << SEMICOLON))) != 0)) {
				{
				setState(151); eval();
				}
			}

			setState(154); match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(WormsParserParser.ASSIGN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(WormsParserParser.IDENTIFIER, 0); }
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitAssign(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156); match(IDENTIFIER);
			setState(157); match(ASSIGN);
			setState(158); expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public BinopContext binop() {
			return getRuleContext(BinopContext.class,0);
		}
		public UnopContext unop() {
			return getRuleContext(UnopContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode IDENTIFIER() { return getToken(WormsParserParser.IDENTIFIER, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(WormsParserParser.RIGHT_PAREN, 0); }
		public TerminalNode NUMBER() { return getToken(WormsParserParser.NUMBER, 0); }
		public NamedconstContext namedconst() {
			return getRuleContext(NamedconstContext.class,0);
		}
		public TerminalNode LEFT_PAREN() { return getToken(WormsParserParser.LEFT_PAREN, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			switch (_input.LA(1)) {
			case NUMBER:
				{
				setState(161); match(NUMBER);
				}
				break;
			case IDENTIFIER:
				{
				setState(162); match(IDENTIFIER);
				}
				break;
			case LEFT_PAREN:
				{
				setState(163); match(LEFT_PAREN);
				setState(164); expr(0);
				setState(165); match(RIGHT_PAREN);
				}
				break;
			case SELF:
			case TRUE:
			case FALSE:
			case NULL:
				{
				setState(167); namedconst();
				}
				break;
			case GETX:
			case GETY:
			case GETDIR:
			case GETRADIUS:
			case GETAP:
			case GETMAXAP:
			case GETHP:
			case GETMAXHP:
			case SAMETEAM:
			case SEARCHOBJ:
			case ISWORM:
			case ISFOOD:
			case SQRT:
			case SIN:
			case COS:
			case NOT:
				{
				setState(168); unop();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(177);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExprContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_expr);
					setState(171);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(172); binop();
					setState(173); expr(2);
					}
					} 
				}
				setState(179);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class NamedconstContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(WormsParserParser.TRUE, 0); }
		public TerminalNode SELF() { return getToken(WormsParserParser.SELF, 0); }
		public TerminalNode NULL() { return getToken(WormsParserParser.NULL, 0); }
		public TerminalNode FALSE() { return getToken(WormsParserParser.FALSE, 0); }
		public NamedconstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedconst; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterNamedconst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitNamedconst(this);
		}
	}

	public final NamedconstContext namedconst() throws RecognitionException {
		NamedconstContext _localctx = new NamedconstContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_namedconst);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SELF) | (1L << TRUE) | (1L << FALSE) | (1L << NULL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(WormsParserParser.BOOL, 0); }
		public TerminalNode ENTITY() { return getToken(WormsParserParser.ENTITY, 0); }
		public TerminalNode DOUBLE() { return getToken(WormsParserParser.DOUBLE, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << DOUBLE) | (1L << ENTITY))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EntityspecContext extends ParserRuleContext {
		public TerminalNode FOOD() { return getToken(WormsParserParser.FOOD, 0); }
		public TerminalNode ANY() { return getToken(WormsParserParser.ANY, 0); }
		public TerminalNode WORM() { return getToken(WormsParserParser.WORM, 0); }
		public EntityspecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entityspec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterEntityspec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitEntityspec(this);
		}
	}

	public final EntityspecContext entityspec() throws RecognitionException {
		EntityspecContext _localctx = new EntityspecContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_entityspec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << WORM) | (1L << FOOD) | (1L << ANY))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinopContext extends ParserRuleContext {
		public TerminalNode GEQ() { return getToken(WormsParserParser.GEQ, 0); }
		public TerminalNode NEQ() { return getToken(WormsParserParser.NEQ, 0); }
		public TerminalNode MUL() { return getToken(WormsParserParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(WormsParserParser.DIV, 0); }
		public TerminalNode AND() { return getToken(WormsParserParser.AND, 0); }
		public TerminalNode LT() { return getToken(WormsParserParser.LT, 0); }
		public TerminalNode OR() { return getToken(WormsParserParser.OR, 0); }
		public TerminalNode LEQ() { return getToken(WormsParserParser.LEQ, 0); }
		public TerminalNode GT() { return getToken(WormsParserParser.GT, 0); }
		public TerminalNode EQ() { return getToken(WormsParserParser.EQ, 0); }
		public TerminalNode SUB() { return getToken(WormsParserParser.SUB, 0); }
		public TerminalNode ADD() { return getToken(WormsParserParser.ADD, 0); }
		public BinopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).enterBinop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WormsParserListener ) ((WormsParserListener)listener).exitBinop(this);
		}
	}

	public final BinopContext binop() throws RecognitionException {
		BinopContext _localctx = new BinopContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_binop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << ADD) | (1L << SUB) | (1L << EQ) | (1L << NEQ) | (1L << LT) | (1L << GT) | (1L << LEQ) | (1L << GEQ) | (1L << AND) | (1L << OR))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3C\u00bf\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2.\n\2\3\2\5\2\61\n\2\3\3\3\3\3\3\3\3"+
		"\5\3\67\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4A\n\4\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\5\5l\n\5\3\6\3\6\3\6\5\6q\n\6\3\7\3\7\3\7\5\7v\n\7\3\7\3\7\5\7"+
		"z\n\7\3\7\3\7\3\7\3\7\5\7\u0080\n\7\3\7\5\7\u0083\n\7\3\b\3\b\3\b\5\b"+
		"\u0088\n\b\3\b\3\b\5\b\u008c\n\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5"+
		"\t\u0097\n\t\3\t\3\t\5\t\u009b\n\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00ac\n\13\3\13\3\13\3\13\3\13"+
		"\7\13\u00b2\n\13\f\13\16\13\u00b5\13\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17"+
		"\3\17\3\17\2\3\24\20\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\6\3\2\3\6\3"+
		"\2\b\n\4\2\13\f\16\16\3\2,\67\u00da\2-\3\2\2\2\4\62\3\2\2\2\6@\3\2\2\2"+
		"\bk\3\2\2\2\np\3\2\2\2\fr\3\2\2\2\16\u0084\3\2\2\2\20\u008f\3\2\2\2\22"+
		"\u009e\3\2\2\2\24\u00ab\3\2\2\2\26\u00b6\3\2\2\2\30\u00b8\3\2\2\2\32\u00ba"+
		"\3\2\2\2\34\u00bc\3\2\2\2\36\37\5\4\3\2\37 \7@\2\2 .\3\2\2\2!\"\5\6\4"+
		"\2\"#\7@\2\2#.\3\2\2\2$%\5\22\n\2%&\7@\2\2&.\3\2\2\2\'(\7\7\2\2()\5\24"+
		"\13\2)*\7@\2\2*.\3\2\2\2+.\5\n\6\2,.\7@\2\2-\36\3\2\2\2-!\3\2\2\2-$\3"+
		"\2\2\2-\'\3\2\2\2-+\3\2\2\2-,\3\2\2\2.\60\3\2\2\2/\61\5\2\2\2\60/\3\2"+
		"\2\2\60\61\3\2\2\2\61\3\3\2\2\2\62\63\5\30\r\2\63\66\7;\2\2\64\65\7+\2"+
		"\2\65\67\5\24\13\2\66\64\3\2\2\2\66\67\3\2\2\2\67\5\3\2\2\289\7\37\2\2"+
		"9A\5\24\13\2:A\7 \2\2;A\7!\2\2<A\7\"\2\2=>\7#\2\2>A\5\24\13\2?A\7$\2\2"+
		"@8\3\2\2\2@:\3\2\2\2@;\3\2\2\2@<\3\2\2\2@=\3\2\2\2@?\3\2\2\2A\7\3\2\2"+
		"\2BC\7\17\2\2Cl\5\24\13\2DE\7\20\2\2El\5\24\13\2FG\7\21\2\2Gl\5\24\13"+
		"\2HI\7\22\2\2Il\5\24\13\2JK\7\23\2\2Kl\5\24\13\2LM\7\24\2\2Ml\5\24\13"+
		"\2NO\7\25\2\2Ol\5\24\13\2PQ\7\26\2\2Ql\5\24\13\2RS\7\27\2\2Sl\5\24\13"+
		"\2TU\7\30\2\2Ul\5\24\13\2VW\7\31\2\2Wl\5\24\13\2XY\7\32\2\2Yl\5\24\13"+
		"\2Z[\7\33\2\2[\\\7<\2\2\\]\5\24\13\2]^\7=\2\2^l\3\2\2\2_`\7\34\2\2`a\7"+
		"<\2\2ab\5\24\13\2bc\7=\2\2cl\3\2\2\2de\7\35\2\2ef\7<\2\2fg\5\24\13\2g"+
		"h\7=\2\2hl\3\2\2\2ij\7\36\2\2jl\5\24\13\2kB\3\2\2\2kD\3\2\2\2kF\3\2\2"+
		"\2kH\3\2\2\2kJ\3\2\2\2kL\3\2\2\2kN\3\2\2\2kP\3\2\2\2kR\3\2\2\2kT\3\2\2"+
		"\2kV\3\2\2\2kX\3\2\2\2kZ\3\2\2\2k_\3\2\2\2kd\3\2\2\2ki\3\2\2\2l\t\3\2"+
		"\2\2mq\5\f\7\2nq\5\16\b\2oq\5\20\t\2pm\3\2\2\2pn\3\2\2\2po\3\2\2\2q\13"+
		"\3\2\2\2rs\7%\2\2su\5\24\13\2tv\7&\2\2ut\3\2\2\2uv\3\2\2\2vw\3\2\2\2w"+
		"y\7>\2\2xz\5\2\2\2yx\3\2\2\2yz\3\2\2\2z{\3\2\2\2{\u0082\7?\2\2|}\7\'\2"+
		"\2}\177\7>\2\2~\u0080\5\2\2\2\177~\3\2\2\2\177\u0080\3\2\2\2\u0080\u0081"+
		"\3\2\2\2\u0081\u0083\7?\2\2\u0082|\3\2\2\2\u0082\u0083\3\2\2\2\u0083\r"+
		"\3\2\2\2\u0084\u0085\7(\2\2\u0085\u0087\5\24\13\2\u0086\u0088\7)\2\2\u0087"+
		"\u0086\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008b\7>"+
		"\2\2\u008a\u008c\5\2\2\2\u008b\u008a\3\2\2\2\u008b\u008c\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\u008e\7?\2\2\u008e\17\3\2\2\2\u008f\u0090\7*\2\2"+
		"\u0090\u0091\7<\2\2\u0091\u0092\5\32\16\2\u0092\u0093\7A\2\2\u0093\u0094"+
		"\7;\2\2\u0094\u0096\7=\2\2\u0095\u0097\7)\2\2\u0096\u0095\3\2\2\2\u0096"+
		"\u0097\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u009a\7>\2\2\u0099\u009b\5\2"+
		"\2\2\u009a\u0099\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009c\3\2\2\2\u009c"+
		"\u009d\7?\2\2\u009d\21\3\2\2\2\u009e\u009f\7;\2\2\u009f\u00a0\7+\2\2\u00a0"+
		"\u00a1\5\24\13\2\u00a1\23\3\2\2\2\u00a2\u00a3\b\13\1\2\u00a3\u00ac\78"+
		"\2\2\u00a4\u00ac\7;\2\2\u00a5\u00a6\7<\2\2\u00a6\u00a7\5\24\13\2\u00a7"+
		"\u00a8\7=\2\2\u00a8\u00ac\3\2\2\2\u00a9\u00ac\5\26\f\2\u00aa\u00ac\5\b"+
		"\5\2\u00ab\u00a2\3\2\2\2\u00ab\u00a4\3\2\2\2\u00ab\u00a5\3\2\2\2\u00ab"+
		"\u00a9\3\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00b3\3\2\2\2\u00ad\u00ae\f\3"+
		"\2\2\u00ae\u00af\5\34\17\2\u00af\u00b0\5\24\13\4\u00b0\u00b2\3\2\2\2\u00b1"+
		"\u00ad\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2"+
		"\2\2\u00b4\25\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\u00b7\t\2\2\2\u00b7\27"+
		"\3\2\2\2\u00b8\u00b9\t\3\2\2\u00b9\31\3\2\2\2\u00ba\u00bb\t\4\2\2\u00bb"+
		"\33\3\2\2\2\u00bc\u00bd\t\5\2\2\u00bd\35\3\2\2\2\22-\60\66@kpuy\177\u0082"+
		"\u0087\u008b\u0096\u009a\u00ab\u00b3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}