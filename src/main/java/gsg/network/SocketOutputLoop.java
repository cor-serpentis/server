package gsg.network;

import gsg.server.infrastructure.MessageRegistrator;
import gsg.threads.IJob;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;

import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

/**
* @author zkejid@gmail.com
*         Created: 14.07.15 23:33
*/
public class SocketOutputLoop implements IJob, MessageRegistrator {

	private Socket socket;
	private String key;
	private PrintStream os;
	private ArrayBlockingQueue<Command> commands;

	public SocketOutputLoop(Socket socket, String key) {
		this.socket = socket;
		this.key = key;
		try {
			os = new PrintStream(socket.getOutputStream());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		commands = new ArrayBlockingQueue<Command>(10);
	}

	@Override
	public void doJob(JobRunnerConfiguration configuration, JobRunnerData jobRunnerData) {
		final Command poll = commands.poll();
		if (poll != null) {
			os.println(poll.message);
			System.out.println("source: "+poll.source+" command: " + poll.message);
		}
	}

	@Override
	public void registerMessage(String source, String message) {
		commands.add(new Command(source, message));
	}

	private class Command {
		public final String source;
		public final String message;

		private Command(String source, String message) {
			this.source = source;
			this.message = message;
		}
	}
}
