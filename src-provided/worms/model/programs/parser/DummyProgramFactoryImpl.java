package worms.model.programs.parser;

import java.util.List;

import worms.model.programs.ProgramFactory;

class DummyExpression {

}

class DummyStatement {

}

class DummyType {

}

public class DummyProgramFactoryImpl implements
		ProgramFactory<DummyExpression, DummyStatement, DummyType> {

	@Override
	public DummyExpression createDoubleLiteral(int line, int column, double d) {
		return (null);
	}

	@Override
	public DummyExpression createBooleanLiteral(int line, int column, boolean b) {
		return (null);
	}

	@Override
	public DummyExpression createAnd(int line, int column, DummyExpression e1,
			DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createOr(int line, int column, DummyExpression e1,
			DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createNot(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createNull(int line, int column) {
		return (null);
	}

	@Override
	public DummyExpression createSelf(int line, int column) {
		return (null);
	}

	@Override
	public DummyExpression createGetX(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createGetY(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createGetRadius(int line, int column,
			DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createGetDir(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createGetAP(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createGetMaxAP(int line, int column,
			DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createGetHP(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createGetMaxHP(int line, int column,
			DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createSameTeam(int line, int column,
			DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createSearchObj(int line, int column,
			DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createIsWorm(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createIsFood(int line, int column, DummyExpression e) {
		return (null);
	}

	// public E IsTerrain(int line, int column, E e);

	@Override
	public DummyExpression createVariableAccess(int line, int column,
			String name) {
		return null;
	}

	@Override
	public DummyExpression createVariableAccess(int line, int column,
			String name, DummyType type) {
		return (null);
	}

	@Override
	public DummyExpression createLessThan(int line, int column,
			DummyExpression e1, DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createGreaterThan(int line, int column,
			DummyExpression e1, DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createLessThanOrEqualTo(int line, int column,
			DummyExpression e1, DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createGreaterThanOrEqualTo(int line, int column,
			DummyExpression e1, DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createEquality(int line, int column,
			DummyExpression e1, DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createInequality(int line, int column,
			DummyExpression e1, DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createAdd(int line, int column, DummyExpression e1,
			DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createSubtraction(int line, int column,
			DummyExpression e1, DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createMul(int line, int column, DummyExpression e1,
			DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createDivision(int line, int column,
			DummyExpression e1, DummyExpression e2) {
		return (null);
	}

	@Override
	public DummyExpression createSqrt(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createSin(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyExpression createCos(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyStatement createTurn(int line, int column, DummyExpression angle) {
		return (null);
	}

	@Override
	public DummyStatement createMove(int line, int column) {
		return (null);
	}

	@Override
	public DummyStatement createJump(int line, int column) {
		return (null);
	}

	@Override
	public DummyStatement createToggleWeap(int line, int column) {
		return (null);
	}

	@Override
	public DummyStatement createFire(int line, int column, DummyExpression yield) {
		return (null);
	}

	@Override
	public DummyStatement createAssignment(int line, int column,
			String variable, DummyExpression rhs) {
		return (null);
	}

	@Override
	public DummyStatement createIf(int line, int column,
			DummyExpression condition, DummyStatement then,
			DummyStatement otherwise) {
		return (null);
	}

	@Override
	public DummyStatement createWhile(int line, int column,
			DummyExpression condition, DummyStatement body) {
		return (null);
	}

	@Override
	public DummyStatement createForeach(int line, int column, ForeachType type,
			String variableName, DummyStatement body) {
		return (null);
	}

	@Override
	public DummyStatement createSkip(int line, int column) {
		return (null);
	}

	@Override
	public DummyStatement createSequence(int line, int column,
			List<DummyStatement> statements) {
		return (null);
	}

	@Override
	public DummyStatement createPrint(int line, int column, DummyExpression e) {
		return (null);
	}

	@Override
	public DummyType createDoubleType() {
		return (null);
	}

	@Override
	public DummyType createBooleanType() {
		return (null);
	}

	@Override
	public DummyType createEntityType() {
		return (null);
	}
}
