package worms.model.programs;

import java.util.List;

import worms.model.Program;

/**
 * Represents the outcome of parsing a program.
 * The outcome can denote success or failure.
 * Depending on the outcome, the result may have a different type T.
 */
public abstract class ParseOutcome<T> {

	/**
	 * Create a new ParseOutcome that denotes a failure.
	 * The parse result is a list of error strings.
	 */
	public static Failure failure(List<String> errors) {
		return new Failure(errors);
	}

	/**
	 * Create a new ParseOutcome that denotes success.
	 * The parse result is a Program object that represents the parsed program.
	 */
	public static Success success(Program program) {
		return new Success(program);
	}

	public static class Failure extends ParseOutcome<List<String>> {

		private Failure(List<String> errors) {
			super(errors);
		}

		@Override
		public boolean isSuccess() {
			return false;
		}

	}

	public static class Success extends ParseOutcome<Program> {
		private Success(Program program) {
			super(program);
		}

		@Override
		public boolean isSuccess() {
			return true;
		}
	}

	private final T result;

	private ParseOutcome(T result) {
		this.result = result;
	}

	public abstract boolean isSuccess();

	protected final boolean isFailure() {
		return !isSuccess();
	}

	public T getResult() {
		return result;
	}
}
