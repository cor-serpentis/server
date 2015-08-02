package gsg.client;

import gsg.infrastructure.Utils;
import gsg.infrastructure.messages.ServerMessageRegistrator;
import gsg.network.InputLoop;
import gsg.network.provider.input.RawInputStreamProvider;

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

			final DataInputStream is = new DataInputStream(s.getInputStream());
			InputLoop inputLoop = new InputLoop("server", new RawInputStreamProvider(is), new ServerMessageRegistrator() {
				@Override
				public void registerMessage(String source, String line) {
					System.out.println(source+": "+line);
				}
			});
			Utils.runLoop(inputLoop);

			PrintStream os = new PrintStream(s.getOutputStream());
			os.println("create");
			while (true) {
				Thread.sleep(5000l);
				os.println("up");
				Thread.sleep(5000l);
				os.println("left");
				Thread.sleep(5000l);
				os.println("down");
				Thread.sleep(5000l);
				os.println("right");
				Thread.sleep(5000l);
				os.println("right");
				Thread.sleep(5000l);
				os.println("down");
				Thread.sleep(5000l);
				os.println("left");
				Thread.sleep(5000l);
				os.println("up");
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
