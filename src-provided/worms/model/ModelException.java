package worms.model;

@SuppressWarnings("serial")
/**
 * <code>Facade</code> is not allowed to throw exceptions except for <code>ModelException</code>.
 * 
 * Do not use ModelException outside of <code>Facade</code>.
 */
public class ModelException extends RuntimeException {
	public ModelException(String message) {
		super(message);
	}

	public ModelException(Throwable cause) {
		super(cause);
	}

	public ModelException(String message, Throwable cause) {
		super(message, cause);
	}
}