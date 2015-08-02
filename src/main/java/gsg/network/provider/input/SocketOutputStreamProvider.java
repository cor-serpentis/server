package gsg.network.provider.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zkejid@gmail.com
 *         Created: 02.08.15 12:44
 */
public class SocketOutputStreamProvider implements OutputStreamProvider {
	private final Socket socket;

	public SocketOutputStreamProvider(Socket socket) {
		this.socket = socket;
	}

	@Override
	public OutputStream getStream() {
		try {
			return socket.getOutputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
