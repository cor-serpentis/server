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
	 * Сценарий: пустой мир отсчитал три тика.
	 */
	@Test
	public void testTicksLeft() {
		final GameEngine engine = new GameEngine();

		engine.doNextTick();
		engine.doNextTick();
		engine.doNextTick();

		assertEquals(3L, engine.getTicksLeft());
	}

	/**
	 * Сценарий: пустой мир отсчитал три тика. Каждый тик запустил одно действие.
	 */
	public void testDoTickAction() {
		final AtomicLong result = new AtomicLong(0L);
		final GameEngine engine = new GameEngine();
		final ITickAction action = new ITickAction() {
			@Override
			public void doAction() {
				result.incrementAndGet();
			}
		};
		engine.addTickAction(action);

		engine.doNextTick();
		engine.doNextTick();
		engine.doNextTick();

		assertEquals(result.get(), engine.getTicksLeft());
	}

	/**
	 * Сценарий: пустой мир отсчитал три тика. Каждый тик запустил одно действие.
	 * После чего мир отсчитал ещё три тика не выполнив действий.
	 */
	public void testDoTickActionAndDelete() {
		final AtomicLong result = new AtomicLong(0L);
		final GameEngine engine = new GameEngine();
		final ITickAction action = new ITickAction() {
			@Override
			public void doAction() {
				result.incrementAndGet();
			}
		};
		final IIdentifier actionId = engine.addTickAction(action);

		engine.doNextTick();
		engine.doNextTick();
		engine.doNextTick();

		engine.removeTickAction(actionId);

		engine.doNextTick();
		engine.doNextTick();
		engine.doNextTick();

		assertEquals(3L, result.get());
		assertEquals(6L, engine.getTicksLeft());
	}
}
