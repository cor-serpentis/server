package experiments.impl.notions;

import experiments.api.notions.IIdentifier;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 15:49
 */
public class Identifier implements IIdentifier {

	private Identifier(){
		super();
	}

	public static IIdentifier newId() {
		return new Identifier();
	}
}
