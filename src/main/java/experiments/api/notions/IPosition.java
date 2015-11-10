package experiments.api.notions;

/**
 * @author zkejid@gmail.com
 *         Created: 08.11.15 0:03
 */
public interface IPosition {
	void move(IDirection direction);

	void set(IPosition position);

	Axes get();
}
