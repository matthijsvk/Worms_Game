package worms.model;

import worms.gui.game.IActionHandler;

/**
 * A simple action handler that just calls the necessary methods on the facade.
 * Useful for testing purposes.
 */
public class SimpleActionHandler implements IActionHandler {

	private final IFacade facade;

	public SimpleActionHandler(IFacade facade) {
		this.facade = facade;
	}

	@Override
	public boolean turn(Worm worm, double angle) {
		try {
			if (facade.canTurn(worm, angle)) {
				facade.turn(worm, angle);
				return true;
			}
		} catch (ModelException e) {
		}
		return false;
	}

	@Override
	public boolean toggleWeapon(Worm worm) {
		try {
			facade.selectNextWeapon(worm);
			return true;
		} catch (ModelException e) {
		}
		return false;
	}

	@Override
	public void print(String message) {
		System.out.println(message);
	}

	@Override
	public boolean move(Worm worm) {
		try {
			if (facade.canMove(worm)) {
				facade.move(worm);
				if (facade.canFall(worm)) {
					facade.fall(worm);
				}
				return true;
			}
		} catch (ModelException e) {
		}
		return false;
	}

	@Override
	public boolean jump(Worm worm) {
		try {
			facade.jump(worm, 1e-4);
			if (facade.canFall(worm)) {
				facade.fall(worm);
			}
			return true;
		} catch (ModelException e) {
		}
		return false;
	}

	@Override
	public boolean fire(Worm worm, int propulsion) {
		try {
			facade.shoot(worm, propulsion);
			return true;
		} catch (ModelException e) {
		}
		return false;
	}
}