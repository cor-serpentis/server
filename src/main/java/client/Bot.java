package client;

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

			DataInputStream is = new DataInputStream(s.getInputStream());
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
