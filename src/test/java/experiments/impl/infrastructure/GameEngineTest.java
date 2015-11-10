package experiments.impl.infrastructure;


import experiments.api.infrastructure.ITickAction;
import experiments.api.notions.IIdentifier;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:54
 */
public class GameEngineTest {

	/**
	 * Scenario: 3 ticks left. Than 3 ticks more left, calling an action each time. Than 3 ticks more left without any action.
	 */
	@Test
	public void testDoTickActionAndDelete() {
		final AtomicLong result = new AtomicLong(0L);
		final GameEngine engine = new GameEngine();
		final ITickAction action = new NoOpAction() {
			@Override
			public void doAction() {
				result.incrementAndGet();
			}
		};

		final long checkpointZero = engine.getTicksLeft();

		engine.doNextTick();
		engine.doNextTick();
		engine.doNextTick();

		final long checkpointOne = engine.getTicksLeft();
		final IIdentifier actionId = engine.addTickAction(action);

		engine.doNextTick();
		engine.doNextTick();
		engine.doNextTick();

		final long checkpointTwo = engine.getTicksLeft();
		engine.removeTickAction(actionId);

		engine.doNextTick();
		engine.doNextTick();
		engine.doNextTick();

		final long checkpointThree = engine.getTicksLeft();

		assertEquals(0L, checkpointZero);
		assertEquals(3L, checkpointOne);
		assertEquals(6L, checkpointTwo);
		assertEquals(9L, checkpointThree);
		assertEquals(3L, result.get());
	}

	/**
	 * Scenario: null argument of 'addTickAction' should produce exception
	 */
	@Test(expected = RuntimeException.class)
	public void addTickActionNegative() {
		final GameEngine engine = new GameEngine();

		engine.addTickAction(null);
	}

	/**
	 * Scenario: null argument of 'removeTickAction' should produce exception
	 */
	@Test(expected = RuntimeException.class)
	public void removeTickActionNegativeNull() {
		final GameEngine engine = new GameEngine();

		engine.removeTickAction(null);
	}

	/**
	 * Scenario: remove action which doesn't exist should produce exception
	 */
	@Test(expected = RuntimeException.class)
	public void removeTickActionNegativeObsolete() {
		final GameEngine engine = new GameEngine();
		final IIdentifier identifier = engine.addTickAction(new NoOpAction());

		engine.removeTickAction(identifier);
		engine.removeTickAction(identifier);
	}
}
