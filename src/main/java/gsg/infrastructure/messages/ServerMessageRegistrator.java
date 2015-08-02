package gsg.infrastructure.messages;

/**
* @author zkejid@gmail.com
*         Created: 14.07.15 23:32
*/
public interface ServerMessageRegistrator {
	/**
	 * Source line describes the _source_ of message, i.e. input stream
	 * which produces messages for server.
	 */
	void registerMessage(String source, String line);
}
