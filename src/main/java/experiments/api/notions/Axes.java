package experiments.api.notions;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author zkejid@gmail.com
 *         Created: 10.11.15 23:14
 */
public class Axes {
	private Map<String, Long> values;

	public void move(Axes axes) {
		for (String name : axes.values.keySet()) {
			if (values.containsKey(name)) {
				values.put(name, values.get(name) + axes.values.get(name));
			}
			else {
				throw new RuntimeException("no such axis: "+name);
			}
		}
	}

	public void set(Axes axes) {
		for (String name : axes.values.keySet()) {
			if (values.containsKey(name)) {
				values.put(name, axes.values.get(name));
			}
			else {
				throw new RuntimeException("no such axis: "+name);
			}
		}
	}

	public Long getValue(String name) {
		if (values.containsKey(name)) {
			return values.get(name);
		}
		else {
			throw new RuntimeException("no such axis: "+name);
		}
	}

	public void setValue(String name, Long value) {
		if (values.containsKey(name)) {
			values.put(name, value);
		}
		else {
			throw new RuntimeException("no such axis: "+name);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Axes axes = (Axes) o;

		if (!values.equals(axes.values)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return values.hashCode();
	}

	private Axes(String ... names) {
		values = Maps.newHashMap();
		for (String name : names) {
			values.put(name, 0L);
		}
	}


	public static Axes zero(String ... names) {
		return new Axes(names);
	}
}
