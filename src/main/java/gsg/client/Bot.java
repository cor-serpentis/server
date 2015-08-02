package gsg.client;

import gsg.infrastructure.Utils;
import gsg.infrastructure.messages.MessageRegistrator;
import gsg.network.InputLoop;
import gsg.network.OutputLoop;
import gsg.network.provider.ProviderFactory;
import gsg.network.provider.input.RawInputStreamProvider;
import org.jbox2d.testbed.framework.TestbedController;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author zkejid@gmail.com
 *         Created: 14.07.15 22:50
 */
public class Bot {

	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 9189);

			final DumbView frame = new DumbView();
			TestbedModel model = new TestbedModel();
			model.addTest(frame);
			TestbedPanel panel = new TestPanelJ2D(model);
			JFrame testbed = new TestbedFrame(model, panel, TestbedController.UpdateBehavior.UPDATE_CALLED);
			testbed.setVisible(true);
			testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			final DataInputStream is = new DataInputStream(s.getInputStream());
			final String key = frame.connect();
			InputLoop inputLoop = new InputLoop(key, new RawInputStreamProvider(is), new MessageRegistrator() {
				@Override
				public void registerMessage(String source, String line) {
					System.out.println(source+": "+line);
					frame.processMessage(source, line);
				}
			});
			Utils.runLoop(inputLoop);

			OutputLoop outputLoop = new OutputLoop(ProviderFactory.outputToSocket(s));
			Utils.runLoop(outputLoop);

			BotLoop botLoop = new BotLoop(outputLoop);
			Utils.runLoopAndJoin(botLoop);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
