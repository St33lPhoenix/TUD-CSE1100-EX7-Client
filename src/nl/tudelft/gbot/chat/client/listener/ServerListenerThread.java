package nl.tudelft.gbot.chat.client.listener;

import java.io.IOException;
import java.io.InputStream;

public class ServerListenerThread extends AbstractListenerThread {
	private final InputStream in;
	public ServerListenerThread(InputStream in) {
		super("Server input thread", in, System.out);
		this.in = in;
	}
	@Override
	protected final boolean checkState() {
		try {
			in.read(new byte[0]);
			return true;
		} catch (IOException exception) {
			System.out.println("You were disconnected from the server.");
			System.exit(0);
		}
		return false;
	}
}
