package gsg.server;

import gsg.server.infrastructure.ServerUtils;
import gsg.server.logic.GameLoop;
import gsg.server.network.ConnectionLoop;

/**
 * @author zkejid@gmail.com
 *         Created: 13.07.15 22:55
 */
public class Server {
	public static void main(String[] args) {
		final ConnectionLoop connectionLoop = new ConnectionLoop(9189);
		final GameLoop gameLoop = new GameLoop();

		connectionLoop.setConnectionRegistrator(gameLoop);

		ServerUtils.runLoop(connectionLoop);
		ServerUtils.runLoopAndJoin(gameLoop);
	}


}
