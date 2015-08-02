package gsg.client;

import gsg.infrastructure.messages.MessageRegistrator;
import gsg.threads.IJob;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;

/**
 * @author zkejid@gmail.com
 *         Created: 23.07.15 9:18
 */
public class BotLoop implements IJob {
	public static final long STEP_TIME = 5000L;
	private static String[] route = new String[]{"left", "up", "right", "down", "down", "right", "up", "left"};

	private int currentStep;
	private String key;
	private MessageRegistrator registrator;
	private long time;

	public BotLoop(String key, MessageRegistrator registrator) {
		this.key = key;
		this.registrator = registrator;
		currentStep = -1;
		time = -1L;
	}

	@Override
	public void doJob(JobRunnerConfiguration configuration, JobRunnerData data) {
		if (timeHasCome()) {
			time = System.currentTimeMillis() + STEP_TIME;
			currentStep = (currentStep + 1) % route.length;
			final String line = route[currentStep];
			registrator.registerMessage(key, line);
			System.out.println("client generates action: "+line);
		}
	}

	private boolean timeHasCome() {
		final long currentTime = System.currentTimeMillis();
		return currentTime >= time;
	}
}
