package gsg.server.logic;

import gsg.infrastructure.messages.Command;
import gsg.infrastructure.messages.IContainer;
import gsg.infrastructure.messages.MessageContainer;

/**
 * @author zkejid@gmail.com
 *         Created: 16.08.15 13:58
 */
public class MessageTransport implements ITransport {
	private MessageContainer out = new MessageContainer();
	private MessageContainer in = new MessageContainer();

	@Override
	public IContainer getSide1() {
		return new IContainer() {
			@Override
			public void registerMessage(String source, String line) {
				out.registerMessage(source, line);
			}

			@Override
			public Command getMessage() {
				return in.getMessage();
			}
		};
	}

	@Override
	public IContainer getSide2() {
		return new IContainer() {
			@Override
			public void registerMessage(String source, String line) {
				in.registerMessage(source, line);
			}

			@Override
			public Command getMessage() {
				return out.getMessage();
			}
		};
	}
}
