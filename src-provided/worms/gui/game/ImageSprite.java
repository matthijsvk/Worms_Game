package worms.gui.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import worms.gui.GUIUtils;

public abstract class ImageSprite<T> extends Sprite<T> {

	// original image, at original scale
	private final BufferedImage originalImage;

	// only created when scale != 1.0
	private BufferedImage scaledImage;
	// only create when necessary
	private BufferedImage scaledImageHflipped;

	private boolean hflipped = false;

	private double scale;

	protected ImageSprite(PlayGameScreen screen, String filename) {
		super(screen);
		this.scale = 1.0;
		this.originalImage = loadImage(filename);
		this.scaledImage = originalImage;
	}

	@Override
	public double getWidth(Graphics2D g) {
		return getImageWidth() * scale;
	}

	@Override
	public double getHeight(Graphics2D g) {
		return getImageHeight() * scale;
	}

	public int getImageWidth() {
		return originalImage.getWidth();
	}

	public int getImageHeight() {
		return originalImage.getHeight();
	}

	public void setScale(double newScale) {
		if (newScale == this.scale) {
			return;
		}

		this.scale = newScale;
		if (newScale != 1.0) {
			this.scaledImage = toBufferedImage(originalImage.getScaledInstance(
					(int) (newScale * originalImage.getWidth()),
					(int) (newScale * originalImage.getHeight()),
					Image.SCALE_SMOOTH));
		} else {
			this.scaledImage = originalImage;
		}

		if (isHflipped()) {
			this.scaledImageHflipped = hflip(this.scaledImage);
		} else {
			this.scaledImageHflipped = null;
		}
	}

	public double getScale() {
		return scale;
	}

	protected Image getImageToDraw() {
		Image imageToDraw = scaledImage;
		if (isHflipped()) {
			if (scaledImageHflipped == null) {
				scaledImageHflipped = hflip(scaledImage);
			}
			imageToDraw = scaledImageHflipped;
		}
		return imageToDraw;
	}

	protected BufferedImage loadImage(String filename) {
		try {
			InputStream inputStream = GUIUtils.openResource(filename);
			BufferedImage result = ImageIO.read(inputStream);
			inputStream.close();
			return result;
		} catch (IOException e) {
			throw new RuntimeException(
					"Could not read file '" + filename + "'", e);
		}
	}

	public void setHflipped(boolean value) {
		hflipped = value;
	}

	public boolean isHflipped() {
		return hflipped;
	}

	protected static BufferedImage hflip(BufferedImage image) {
		BufferedImage flippedImage = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());
		Graphics2D flippedGraphics = flippedImage.createGraphics();
		flippedGraphics.scale(-1, 1);
		flippedGraphics.drawImage(image, -image.getWidth(null), 0, null);
		flippedGraphics.dispose();
		return flippedImage;
	}

	protected static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		BufferedImage result = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		Graphics2D resultGraphics = result.createGraphics();
		resultGraphics.drawImage(img, 0, 0, null);
		resultGraphics.dispose();

		return result;
	}

	@Override
	public void draw(Graphics2D g) {
		int x = (int) (getCenterX() - getWidth(g) / 2);
		int y = (int) (getCenterY() - getHeight(g) / 2);
		g.drawImage(getImageToDraw(), x, y, null);
	}
}