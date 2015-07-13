package gsg.server;

import gsg.threads.IJob;
import gsg.threads.JobRunner;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;
import org.jbox2d.testbed.framework.*;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import javax.swing.*;

/**
 * @author Ivan Panfilov, folremo@axmor.com
 *         Created: 13.07.15 22:55
 */
public class Server {
	public static void main(String[] args) {
		final JobRunnerConfiguration configuration = new JobRunnerConfiguration();
		final Job job = new Job();
		JobRunner runner = new JobRunner(configuration, job);
		runner.start();
		try {
			runner.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static class Job implements IJob {
		private final TestbedTest frame;

		public Job() {
			frame = new DumbView();
			TestbedModel model = new TestbedModel();
			model.addTest(frame);
			TestbedPanel panel = new TestPanelJ2D(model);
			JFrame testbed = new TestbedFrame(model, panel, TestbedController.UpdateBehavior.UPDATE_CALLED);
			testbed.setVisible(true);
			testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		@Override
		public void doJob(JobRunnerConfiguration configuration, JobRunnerData jobRunnerData) {

		}
	}

}
