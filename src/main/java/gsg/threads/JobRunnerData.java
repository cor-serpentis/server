package gsg.threads;

/**
 * @author Ivan Panfilov, folremo@axmor.com
 *         Created: 12.07.15 17:48
 */
public class JobRunnerData {
	private long maxTickTime = 0;
	private long currentTickTime = 0;
	private long totalTime = 0;
	private long finishedTicksCount = 0;

	public long getMaxTickTime() {
		return maxTickTime;
	}

	public void setMaxTickTime(long maxTickTime) {
		this.maxTickTime = maxTickTime;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public long getFinishedTicksCount() {
		return finishedTicksCount;
	}

	public void setFinishedTicksCount(long finishedTicksCount) {
		this.finishedTicksCount = finishedTicksCount;
	}

	public long getCurrentTickTime() {
		return currentTickTime;
	}

	public void setCurrentTickTime(long currentTickTime) {
		this.currentTickTime = currentTickTime;
	}
}
