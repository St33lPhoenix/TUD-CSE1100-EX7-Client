package nl.tudelft.gbot.chat.client.listener;

import java.io.OutputStream;

public class InputListenerThread extends AbstractListenerThread {
	public InputListenerThread(OutputStream out) {
		super("Client input thread", System.in, out);
	}
}
