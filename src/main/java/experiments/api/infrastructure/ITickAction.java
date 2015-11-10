package experiments.api.infrastructure;

import experiments.api.notions.IIdentifier;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:38
 */
public interface ITickAction {
	void doAction();

	IIdentifier getId();
}
