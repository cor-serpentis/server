package gsg.server.network;

import gsg.server.infrastructure.ConnectionRegistrator;
import gsg.threads.IJob;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
* @author zkejid@gmail.com
*         Created: 14.07.15 23:33
*/
public class ConnectionLoop implements IJob {
	private ServerSocket server;
	private ConnectionRegistrator registrator;

	public ConnectionLoop(int port) {
		try {
			this.server = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doJob(JobRunnerConfiguration configuration, JobRunnerData jobRunnerData) {
		try {
			final Socket accept = server.accept();
			if (registrator != null) {
				registrator.registerConnection(accept);
			}
		} catch (IOException e) {
			e.printStackTrace();
			configuration.setActive(false);
		}
	}


	public void setConnectionRegistrator(ConnectionRegistrator registrator) {
		this.registrator = registrator;
	}
}
