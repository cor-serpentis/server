package experiments.impl.infrastructure;

import experiments.api.notions.IDirection;
import experiments.api.notions.IIdentifier;
import experiments.api.notions.IPosition;
import experiments.impl.notions.FieldObject;
import experiments.impl.notions.FieldPosition;
import experiments.impl.notions.Identifier;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author zkejid@gmail.com
 *         Created: 08.11.15 0:06
 */
public class PhysicsEngineTest {

	/**
	 * Scenario: add object and get identifier. Check the position of object
	 */
	@Test
	public void testAddGet(){
		final PhysicsEngine engine = new PhysicsEngine();
		final FieldObject fieldObject = new FieldObject();

		final IIdentifier identifier = engine.add(fieldObject);
		final IPosition position = engine.get(identifier);

		assertNotNull(identifier);
		assertNotNull(position);
	}

	/**
	 * Scenario: add object twice should produce exception.
	 */
	@Test(expected = RuntimeException.class)
	public void testAddTwice(){
		final PhysicsEngine engine = new PhysicsEngine();
		final FieldObject fieldObject = new FieldObject();

		engine.add(fieldObject);
		engine.add(fieldObject);
	}

	/**
	 * Scenario: delete object.
	 */
	@Test
	public void testDelete(){
		final PhysicsEngine engine = new PhysicsEngine();
		final FieldObject fieldObject = new FieldObject();
		final IIdentifier identifier = engine.add(fieldObject);

		engine.remove(identifier);
	}

	/**
	 * Scenario: set position for given object. Current position of object should be equal to the argument of 'set' call
	 */
	@Test
	public void testSet() {
		final PhysicsEngine engine = new PhysicsEngine();
		final FieldObject fieldObject = new FieldObject();
		final IIdentifier identifier = engine.add(fieldObject);
		final IPosition expectedPosition = FieldPosition.position(4L, 2L);

		engine.set(identifier, expectedPosition);
		final IPosition actualPosition = engine.get(identifier);

		assertEquals(expectedPosition, actualPosition);
	}

	/**
	 * Scenario: move object in given direction. Position of object should change.
	 */
	@Test
	public void testMove() {
		final PhysicsEngine engine = new PhysicsEngine();
		final FieldObject fieldObject = new FieldObject();
		final IIdentifier identifier = engine.add(fieldObject);
		final IPosition initialPosition = engine.get(identifier);
		final IDirection direction = FieldPosition.left();

		engine.move(identifier, direction);
		final IPosition resultPosition = engine.get(identifier);

		assertNotEquals(initialPosition, resultPosition);
	}

	/**
	 * Scenario: call 'add' with null argument should produce exception.
	 */
	@Test(expected = RuntimeException.class)
	public void addNegative(){
		final PhysicsEngine engine = new PhysicsEngine();

		engine.add(null);
	}

	/**
	 * Scenario: call 'get' with null argument should produce exception.
	 */
	@Test(expected = RuntimeException.class)
	public void getNegativeNull(){
		final PhysicsEngine engine = new PhysicsEngine();

		engine.get(null);
	}

	/**
	 * Scenario: call 'get' with obsolete argument should produce exception.
	 */
	@Test(expected = RuntimeException.class)
	public void getNegativeObsolete(){
		final PhysicsEngine engine = new PhysicsEngine();
		final FieldObject object = new FieldObject();
		final IIdentifier identifier = engine.add(object);

		engine.remove(identifier);
		engine.get(identifier);
	}

	/**
	 * Scenario: call 'remove' with null argument should produce exception.
	 */
	@Test(expected = RuntimeException.class)
	public void removeNegative(){
		final PhysicsEngine engine = new PhysicsEngine();

		engine.remove(null);
	}

	/**
	 * Scenario: call 'move' with null identifier argument should produce exception.
	 */
	@Test(expected = RuntimeException.class)
	public void moveNegative1(){
		final PhysicsEngine engine = new PhysicsEngine();
		final IDirection direction = FieldPosition.left();

		engine.move(null, direction);
	}

	/**
	 * Scenario: call 'move' with null direction argument should produce exception.
	 */
	@Test(expected = RuntimeException.class)
	public void moveNegative2(){
		final PhysicsEngine engine = new PhysicsEngine();
		final FieldObject fieldObject = new FieldObject();
		final IIdentifier identifier = engine.add(fieldObject);

		engine.move(identifier, null);
	}
}
