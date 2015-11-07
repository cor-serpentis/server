package gsg.server.logic;

import gsg.infrastructure.messages.IContainer;

/**
 * @author zkejid@gmail.com
 *         Created: 16.08.15 14:01
 */
public interface ITransport {
	IContainer getSide1();
	IContainer getSide2();
}
