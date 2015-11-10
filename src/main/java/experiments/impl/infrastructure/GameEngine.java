package experiments.impl.infrastructure;

import com.google.common.collect.Maps;
import experiments.api.infrastructure.IGameEngine;
import experiments.api.infrastructure.ITickAction;
import experiments.api.notions.IIdentifier;
import experiments.impl.notions.Identifier;

import java.util.Map;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:50
 */
public class GameEngine implements IGameEngine {
	private long ticks = 0;
	private Map<IIdentifier, ITickAction> actions = Maps.newHashMap();


	@Override
	public void doNextTick() {
		for (IIdentifier identifier : actions.keySet()) {
			final ITickAction action = actions.get(identifier);
			action.doAction();
		}
		ticks++;
	}

	@Override
	public long getTicksLeft() {
		return ticks;
	}

	@Override
	public IIdentifier addTickAction(ITickAction action) {
		if (action == null) {
			throw new RuntimeException("argument should not be null");
		}
		IIdentifier identifier = action.getId();
		actions.put(identifier, action);
		return identifier;
	}

	@Override
	public void removeTickAction(IIdentifier id) {
		if (id == null) {
			throw new RuntimeException("argument should not be null");
		}
		if (!actions.containsKey(id)) {
			throw new RuntimeException("No such key: "+id);
		}

		actions.remove(id);
	}
}
