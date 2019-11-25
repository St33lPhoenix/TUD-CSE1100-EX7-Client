package nl.tudelft.gbot.chat.client;

import java.io.IOException;

import nl.tudelft.gbot.chat.client.api.IChatClient;

public class ShutdownThread extends Thread {
	private final IChatClient client;
	/**
	 * Thread used as shutdown hook to call {@link IChatClient#close()}
	 * @param client client to close
	 * @see Runtime#addShutdownHook(Thread)
	 */
	public ShutdownThread(IChatClient client) {
		super("Client closer thread");
		if (client == null) {
			throw new IllegalArgumentException("Client cannot be null");
		}
		this.client = client;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void run() {
		try {
			client.close();
		} catch (IOException exception) {
			System.err.println("Could not close client properly: " + exception.getMessage());
		}
	}
}
