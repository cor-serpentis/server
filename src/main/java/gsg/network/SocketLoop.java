package gsg.network;

import gsg.server.infrastructure.MessageRegistrator;
import gsg.threads.IJob;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
* @author zkejid@gmail.com
*         Created: 14.07.15 23:33
*/
public class SocketLoop implements IJob {

	private Socket socket;
	private String key;
	private DataInputStream is;
	private PrintStream os;
	private MessageRegistrator registrator;

	public SocketLoop(Socket socket, String key) {
		this.socket = socket;
		this.key = key;
		try {
			is = new DataInputStream(socket.getInputStream());
			os = new PrintStream(socket.getOutputStream());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doJob(JobRunnerConfiguration configuration, JobRunnerData jobRunnerData) {
		String line = null;
		while (true) {
			try {
				line = is.readLine();
				if (registrator != null) {
					registrator.registerMessage(key, line);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			System.out.println("command: " + line);
		}
	}

	public void setRegistrator(MessageRegistrator registrator) {
		this.registrator = registrator;
	}
}
