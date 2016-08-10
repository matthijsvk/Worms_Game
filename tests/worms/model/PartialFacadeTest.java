package worms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import worms.gui.game.IActionHandler;
import worms.model.programs.ParseOutcome;
import worms.model.programs.ParseOutcome.Success;
import worms.util.Util;

public class PartialFacadeTest {

	private static final double EPS = Util.DEFAULT_EPSILON;

	private IFacade facade;

	private Random random;

	private World world;

	// X X X X
	// . . . .
	// . . . .
	// X X X X
	private boolean[][] passableMap = new boolean[][] {
			{ false, false, false, false }, { true, true, true, true },
			{ true, true, true, true }, { false, false, false, false } };

	@Before
	public void setup() {
		facade = new Facade();
		random = new Random(7357);
		world = facade.createWorld(4.0, 4.0, passableMap, random);
	}

	@Test
	public void testMaximumActionPoints() {
		Worm worm = facade.createWorm(world, 1, 2, 0, 1, "Test", null);
		assertEquals(4448, facade.getMaxActionPoints(worm));
	}

	@Test
	public void testMoveHorizontal() {
		Worm worm = facade.createWorm(world, 1, 2, 0, 1, "Test", null);
		facade.move(worm);
		assertEquals(2, facade.getX(worm), EPS);
		assertEquals(2, facade.getY(worm), EPS);
	}

	@Test
	public void testMoveVertical() {
		Worm worm = facade.createWorm(world, 1, 1.5, Math.PI / 2, 0.5, "Test",
				null);
		facade.move(worm);
		assertEquals(1, facade.getX(worm), EPS);
		assertEquals(2.0, facade.getY(worm), EPS);
	}

	@Test
	public void testMoveVerticalAlongTerrain() {
		// . . X
		// . w X
		World world = facade.createWorld(3.0, 2.0, new boolean[][] {
				{ true, true, false }, { true, true, false } }, random);
		Worm worm = facade.createWorm(world, 1.5, 0.5,
				Math.PI / 2 - 10 * 0.0175, 0.5, "Test", null);
		facade.move(worm);
		assertEquals(1.5, facade.getX(worm), 0.1); //our method follows the direction of the worm more, and does not go completely straight up. It's endposition is still Adjacent, so that's not a problem.
		assertEquals(1.0, facade.getY(worm), 0.01);
		assertTrue(world.isAdjacentPosition(facade.getX(worm), facade.getY(worm), facade.getRadius(worm)));
	}

	@Test
	public void testFall() {
		// . X .
		// . w .
		// . . .
		// X X X
		World world = facade.createWorld(3.0, 4.0, new boolean[][] {
				{ true, false, true }, { true, true, true },
				{ true, true, true }, { false, false, false } }, random);
		Worm worm = facade.createWorm(world, 1.5, 2.5, -Math.PI / 2, 0.5,
				"Test", null);
		assertFalse(facade.canFall(worm));
		facade.move(worm);
		assertTrue(facade.canFall(worm));
		facade.fall(worm);
		assertEquals(1.5, facade.getX(worm), EPS);
		assertTrue("Worm must land at adjacent location",
				Util.fuzzyBetween(1.5, 1.55, facade.getY(worm), EPS));
	};
	
	@Test
	public void testProgram() {
		IActionHandler handler = new SimpleActionHandler(facade);
		World world = facade.createWorld(100.0, 100.0, new boolean[][] { {true}, {false} }, random);
		ParseOutcome<?> outcome = facade.parseProgram(""
				+ "double x;\n"
				+ "while (x < 1.5) {\n"
				+ "x := x + 0.1;\n"
				+ "}\n "
				+ "turn x;\n", handler);
		assertTrue(outcome.isSuccess());
		Program program = ((Success)outcome).getResult();
		Worm worm = facade.createWorm(world, 50.0, 50.51, 0, 0.5, "Test", program);
		facade.addNewWorm(world, null); // add another worm
		double oldOrientation = facade.getOrientation(worm);
		//facade.startGame(world); // this will run the program
		worm.getProgram().nextExec(); // run program only once!
		double newOrientation = facade.getOrientation(worm);
		assertEquals(oldOrientation + 1.5, newOrientation, EPS);
		assertNotEquals(worm, facade.getCurrentWorm(world)); // turn must end after executing program
	}

}
