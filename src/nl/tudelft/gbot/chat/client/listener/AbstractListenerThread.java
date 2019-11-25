package nl.tudelft.gbot.chat.client.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import nl.tudelft.gbot.chat.client.api.IListenerThread;

public abstract class AbstractListenerThread extends Thread implements IListenerThread {
	private final BufferedReader in;
	private final BufferedWriter out;
	protected AbstractListenerThread(String name, InputStream in, OutputStream out) {
		super(name);
		if (in == null) {
			throw new IllegalArgumentException("Input cannot be null");
		}
		if (out == null) {
			throw new IllegalArgumentException("Output cannot be null");
		}
		
		// Create a BufferedReader from InputStream with UTF-8 encoding
		this.in = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
		
		// Create a BufferedWriter from OutputStream with UTF-8 encoding
		this.out = new BufferedWriter(new OutputStreamWriter(out, Charset.forName("UTF-8")));
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final BufferedReader getInput() {
		return in;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final BufferedWriter getOutput() {
		return out;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void run() {
		while (true) {
			
			// Check socket state
			if (!checkState()) {
				return;
			}
			
			// Try to read the input
			String line;
			try {
				line = in.readLine();
			} catch (IOException exception) {
				System.err.println("Could not read line: " + exception.getMessage());
				continue;
			}
			
			// Try to write the line
			try {
				if (line != null) {
					out.write(line.toCharArray());
					out.write('\n');
					out.flush();
					line = null;
				}
			} catch (IOException exception) {
				System.err.println("Could not write line: " + exception.getMessage());
			}
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void close() throws IOException {
		in.close();
		out.close();
	}
	/**
	 * Check if the connection is still usable
	 * @return true if usable
	 */
	protected boolean checkState() {
		return true;
	}
}
