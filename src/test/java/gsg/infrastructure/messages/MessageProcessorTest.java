package gsg.infrastructure.messages;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zkejid@gmail.com
 *         Created: 07.11.15 14:57
 */
public class MessageProcessorTest {

	@Test
	public void positiveTest() {
		final AtomicBoolean result = new AtomicBoolean(false);
		final MessageProcessor processor = prepare(result);
//		processor.process();
		//todo
	}

	private MessageProcessor prepare(final AtomicBoolean result) {
		MessageProcessor processor = new MessageProcessor();
		processor.root = processor.create(processor.create("node1", new MessageProcessor.MessageProcessorAction() {
			@Override
			public void doAction(String source, String arguments) {
				result.set(true);
			}
		}));
		return processor;
	}
}
