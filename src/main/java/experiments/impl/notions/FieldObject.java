package experiments.impl.notions;

import experiments.api.notions.IFieldObject;
import experiments.api.notions.IIdentifier;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:49
 */
public class FieldObject implements IFieldObject {

	private IIdentifier iIdentifier = Identifier.newId();;

	@Override
	public IIdentifier getId() {
		return iIdentifier;
	}
}
