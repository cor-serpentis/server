package gsg.threads;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Ivan Panfilov, folremo@axmor.com
 *         Created: 12.07.15 17:47
 */
public class JobRunnerConfiguration {
	private boolean active = true;
	private long tickTime = 50;
	private boolean doLog = true;

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getTickTime() {
		return tickTime;
	}

	public void setTickTime(long tickTime) {
		this.tickTime = tickTime;
	}

	public boolean isDoLog() {
		return doLog;
	}

	public void setDoLog(boolean doLog) {
		this.doLog = doLog;
	}
}
