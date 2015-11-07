package gsg.server.logic;

import com.google.common.collect.Maps;
import gsg.infrastructure.messages.MessageContainer;
import gsg.infrastructure.messages.MessageProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author zkejid@gmail.com
 *         Created: 13.07.15 23:01
 */
public class DumbView extends TestbedTest {

	private static final Logger logger = LogManager.getLogger();

	private final Map<String, Body> bodies = Maps.newHashMap();
	private final ArrayBlockingQueue<String> queueOfConnectionsUp = new ArrayBlockingQueue<String>(10);
	private final ArrayBlockingQueue<String> queueOfConnectionsDown = new ArrayBlockingQueue<String>(10);

	final int velocity = 10;

	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("Couple of Things Test");

		getWorld().setGravity(new Vec2());
//			createBox(1);
	}

	@Override
	public String getTestName() {
		return "Test World";
	}

	/**
	 * Generate UID for connection. Schedule display of connected user
	 */
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






	/***********************************************/

	public void stop(String key) {
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

	}
}
