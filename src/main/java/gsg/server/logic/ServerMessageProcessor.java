package gsg.server.logic;

import gsg.infrastructure.messages.IContainer;
import gsg.infrastructure.messages.MessageProcessor;

/**
 * @author zkejid@gmail.com
 *         Created: 16.08.15 10:23
 */
public class ServerMessageProcessor extends MessageProcessor {

	public ServerMessageProcessor(DumbView core, IContainer messageInterface) {
		root = create(
				create("left", new Left(core, messageInterface)),
				create("right", new Right(core, messageInterface)),
				create("up", new Up(core, messageInterface)),
				create("down", new Down(core, messageInterface)),
				create("stop", new Stop(core, messageInterface))
		);
	}

	public static class Left implements MessageProcessorAction {

		private DumbView core;

		public Left(DumbView core, IContainer messageInterface) {
			this.core = core;
		}

		@Override
		public void doAction(String source, String arguments) {
			core.left(arguments);
		}
	}

	public static class Right implements MessageProcessorAction {

		private DumbView core;

		public Right(DumbView core, IContainer messageInterface) {
			this.core = core;
		}

		@Override
		public void doAction(String source, String arguments) {
			core.right(arguments);
		}
	}

	public static class Up implements MessageProcessorAction {

		private DumbView core;

		public Up(DumbView core, IContainer messageInterface) {
			this.core = core;
		}

		@Override
		public void doAction(String source, String arguments) {
			core.up(arguments);
		}
	}

	public static class Down implements MessageProcessorAction {

		private DumbView core;
		private IContainer messageInterface;

		public Down(DumbView core, IContainer messageInterface) {
			this.core = core;
			this.messageInterface = messageInterface;
		}

		@Override
		public void doAction(String source, String arguments) {
			core.down(arguments);
			messageInterface.registerMessage(source, "down "+source);
		}
	}

	public static class Stop implements MessageProcessorAction {

		private DumbView core;

		public Stop(DumbView core, IContainer messageInterface) {
			this.core = core;
		}

		@Override
		public void doAction(String source, String arguments) {
			core.stop(arguments);
		}
	}
}
