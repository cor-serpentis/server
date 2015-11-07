package gsg.infrastructure.messages;

/**
* @author zkejid@gmail.com
*         Created: 16.08.15 14:02
*/
public class Command {
	public final String source;
	public final String line;

	public Command(String line, String source) {
		this.line = line;
		this.source = source;
	}
}
