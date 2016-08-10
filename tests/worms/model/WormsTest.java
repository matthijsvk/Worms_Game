package worms.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import worms.model.exceptions.*;
import worms.gui.GUIConstants;
import worms.gui.GUIOptions;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
@SuppressWarnings(value="all")
/**
 * This is the test class for Worm.java
 * 
 * 			DON'T FORGET TO ENABLE ASSERTIONS, OTHERWISE IT WON'T RUN!!!!!!!!!
 * 
 * @author bob, matthijs
 *
 */
public class WormsTest {
	
	/**
	 * a variable that stores the maximum difference between 2 doubles to be qualified as equal to each other.
	 */
	private static double delta = 0.001;
	
	private Worm testWorm;
	
	private Random random;
	
	private World testWorld;
	
	private Team testTeam;
	
	//testWorld map
	// X X X X
	// . . . .
	// . . . .
	// . X X X
	private final boolean[][] passableMap  = new boolean[][] 
			{ 	{ false, false, false, false }, 
				{ true, true, true, true },
				{ true, true, true, true }, 
				{ true, false, false, false } 
			};
	
	
	/**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    	testWorld = new World(4,4,passableMap,random); // 1 pixel/meter
    	testWorm = new Worm(testWorld, 1.0, 1.0, 0.0, 1.0,"Test worm");
//    	testWorld.addWorm(testWorm); // otherwise binding not bidirectional!!! (had to write new method in world)
    	testTeam = new Team("TestTeam"); // First letter of team had to be a Capital!
    	testWorm.addToTeam(testTeam);
    	
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
	

	// -------------------- Tests ----------------------------
	
 	// CONSTRUCTOR TESTS
	
	@Test
	public final void wormConstructorLegalCaseTest() { 
		//worm already constructed in setUp()
		assertEquals(testWorm.getXPosition(), 1, delta);
		assertTrue(testWorm.isValidXPosition(testWorm.getXPosition()));
		assertEquals(testWorm.getYPosition(),1, delta);
		assertTrue(testWorm.isValidYPosition(testWorm.getYPosition()));
		assertTrue(testWorld.isAdjacentPosition(testWorm.getXPosition(), testWorm.getYPosition(), testWorm.getRadius()));
		assertEquals(testWorm.getDirection(), 0, delta);
		assertEquals(testWorm.getWorld(), testWorld);
		assertEquals(testWorm.getTeam(), testTeam);
		assertEquals(testWorm.getTeam(), testTeam);
		assertTrue(testTeam.getAllTeamMembers().contains(testWorm));
		assertTrue(testWorm.getAllPosessedWeapons().contains(Guns.Rifle) && testWorm.getAllPosessedWeapons().contains(Guns.Bazooka));
		assertTrue((testWorm.getName()).equals("Test worm"));
		assertEquals(testWorm.getRemainingActionPoints(), testWorm.getMaxActionPoints());
		assertEquals(testWorm.getRemainingHitPoints(), testWorm.getMaxHitPoints(), delta);
	}
	
	@Test(expected = IllegalRadiusException.class)
	public final void constructorIllegalRadiusTest() {
		Worm testWorm = new Worm(testWorld,1,1,Math.PI/2,-3,"Test worm");
	}
	
	@Test(expected = IllegalNameException.class)
	public final void constructorIllegalNameTest() {
		Worm testWorm2 = new Worm(testWorld,1,1,Math.PI/2,1,"Test worm&**(&)");
	}
	
	@Test(expected = IllegalPositionException.class)
	public final void constructorIllegalYPositionTest() {  //x position out of bounds of the world
		Worm testWorm2 = new Worm(testWorld,1,7,Math.PI/2,1,"Test worm");
	}
	
	@Test(expected = IllegalPositionException.class)
	public final void constructorIllegalXPositionTest() {  //x position out of bounds of the world
		Worm testWorm2 = new Worm(testWorld,7,1,Math.PI/2,1,"Test worm");
	}
	
	@Test(expected = IllegalNameException.class)
	public final void constructorIllegalNameTeamTest() {
		Team testTeam2 = new Team("TestTeam9");
	}
	
	//	POSITION TESTS
	
	@Test
	public final void isValidPositionTrueTest() {
		assertTrue(testWorm.isValidXPosition(1));
		assertTrue(testWorm.isValidYPosition(1));
	}
	
	@Test
	public final void isValidPositionNaNTest() {
		assertFalse(testWorm.isValidXPosition(Double.NaN));
		assertFalse(testWorm.isValidYPosition(Double.NaN));
	}

	@Test
	public final void isValidPositionFalseTest() {
		// world : x = 0..4
		//		   y = 0..4
		assertTrue(testWorm.isValidXPosition(4));
		assertFalse(testWorm.isValidXPosition(-2));
		assertFalse(testWorm.isValidXPosition(5));
		assertTrue(testWorm.isValidYPosition(4));
		assertFalse(testWorm.isValidYPosition(-2));
		assertFalse(testWorm.isValidYPosition(5));
	}
	
	
	//	DIRECTION TESTS
	
	@Test
	public final void isValidDirectionTrueTest() {
		assertTrue(testWorm.isValidDirection(Math.PI));
	}
		
	@Test(expected = AssertionError.class)
	public final void FalseSetDirectionOverflowTest() {
		testWorm.setDirection(10);
	}
	
	@Test
	public final void TrueSetDirectionTest() {
		testWorm.setDirection(Math.PI);
		assertEquals(testWorm.getDirection(), Math.PI, delta);
	}
	
	
	// RADIUS TEST
	
	@Test
	public final void isValidRadiusTrueTest() {
		Worm testWorm = new Worm(testWorld,1,1,Math.PI/2,1,"Test worm");
		assertTrue(testWorm.isValidRadius(1));
	}
	
	@Test
	public final void isValidRadiusFalseTest() {
		Worm testWorm2 = new Worm(testWorld,1,1,Math.PI/2,1,"Test worm");
		assertFalse(testWorm2.isValidRadius(-1));
		assertFalse(testWorm2.isValidRadius(testWorm.getMinRadius()/2));
	}
	
	@Test(expected = IllegalRadiusException.class)
	public final void FalseSetRadiusTest() {
		testWorm.setRadius(0.1);
	}
	
	@Test
	public final void TrueSetRadiusTest() {
		testWorm.setRadius(1);
	}
	
	

	
	//  ACTION POINTS TEST
	
	@Test
	public final void setRemainingActionPoint_LegalCaseTest() {
		testWorm.setRemainingActionPoints(5);
		assertEquals(testWorm.getRemainingActionPoints(), 5);
	}
	
	@Test
	public final void setRemainingActionPoint_IllegalCaseTest() {
		int actionPoints = testWorm.getRemainingActionPoints();
		testWorm.setRemainingActionPoints(-5);
		assertEquals(testWorm.getRemainingActionPoints(), actionPoints);
		testWorm.setRemainingActionPoints(100000);
		assertEquals(testWorm.getRemainingActionPoints(), testWorm.getMaxActionPoints());
	}
	
	// HITPOINTS TESTS
	
	@Test
	public final void SetRemainingHitPointsLegalCaseTest() {
		testWorm.setRemainingHitPoints(2);
		assertEquals(2, testWorm.getRemainingHitPoints());
	}
	
	@Test
	public final void SetRemainingHitPointsIllegalTest(){
		testWorm.setRemainingHitPoints(1000000);
		assertEquals(testWorm.getMaxHitPoints(),testWorm.getRemainingHitPoints());
		testWorm.setRemainingHitPoints(0);
		assertTrue(testWorm.isTerminated());
	}
	
	@Test
	public final void SetRemainingHitPointsNegativeTest(){
		testWorm.setRemainingHitPoints(-5);
		assertTrue(testWorm.isTerminated());
	}
	
	// TURN TESTS
	
	@Test
	public final void Turn_LegalCaseTest() {
		int actionPoints = testWorm.getRemainingActionPoints();
		testWorm.turn(Math.PI/2);
		assertEquals(testWorm.getDirection(), Math.PI/2, delta); //starting angle = 0
		assertEquals(testWorm.getRemainingActionPoints(), actionPoints - 15 );
	}
	
	// MOVE TESTS
	@Test
	public final void isAbleToMove_Test() {
		assertTrue(testWorm.isAbleToMove());
		testWorm.setRemainingActionPoints(0);
		assertFalse(testWorm.isAbleToMove());
	}
	
	//testWorld map
		// X X X X
		// . . . .
		// . . . .
		// . X X X
	
	@Test
	public final void Move_LegalCaseTest() throws IllegalPositionException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalActionPointException {  //testWorm= 
		testWorm = new Worm(testWorld, 1.0, 1.0, 0.05, 1.0,"Test worm"); //not facing entirely to the right to test if adjacent pos are found in the 0- direction.
		int actionPoints = testWorm.getRemainingActionPoints();
		testWorm.move();
		assertEquals(2.0, testWorm.getXPosition(), testWorm.getRadius()/5.0 ); // moved one to the right, must go to adjacent position.
		assertEquals(actionPoints- 1, testWorm.getRemainingActionPoints() , 2);
		testWorm.setDirection(Math.PI/2); // lose no action points for turning
		testWorm.move(); // move up
		assertEquals(testWorm.getYPosition(), 1 + testWorm.getRadius(), testWorm.getRadius()/5.0 ); // moved one up, no adjacent to be found, so go in this.direction 
		assertEquals(testWorm.getXPosition(), 1 + testWorm.getRadius(), testWorm.getRadius()/5.0 );   //x stays the same
		assertEquals(testWorm.getRemainingActionPoints(), actionPoints- 1 - 4, 2);
	}
	
	@Test
	public void testMoveVertical() throws IllegalPositionException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalActionPointException {
		Worm testWorm = new Worm(testWorld, 1, 1.5, Math.PI / 2, 0.5, "Test");
		testWorm.move();
		assertEquals(1.0, testWorm.getXPosition(), delta);
		assertEquals(2.0, testWorm.getYPosition(), delta);
	}
	

	@Test
	public final void moveRightToAdjacentLegalCaseTest() throws IllegalPositionException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalActionPointException {
		testWorm = new Worm(testWorld, 1.0, 1.0, 0.0, 1.0,"Test worm");
		testWorm.move();
		assertEquals(testWorm.getXPosition(), 2.0, delta);
	}

	// JUMP TESTS
	/*	used an object from the class Worm to test the functionality of jump(). 
	 *  Jump() is Overridden in Worm, but this is only because of the action points.
	 */
	
	/* Using: testWorm (x=1,y=1,d=0,r=1)
	 *		  testWorld : 
	 *						X X X X
 	 *						. . . .
 	 *						. w . .
 	 *						. X X X
 	 * 
 	 */
	
	@Test
	public final void JumpLegalCaseTest() {	
		double timeStep = GUIConstants.JUMP_TIME_STEP;
		Worm testWorm2 = new Worm(testWorld, 1, 1, 1, 1, "Abcd");	
		testWorld.startGame();	
		// Successful jump
		testWorm.turn(Math.PI/3); // <- put it at a nice angle
		double[] hit = {-1,-1};
		hit = testWorm.getJumpStep(testWorm.getJumpTime(timeStep)); // jumpTime and jumpStep are tested in another function
		hit[1] = 3; //Worm hits ceiling
		testWorm.jump(timeStep);	
		assertEquals(0, testWorm.getRemainingActionPoints());
		assertEquals(testWorm.getXPosition(), hit[0],10*delta);
		assertEquals(testWorm.getYPosition(), hit[1],10*delta);
		
	}
	
	@Test
	public final void JumpTerminateTest() {
		// worm out of world
		double timeStep = GUIConstants.JUMP_TIME_STEP;
		testWorm.setDirection(Math.PI-Math.PI/8);
		testWorm.setXPosition(1);
		testWorm.setYPosition(1);
		testWorm.jump(timeStep);
		assert testWorm.isTerminated(); // yields true
		
	}
	
	@Test
	public final void getJumpStepLegalCaseTest(){
		testWorm.turn(Math.PI/4);
		// --- start position
		double[] begin = testWorm.getJumpStep(0);
		assertEquals(begin[0],testWorm.getXPosition(),delta);
		assertEquals(begin[1],testWorm.getYPosition(),delta);
		
		// --- position after 0.5 sec.
		// needed variables to test
		double time = 0.5;
		double v0 = testWorm.getV0();
		double d = Math.PI/4;
		double v0x = v0 * Math.cos(d);
		double v0y = v0 * Math.sin(d);
		double xDeltaT =  testWorm.getXPosition() + v0x * time;
		double yDeltaT = testWorm.getYPosition() + v0y * time - 0.5* 9.80665* Math.pow(time, 2);
		// actual function call
		double[] oneSec = testWorm.getJumpStep(time);
		// comparing results
		assertEquals(xDeltaT, oneSec[0], delta);
		assertEquals(yDeltaT, oneSec[1], delta);
		
	}
	
	@Test (expected = IllegalPositionException.class)
	public final void getJumpStepIllegalCaseTestOutOfWorld(){
		testWorm.turn(Math.PI/4);
		// position after 1 sec.
		double[] oneSec = testWorm.getJumpStep(1.0);
	}
	
	@Test
	public final void getJumpTimeLegalCaseTest(){
		testWorm.turn(Math.PI/3);
		double v0 = testWorm.getV0();
		double time = testWorm.getJumpTime(GUIConstants.JUMP_TIME_STEP);
		assertEquals(0.51579,time,delta);
	}
	
	// FALL
	
	/* Using: testWorm (x=1,y=1,d=0,r=1)
	 *		  testWorld : 
	 *						X X X X
 	 *						. . . .
 	 *						. w . .
 	 *						. X X X
 	 * 
 	 */
	
	@Test
	public void fallLegalCaseTest(){
		// still in world
		double y = testWorm.getYPosition();
		testWorm.setRadius(0.25);
		testWorm.setXPosition(2);
		testWorm.setYPosition(1.5);
		testWorm.fall();
		assertEquals(testWorm.getYPosition(), y,testWorm.getRadius());
		// Not in World any more
		testWorm.setXPosition(0.25);
		testWorm.setYPosition(1.5);
		testWorm.fall();
		assert testWorm.isTerminated(); // yields true
	}
	
	@Test (expected = IllegalAccessError.class)
	public void fallIllegalCaseTest(){
		testWorm.fall();
	}
	
	// SHOOT
	
	/* Using: testWorm1 (x=1,y=1,d=0,r=0.5) -> 1
	 * 		  testWorm2 (x=2,y=1,d=0,r=0.5) -> 2
	 *		  testWorld : 
	 *						X X X X
 	 *						. . . .
 	 *						. 1 2 .
 	 *						. X X X
 	 * 
 	 */
	
	@Test
	public void ShooootLegalCaseTest(){
		World testWorld2 = new World(4,4,passableMap,random); // same as default, but without testWorm
		Worm testWorm1 = new Worm(testWorld2,2.0,1.0,0.1,0.5,"TestWorm1");
//		testWorld2.addWorm(testWorm1);
		try {
			testWorm1.shoot(50);
			Projectile projectile = testWorm1.getWorld().getActiveProjectile();
			projectile.jump(GUIConstants.JUMP_TIME_STEP);
		} 
		catch (IllegalActionPointException e) {} //Mottige java-constructie
		assertEquals(testWorm1.getRemainingActionPoints(), testWorm1.getMaxActionPoints()- Guns.Rifle.getActionPointsConsumption());
		
		Worm testWorm2 = new Worm(testWorld2,3.5,1.0,0.0,0.5,"TestWorm2");
//		testWorld2.addWorm(testWorm2);
		testWorm1.setDirection(Math.PI/2.7); // very large projectile, so try to shoot it very softly, so it stays in this little world
		testWorm1.selectNextWeapon(); //Bazooka selected
		try {
			testWorm1.shoot(1);
			Projectile projectile= testWorm1.getWorld().getActiveProjectile();
			projectile.jump(GUIConstants.JUMP_TIME_STEP);
		} 
		catch (IllegalActionPointException e) {} //Mottige java-constructie
		assertEquals( testWorm2.getMaxHitPoints()- Guns.Bazooka.getHitpointsDamage(), testWorm2.getRemainingHitPoints());
	}

}
