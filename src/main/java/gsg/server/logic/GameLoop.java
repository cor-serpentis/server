package gsg.server.logic;

import gsg.infrastructure.Utils;
import gsg.network.SocketInputLoop;
import gsg.server.infrastructure.ConnectionRegistrator;
import gsg.infrastructure.MessageRegistrator;
import gsg.server.infrastructure.ServerUtils;
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
public class GameLoop implements IJob, ConnectionRegistrator, MessageRegistrator {
	private final DumbView frame;

	public GameLoop() {
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

	@Override
	public void registerConnection(Socket socket) {
		final String connect = frame.connect();
		final SocketInputLoop socketLoop = new SocketInputLoop(socket, connect);
		socketLoop.setRegistrator(this);
		ServerUtils.runLoop(socketLoop);
	}

	@Override
	public void registerMessage(String line) {
		final String key = Utils.getKey(line);
		final String message = Utils.getMessage(line);
		frame.processMessage(key, message);
	}
}
