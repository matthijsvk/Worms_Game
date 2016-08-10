package worms.model.part3;

import java.util.List;

import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramParser;


public class ProgramFactoryImplementation
  implements ProgramFactory<Expression<Type>, Statement, Type>{
	
	private static ZeroArguments Z = new ZeroArguments<Type>();
	private static OneArgument O = new OneArgument();
	private static TwoArguments T = new TwoArguments();
	private static Control C = new Control();
	private static Action A = new Action();
	private ProgramParser<Expression<Type>,Statement,Type> parser;
	
public void setParser(ProgramParser<Expression<Type>,Statement,Type> parser){
	this.parser = parser;
}

  @Override
  public Expression<Type> createDoubleLiteral(int line, int column, double d) {
    return Z.new Constant(d, line);
  }

  @Override
  public Expression<Type> createBooleanLiteral(int line, int column, boolean b) {
	  if (b){
		  return Z.new True(line);
	  }
		  
	  else
		  return Z.new False(line);
  }

  @Override
  public Expression<Type> createAnd(int line, int column, Expression e1,
    Expression e2) {
    return T.new And(e1,e2,line);
  }

  @Override
  public Expression<Type> createOr(int line, int column, Expression e1,
    Expression e2) {
    return T.new Or(e1,e2,line);
  }

  @Override
  public Expression<Type> createNot(int line, int column, Expression e) {
    return O.new Not(e,line);
  }

  @Override
  public Expression<Type> createNull(int line, int column) {
    return Z.new Null(line);
  }

  @Override
  public Expression<Type> createSelf(int line, int column) {
    return Z.new Self(line);
  }

  @Override
  public Expression<Type> createGetX(int line, int column, Expression e) {
    return O.new Getx(e,line);
  }

  @Override
  public Expression<Type> createGetY(int line, int column, Expression e) {
    return O.new Gety(e,line);
  }

  @Override
  public Expression<Type> createGetRadius(int line, int column, Expression e) {
    return O.new Getradius(e,line);
  }

  @Override
  public Expression<Type> createGetDir(int line, int column, Expression e) {
    return O.new Getdirection(e,line);
  }

  @Override
  public Expression<Type> createGetAP(int line, int column, Expression e) {
    return O.new Getap(e,line);
  }

  @Override
  public Expression<Type> createGetMaxAP(int line, int column, Expression e) {
    return O.new Getmaxap(e,line);
  }

  @Override
  public Expression<Type> createGetHP(int line, int column, Expression e) {
    return O.new Gethp(e,line);
  }

  @Override
  public Expression<Type> createGetMaxHP(int line, int column, Expression e) {
    return O.new Getmaxhp(e,line);
  }

  @Override
  public Expression<Type> createSameTeam(int line, int column, Expression e) {
    return O.new SameTeam(e,line);
  }

  @Override
  public Expression<Type> createSearchObj(int line, int column, Expression e) {
    return O.new SearchObj(e,line);
  }

  @Override
  public Expression<Type> createIsWorm(int line, int column, Expression e) {
    return O.new IsWorm(e,line);
  }

  @Override
  public Expression<Type> createIsFood(int line, int column, Expression e) {
    return O.new IsFood(e,line);
  }

//  public E IsTerrain(int line, int column, E e);
  
  @Override
  public Expression<Type> createVariableAccess(int line, int column, String name) {
	  return null;
  }
  
  @Override
  public Expression<Type> createVariableAccess(int line, int column, String name, Type type) {
	  return Z.new Variable(name, type, line);
  }

  @Override
  public Expression<Type> createLessThan(int line, int column, Expression e1, Expression e2) {
    return T.new SmallerThan(e1,e2,line);
  }

  @Override
  public Expression<Type> createGreaterThan(int line, int column, Expression e1, Expression e2) {
    return T.new BiggerThan(e1,e2,line);
  }

  @Override
  public Expression<Type> createLessThanOrEqualTo(int line, int column, Expression e1, Expression e2) {
    return T.new SmallerOrEquals(e1,e2,line);
  }

  @Override
  public Expression<Type> createGreaterThanOrEqualTo(int line, int column, Expression e1, Expression e2) {
    return T.new BiggerOrEquals(e1,e2,line);
  }

  @Override
  public Expression<Type> createEquality(int line, int column, Expression e1, Expression e2) {
    return T.new Equals(e1,e2,line);
  }

  @Override
  public Expression<Type> createInequality(int line, int column, Expression e1, Expression e2) {
    return T.new NotEquals(e1,e2,line);
  }

  @Override
  public Expression<Type> createAdd(int line, int column, Expression e1, Expression e2) {
	  System.out.print("PROG Implementation add:");System.out.print(e1+ " "); ; System.out.println(e2+ " ");
    return T.new Add(e1,e2,line);
  }

  @Override
  public Expression<Type> createSubtraction(int line, int column, Expression e1, Expression e2) {
	  System.out.print("PROG Implementation substraction:");System.out.print(e1 +" "); System.out.println(e2+ " ");
    return T.new Substract(e1,e2,line);
  }

  @Override
  public Expression<Type> createMul(int line, int column, Expression e1, Expression e2) {
	  System.out.print("PROG Implementation multiply:"); System.out.println(e1+ " ");  
    return T.new Multiply(e1,e2,line);
  }

  @Override
  public Expression<Type> createDivision(int line, int column, Expression e1, Expression e2) {
    return T.new Divide(e1,e2,line);
  }

  @Override
  public Expression<Type> createSqrt(int line, int column, Expression e) {
    return O.new Sqrt(e,line);
  }

  @Override
  public Expression<Type> createSin(int line, int column, Expression e) {
    return O.new Sin(e,line);
  }

  @Override
  public Expression<Type> createCos(int line, int column, Expression e) {
    return O.new Cos(e,line);
  }

  @Override
  public Statement createTurn(int line, int column, Expression angle) {
    return A.new Turn(angle,line);
  }

  @Override
  public Statement createMove(int line, int column) {
    return A.new Move(line);
  }
  
  @Override
  public Statement createJump(int line, int column) {
    return A.new Jump(line);
  }

  @Override
  public Statement createToggleWeap(int line, int column) {
    return A.new ToggleWeapon(line);
  }

  @Override
  public Statement createFire(int line, int column, Expression yield) {
    return A.new Fire(yield,line);
  }
  
  @Override
  public Statement createAssignment(int line, int column, String variable, Expression rhs) {
//	  System.out.println(this.parser.getListener().getGlobals().get(variable));
	  Type t = (Type) this.parser.getListener().getGlobals().get(variable);
	  return new Assignment(variable, rhs,line, t);
  }

  @Override
  public Statement createIf(int line, int column, Expression condition, Statement then, Statement otherwise) {
    return C.new If(condition, then, otherwise,line);
  }

  @Override
  public Statement createWhile(int line, int column, Expression condition, Statement body) {
    return C.new While(condition,body,line);
  }

  @Override
  public Statement createForeach(int line, int column, ForeachType type, String variableName, Statement body) {
	  return C.new ForEach(type, variableName, body,line);
    
  }

  @Override
  public Statement createSkip(int line, int column) {
    return A.new Skip(line);
  }

  @Override
  public Statement createSequence(int line, int column, List<Statement> statements) {
    return new SequenceOfStatements(statements,line);
  }

  @Override
  public Statement createPrint(int line, int column, Expression e) {
    return new Print(e,line);
  }

  @Override
  public Type createDoubleType() {
    return new Double(1);
  }

  @Override
  public Type createBooleanType() {
    return new Boolean(true); //we have a positive attitude
  }

  @Override
  public Type createEntityType() {
	  return new Entity();
  }

}
