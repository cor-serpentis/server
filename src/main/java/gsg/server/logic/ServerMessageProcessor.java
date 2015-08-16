package gsg.server.logic;

import gsg.infrastructure.messages.MessageProcessor;

/**
 * @author zkejid@gmail.com
 *         Created: 16.08.15 10:23
 */
public class ServerMessageProcessor extends MessageProcessor {
	public ServerMessageProcessor(DumbView core) {
		root = create(
				create("left", new Left(core)),
				create("right", new Right(core)),
				create("up", new Up(core)),
				create("down", new Down(core)),
				create("stop", new Stop(core))
		);
	}

	public static class Left implements MessageProcessorAction {

		private DumbView core;

		public Left(DumbView core) {
			this.core = core;
		}

		@Override
		public void doAction(String arguments) {
			core.left(arguments);
		}
	}

	public static class Right implements MessageProcessorAction {

		private DumbView core;

		public Right(DumbView core) {
			this.core = core;
		}

		@Override
		public void doAction(String arguments) {
			core.right(arguments);
		}
	}

	public static class Up implements MessageProcessorAction {

		private DumbView core;

		public Up(DumbView core) {
			this.core = core;
		}

		@Override
		public void doAction(String arguments) {
			core.up(arguments);
		}
	}

	public static class Down implements MessageProcessorAction {

		private DumbView core;

		public Down(DumbView core) {
			this.core = core;
		}

		@Override
		public void doAction(String arguments) {
			core.down(arguments);
		}
	}

	public static class Stop implements MessageProcessorAction {

		private DumbView core;

		public Stop(DumbView core) {
			this.core = core;
		}

		@Override
		public void doAction(String arguments) {
			core.stop(arguments);
		}
	}
}
