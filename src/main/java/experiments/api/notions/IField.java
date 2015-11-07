package experiments.api.notions;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:26
 */
public interface IField {

	IIdentifier addObject(IFieldObject object);
	void removeObject(IIdentifier id);
}
