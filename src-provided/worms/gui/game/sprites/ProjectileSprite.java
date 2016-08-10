package worms.gui.game.sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import worms.gui.GUIUtils;
import worms.gui.game.PlayGameScreen;
import worms.gui.game.Sprite;
import worms.model.Projectile;

public class ProjectileSprite extends Sprite<Projectile> {

	private static final int MIN_DISPLAY_SIZE = 3; // pixels
	private static final int MAX_DISPLAY_SIZE = 10; // pixels
	private static final int DISPLAY_SCALE = 10;

	private static final int NB_HISTORY = 15;
	private static final Color[] colors = new Color[NB_HISTORY];
	static {
		for (int i = 0; i < colors.length; i++) {
			float t = (float) i / colors.length;
			colors[i] = new Color(1.0F, (1 - t), 0, 0.8F);
		}
	}

	private final Projectile projectile;

	private double sizeInPixels;

	@SuppressWarnings("serial")
	private static class LimitedQueue<E> extends LinkedList<E> {
		private int limit;

		public LimitedQueue(int limit) {
			this.limit = limit;
		}

		@Override
		public boolean add(E o) {
			boolean result = super.add(o);
			while (size() > limit) {
				super.remove();
			}
			return result;
		}
	}

	private final List<double[]> lastLocations = Collections.synchronizedList(new LimitedQueue<double[]>(
			NB_HISTORY));

	public ProjectileSprite(PlayGameScreen screen, Projectile projectile) {
		super(screen);
		this.projectile = projectile;
	}

	@Override
	public synchronized void draw(Graphics2D g) {
		for (int i = 0; i < lastLocations.size(); i++) {
			double[] loc = lastLocations.get(i);
			g.setColor(colors[i]);
			double t = (double) (i + 1) / lastLocations.size();
			double size = sizeInPixels * t * t * t;
			g.fill(GUIUtils.circleAt(loc[0], loc[1], size));
		}
	}

	@Override
	public Projectile getObject() {
		return getProjectile();
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public synchronized void setSize(double sizeInPixels) {
		this.sizeInPixels = Math.min(MAX_DISPLAY_SIZE,
				Math.max(MIN_DISPLAY_SIZE, DISPLAY_SCALE * sizeInPixels));
	}

	@Override
	public double getHeight(Graphics2D g) {
		return sizeInPixels;
	}

	@Override
	public double getWidth(Graphics2D g) {
		return sizeInPixels;
	}

	@Override
	public boolean isObjectAlive() {
		return getFacade().isActive(getProjectile());
	}

	@Override
	public synchronized void setCenterLocation(double x, double y) {
		super.setCenterLocation(x, y);
		if (!lastLocations.isEmpty()) {
			// do some averaving to show some intermediate positions for
			// fast-moving object (rifle projectiles)

			if (lastLocations.size() > 1) {
				double[] c1 = lastLocations.get(lastLocations.size() - 1);
				double[] c2 = lastLocations.get(0);
				lastLocations.add(new double[] { (c1[0] + c2[0]) / 2,
						(c1[1] + c2[1]) / 2 });
			}
			double[] prev = lastLocations.get(lastLocations.size() - 1);
			lastLocations.remove(prev);

			for (int i = 0; i < NB_HISTORY; i++) {
				if (prev != null) {
					double t = (double) i / (NB_HISTORY);
					lastLocations.add(new double[] { t * x + (1 - t) * prev[0],
							t * y + (1 - t) * prev[1] });
				}
			}
		}
		lastLocations.add(new double[] { x, y });
	}
}
