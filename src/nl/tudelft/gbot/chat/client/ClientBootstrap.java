package nl.tudelft.gbot.chat.client;

import java.io.IOException;
import java.net.Socket;

import nl.tudelft.gbot.chat.client.api.IChatClient;

public class ClientBootstrap {
	private static final String IP = "127.0.0.1";
	private static final int PORT = 50000;
	/**
	 * Application entry point
	 * @param args first argument is IP, second is port. This is optional
	 */
	public static final void main(String[] args) {

		// Check Java version
		if (Double.parseDouble(System.getProperty("java.class.version")) < 52D) {
			System.err.println("This application requires Java 8 or higher");
			return;
		}
		
		// Get the IP from command line arguments or use default if unavailable
		String ip = args.length > 0 ? args[0] : IP;
		
		// Get the port from command line arguments or use default if unavailable
		int port = PORT;
		if (args.length > 1) {
			try {
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException exception) {
				// Nothing
			}
		}
		
		// Try to create a new client
		System.out.println("Attempting to connect to " + ip + ':' + port);
		IChatClient client;
		try {
			Socket socket = new Socket(ip, port);
			client = new ChatClient(socket);
		} catch (IOException exception) {
			System.err.println("Could not connect to " + ip + ':' + port);
			return;
		}
		
		// If client creation is successful, we create a new ShutdownThread for it
		// This is to properly close all I/O, because the assignment just ignores it
		Runtime.getRuntime().addShutdownHook(new ShutdownThread(client));
	}
}
