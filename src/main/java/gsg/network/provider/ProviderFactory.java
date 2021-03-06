package gsg.network.provider;

import gsg.network.provider.input.InputStreamProvider;
import gsg.network.provider.input.OutputStreamProvider;
import gsg.network.provider.input.SocketInputStreamProvider;
import gsg.network.provider.input.SocketOutputStreamProvider;

import java.net.Socket;

/**
 * @author zkejid@gmail.com
 *         Created: 02.08.15 12:47
 */
public class ProviderFactory {
	public static InputStreamProvider inputFromSocket(Socket socket) {
		return new SocketInputStreamProvider(socket);
	}

	public static OutputStreamProvider outputToSocket(Socket socket) {
		return new SocketOutputStreamProvider(socket);
	}
}
