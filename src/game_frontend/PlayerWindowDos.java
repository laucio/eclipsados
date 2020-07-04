package game_frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class PlayerWindowDos extends JFrame implements Runnable{

	private static final long serialVersionUID = 1292509713072966217L;
	
	Container mainContainer;
	
	private JPanelBackground topPanel;
	
	private JPanelBackground middlePanel;
	
	private JPanelBackground gridPanel;
	private JButton btnAyuda;
	private JButton btnHistorial;
	private JButton btnSalir;
	
	private JPanelBackground bottomPanel;
	
	private String adventureName;
	private String userName;

	public PlayerWindowDos(String userName, String adventureName) {
		this.adventureName = adventureName;
		this.userName = userName;
		setTitle("Eclipsados - " + adventureName);
		setResizable(true);
		setBounds(200, 200, 650, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainContainer = this.getContentPane();
		mainContainer.setLayout(new BorderLayout(8,6));
		mainContainer.setBackground(Color.RED);
		this.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.gray));
		
		topPanel = new JPanelBackground();
		topPanel.setBorder(new LineBorder(Color.black, 3));
		topPanel.setBackground(Color.ORANGE);
		topPanel.setLayout(new FlowLayout(5));
		//agregar cosas a panel de arriba toppanel
		mainContainer.add(topPanel, BorderLayout.NORTH);
		
		middlePanel = new JPanelBackground();
		middlePanel.setBorder(new LineBorder(Color.black, 3));
		middlePanel.setBackground(Color.CYAN);
		middlePanel.setLayout(new FlowLayout(4,4,4));
		
		gridPanel = new JPanelBackground();
		gridPanel.setBorder(new LineBorder(Color.black, 3));
		gridPanel.setBackground(Color.DARK_GRAY);
		gridPanel.setLayout(new GridLayout(3,1,5,5));
		
		btnAyuda = new JButton();
		btnHistorial = new JButton();
		btnSalir = new JButton();
		
		gridPanel.add(btnAyuda);
		gridPanel.add(btnHistorial);
		gridPanel.add(btnSalir);
		
		middlePanel.add(gridPanel);
		mainContainer.add(middlePanel, BorderLayout.WEST);

		
	}

	@Override
	public void run() {
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		PlayerWindowDos ventana = new PlayerWindowDos("Mamita", "Bolitas Calientes");
		ventana.run();
	}
	
}
