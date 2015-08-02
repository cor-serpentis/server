package gsg.network;

import gsg.infrastructure.messages.ServerMessageRegistrator;
import gsg.network.provider.input.InputStreamProvider;
import gsg.threads.IJob;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;

import java.io.DataInputStream;
import java.io.IOException;

/**
* @author zkejid@gmail.com
*         Created: 14.07.15 23:33
*/
public class InputLoop implements IJob {

	private DataInputStream is;
	private final String name;
	private ServerMessageRegistrator registrator;

	public InputLoop(String name, InputStreamProvider input, ServerMessageRegistrator registrator) {
		this.name = name;
		this.registrator = registrator;
		try {
			is = new DataInputStream(input.getStream());

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
				registrator.registerMessage(name, line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("InputLoop: command: " + line);
	}
}
