package gsg.network;

import gsg.infrastructure.MessageRegistrator;
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

	private String key;
	private DataInputStream is;
	private MessageRegistrator registrator;

	public InputLoop(InputStreamProvider input, String key) {
		this.key = key;
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
				registrator.registerMessage(line);
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
