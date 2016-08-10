package worms.model;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WorldTest {
	
	private static double delta = 0.001;
	
	private Worm testWorm;
	
	private Random random;
	
	private World testWorld;
	
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
	
	@Before
	public void setUp() throws Exception {
		testWorld = new World(4,4,passableMap,random); // 1 pixel/meter
    	testWorm = new Worm(testWorld, 1.0, 1.0, 0.0, 1.0,"Test worm");
//    	testWorld.addWorm(testWorm); // otherwise binding not bidirectional!!! (had to write new method in world)
	}

	@After
	public void tearDown() throws Exception {
		testWorm.terminate();
		testWorld.endGame();
	}
	
	// ----------- start tests --------------

	 // MAP of the testWorld
	// X X X X
	// . . . .
	// . . . .
	// . X X X
    @Test
    public final void isImPassable(){
    	assertFalse(testWorld.isImpassablePosition(2, 2, 0.99));
    	assertFalse(testWorld.isImpassablePosition(2, 2, 1)); 
    	assertFalse(testWorld.isImpassablePosition(0.1, 0.1, 0.1));
    	assertFalse(testWorld.isImpassablePosition(0.05, 0.05, .1));
    	assertFalse(testWorld.isImpassablePosition(10, -10, 0.99));
    	assertTrue(testWorld.isImpassablePosition(3, 3, 0.99));
    	assertTrue(testWorld.isImpassablePosition(2, 2, 1.1));
    }
    
 // MAP of the testWorld
 	// X X X X
 	// . . . .
 	// . . . .
 	// . X X X
    @Test
    public final void isAdjacent(){
    	assertFalse(testWorld.isAdjacentPosition(1, 1, 1.1));
    	assertFalse(testWorld.isAdjacentPosition(2, 0.99, 1.0)); 
    	assertFalse(testWorld.isAdjacentPosition(0, 1, 0.99));
    	assertTrue(testWorld.isAdjacentPosition(1, 1, 0.99));
    	assertTrue(testWorld.isAdjacentPosition(2, 1, 0.99));
    	assertTrue(testWorld.isAdjacentPosition(2, 2, 0.99));
    	assertTrue(testWorld.isAdjacentPosition(2, 2, 1.0));	
    }
    
    
    // . X .
 	// . w .
 	// . . .
 	// X X X
    @Test
    public final void isAdjacent2(){
    	World world = new World(3.0, 4.0, new boolean[][] {
			{ true, false, true }, { true, true, true },
			{ true, true, true }, { false, false, false } }, random);
    	Worm worm = new Worm(world, 1.5, 2.5, 3.0/2.0*Math.PI, 0.5,
			"Test");
    	assertTrue(world.isAdjacentPosition(worm.getXPosition(), worm.getYPosition(), worm.getRadius()));
    }
    
    @Test
    public final void pythagoras(){
    	double[] first = {1,1};
    	double[] second = {2,2};
    	double dist = World.pythagorianDistance(first,second);
    	assertEquals(Math.sqrt(2),dist,delta);
    }
    
    @Test
    public final void widthTest(){
    	double w = testWorld.getWidth();
    	assertEquals(4, w, delta);
    	assertTrue(World.isValidWidth(2));
    	assertFalse(World.isValidWidth(-1));
    }
    
    @Test
    public final void heightTest(){
    	double h = testWorld.getHeight();
    	assertEquals(4, h, delta);
    	assertTrue(World.isValidHeight(2));
    	assertFalse(World.isValidHeight(-1));
    }
    
    @Test
    public final void gameFlowTest(){
    	testWorld.startGame();
    	assertTrue(testWorld.isGameFinished()); //only one worm, so game finishes
    	testWorld.addNewWorm(null);; // 2 worms needed to play..
    	testWorld.startGame();
    	assertFalse(testWorld.isGameFinished());
    	testWorld.endGame();
    	assertTrue(testWorld.isGameFinished());
    	testWorld.setGameFinished(false);
    	assertFalse(testWorld.isGameFinished());
    	testWorld.startNextTurn();
    	assertFalse(testWorld.isGameFinished());
    	testWorld.removeWorm(testWorld.getActiveWorm());
    	testWorld.startNextTurn();
    	assertTrue(testWorld.isGameFinished());
    	
    }
    
    @Test
    public final void projectileTest(){
    	assertEquals(null,testWorld.getActiveProjectile());
    	Projectile p = new Projectile(testWorld,2.0,2.0,0.0,1,Guns.Rifle); // implicitly test setActiveProjectile(p)
    	assertEquals(p,testWorld.getActiveProjectile());
    	p.terminate(); // implicitly test setActiveProjectile(null)
    	assertEquals(null,testWorld.getActiveProjectile());
    }
    
    @Test
    public final void fullyInWorldTest(){
    	assertTrue(testWorld.isFullyInWorld(1, 1, 1));
    	assertFalse(testWorld.isFullyInWorld(5, 1, 1));
    	assertFalse(testWorld.isFullyInWorld(1, 5, 1));
    	assertFalse(testWorld.isFullyInWorld(1, 1, 10));
    }
    
    @Test
    public final void foodTest(){
    	assertEquals(0,testWorld.getAllFoodProtected().size());
    	testWorld.addNewFood();
    	assertEquals(1,testWorld.getAllFoodProtected().size());
    	testWorld.createFood(1, 1);
    	assertEquals(2,testWorld.getAllFoodProtected().size());
    	testWorld.removeFood(testWorld.getAllFoodProtected().getFirst());
    	assertEquals(1,testWorld.getAllFoodProtected().size());
    }
    
    @Test
    public final void wormTest(){
    	World testWorld2 = new World(4,4,passableMap,random); // exactly the same as World, but no worms in it
    	assertEquals(0,testWorld2.getAllWormsProtected().size());
    	testWorld2.addNewWorm(null);
    	assertEquals(1,testWorld2.getAllWormsProtected().size());
    	Worm w = testWorld2.createWorm(1.0, 1.0, 0.0, 1.0, "Hanspeter",null);
    	assertEquals(2,testWorld2.getAllWormsProtected().size());
    	assertTrue(testWorld2.getAllWormsProtected().contains(w));
    	testWorld2.removeWorm(testWorld2.getAllWormsProtected().getFirst());
    	assertEquals(1,testWorld2.getAllWormsProtected().size());
    }
    
    @Test
    public final void teamTest(){
    	assertEquals(0,testWorld.getAllTeamsProtected().size());
    	testWorld.addEmptyTeam("BobAndMatthijs");
    	assertEquals(1,testWorld.getAllTeamsProtected().size());
    	testWorld.addEmptyTeam("TeamAweSome");
    	Worm testWorm2 = new Worm(testWorld, 1.0, 1.0, 0.0, 1.0,"Test worm2");
    	testWorld.addWorm(testWorm2);
    	testWorld.getAllTeamsProtected().getFirst().addTeamMember(testWorm);
    	testWorld.getAllTeamsProtected().getFirst().addTeamMember(testWorm2);
    	testWorld.startGame();
    	testWorld.startNextTurn(); //removes empty team (implicitly tested removeTeam(team)
    	assertEquals(1,testWorld.getAllTeamsProtected().size());
    }
    
    @Test
    public final void allWorldObjectsTest(){
    	testWorld.addNewFood();
    	Projectile p = new Projectile(testWorld,2.0,2.0,0.0,1,Guns.Rifle);
    	testWorld.setActiveProjectile(p);
    	System.out.println(testWorld.getAllWorms());
    	assertEquals(3,testWorld.getAllWorldObjects().size());
    	assertEquals(1,testWorld.getAllWorldObjects("worm").size());
    	assertEquals(testWorm,testWorld.getAllWorldObjects("worm").getFirst());
    	assertEquals(1,testWorld.getAllWorldObjects("FoOd").size());
    	assertEquals(1,testWorld.getAllWorldObjects("PROJECTILE").size());
    }
    
    @Test
    public final void OverlapWithTest(){
    	// Overlapping food
    	testWorld.createFood(1, 1);
    	GameObject item = (GameObject) testWorm;
    	LinkedList<GameObject> dest = new LinkedList<>();
    	dest.add((GameObject)testWorld.getAllFoodProtected().getFirst());
    	double[] pos = {item.getXPosition(), item.getYPosition()};
    	assertTrue(testWorld.overlapsWith(pos, item.getRadius(), dest) == testWorld.getAllFoodProtected().getFirst());
    	testWorld.removeFood(testWorld.getAllFoodProtected().getFirst());
    	// Non-overlapping food
    	testWorld.createFood(3, 3);
    	dest = new LinkedList<>();
    	dest.add((GameObject)testWorld.getAllFoodProtected().getFirst());
    	pos[0] = item.getXPosition();
    	pos[1] = item.getYPosition();
    	assertFalse(testWorld.overlapsWith(pos, item.getRadius(), dest) == testWorld.getAllFoodProtected().getFirst());
    	testWorld.removeFood(testWorld.getAllFoodProtected().getFirst());
    	// Overlapping bullet
    	Projectile p = new Projectile(testWorld,1.0,1.0,0.0,1,Guns.Rifle);
    	testWorld.setActiveProjectile(p);
    	dest = new LinkedList<>();
    	dest.add((GameObject) testWorld.getActiveProjectile());
    	pos[0] = item.getXPosition();
    	pos[1] = item.getYPosition();
    	assertTrue(testWorld.overlapsWith(pos, item.getRadius(), dest) == testWorld.getActiveProjectile());
    }
    
}
