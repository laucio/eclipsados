package game_frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import juego_de_aventura.GraphicalUserInterface;

public class PlayerWindow extends JFrame implements Runnable, Normalizador {

	private static final long serialVersionUID = 1292509713072966217L;

	private GraphicalUserInterface guInterface;

	private Container mainContainer;
//Panel superior	
	private JPanelBackground topPanel;
	private JLabel userNameLabel;
	private JLabel pointsLabel;
	private JLabel commandCounterLabel;
	private JLabel currentLocationLabel;

//Panel Medio	
	private JPanelBackground middlePanel;
	private JPanelBackground gridPanel;
	private JButton btnAyuda;
	private JButton btnHistorial;
	private JButton btnSalir;

	private ImageIcon imageIcon;
	private JLabel imageLabel;

	private JTextArea textArea;
	private JScrollPane scrollPane;

//Panel Inferior
	private JPanelBackground bottomPanel;
	private JTextField commandTextField;

	private String adventureName;
	private String userName;

	public PlayerWindow(GraphicalUserInterface guInterface, String adventureName) {
		this.guInterface = guInterface;
		this.adventureName = adventureName.replace("Adventures\\", "").replace(".json", "");
		this.userName = this.guInterface.getUserName();

		setTitle("Eclipsados - " + this.adventureName);
		setResizable(false);
		setBounds(200, 200, 800, 400);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		mainContainer = this.getContentPane();
		mainContainer.setLayout(new BorderLayout(8, 6));
		mainContainer.setBackground(Color.BLACK);
		this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.gray));

		topPanel = new JPanelBackground();
		topPanel.setBorder(new LineBorder(Color.BLUE, 1));
		topPanel.setBackground(Color.ORANGE);
		topPanel.setBackground(Color.BLACK);
		topPanel.setLayout(new GridLayout(1, 4));

		userNameLabel = new JLabel("Usuario: " + guInterface.getUserName());
		currentLocationLabel = new JLabel();
		pointsLabel = new JLabel();
		commandCounterLabel = new JLabel();

		updateWindowInfo();

		userNameLabel.setForeground(Color.WHITE);
		currentLocationLabel.setForeground(Color.WHITE);
		pointsLabel.setForeground(Color.WHITE);
		commandCounterLabel.setForeground(Color.WHITE);

		topPanel.add(userNameLabel, BorderLayout.CENTER);
		topPanel.add(currentLocationLabel, BorderLayout.CENTER);
		topPanel.add(pointsLabel, BorderLayout.CENTER);
		topPanel.add(commandCounterLabel, BorderLayout.CENTER);

		mainContainer.add(topPanel, BorderLayout.NORTH);

		middlePanel = new JPanelBackground();
		middlePanel.setBorder(new LineBorder(Color.BLUE, 1));
		middlePanel.setBackground(Color.BLACK);
		middlePanel.setLayout(new GridLayout(2, 1, 5, 5));

		gridPanel = new JPanelBackground();
		gridPanel.setBorder(new LineBorder(Color.BLUE, 1));
		gridPanel.setBackground(Color.DARK_GRAY);
		gridPanel.setLayout(new GridLayout(3, 1, 5, 5));

		btnAyuda = new JButton();
		btnHistorial = new JButton();
		btnSalir = new JButton();

		btnAyuda.setText("Ayuda");
		btnSalir.setText("Salir");
		btnHistorial.setText("Guardar Historial");

		btnAyuda.setToolTipText("Click para ver posibles comandos");
		btnSalir.setToolTipText("Click para abandonar partida");
		btnHistorial.setToolTipText("Click para guardar progreso");

		btnAyuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showHelp();
			}
		});

		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"En verdad desea salir?\nPerderas tu progreso no guardado", "Advertencia",
						JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					closePlayerWindow();
				}
			}
		});

		btnHistorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guInterface.saveProgress();
				if (guInterface.isSaved()) {
					addTextToTextArea("-- PROGRESO GUARDADO--\n");
				}
			}
		});

		gridPanel.add(btnHistorial);
		gridPanel.add(btnSalir);
		gridPanel.add(btnAyuda);

		middlePanel.add(gridPanel);

		imageLabel = new JLabel();
		imageLabel.setBorder(new LineBorder(Color.BLUE, 1));
		updateImage();

		middlePanel.add(imageLabel, BorderLayout.CENTER);

		mainContainer.add(middlePanel, BorderLayout.WEST);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);

		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setEditable(false);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.CYAN);
		scrollPane.setViewportView(textArea);
		scrollPane.setBorder(new LineBorder(Color.BLUE, 1));

		mainContainer.add(scrollPane, BorderLayout.CENTER);
		bottomPanel = new JPanelBackground();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.setBorder(new LineBorder(Color.BLUE, 1));
		bottomPanel.setBackground("Images/background.jpg");
		bottomPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		textArea.setLineWrap(true);

		commandTextField = new JTextField();
		commandTextField.setBackground(Color.BLACK);
		commandTextField.setForeground(Color.CYAN);
		commandTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					textArea.append(normalizar(guInterface.getUserName()) + ": " + commandTextField.getText() + "\n\n");
					String output = guInterface.processCommand(commandTextField.getText());
					textArea.append(">> " + output + "\n\n");
					updateWindowInfo();
					updateImage();

					if (guInterface.isEndgame()) {
						commandTextField.setText("FELICITACIONES, HAS GANADO LA PARTIDA!");
						commandTextField.setEditable(false);
					} else {
						commandTextField.setText("");
					}

				}
			}
		});

		commandTextField.setToolTipText("Escriba su mensaje para enviar");

		commandTextField.setColumns(65);

		bottomPanel.add(commandTextField, BorderLayout.SOUTH);
		mainContainer.add(bottomPanel, BorderLayout.SOUTH);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				guInterface.openLobby();
			}

		});
	}

	@Override
	public void run() {
		setVisible(true);

	}

	public void addTextToTextArea(String text) {
		textArea.append(text + "\n");
	}

	private void updateWindowInfo() {

		currentLocationLabel.setText("Ubicacion actual: " + normalizar(guInterface.getCurrentLocation()));
		pointsLabel.setText("Vida: " + guInterface.getHitPoints());
		commandCounterLabel.setText("Comandos: " + guInterface.getCommandCounter());
	}

	public void closePlayerWindow() {
		guInterface.openLobby();
		this.dispose();
	}

	public void showHelp() {
		addTextToTextArea("-- AYUDA --");
		addTextToTextArea("Para jugar tu personaje puede realizar acciones como:");
		addTextToTextArea(" - ir a un lugar");
		addTextToTextArea(" - tomar un objeto");
		addTextToTextArea(" - abrir puertas");
		addTextToTextArea(" - atacar con un objeto a otro personaje");
		addTextToTextArea(" - dar un objeto a otro personaje");
		addTextToTextArea(" - hablar con un personaje");
		addTextToTextArea(" - mirar alrededor y mirar tu inventario");
		addTextToTextArea("-- FIN DE AYUDA --\n");
	}

	public void updateImage() {
		imageIcon = new ImageIcon("Images/" + adventureName + "/" + guInterface.getImageName() + ".png");
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);
		imageLabel.setIcon(imageIcon);
	}

	public String normalizar(String cadena) {
		return cadena.substring(0, 1).toUpperCase() + cadena.substring(1).toLowerCase();
	}

}
