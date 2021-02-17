import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientUI extends JFrame implements ActionListener {	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Socket Related
	private static Socket clientSocket;
	private static int PORT;
	private PrintWriter out;

	// JFrame related
	private JPanel contentPane;
	private JTextArea txtAreaLogs;
	private JButton btnStart;
	private JPanel panelNorth;
	private JLabel lblChatClient;
	private JPanel panelNorthSouth;
	private JLabel lblPort;
	private JLabel lblName;
	private JPanel panelSouth;
	private JButton btnSend;
	private JTextField txtMessage;
	private JTextField txtNickname;
	private JTextField txtPort;
	private String clientName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI frame = new ClientUI();
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
	public ClientUI() {
		super("Batiyao ChatApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panelNorth = new JPanel();
		contentPane.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new BorderLayout(0, 0));

		lblChatClient = new JLabel("CHAT CLIENT");
		lblChatClient.setHorizontalAlignment(SwingConstants.CENTER);
		lblChatClient.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNorth.add(lblChatClient, BorderLayout.NORTH);

		panelNorthSouth = new JPanel();
		panelNorth.add(panelNorthSouth, BorderLayout.SOUTH);
		panelNorthSouth.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		lblName = new JLabel("Nickname");
		lblName.setFont(new Font("Arial", Font.PLAIN, 18));
		panelNorthSouth.add(lblName);

		txtNickname = new JTextField();
		txtNickname.setColumns(15);
		panelNorthSouth.add(txtNickname);

		lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Arial", Font.PLAIN, 18));
		panelNorthSouth.add(lblPort);

		txtPort = new JTextField();
		panelNorthSouth.add(txtPort);
		txtPort.setColumns(15);

		btnStart = new JButton("CONNECT");
		panelNorthSouth.add(btnStart);
		btnStart.addActionListener(this);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 12));

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

		txtMessage = new JTextField();
		panelSouth.add(txtMessage);
		txtMessage.setColumns(50);

		btnSend = new JButton("SEND");
		btnSend.addActionListener(this);
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelSouth.add(btnSend);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == btnStart) {
			if(btnStart.getText().equals("CONNECT")) {
				btnStart.setText("DISCONNECT");
				start();
			}else {
				btnStart.setText("CONNECT");
				stop();
			}
		}else if(e.getSource() == btnSend) {
			String message = txtMessage.getText().trim();
			if(!message.isEmpty()) {
				out.println(message);
				txtMessage.setText("");

			}
		}
		//Refresh UI
		refreshUIComponents();
	}

	public void refreshUIComponents()
	{
		return;
	}

	public void start() {
		try {
			PORT = Integer.parseInt(txtPort.getText().trim());
			clientName = txtNickname.getText().trim();
			clientSocket = new Socket("localhost", PORT);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			new Thread(new Listener()).start();
			//send name
			out.println(clientName);
		} catch (Exception err) {
			addToLogs("[ERROR] "+err.getLocalizedMessage());
		}
	}

	public void stop(){
		if(!clientSocket.isClosed()) {
			try {
				clientSocket.close();
			} catch (IOException e1) {}
		}
	}

	public static void addToLogs(String message) {
		System.out.printf("%s %s\n", ServerUI.formatter.format(new Date()), message);
	}

	private static class Listener implements Runnable {
		private BufferedReader in;
		@Override
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String read;
				for(;;) {
					read = in.readLine();
					if (read != null && !(read.isEmpty())) addToLogs(read);
				}
			} catch (IOException e) {
				return;
			}
		}

	}
}
