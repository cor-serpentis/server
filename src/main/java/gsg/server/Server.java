package gsg.server;

import gsg.threads.IJob;
import gsg.threads.JobRunner;
import gsg.threads.JobRunnerConfiguration;
import gsg.threads.JobRunnerData;
import org.jbox2d.testbed.framework.*;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Ivan Panfilov, folremo@axmor.com
 *         Created: 13.07.15 22:55
 */
public class Server {
	public static void main(String[] args) {
		final ConnectionLoop connectionLoop = new ConnectionLoop(9189);
		final GameLoop gameLoop = new GameLoop();

		connectionLoop.setConnectionRegistrator(gameLoop);

		runLoop(connectionLoop);
		runLoopAndJoin(gameLoop);
	}

	private static JobRunner runLoop(IJob job) {
		final JobRunnerConfiguration configuration = new JobRunnerConfiguration();
		final JobRunner runner = new JobRunner(configuration, job);
		runner.start();
		return runner;
	}

	private static void runLoopAndJoin(IJob job) {
		final JobRunner runner = runLoop(job);
		try {
			runner.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static class GameLoop implements IJob, ConnectionRegistrator, MessageRegistrator {
		private final DumbView frame;

		public GameLoop() {
			frame = new DumbView();
			TestbedModel model = new TestbedModel();
			model.addTest(frame);
			TestbedPanel panel = new TestPanelJ2D(model);
			JFrame testbed = new TestbedFrame(model, panel, TestbedController.UpdateBehavior.UPDATE_CALLED);
			testbed.setVisible(true);
			testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		@Override
		public void doJob(JobRunnerConfiguration configuration, JobRunnerData jobRunnerData) {

		}

		@Override
		public void registerConnection(Socket socket) {
			final String connect = frame.connect();
			final SocketLoop socketLoop = new SocketLoop(socket, connect);
			socketLoop.setRegistrator(this);
			runLoop(socketLoop);
		}

		@Override
		public void registerMessage(String source, String message) {
			frame.processMessage(source, message);
		}
	}

	public static interface ConnectionRegistrator {
		void registerConnection(Socket socket);
	}

	public static interface MessageRegistrator {
		void registerMessage(String source, String message);
	}


	public static class ConnectionLoop implements IJob {
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

	public static class SocketLoop implements IJob {

		private Socket socket;
		private String key;
		private DataInputStream is;
		private PrintStream os;
		private MessageRegistrator registrator;

		public SocketLoop(Socket socket, String key) {
			this.socket = socket;
			this.key = key;
			try {
				is = new DataInputStream(socket.getInputStream());
				os = new PrintStream(socket.getOutputStream());

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void doJob(JobRunnerConfiguration configuration, JobRunnerData jobRunnerData) {
			String line = null;
			while (true) {
				try {
					line = is.readLine();
					if (registrator != null) {
						registrator.registerMessage(key, line);
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				System.out.println("command: " + line);
			}
		}

		public void setRegistrator(MessageRegistrator registrator) {
			this.registrator = registrator;
		}
	}
}
