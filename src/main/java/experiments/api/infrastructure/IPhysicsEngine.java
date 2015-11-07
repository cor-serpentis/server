package experiments.api.infrastructure;

import experiments.api.notions.IDirection;
import experiments.api.notions.IFieldObject;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:33
 */
public interface IPhysicsEngine {
	void move(IFieldObject object, IDirection direction);
}
