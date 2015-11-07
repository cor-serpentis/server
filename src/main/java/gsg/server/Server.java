package gsg.server;

import gsg.client.Bot;
import gsg.infrastructure.Utils;
import gsg.server.logic.GameLoop;
import gsg.server.logic.MessageLoop;
import gsg.server.network.ConnectionLoop;

/**
 * @author zkejid@gmail.com
 *         Created: 13.07.15 22:55
 */
public class Server {
	public static void main(String[] args) {
		final GameLoop gameLoop = new GameLoop();
		Utils.runLoop(gameLoop);

		Bot.main(args);
	}


}
