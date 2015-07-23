package gsg.threads;

/**
 * @author Ivan Panfilov, folremo@axmor.com
 *         Created: 12.07.15 17:56
 */
public interface IJob {
	void doJob(JobRunnerConfiguration configuration, JobRunnerData data);
}
