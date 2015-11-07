package gsg.client;

import com.google.common.collect.Maps;
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

/**
 * @author zkejid@gmail.com
 *         Created: 13.07.15 23:01
 */
public class ClientDumbView extends TestbedTest {

	private static final Logger logger = LogManager.getLogger();
	public static final String LEFT = "left ";
	public static final String RIGHT = "right ";
	public static final String UP = "up ";
	public static final String DOWN = "down ";
	public static final String STOP = "stop ";
	public static final String CREATE_SELF = "create self ";
	public static final String CREATE_USER = "create user ";

	private final Map<String, Body> bodies = Maps.newHashMap();
	private final ArrayBlockingQueue<String> queueOfConnectionsUp = new ArrayBlockingQueue<String>(10);
	private final ArrayBlockingQueue<String> queueOfConnectionsDown = new ArrayBlockingQueue<String>(10);

	private String self;


	final int velocity = 10;

	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("Couple of Things Test");

		getWorld().setGravity(new Vec2());
	}

	@Override
	public String getTestName() {
		return "Test World";
	}

	public String connect(String connect) {
		final String key = connect == null ? UUID.randomUUID().toString() : connect;
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
//		switch (argKeyChar) {
//			case 'w':
//				processMessage("up");
//				break;
//
//			case 'a':
//				processMessage("left");
//				break;
//
//			case 's':
//				processMessage("down");
//				break;
//
//			case 'd':
//				processMessage("right");
//				break;
//		}
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

	public void processMessage(String message) {
		if (message.startsWith(LEFT)) {
			String userUID = getTail(message, LEFT);
			left(userUID);
		}
		else if (message.startsWith(RIGHT)) {
			String userUID = getTail(message, RIGHT);
			right(userUID);
		}
		else if (message.startsWith(UP)) {
			String userUID = getTail(message, UP);
			up(userUID);
		}
		else if (message.startsWith(DOWN)) {
			String userUID = getTail(message, DOWN);
			down(userUID);
		}
		else if (message.startsWith(STOP)) {
			String userUID = getTail(message, STOP);
			stop(userUID);
		}
		else if (message.startsWith(CREATE_SELF)) {
			String userUID = getTail(message, CREATE_SELF);
			self = userUID;
			connect(userUID);
		}
		else if (message.startsWith(CREATE_USER)) {
			String userUID = getTail(message, CREATE_USER);
			connect(userUID);
		}
		else {
			logger.warn("Unknown message: %s", message);
		}
	}

	private String getTail(String message, String command) {
		return message.substring(command.length());
	}


	/***********************************************/

	private void keyReleased(char argKeyChar, String key) {
		switch (argKeyChar) {
			case 'w':
			case 'a':
			case 's':
			case 'd':
				processMessage("stop");
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
}
