package nl.tudelft.gbot.chat.client.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;

public interface IListenerThread extends Closeable, Runnable {
	/**
	 * Start the thread
	 * @see Thread#start()
	 */
	public void start();
	/**
	 * Get the listener's input reader
	 * @return the listener's input reader
	 */
	public BufferedReader getInput();
	/**
	 * Get the listener's output writer
	 * @return the listener's output writer
	 */
	public BufferedWriter getOutput();
}
