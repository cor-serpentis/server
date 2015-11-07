package gsg.network;

import gsg.network.provider.input.OutputStreamProvider;
import gsg.threads.IJob;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;

import java.io.PrintStream;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
* @author zkejid@gmail.com
*         Created: 14.07.15 23:33
*/
public class OutputLoop implements IJob {

	private final PrintStream os;
	private final ConcurrentLinkedQueue<String> commands;

	public OutputLoop(OutputStreamProvider provider) {
		try {
			os = new PrintStream(provider.getStream());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		commands = new ConcurrentLinkedQueue<String>();
	}

	@Override
	public void onStart() {}

	@Override
	public void doJob(JobRunnerConfiguration configuration, JobRunnerData jobRunnerData) {
		final String poll = commands.poll();
		if (poll != null) {
			os.println(poll);
			System.out.println("OutputLoop: "+poll);
		}
	}

	public void registerMessage(String line) {
		commands.add(line);
	}
}
