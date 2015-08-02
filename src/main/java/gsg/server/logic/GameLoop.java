package gsg.server.logic;

import gsg.infrastructure.Utils;
import gsg.infrastructure.messages.MessageContainer;
import gsg.network.ConnectionLibrary;
import gsg.network.InputLoop;
import gsg.network.OutputLoop;
import gsg.network.provider.ProviderFactory;
import gsg.network.provider.input.InputStreamProvider;
import gsg.server.infrastructure.ConnectionRegistrator;
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
public class GameLoop implements IJob, ConnectionRegistrator {
	private final DumbView frame;
	private final ConnectionLibrary connections;

	public GameLoop() {
		frame = new DumbView();
		connections = new ConnectionLibrary();
		TestbedModel model = new TestbedModel();
		model.addTest(frame);
		TestbedPanel panel = new TestPanelJ2D(model);
		JFrame testbed = new TestbedFrame(model, panel, TestbedController.UpdateBehavior.UPDATE_CALLED);
		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void doJob(JobRunnerConfiguration configuration, JobRunnerData jobRunnerData) {
		final MessageContainer.Command message = frame.getOutgoingMessages().getMessage();
		if (message != null) {
			System.out.println("GameLoop: "+message.source+" "+message.line);
			final OutputLoop output = connections.getOutput(message.source);
			output.registerMessage(message.line);
		}
	}

	@Override
	public void registerConnection(Socket socket) {
		final InputStreamProvider input = ProviderFactory.inputFromSocket(socket);
		final String connect = frame.connect();
		final InputLoop inputLoop = new InputLoop(connect, input, frame.getIncomingMessages());
		final OutputLoop outputLoop = new OutputLoop(ProviderFactory.outputToSocket(socket));
		connections.add(connect, inputLoop, outputLoop);
		Utils.runLoop(inputLoop);
		Utils.runLoop(outputLoop);
	}
}
