package gsg.network;

import gsg.infrastructure.MessageRegistrator;
import gsg.threads.IJob;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
* @author zkejid@gmail.com
*         Created: 14.07.15 23:33
*/
public class SocketInputLoop implements IJob {

	private Socket socket;
	private String key;
	private DataInputStream is;
	private MessageRegistrator registrator;

	public SocketInputLoop(Socket socket, String key) {
		this.socket = socket;
		this.key = key;
		try {
			is = new DataInputStream(socket.getInputStream());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doJob(JobRunnerConfiguration configuration, JobRunnerData jobRunnerData) {
		String line;
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

	public void setRegistrator(MessageRegistrator registrator) {
		this.registrator = registrator;
	}
}
