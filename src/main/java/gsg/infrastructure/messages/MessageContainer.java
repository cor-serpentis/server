package gsg.infrastructure.messages;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author zkejid@gmail.com
 *         Created: 02.08.15 13:04
 */
public class MessageContainer implements ServerMessageRegistrator {

	private ConcurrentLinkedQueue<Command> commands;

	public MessageContainer() {
		this.commands = new ConcurrentLinkedQueue<Command>();
	}

	@Override
	public void registerMessage(String source, String line) {
		commands.add(new Command(line, source));
		System.out.println("MessageContainer: source: "+source+" message: "+line);
	}

	public Command getMessage() {
		return commands.poll();
	}

	public static class Command {
		public final String source;
		public final String line;

		public Command(String line, String source) {
			this.line = line;
			this.source = source;
		}
	}
}