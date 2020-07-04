package game_frontend;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PlayerWindow extends JFrame implements Runnable {

	private JPanel contentPane;
	private JPanel panelMensajeAEnviar;
	private JTextField textField;
	private JButton btnEnviar;
	private JButton btnDescargar;
	private JScrollPane scrollPane;
	private JTextArea textArea;


	public PlayerWindow() {
		
		setTitle("Nombre de juego");
		setResizable(true);
		setBounds(100, 100, 650, 400);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				textField.requestFocus();

			}
		});

		
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(1, 1));
		setContentPane(contentPane);

		panelMensajeAEnviar = new JPanel();
		contentPane.add(panelMensajeAEnviar, BorderLayout.SOUTH);
		panelMensajeAEnviar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

		JLabel username = new JLabel("Usuario");
		contentPane.add(username, BorderLayout.NORTH);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					textField.setText("");
				}

			}
		});

		textField.setToolTipText("Escriba su mensaje para enviar");
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setColumns(40);
		panelMensajeAEnviar.add(textField);

		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		btnEnviar.setToolTipText("Click para enviar mensaje");
		panelMensajeAEnviar.add(btnEnviar);

		btnDescargar = new JButton("Descargar");
		btnDescargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		btnDescargar.setToolTipText("Click para guardar historial");
		panelMensajeAEnviar.add(btnDescargar);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
			}

		});

		//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}


	public void agregarTextoTextArea(String texto) {
		textArea.append(texto + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	private void selectAllTextoTextField(JTextField textField) {
		textField.requestFocus();
		textField.setSelectionStart(0);
		textField.setSelectionEnd(textField.getText().length());
	}

	@Override
	public void run() {
		this.setVisible(true);

	}

	public String horaYFechaActual() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public void cerrarVentana() {
		this.setVisible(false);
		
	}

	public void bloquearVentana() {
		this.btnEnviar.setVisible(false);;
		this.textArea.setVisible(false);
	}
	
	public static void main(String[] args) {
		PlayerWindow player = new PlayerWindow();
		player.run();
	}

}


