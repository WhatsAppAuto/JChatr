package Chatr;

import Chatr.Client.ConnectionManager;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionManagerTest extends TestCase {

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( ConnectionManagerTest.class );
	}

	public void testServerStress() {
		URL url = null;
		try {
			url = new URL("http://localhost:3456");
		} catch (MalformedURLException e) {
		}
		int clients = 500;
		for (int i = 0; i < clients; i++) {
			(new Thread(new ConnectionManager(url))).start();
			System.out.printf("Instantiated Tread #%d\n", i);
		}
		while (true) {
			assertTrue(true);
		}
	}
}