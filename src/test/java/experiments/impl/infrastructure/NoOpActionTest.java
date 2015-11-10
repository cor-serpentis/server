package experiments.impl.infrastructure;

import experiments.api.notions.IIdentifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zkejid@gmail.com
 *         Created: 08.11.15 15:40
 */
public class NoOpActionTest {

	@Test
	public void testIdTheSame() {
		final NoOpAction noOpAction = new NoOpAction();

		final IIdentifier id1 = noOpAction.getId();
		final IIdentifier id2 = noOpAction.getId();

		assertEquals(id1, id2);
	}
}
