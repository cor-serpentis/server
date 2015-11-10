package experiments.impl.infrastructure;

import com.google.common.collect.Maps;
import experiments.api.infrastructure.IPhysicsEngine;
import experiments.api.notions.IDirection;
import experiments.api.notions.IFieldObject;
import experiments.api.notions.IIdentifier;
import experiments.api.notions.IPosition;
import experiments.impl.notions.FieldPosition;

import java.util.Map;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:50
 */
public class PhysicsEngine implements IPhysicsEngine {

	private Map<IIdentifier, InnerData> objects = Maps.newHashMap();

	@Override
	public IPosition get(IIdentifier identifier) {
		if (identifier == null) {
			throw new RuntimeException("argument should not be null");
		}
		if (!objects.containsKey(identifier)) {
			throw new RuntimeException("Object with given id not found: "+identifier);
		}

		return FieldPosition.position(objects.get(identifier).position);
	}

	@Override
	public IIdentifier add(IFieldObject object) {
		if (object == null) {
			throw new RuntimeException("argument should not be null");
		}
		IIdentifier identifier = object.getId();
		if (objects.containsKey(identifier)) {
			throw new RuntimeException("object already added to engine: "+identifier);
		}

		final IPosition position = getDefaultPosition();
		final InnerData innerData = new InnerData(object, position);
		objects.put(identifier, innerData);

		return identifier;
	}

	@Override
	public void remove(IIdentifier identifier) {
		if (identifier == null) {
			throw new RuntimeException("argument should not be null");
		}
		if (!objects.containsKey(identifier)) {
			throw new RuntimeException("identifier should be added to engine before call 'remove'");
		}

		objects.remove(identifier);
	}

	@Override
	public void move(IIdentifier identifier, IDirection direction) {
		if (identifier == null) {
			throw new RuntimeException("identifier argument should not be null");
		}
		if (direction == null) {
			throw new RuntimeException("direction argument should not be null");
		}
		if (!objects.containsKey(identifier)) {
			throw new RuntimeException("identifier should be added to engine before call 'move'");
		}
		final InnerData innerData = objects.get(identifier);

		//TODO make physics check.

		innerData.position.move(direction);
	}

	@Override
	public void set(IIdentifier identifier, IPosition position) {
		if (identifier == null) {
			throw new RuntimeException("identifier argument should not be null");
		}
		if (position == null) {
			throw new RuntimeException("position argument should not be null");
		}
		if (!objects.containsKey(identifier)) {
			throw new RuntimeException("identifier should be added to engine before call 'set'");
		}
		final InnerData innerData = objects.get(identifier);

		//TODO make physics check.

		innerData.position.set(position);
	}

	private IPosition getDefaultPosition() {
		return FieldPosition.zeroPosition();
	}

	private static class InnerData {
		public final IFieldObject object;
		public final IPosition position;

		private InnerData(IFieldObject object, IPosition position) {
			this.object = object;
			this.position = position;
		}
	}
}
