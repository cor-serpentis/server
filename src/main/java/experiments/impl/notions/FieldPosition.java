package experiments.impl.notions;

import experiments.api.notions.Axes;
import experiments.api.notions.IDirection;
import experiments.api.notions.IPosition;

/**
 * @author zkejid@gmail.com
 *         Created: 08.11.15 13:20
 */
public class FieldPosition implements IPosition, IDirection{

	public static final String X = "x";
	public static final String Y = "y";

	private Axes axes;

	private FieldPosition() {
		axes = Axes.zero(X, Y);
	}

	private FieldPosition(long x, long y) {
		axes = Axes.zero(X, Y);
		axes.setValue(X, x);
		axes.setValue(Y, y);
	}

	@Override
	public void move(IDirection direction) {
		direction.apply(this);
	}

	@Override
	public void set(IPosition position) {
		axes.set(position.get());
	}

	@Override
	public Axes get() {
		return axes;
	}

	public long getX() {
		return axes.getValue(X);
	}

	public long getY() {
		return axes.getValue(Y);
	}

	@Override
	public void apply(IPosition position) {
		position.get().move(axes);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FieldPosition that = (FieldPosition) o;

		if (!axes.equals(that.axes)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return axes.hashCode();
	}

	public static IPosition zeroPosition() {
		return new FieldPosition();
	}

	public static IPosition position(long x, long y) {
		return new FieldPosition(x, y);
	}

	public static IPosition position(IPosition position) {
		final FieldPosition fieldPosition = new FieldPosition();
		fieldPosition.set(position);
		return fieldPosition;
	}

	public static IDirection left() {
		return new FieldPosition(-1L, 0L);
	}

	public static IDirection right() {
		return new FieldPosition(1L, 0L);
	}

	public static IDirection top() {
		return new FieldPosition(0L, 1L);
	}

	public static IDirection bottom() {
		return new FieldPosition(0L, -1L);
	}
}
