package worms.model.programs;

import java.util.List;

/**
 * A factory for creating expressions, statements and types.
 * 
 * The generic parameters E, S, and T must be instantiated with the concrete
 * classes that are used to represent expressions, statements and types,
 * respectively.
 * 
 * When an expression, statement or type declaration is encountered by the
 * parser, this will call the corresponding factory method.
 * 
 * The arguments of the factory methods contain the line and column number of
 * the text that is currently being parsed, and the expression objects (which
 * are previously created by also calling a factory method) that act as
 * arguments for the expression or statement, if any.
 * 
 * For example, when the following program is parsed: <code>
 *   double a;
 *   double b := 3.14;
 *   a := 1.0 - sin(b);
 *   jump;
 * </code> this results in the following calls on the factory (simplified
 * pseudocode): <code>
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
public interface ProgramFactory<E, S, T> {

	/* possible types for a foreach statement */

	public enum ForeachType {
		WORM, FOOD, ANY
	}

	/* expressions */

	/**
	 * Create an expression representing a double literal, with value d
	 */
	public E createDoubleLiteral(int line, int column, double d);

	/**
	 * Create an expression representing a boolean literal, with value b
	 */
	public E createBooleanLiteral(int line, int column, boolean b);

	/**
	 * Create an expression representing the logical and operation on two
	 * expressions e1 and e2
	 */
	public E createAnd(int line, int column, E e1, E e2);

	/**
	 * Create an expression representing the logical or operation on two
	 * expressions e1 and e2
	 */
	public E createOr(int line, int column, E e1, E e2);

	/**
	 * Create an expression representing the logical not operation on the
	 * expression e
	 */
	public E createNot(int line, int column, E e);

	/**
	 * Create an expression representing the literal 'null'
	 */
	public E createNull(int line, int column);

	/**
	 * Create an expression representing a reference to the worm that is
	 * executing the program
	 */
	public E createSelf(int line, int column);

	/**
	 * Create an expression to get the x-coordinate of the entity identified by
	 * the expression e
	 */
	public E createGetX(int line, int column, E e);

	/**
	 * Create an expression to get the y-coordinate of the entity identified by
	 * the expression e
	 */
	public E createGetY(int line, int column, E e);

	/**
	 * Create an expression to get the radius of the entity identified by the
	 * expression e
	 */
	public E createGetRadius(int line, int column, E e);

	/**
	 * Create an expression to get the direction of the entity identified by the
	 * expression e
	 */
	public E createGetDir(int line, int column, E e);

	/**
	 * Create an expression to get the action points of the entity identified by
	 * the expression e
	 */
	public E createGetAP(int line, int column, E e);

	/**
	 * Create an expression to get the maximum number of action points of the
	 * entity identified by the expression e
	 */
	public E createGetMaxAP(int line, int column, E e);

	/**
	 * Create an expression to get the hit points of the entity identified by
	 * the expression e
	 */
	public E createGetHP(int line, int column, E e);

	/**
	 * Create an expression to get the maximum number of hit points of the
	 * entity identified by the expression e
	 */
	public E createGetMaxHP(int line, int column, E e);

	/**
	 * Create an expression to evaluate whether the worm identified by the
	 * expression e belongs to the same team as the worm that is executing the
	 * program
	 */
	public E createSameTeam(int line, int column, E e);

	/**
	 * Create an expression to get the closest object in the direction theta+e,
	 * starting from the position of the worm that is executing the program,
	 * where theta is the current direction of the worm that is executing the
	 * program
	 */
	public E createSearchObj(int line, int column, E e);

	/**
	 * Create an expression that evaluates whether the entity identified by the
	 * expression e is a worm
	 */
	public E createIsWorm(int line, int column, E e);

	/**
	 * Create an expression that evaluates whether the entity identified by the
	 * expression e is a food ration
	 */
	public E createIsFood(int line, int column, E e);

	/**
	 * Create an expression that evaluates to the value of the variable with the
	 * given name
	 */
	public E createVariableAccess(int line, int column, String name);
	
	/**
	 * OPTIONAL METHOD
	 * This method is only relevant for students that choose to work on static type checking.
	 * You may ignore this method if you want,
	 * and only implement the version of this method without the type argument. 
	 * - If you do not use this method, return null.
	 * - Otherwise, return null from the other createVariableAccess method.
	 *  
	 * Create an expression that evaluates to the value of the variable with the
	 * given name.
	 * 
	 * The given type is the type of the variable with the given name,
	 * as determined while parsing the variable declarations in the program.
	 * 
	 * If the variable with the given name was not declared, the given type is null.  
	 */
	public E createVariableAccess(int line, int column, String name, T type);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * less than the value of the expression e2
	 */
	public E createLessThan(int line, int column, E e1, E e2);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * greater than the value of the expression e2
	 */
	public E createGreaterThan(int line, int column, E e1, E e2);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * less than or equal to the value of the expression e2
	 */
	public E createLessThanOrEqualTo(int line, int column, E e1, E e2);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * greater than or equal to the value of the expression e2
	 */
	public E createGreaterThanOrEqualTo(int line, int column, E e1, E e2);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * equal to the value of the expression e2
	 */
	public E createEquality(int line, int column, E e1, E e2);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * not equal to the value of the expression e2
	 */
	public E createInequality(int line, int column, E e1, E e2);

	/**
	 * Create an expression that represents the addition of the value of
	 * expression e1 and the value of the expression e2
	 */
	public E createAdd(int line, int column, E e1, E e2);

	/**
	 * Create an expression that represents the subtraction of the value of
	 * expression e1 and the value of the expression e2
	 */
	public E createSubtraction(int line, int column, E e1, E e2);

	/**
	 * Create an expression that represents the multiplication of the value of
	 * expression e1 and the value of the expression e2
	 */
	public E createMul(int line, int column, E e1, E e2);

	/**
	 * Create an expression that represents the division of the value of
	 * expression e1 and the value of the expression e2
	 */
	public E createDivision(int line, int column, E e1, E e2);

	/**
	 * Create an expression that represents the square root of the value of
	 * expression e1 and the value of the expression e2
	 */
	public E createSqrt(int line, int column, E e);

	/**
	 * Create an expression that represents the sine of the value of expression
	 * e1 and the value of the expression e2
	 */
	public E createSin(int line, int column, E e);

	/**
	 * Create an expression that represents the cosine of the value of
	 * expression e1 and the value of the expression e2
	 */
	public E createCos(int line, int column, E e);

	/* actions */

	/**
	 * Create a statement that represents a turn of the worm executing the
	 * program by the value of the angle expression
	 */
	public S createTurn(int line, int column, E angle);

	/**
	 * Create a statement that represents a move of the worm executing the
	 * program
	 */
	public S createMove(int line, int column);

	/**
	 * Create a statement that represents a jump of the worm executing the
	 * program
	 */
	public S createJump(int line, int column);

	/**
	 * Create a statement that represents toggling the weapon of the worm
	 * executing the program
	 */
	public S createToggleWeap(int line, int column);

	/**
	 * Create a statement that represents firing the current weapon of the worm
	 * executing the program, where the propulsion yield is given by the yield
	 * expression
	 */
	public S createFire(int line, int column, E yield);

	/**
	 * Create a statement that represents no action of a worm
	 */
	public S createSkip(int line, int column);

	/* other statements */

	/**
	 * Create a statement that represents the assignment of the value of the rhs
	 * expression to a variable with the given name
	 */
	public S createAssignment(int line, int column, String variableName, E rhs);

	/**
	 * Create a statement that represents the conditional execution of the
	 * statements then or otherwise, depending on the value of the condition
	 * expression
	 */
	public S createIf(int line, int column, E condition, S then, S otherwise);

	/**
	 * Create a statement that represents the repeated execution of the body
	 * statement, as long as the value of the condition expression evaluates to
	 * true
	 */
	public S createWhile(int line, int column, E condition, S body);

	/**
	 * Create a statement that represents the repeated execution of the body
	 * statement, where for each execution the value of the variable with the
	 * given name is set to a different object of the given type.
	 */
	public S createForeach(int line, int column, ForeachType type,
			String variableName, S body);

	/**
	 * Create a statement that represents the sequential execution of the given
	 * statements
	 */
	public S createSequence(int line, int column, List<S> statements);

	/**
	 * Create a statement that represents printing out the value of the
	 * expression e
	 */
	public S createPrint(int line, int column, E e);

	/* types */

	/**
	 * Returns an object that represents the type of a global variable with
	 * declared type 'double'.
	 */
	public T createDoubleType();

	/**
	 * Returns an object that represents the type of a global variable with
	 * declared type 'boolean'.
	 */
	public T createBooleanType();

	/**
	 * Returns an object that represents the type of a global variable with
	 * declared type 'entity'.
	 */
	public T createEntityType();
}
