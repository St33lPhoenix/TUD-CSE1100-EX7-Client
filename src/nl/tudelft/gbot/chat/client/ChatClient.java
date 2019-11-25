package nl.tudelft.gbot.chat.client;

import java.io.IOException;
import java.net.Socket;

import nl.tudelft.gbot.chat.client.api.IChatClient;
import nl.tudelft.gbot.chat.client.api.IListenerThread;
import nl.tudelft.gbot.chat.client.listener.InputListenerThread;
import nl.tudelft.gbot.chat.client.listener.ServerListenerThread;

public class ChatClient implements IChatClient {
	private final Socket socket;
	private final IListenerThread input;
	private final IListenerThread server;
	public ChatClient(Socket socket) throws IOException {
		if (socket == null) {
			throw new IllegalArgumentException("Socket cannot be null");
		}
		this.socket = socket;
		
		// Thread for reading from the socket and writing to the terminal
		this.input = new InputListenerThread(socket.getOutputStream());
		
		// Thread for reading from the terminal and writing to the socket
		this.server = new ServerListenerThread(socket.getInputStream());
		
		// Status message
		System.out.println("Connected.");
		
		// First data sent to the socket will be used as username
		System.out.println("Please enter a username:");
		
		// Start the threads
		input.start();
		server.start();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void close() throws IOException {
		System.out.println("Closing chat client...");
		input.close();
		server.close();
		socket.close();
	}
}