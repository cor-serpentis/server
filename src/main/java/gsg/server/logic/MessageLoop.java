package gsg.server.logic;

import gsg.infrastructure.Utils;
import gsg.infrastructure.messages.Command;
import gsg.infrastructure.messages.MessageContainer;
import gsg.network.ConnectionLibrary;
import gsg.network.InputLoop;
import gsg.network.OutputLoop;
import gsg.network.provider.ProviderFactory;
import gsg.network.provider.input.InputStreamProvider;
import gsg.network.provider.input.OutputStreamProvider;
import gsg.server.infrastructure.ConnectionRegistrator;
import gsg.threads.IJob;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;
import java.util.UUID;

/**
 * @author zkejid@gmail.com
 *         Created: 16.08.15 13:08
 */
public class MessageLoop implements IJob, ConnectionRegistrator {
	private static final Logger logger = LogManager.getLogger();

	private final MessageContainer incomingMessages = new MessageContainer();
	private final MessageContainer outgoingMessages = new MessageContainer();
	private final ServerMessageProcessor messageProcessor;
	private final ConnectionLibrary connections;

	public MessageLoop(ServerMessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
		connections = new ConnectionLibrary();
	}

	@Override
	public void onStart() {}

	@Override
	public void doJob(JobRunnerConfiguration configuration, JobRunnerData data) {
		final Command inMessage = incomingMessages.getMessage();
		if (inMessage != null) {
			logger.info("MessageLoop: source: " + inMessage.source + " message: " + inMessage.line);
			processMessage(inMessage.source, inMessage.line);
		}
		final Command outMessage = outgoingMessages.getMessage();
		if (outMessage != null) {
			logger.info("MessageLoop: " + outMessage.source + " " + outMessage.line);
			final OutputLoop output = connections.getOutput(outMessage.source);
			output.registerMessage(outMessage.line);
		}

	}
	public void processMessage(String key, String message) {
		if (connections.getConnectionIDs().contains(key)) {
			messageProcessor.process(key, message);
		}
		else {
			logger.warn("Unknown source: %s", key);
		}
	}

	/**
	 * Send message about connection to clients
	 * TODO: ensure ALL connect messages reached ALL clients and nothing lost.
	 */
	public void clientConnected(String connect) {
		for (String s : connections.getConnectionIDs()) {
			if (s.equals(connect)) {
				outgoingMessages.registerMessage(s, "create self "+s);
			}
			else {
				outgoingMessages.registerMessage(s, "create user "+s);
			}
		}
	}


	@Override
	public void registerConnection(Socket socket) {
		final String connect = UUID.randomUUID().toString();

		final InputStreamProvider input = ProviderFactory.inputFromSocket(socket);
		final InputLoop inputLoop = new InputLoop(connect, input, incomingMessages);
		final OutputStreamProvider output = ProviderFactory.outputToSocket(socket);
		final OutputLoop outputLoop = new OutputLoop(output);
		connections.add(connect, inputLoop, outputLoop);
		Utils.runLoop(inputLoop);
		Utils.runLoop(outputLoop);

		clientConnected(connect);
	}

	public void broadcastMessage(String source, String messageToSource, String messageToOther) {
		for (String s : connections.getConnectionIDs()) {
			if (s.equals(source)) {
				outgoingMessages.registerMessage(s, messageToSource);
			}
			else {
				outgoingMessages.registerMessage(s, messageToOther);
			}
		}
	}
}
