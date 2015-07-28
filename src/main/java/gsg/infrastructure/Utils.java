package gsg.infrastructure;

import gsg.threads.IJob;
import gsg.threads.JobRunner;
import gsg.threads.JobRunnerConfiguration;

/**
 * @author zkejid@gmail.com
 *         Created: 23.07.15 9:37
 */
public class Utils {

	public static String getMessage(String line) {
		final int i = line.indexOf(" ");
		if (i > -1 && i < line.length()) {
			return line.substring(i+1, line.length());
		}
		else {
			return null;
		}
	}

	public static String getKey(String line) {
		final int i = line.indexOf(" ");
		if (i > -1) {
			return line.substring(0, i);
		}
		else {
			return null;
		}
	}

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
