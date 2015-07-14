package gsg.server.infrastructure;

import gsg.threads.IJob;
import gsg.threads.JobRunner;
import gsg.threads.JobRunnerConfiguration;

/**
 * @author zkejid@gmail.com
 *         Created: 14.07.15 23:34
 */
public class ServerUtils {
	public static JobRunner runLoop(IJob job) {
		final JobRunnerConfiguration configuration = new JobRunnerConfiguration();
		final JobRunner runner = new JobRunner(configuration, job);
		runner.start();
		return runner;
	}

	public static void runLoopAndJoin(IJob job) {
		final JobRunner runner = runLoop(job);
		try {
			runner.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
