package experiments.impl.infrastructure;

import experiments.api.infrastructure.IGameEngine;
import experiments.api.infrastructure.ITickAction;
import experiments.api.notions.IIdentifier;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:50
 */
public class GameEngine implements IGameEngine {
	@Override
	public void doNextTick() {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public long getTicksLeft() {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public IIdentifier addTickAction(ITickAction action) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void removeTickAction(IIdentifier id) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
