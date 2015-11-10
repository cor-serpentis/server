package experiments.impl.notions;

import experiments.api.notions.IIdentifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author zkejid@gmail.com
 *         Created: 08.11.15 15:44
 */
public class FieldObjectTest {

	@Test
	public void testIdTheSame() {
		final FieldObject object = new FieldObject();

		final IIdentifier id1 = object.getId();
		final IIdentifier id2 = object.getId();

		assertEquals(id1, id2);
	}
}
