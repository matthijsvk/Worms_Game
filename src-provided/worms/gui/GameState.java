package worms.gui;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import worms.gui.game.commands.Command;
import worms.model.IFacade;
import worms.model.World;
import worms.model.Worm;

public class GameState {

	private final Random random;
	private final IFacade facade;

	private final BlockingQueue<Double> timeDelta = new LinkedBlockingQueue<Double>(
			1);

	private World world;

	private final Level level;

	public GameState(IFacade facade, long randomSeed, Level level) {
		this.random = new Random(randomSeed);
		this.facade = facade;
		this.level = level;
	}

	public synchronized void createWorld() {
		level.load();
		world = facade.createWorld(level.getWorldWidth(),
				level.getWorldHeight(), level.getPassableMap(), random);
	}

	public IFacade getFacade() {
		return facade;
	}

	public Collection<Worm> getWorms() {
		return getFacade().getWorms(getWorld());
	}

	public void evolve(double dt) {
		timeDelta.clear(); // nobody was waiting for the previous tick, so
		// clear it
		timeDelta.offer(dt);
	}

	public boolean executeImmediately(Command cmd) {
		cmd.startExecution();
		while (!cmd.isTerminated()) {
			try {
				Double dt = timeDelta.poll(1000 / GUIConstants.FRAMERATE,
						TimeUnit.MILLISECONDS); // blocks, but allows repainting
												// if necessary
				if (dt != null) {
					cmd.update(dt);
				}
				cmd.getScreen().repaint(); // repaint while executing command
											// (which might block GUI thread)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return cmd.isExecutionCompleted();
	}

	public Level getLevel() {
		return level;
	}

	public synchronized World getWorld() {
		return world;
	}

	public Random getRandom() {
		return random;
	}

}
