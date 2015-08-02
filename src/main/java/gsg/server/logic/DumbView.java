package gsg.server.logic;

import com.google.common.collect.Maps;
import gsg.infrastructure.messages.FrameMessageRegistrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author zkejid@gmail.com
 *         Created: 13.07.15 23:01
 */
public class DumbView extends TestbedTest {

	private static final Logger logger = LogManager.getLogger();

	private final Map<String, Body> bodies = Maps.newHashMap();
	private final ArrayBlockingQueue<String> queueOfConnectionsUp = new ArrayBlockingQueue<String>(10);
	private final ArrayBlockingQueue<String> queueOfConnectionsDown = new ArrayBlockingQueue<String>(10);
	private final FrameMessageRegistrator registrator = new FrameMessageRegistrator();

	private String self;


	final int velocity = 10;

	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("Couple of Things Test");

		getWorld().setGravity(new Vec2());
		createSelf();
//			createBox(1);
	}

	private void createSelf() {
		self = connect();
	}

	@Override
	public String getTestName() {
		return "Test World";
	}

	public String connect() {
		final String key = UUID.randomUUID().toString();
		queueOfConnectionsUp.add(key);
		return key;
	}

	public void disconnect(String key) {
		queueOfConnectionsDown.add(key);
	}

	@Override
	public synchronized void step(TestbedSettings settings) {
		adjustConnections();
		super.step(settings);
	}


	@Override
	public void keyPressed(char argKeyChar, int argKeyCode) {
		keyPressed(argKeyChar, self);
	}

	private void keyPressed(char argKeyChar, String key) {
		switch (argKeyChar) {
			case 'w':
				processMessage(key, "up");
				break;

			case 'a':
				processMessage(key, "left");
				break;

			case 's':
				processMessage(key, "down");
				break;

			case 'd':
				processMessage(key, "right");
				break;
		}
	}

	public void right(String key) {
		if (bodies.containsKey(key)) {
			bodies.get(key).setLinearVelocity(new Vec2(velocity, 0));
		}
	}

	public void down(String key) {
		if (bodies.containsKey(key)) {
			bodies.get(key).setLinearVelocity(new Vec2(0, -velocity));
		}
	}

	public void left(String key) {
		if (bodies.containsKey(key)) {
			bodies.get(key).setLinearVelocity(new Vec2(-velocity, 0));
		}
	}

	public void up(String key) {
		if (bodies.containsKey(key)) {
			bodies.get(key).setLinearVelocity(new Vec2(0, velocity));
		}
	}

	@Override
	public void keyReleased(char argKeyChar, int argKeyCode) {
		keyReleased(argKeyChar,  self);
	}

	public void processMessage(String key, String message) {
		if (bodies.containsKey(key)) {
			if ("left".equals(message)) {
				left(key);
			}
			else if ("right".equals(message)) {
				right(key);
			}
			else if ("up".equals(message)) {
				up(key);
			}
			else if ("down".equals(message)) {
				down(key);
			}
			else if ("stop".equals(message)) {
				stop(key);
			}
			else {
				logger.warn("Unknown message: %s, source: ", message, key);
			}
		}
		else {
			logger.warn("Unknown source: %s", key);
		}
	}




	/***********************************************/

	private void keyReleased(char argKeyChar, String key) {
		switch (argKeyChar) {
			case 'w':
			case 'a':
			case 's':
			case 'd':
				processMessage(key, "stop");
				break;
		}
	}

	private void stop(String key) {
		if (bodies.containsKey(key)) {
			bodies.get(key).setLinearVelocity(new Vec2());
		}
	}


	private void adjustConnections() {
		final String down = queueOfConnectionsDown.poll();
		if (down != null) {
			final Body body = bodies.get(down);
			if (body == null) {
				logger.warn("Not found body to remove: %s", down);
			}
			else {
				getWorld().destroyBody(body);
			}
		}
		final String up = queueOfConnectionsUp.poll();
		if (up != null) {
			if (bodies.containsKey(up)) {
				logger.warn("Key for body already exists: %s", up);
			}
			else {
				PolygonShape polygonShape = new PolygonShape();
				polygonShape.setAsBox(1, 1);

				BodyDef bodyDef = new BodyDef();
				bodyDef.type = BodyType.DYNAMIC;
				bodyDef.position.set(0, 0);
				bodyDef.angle = (float) (0);
				bodyDef.allowSleep = false;
				Body self = getWorld().createBody(bodyDef);
				self.createFixture(polygonShape, 5.0f);
				bodies.put(up, self);
			}
		}
	}

	public FrameMessageRegistrator getRegistrator() {
		return registrator;
	}

	@Override
	public void update() {
		super.update();
		doStep();
	}

	/**
	 * Method contains code to process on each step of visualization.
	 * It should be called by main loop.
	 */
	private void doStep() {
		final FrameMessageRegistrator.Command message = registrator.getMessage();
		if (message != null) {
			processMessage(message.source, message.line);
		}
	}
}
