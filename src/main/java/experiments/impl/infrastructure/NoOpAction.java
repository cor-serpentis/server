package experiments.impl.infrastructure;

import experiments.api.infrastructure.ITickAction;
import experiments.api.notions.IIdentifier;
import experiments.impl.notions.Identifier;

/**
 * @author zkejid@gmail.com
 *         Created: 08.11.15 15:35
 */
public class NoOpAction implements ITickAction {

	private final IIdentifier identifier = Identifier.newId();

	@Override
	public void doAction() {}

	@Override
	public IIdentifier getId() {
		return identifier;
	}
}
