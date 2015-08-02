package gsg.network;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zkejid@gmail.com
 *         Created: 02.08.15 14:14
 */
public class ConnectionLibrary {
	private final ConcurrentHashMap<String, LibraryEntity> data = new ConcurrentHashMap<String, LibraryEntity>();

	private static class LibraryEntity {
		public final InputLoop input;
		public final SocketOutputLoop output;

		private LibraryEntity(InputLoop input, SocketOutputLoop output) {
			this.input = input;
			this.output = output;
		}
	}

	//TODO not atomic map call
	public void add(String key, InputLoop input, SocketOutputLoop output) {
		if (key == null || input == null || output == null) {
			throw new RuntimeException(String.format(
					"Null arguments (key/input/output): %s/ %s / %s", key, input, output));
		}
		if (data.containsKey(key)) {
			throw new RuntimeException("Library already contains key "+key);
		}
		else {
			data.put(key, new LibraryEntity(input, output));
		}
	}

	public InputLoop getInput(String key) {
		final LibraryEntity libraryEntity = getLibraryEntity(key);
		return libraryEntity.input;
	}

	private LibraryEntity getLibraryEntity(String key) {
		final LibraryEntity libraryEntity = data.get(key);
		if (libraryEntity == null) {
			throw new RuntimeException("No data for key "+key);
		}
		return libraryEntity;
	}

	public SocketOutputLoop getOutput(String key) {
		final LibraryEntity libraryEntity = getLibraryEntity(key);
		return libraryEntity.output;
	}
}
