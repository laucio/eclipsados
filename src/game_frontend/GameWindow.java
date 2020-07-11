package game_frontend;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import juego_de_aventura.GraphicalUserInterface;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class GameWindow extends JFrame implements Runnable, Normalizador{

	private static final long serialVersionUID = 5807596703352926257L;

	private JPanel contentPane;
	
	private JPanelBackground panelSettings;
	private JTextField textFieldUserName;
	private JComboBox<String> aventuras;
	
	private JPanelBackground panelAdventures;
	
	private JPanelBackground panelOptions;
	private JButton btnEntrar;
	private JButton btnSalir;

	private GraphicalUserInterface userInterface;

	public GameWindow(GraphicalUserInterface userInterface) {
		
		this.userInterface = userInterface;
		setTitle("Eclipsados");
		setResizable(true);
		setBounds(100, 100, 400, 200);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				textFieldUserName.requestFocus();

			}
		});


		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(1, 1));
		setContentPane(contentPane);

		panelSettings = new JPanelBackground();
		contentPane.add(panelSettings, BorderLayout.NORTH);
		panelSettings.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		panelSettings.setBackground("Images/background.jpg");


		textFieldUserName = new JTextField();

		textFieldUserName.addKeyListener(new KeyAdapter() {
	
	        @Override
	        public void keyTyped(KeyEvent e) {
	            if (textFieldUserName.getText().length() >= 10 ) // limit to 3 characters
	                e.consume();
	        }
		});
		
		textFieldUserName.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldUserName.setText("");
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
		});
		
		textFieldUserName.setText("Escriba su Nombre de Usuario");
		textFieldUserName.setToolTipText("Escriba su nombre de Usuario");
		textFieldUserName.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldUserName.setColumns(20);
		panelSettings.add(textFieldUserName);

		panelAdventures = new JPanelBackground();
		contentPane.add(panelAdventures, BorderLayout.CENTER);
		panelAdventures.setBackground("Images/background.jpg");
		
		
		aventuras = new JComboBox<String>();
		aventuras.setToolTipText("Seleccione una aventura");
		aventuras.addItem("-- Seleccione una aventura --");
		panelAdventures.add(aventuras, BorderLayout.CENTER);

		aventuras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!aventuras.getSelectedItem().equals("-- Seleccione una aventura --")) {
					String aventuraElegida = aventuras.getSelectedItem().toString();
				}

			}
		});

		panelOptions = new JPanelBackground();
		contentPane.add(panelOptions, BorderLayout.SOUTH);
		panelOptions.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		panelOptions.setBackground("Images/background.jpg");
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String mensaje = "";
				int index = aventuras.getSelectedIndex(); 
				
				if(index != 0 && !textFieldUserName.getText().equals("Escriba su Nombre de Usuario")  
						&& !textFieldUserName.getText().equals("") 
						&& isAlphaNumeric(textFieldUserName.getText())) {
					userInterface.setPlayerWindow(index,normalizar(textFieldUserName.getText()));	
				}else {
					if(textFieldUserName.getText().equals("Escriba su Nombre de Usuario") || 
							textFieldUserName.getText().equals("")	){
						mensaje += "Debes ingresar un nombre de usuario\n";
					}
					if(!isAlphaNumeric(textFieldUserName.getText())) {
						mensaje += "El nombre debe ser alfanumerico \nsin espacios (long. max = 10)\n";
					}
					if(index == 0) {
						mensaje += "Debes seleccionar una aventura para poder empezar\n";
					}
					JOptionPane.showMessageDialog(null,
							mensaje);
				}
					
				

			}
		});
		
		btnEntrar.setToolTipText("Click para ingresar a aventura seleccionada");
		btnEntrar.requestFocus();
		panelOptions.add(btnEntrar);
		
		
		JLabel powered = new JLabel("Powered By Eclipsados 2020");
		powered.setForeground(Color.WHITE);
		panelOptions.add(powered, BorderLayout.SOUTH);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int dialogResult = JOptionPane.showConfirmDialog (null, "En verdad desea salir?","Advertencia",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION){
				  System.exit(0);
				}
			}
		});
		
		btnSalir.setToolTipText("Click salir del juego");
		panelOptions.add(btnSalir);
		

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
			}

		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}


	@Override
	public void run() {
		this.setVisible(true);

	}

	public void agregarAventurasComboBox(String adventureName) {
		this.aventuras.addItem(adventureName);
	}
	
	public String normalizar(String cadena) {
		return cadena.substring(0, 1).toUpperCase() + cadena.substring(1).toLowerCase();
	}
	
	public static boolean isAlphaNumeric(String s) {
		return s != null && s.matches("^[a-zA-Z0-9]*$");
	}

}

