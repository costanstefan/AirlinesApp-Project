package UserIntroDates;

import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class FirstPageFrame extends JFrame implements ActionListener{

	JLabel midImage;
	
	JPanel imgPanel;
	JPanel btnPanel;
	
	JButton flights;
	JButton passangers;
	JButton tickets;
	JButton cancellation;

	
	
	FirstPageFrame(){};
	
	FirstPageFrame(String message) {
		super(message);
		ImageIcon icon=new ImageIcon("AirplaneIcon.png");
		setSize(500,250);
		setLayout(new BorderLayout());
		setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
		
		icon=new ImageIcon("menuIcon.jpg");
		midImage=new JLabel();
		midImage.setIcon(icon);
		
		imgPanel=new JPanel();
		imgPanel.add(midImage);
		
		flights =new JButton("Flights");
		passangers =new JButton("Passangers");
		tickets =new JButton("Tickets");
		cancellation =new JButton("Cancellation");

		
		btnPanel=new JPanel();
		btnPanel.setLayout(new FlowLayout());
		btnPanel.add(flights);
		btnPanel.add(passangers);
		btnPanel.add(tickets);
		btnPanel.add(cancellation);
		
		flights.addActionListener(this);
		passangers.addActionListener(this);
		tickets.addActionListener(this);
		cancellation.addActionListener(this);
		
		add(imgPanel,BorderLayout.CENTER);
		add(btnPanel,BorderLayout.SOUTH);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(flights.equals(e.getSource())) {
			FlightsManager FM=new FlightsManager();
			setVisible(false);
			dispose();
		}
		if(passangers.equals(e.getSource())) {
			PassengersManager PM=new PassengersManager();
			setVisible(false);
			dispose();
		}
		if(tickets.equals(e.getSource())) {
			TicketsManager TM=new TicketsManager();
			setVisible(false);
			dispose();
		}
		if(cancellation.equals(e.getSource())) {
			Cancelattion CM=new Cancelattion();
			setVisible(false);
			dispose();
		}
	}
}
