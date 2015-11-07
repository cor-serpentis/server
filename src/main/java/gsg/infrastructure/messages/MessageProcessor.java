package gsg.infrastructure.messages;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * @author zkejid@gmail.com
 *         Created: 15.08.15 20:21
 */
public class MessageProcessor {
	private static final String SEPARATOR = " ";

	protected Node root;

	public void process(String source, String message) {
		root.process(source, message);
	}

	/**
	 * Split string on two parts: before separator and after separator. that's all
	 */
	public static String[] split(String arg) {
		String [] result = new String[2];
		final int i = arg.indexOf(SEPARATOR);
		if (i > 0) {
			result[0] = arg.substring(0, i);
			if (i < arg.length()-SEPARATOR.length()) {
				result[1] = arg.substring(i+SEPARATOR.length(), arg.length());
			}
			else {
				result[1] = "";
			}
		}
		else {
			result[0] = arg;
			result[1] = "";
		}
		return result;
	}

	protected Node create(Node ... nodes) {
		return new RootNode("", Collections.unmodifiableList(Lists.newArrayList(nodes)));
	}

	protected Node create(String name, Node ... nodes) {
		return new ActionNode(new PrintToConsoleAction(), name,
				Collections.unmodifiableList(Lists.newArrayList(nodes)));
	}

	protected Node create(String name, MessageProcessorAction action, Node... nodes) {
		return new ActionNode(action, name, Collections.unmodifiableList(Lists.newArrayList(nodes)));
	}


	protected abstract static class Node {
		public final String name;
		public final List<Node> children;

		private Node(String name, List<Node> children) {
			this.name = name;
			this.children = children;
		}

		public abstract void process(String source, String message);

		public void applyToNodeList(String source, String head, String tail) {
			for (Node child : children) {
				if (head.equals(child.name)) {
					child.process(source, tail);
					return;
				}
			}
			throw new RuntimeException("Not found path for "+head+SEPARATOR+tail+" Last node is "+this.toString());
		}
	}

	public class ActionNode extends Node {
		public final MessageProcessorAction action;
		private ActionNode(MessageProcessor.MessageProcessorAction action, String name, List<MessageProcessor.Node> children) {
			super(name, children);
			this.action = action;
		}

		public void process(String source, String message) {
			action.doAction(source, message);
		}
	}

	public class RootNode extends Node {
		private RootNode(String name, List<MessageProcessor.Node> children) {
			super(name, children);
		}

		public void process(String source, String message) {
			final String[] headAndTail = split(message);
			final String head = headAndTail[0];
			final String tail = headAndTail[1];
			applyToNodeList(source, head, tail);
		}
	}

	public static interface MessageProcessorAction {
		void doAction(String source, String arguments);
	}

	public static class PrintToConsoleAction implements MessageProcessorAction {

		@Override
		public void doAction(String source, String arguments) {
			System.out.println(arguments);
		}
	}
}
