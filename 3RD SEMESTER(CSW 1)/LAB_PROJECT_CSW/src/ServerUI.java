
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;

public class ServerUI extends JFrame implements ActionListener {	

	private static final long serialVersionUID = 1L;
	// Socket Related

	public static SimpleDateFormat formatter = new SimpleDateFormat("[hh:mm a]");
	private static HashMap<String, PrintWriter> connectedClients = new HashMap<>();
	private static final int MAX_CONNECTED = 50;
	private static int PORT;
	private static ServerSocket server;
	private static volatile boolean exit = false;
	// JFrame related
	private JPanel contentPane;
	private JTextArea txtAreaLogs;
	private JButton btnStart;
	private JLabel lblChatServer;
	private Container panelSouth;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI frame = new ServerUI();
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);
					//Logs
					System.setOut(new PrintStream(new TextAreaOutputStream(frame.txtAreaLogs)));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerUI() 
	{
		super("Batiyao ChatApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
        
        
		
		lblChatServer = new JLabel("CHAT SERVER");
		lblChatServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblChatServer.setFont(new Font("Tahoma", Font.PLAIN, 40));
		contentPane.add(lblChatServer, BorderLayout.NORTH);

		btnStart = new JButton("START");
		btnStart.addActionListener(this);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(btnStart, BorderLayout.WEST);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		txtAreaLogs = new JTextArea();
		txtAreaLogs.setBackground(Color.BLACK);
		txtAreaLogs.setForeground(Color.WHITE);
		txtAreaLogs.setLineWrap(true);
		scrollPane.setViewportView(txtAreaLogs);


		panelSouth = new JPanel();
		FlowLayout fl_panelSouth = (FlowLayout) panelSouth.getLayout();
		fl_panelSouth.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panelSouth, BorderLayout.SOUTH);

		JLabel namelabel = new JLabel("Made By Vinayak");
		namelabel.setForeground(Color.BLACK);
		namelabel.setFont(new Font("Arial", Font.PLAIN, 25));
		namelabel.setBackground(Color.DARK_GRAY);
		panelSouth.add(namelabel,BorderLayout.WEST);
		
		JButton btnRespond = new JButton("RESPOND");
		btnRespond.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				if(false) {
					JOptionPane.showMessageDialog(null,"Server not started yet!");
				}else if(connectedClients.size()==0) {
					JOptionPane.showMessageDialog(null,"No clients Available now!");
					return;
				}
				String msgString=JOptionPane.showInputDialog("Enter your response to all users!");
				if(msgString==null) return;
				broadcastMessage(" [SERVER] : "+msgString);
			}
		});
		btnRespond.setForeground(Color.WHITE);
		btnRespond.setFont(new Font("Arial", Font.PLAIN, 25));
		btnRespond.setBorderPainted(false);
		btnRespond.setBackground(Color.DARK_GRAY);
		btnRespond.setBounds(100, 393, 150, 80);
		panelSouth.add(btnRespond);

		JButton btnclient = new JButton("ONLINE USERS");
		btnclient.setForeground(Color.WHITE);
		btnclient.setFont(new Font("Arial", Font.PLAIN, 25));
		btnclient.setBorderPainted(false);
		btnclient.setBackground(Color.DARK_GRAY);
		btnclient.setBounds(100, 393, 150, 80);
		panelSouth.add(btnclient);

		btnclient.addActionListener(new ActionListener() {
			@SuppressWarnings({ "unused", "rawtypes" })
			public void actionPerformed(ActionEvent e) {
				 Set set = connectedClients.entrySet();
				// Get an iterator
				Iterator i = set.iterator();
				// Display elements  
					System.out.println("Current Online Users are "+connectedClients.size());
					int c=1;
					while(i.hasNext()) {
						Map.Entry me = (Entry) i.next();
						System.out.println(c+"  [ "+me.getKey()+" ] ");
						c++;
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStart) {
			if(btnStart.getText().equals("START")) {
				exit = false;
				getRandomPort();
				start();
				btnStart.setText("STOP");
			}else {
				addToLogs("Chat server stopped...");
				exit = true;
				btnStart.setText("START");
			}
		}

		//Refresh UI
		refreshUIComponents();
	}

	public void refreshUIComponents() {
		lblChatServer.setText("CHAT SERVER" + (!exit ? ": "+PORT:""));
	}

	public static void start() {
		new Thread(new ServerHandler()).start();
	}

	public static void stop() throws IOException {
		if (!server.isClosed()) server.close();
	}

	private static void broadcastMessage(String message) {
		for (PrintWriter p: connectedClients.values()) {
			p.println(message);
		}
	}

	public static void addToLogs(String message) {
		System.out.printf("%s %s\n", formatter.format(new Date()), message);
	}

	private static int getRandomPort() {
		int port = FreePortFinder.findFreeLocalPort();
		PORT = port;
		return port;
	}

	private static class ServerHandler implements Runnable{
		@Override
		public void run() {
			try {
				server = new ServerSocket(PORT);
				addToLogs("Server started on port: " + PORT);
				addToLogs("Now listening for connections...");
				while(!exit) {
					if (connectedClients.size() <= MAX_CONNECTED){
						new Thread(new ClientHandler(server.accept())).start();
					}
				}
			}
			catch (Exception e) {
				addToLogs("\nError occured: \n");
				addToLogs(Arrays.toString(e.getStackTrace()));
				addToLogs("\nExiting...");
			}
		}
	}

	// Start of Client Handler
	private static class ClientHandler implements Runnable {
		private Socket socket;
		private PrintWriter out;
		private BufferedReader in;
		private String name;

		public ClientHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run(){
			addToLogs("Client connected: " + socket.getInetAddress());
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				for(;;) {
					name = in.readLine();
					if (name == null) {
						return;
					}
					synchronized (connectedClients) {
						if (!name.isEmpty() && !connectedClients.keySet().contains(name)) break;
						else out.println("INVALIDNAME");
					}
				}
				out.println("Welcome to the chat group, " + name.toUpperCase() + "!");
				addToLogs(name.toUpperCase() + " has joined.");
				broadcastMessage("[SYSTEM] " + name.toUpperCase() + " has joined.");
				connectedClients.put(name, out);
				String message;
				out.println("You may join the chat now...");
				while ((message = in.readLine()) != null && !exit) {
					if (!message.isEmpty()) {
						if (message.toLowerCase().equals("/quit")) break;
						broadcastMessage(String.format("[%s] %s",name, message));
					}
				}
			} catch (Exception e) {
				addToLogs(e.getMessage());
			} finally {
				if (name != null) {
					addToLogs(" ["+name+"] " + " is leaving");
					connectedClients.remove(name);
					broadcastMessage(" ["+name+"] "+ " has left");
				}
			}
		}
	}

}
