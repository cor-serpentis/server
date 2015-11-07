package gsg.infrastructure.messages;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author zkejid@gmail.com
 *         Created: 02.08.15 13:04
 */
public class MessageContainer implements IContainer {

	private ConcurrentLinkedQueue<Command> commands;

	public MessageContainer() {
		this.commands = new ConcurrentLinkedQueue<Command>();
	}

	@Override
	public void registerMessage(String source, String line) {
		commands.add(new Command(line, source));
		System.out.println("MessageContainer: source: "+source+" message: "+line);
	}

	@Override
	public Command getMessage() {
		return commands.poll();
	}

}
