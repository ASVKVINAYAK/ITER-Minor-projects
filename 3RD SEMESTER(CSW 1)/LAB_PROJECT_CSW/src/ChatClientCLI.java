
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ChatClientCLI {
	private static HashMap<String, PrintWriter> connectedClients = new HashMap<>();
	private static Socket clientSocket;
	private static ServerSocket server;
	private static BufferedReader in;
	private static class Listener implements Runnable {
		private BufferedReader in;
		@Override
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String read;
				for(;;) {
					read = in.readLine();
					if (read != null && !(read.isEmpty())) System.out.println(read);
				}
			} catch (IOException e) {
				return;
			}
		}

	}

	// Writer class:
	// implements the run method from Runnable to enable thread creation for the
	// writing portion of a server to client communication
	private static class Writer implements Runnable {
		private PrintWriter out;
		private BufferedReader in;
		private String name="[SERVER]";
		@Override
		public void run() {
			Scanner write = new Scanner(System.in);
			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				for(;;) {
					if (write.hasNext()) out.println(write.nextLine());
				}		

			} catch (IOException e) {
				write.close();
				return;
			}
		}
	}



	public static void main(String[] args) {
		String ipAddress = null;
		if(args.length != 0) {
			ipAddress = args[0];
		} else {
			ipAddress = "localhost";
		}
		try {
			// this will try to create socket connection if server socket exists
			clientSocket = new Socket(ipAddress, 4378);
		} catch (Exception e) {
			// throws an error if there is no server socket in that port
			e.printStackTrace();
		}
		new Thread(new Writer()).start();
		new Thread(new Listener()).start();
	}
}