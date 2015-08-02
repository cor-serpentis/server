package gsg.network.provider.input;

import java.io.InputStream;

/**
 * @author zkejid@gmail.com
 *         Created: 02.08.15 15:08
 */
public class RawInputStreamProvider implements InputStreamProvider {
	private final InputStream stream;

	public RawInputStreamProvider(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public InputStream getStream() {
		return stream;
	}
}
