package experiments.impl.infrastructure;

import experiments.api.infrastructure.IPhysicsEngine;
import experiments.api.notions.IDirection;
import experiments.api.notions.IFieldObject;
import experiments.api.notions.IIdentifier;
import experiments.api.notions.IPosition;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:50
 */
public class PhysicsEngine implements IPhysicsEngine {
	@Override
	public IPosition get(IIdentifier identifier) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public IIdentifier add(IFieldObject object) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void remove(IIdentifier identifier) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void move(IIdentifier identifier, IDirection direction) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void set(IIdentifier identifier, IPosition position) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
