package gsg.server.logic;

import gsg.infrastructure.Utils;
import gsg.infrastructure.messages.IContainer;
import gsg.infrastructure.messages.MessageContainer;
import gsg.network.ConnectionLibrary;
import gsg.network.InputLoop;
import gsg.network.OutputLoop;
import gsg.network.provider.ProviderFactory;
import gsg.network.provider.input.InputStreamProvider;
import gsg.server.infrastructure.ConnectionRegistrator;
import gsg.server.network.ConnectionLoop;
import gsg.threads.IJob;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;
import org.jbox2d.testbed.framework.TestbedController;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import javax.swing.*;
import java.net.Socket;

/**
* @author zkejid@gmail.com
*         Created: 14.07.15 23:36
*/
public class GameLoop implements IJob {
	private final DumbView frame;
	private final MessageLoop messageLoop;
	private final ConnectionLoop connectionLoop;

	public GameLoop() {
		frame = new DumbView();
		final MessageTransport messageTransport = new MessageTransport();
		final IContainer frameSide = messageTransport.getSide1();
		final ServerMessageProcessor messageProcessor = new ServerMessageProcessor(frame, frameSide);
		messageLoop = new MessageLoop(messageProcessor);
		connectionLoop = new ConnectionLoop(9189, messageLoop);

		TestbedModel model = new TestbedModel();
		model.addTest(frame);
		TestbedPanel panel = new TestPanelJ2D(model);
		JFrame testbed = new TestbedFrame(model, panel, TestbedController.UpdateBehavior.UPDATE_CALLED);
		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void onStart(){
		Utils.runLoop(messageLoop);
		Utils.runLoop(connectionLoop);
	}

	@Override
	public void doJob(JobRunnerConfiguration configuration, JobRunnerData jobRunnerData) {
	}
}
