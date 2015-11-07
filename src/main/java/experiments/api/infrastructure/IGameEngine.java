package experiments.api.infrastructure;

import experiments.api.notions.IIdentifier;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:37
 */
public interface IGameEngine {
	void doNextTick();
	long getTicksLeft();
	IIdentifier addTickAction(ITickAction action);
	void removeTickAction(IIdentifier id);
}
