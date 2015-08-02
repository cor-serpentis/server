package gsg.network.provider.input;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author zkejid@gmail.com
 *         Created: 02.08.15 12:44
 */
public class SocketInputStreamProvider implements InputStreamProvider {
	private Socket socket;

	public SocketInputStreamProvider(Socket socket) {
		this.socket = socket;
	}

	@Override
	public InputStream getStream() {
		try {
			return socket.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
