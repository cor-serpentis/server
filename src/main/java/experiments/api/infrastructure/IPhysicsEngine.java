package experiments.api.infrastructure;

import experiments.api.notions.IDirection;
import experiments.api.notions.IFieldObject;
import experiments.api.notions.IIdentifier;
import experiments.api.notions.IPosition;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:33
 */
public interface IPhysicsEngine {
	IIdentifier add(IFieldObject object);
	void remove(IIdentifier identifier);
	void move(IIdentifier identifier, IDirection direction);
	void set(IIdentifier identifier, IPosition position);
	IPosition get(IIdentifier identifier);
}
