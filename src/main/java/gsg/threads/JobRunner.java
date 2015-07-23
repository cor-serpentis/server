package gsg.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Ivan Panfilov, folremo@axmor.com
 *         Created: 12.07.15 17:47
 */
public class JobRunner extends Thread {

	private Logger logger = LogManager.getLogger();

	private JobRunnerData data = new JobRunnerData();
	private JobRunnerConfiguration configuration = null;
	private IJob job = null;

	public JobRunner(JobRunnerConfiguration configuration, IJob job) {
		this.configuration = configuration;
		this.job = job;
	}

	@Override
	public void run() {
		while (configuration.getActive()) {
			final long startTime = getCurrentTime();
			job.doJob(configuration, data);
			final long finishTime = getCurrentTime();
			adjustTimeData(startTime, finishTime);
			final long sleepTime = getSleepTime();
			sleepNow(sleepTime);
			doLog();
		}
	}

	public JobRunnerData getData() {
		return data;
	}

	public JobRunnerConfiguration getConfiguration() {
		return configuration;
	}

	/*************************/

	private long getCurrentTime() {
		return System.currentTimeMillis();
	}

	private void adjustTimeData(long startTime, long finishTime) {
		final long tickTime = finishTime - startTime;
		data.setCurrentTickTime(tickTime);
		if (tickTime > data.getMaxTickTime()) {
			data.setMaxTickTime(tickTime);
		}
		data.setTotalTime(data.getTotalTime() + tickTime);
		data.setFinishedTicksCount(data.getFinishedTicksCount() + 1);
	}

	private long getSleepTime() {
		final long expectedTickTime = configuration.getTickTime();
		final long currentTickTime = data.getCurrentTickTime();
		if (expectedTickTime > currentTickTime) {
			return expectedTickTime - currentTickTime;
		}
		else {
			return expectedTickTime;
		}
	}

	private void sleepNow(long sleepTime){
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void doLog() {
		final boolean doLog = configuration.isDoLog();
		if (doLog) {
			final String msg = merge(
					"Tick done: ", data.getFinishedTicksCount(), "\n",
					"Max tick time: ", data.getMaxTickTime(), "\n",
					"Avg tick time: ", getAvgTickTime(), "\n"
			);
			logger.info(msg);
		}
	}

	private double getAvgTickTime() {
		return data.getTotalTime() / data.getFinishedTicksCount();
	}

	private String merge(Object ... msg) {
		StringBuilder sb = new StringBuilder();
		for (Object s : msg) {
			sb.append(s);
		}
		return sb.toString();
	}
}
