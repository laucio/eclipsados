package game_frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import juego_de_aventura.GraphicalUserInterface;

public class PlayerWindow extends JFrame implements Runnable{

	private static final long serialVersionUID = 1292509713072966217L;
	
	private GraphicalUserInterface guInterface;
	
	private Container mainContainer;
//Panel superior	
	private JPanelBackground topPanel;
	private JLabel userNameLabel;
	private JLabel pointsLabel;
	private JLabel commandCounter;
	private JLabel currentLocationLabel;
	
//Panel Medio	
	private JPanelBackground middlePanel;
	private JPanelBackground gridPanel;
	private JButton btnAyuda;
	private JButton btnHistorial;
	private JButton btnSalir;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	
//Panel Inferior
	private JPanelBackground bottomPanel;
	private JTextField commandTextField;
	
	
	private String adventureName;
	private String userName;

	public PlayerWindow(GraphicalUserInterface guInterface, String adventureName) {
		this.guInterface = guInterface;
		this.adventureName = adventureName;
		this.userName = this.guInterface.getUserName();
		
		setTitle("Eclipsados - " + adventureName);
		setResizable(false);
		setBounds(200, 200, 800, 400);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
		mainContainer = this.getContentPane();
		mainContainer.setLayout(new BorderLayout(8,6));
		mainContainer.setBackground(Color.BLACK);
		this.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.gray));
		
		topPanel = new JPanelBackground();
		topPanel.setBorder(new LineBorder(Color.BLUE, 1));
		topPanel.setBackground(Color.ORANGE);
		topPanel.setBackground(Color.BLACK);
		topPanel.setLayout(new GridLayout(1,4));
		currentLocationLabel = new JLabel("Current Location");
		pointsLabel = new JLabel("Puntos");
		commandCounter = new JLabel("Movimientos");
		userNameLabel = new JLabel(userName);
		
		userNameLabel.setForeground(Color.WHITE);
		currentLocationLabel.setForeground(Color.WHITE);
		pointsLabel.setForeground(Color.WHITE);
		commandCounter.setForeground(Color.WHITE);
		
		topPanel.add(userNameLabel,BorderLayout.CENTER);
		topPanel.add(currentLocationLabel,BorderLayout.CENTER);
		topPanel.add(pointsLabel,BorderLayout.CENTER);
		topPanel.add(commandCounter,BorderLayout.CENTER);
		
		mainContainer.add(topPanel, BorderLayout.NORTH);
		
		middlePanel = new JPanelBackground();
		middlePanel.setBorder(new LineBorder(Color.BLUE, 1));

		middlePanel.setBackground("Images/background.jpg");
		
		
		gridPanel = new JPanelBackground();
		gridPanel.setBorder(new LineBorder(Color.BLUE, 1));
		gridPanel.setBackground(Color.DARK_GRAY);
		gridPanel.setLayout(new GridLayout(3,1,5,5));
		
		btnAyuda = new JButton();
		btnHistorial = new JButton();
		btnSalir = new JButton();
		
		btnAyuda.setText("Ayuda");
		btnSalir.setText("Salir");
		btnHistorial.setText("Guardar Historial");
		
		
		gridPanel.add(btnHistorial);
		gridPanel.add(btnSalir);
		gridPanel.add(btnAyuda);

		
		
		
		middlePanel.add(gridPanel,BorderLayout.CENTER);
		mainContainer.add(middlePanel, BorderLayout.WEST);
		
		
		scrollPane = new JScrollPane();
        scrollPane.setEnabled(false);

 

        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.CYAN);
        scrollPane.setViewportView(textArea);
        scrollPane.setBorder(new LineBorder(Color.BLUE,1));

        mainContainer.add(scrollPane, BorderLayout.CENTER);
		bottomPanel = new JPanelBackground();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.setBorder(new LineBorder(Color.BLUE,1));
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
                    textArea.append(commandTextField.getText()+"\n");
                    commandTextField.setText("");
                }
            }
        });


        commandTextField.setToolTipText("Escriba su mensaje para enviar");

        commandTextField.setColumns(65);
        

        bottomPanel.add(commandTextField,BorderLayout.SOUTH);
		mainContainer.add(bottomPanel,BorderLayout.SOUTH);
		
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
		textArea.append(text+"\n");
	}
	
}
