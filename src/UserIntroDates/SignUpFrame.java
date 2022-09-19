package UserIntroDates;

import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.GridBagConstraints;


import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class SignUpFrame extends JFrame implements ActionListener,KeyListener{
	
	JLabel user=new JLabel("UserName:");
	static JTextField userText=new JTextField(10);
	
	JLabel passWord=new JLabel("Password:");
	static JPasswordField  passWordText=new JPasswordField("",10);
	
	JLabel RePassWoord=new JLabel("RePassWord:");
	static JPasswordField RePassWoordText=new JPasswordField("",10);
	
	JButton register=new JButton("Register");
	
	GridBagConstraints gbc=new GridBagConstraints();
	
	
	public SignUpFrame() {
		
	}
	
	SignUpFrame(String message){
		super(message);
		
		ImageIcon icon=new ImageIcon("AirplaneIcon.png");
		
		setIconImage(icon.getImage());
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,250);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
//		getContentPane().setBackground(new Color(59,186,243));
		setVisible(true);
		
		user.setFont(new Font("Serif", Font. BOLD, 20));
		passWord.setFont(new Font("Serif", Font. BOLD, 20));
		RePassWoord.setFont(new Font("Serif", Font. BOLD, 20));


		
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.ipadx=20;
		gbc.ipady=10;
		gbc.insets=new Insets(5,5,5,5);
		
		gbc.gridx=0;
		gbc.gridy=0;
		add(user,gbc);
		gbc.gridx=1;
		gbc.gridy=0;
		add(userText,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		add(passWord,gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		add(passWordText,gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		add(RePassWoord,gbc);
		gbc.gridx=1;
		gbc.gridy=2;
		add(RePassWoordText,gbc);
		
		gbc.gridwidth=3;
		gbc.gridx=0;
		gbc.gridy=3;
		add(register,gbc);
		
		
		register.addActionListener(this);
		RePassWoordText.addKeyListener(this);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String username= userText.getText();
		String password= String .valueOf(passWordText.getPassword());

		username=username.replaceAll("\\s+", "");

		
		if(register.equals(e.getSource())) {  //Register button action
			try {
				TextFieldEmpty();
				Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users_manager", "root","123456"); //connection with database
				String query= "SELECT * FROM accounts WHERE username='"+username+"' AND password='"+password+"'";  // the query which get the compatibility between the inputs values and values from database
				PreparedStatement pst= connection.prepareStatement(query);
				ResultSet rezSet=pst.executeQuery();
				System.out.println(rezSet);
				if(rezSet.next()) {
					JOptionPane.showMessageDialog(null, "Username or Password exists! ","Warrning",JOptionPane.WARNING_MESSAGE);
				
				}else {

					JOptionPane.showMessageDialog(null, "Welcome, "+username+", your account was successfully created");
					query ="INSERT INTO accounts values('"+"0'"+",'"+ username +"','"+ password +"')";
					Statement sta=connection.createStatement();
					int x=sta.executeUpdate(query);
				}
				
				
				connection.close();
				
				
				userText.setText("");
				passWordText.setText("");
				RePassWoordText.setText("");
				setVisible(false);
				dispose();
				
				LoginFrame frame=new LoginFrame();
				
			}catch(Exception e1) {
				System.out.println("An exception occured: "+e1);
			}
		}
	}
	
	static void TextFieldEmpty() throws TextFieldException{
		
	
			if(userText.getText().equals("")) {
				
					JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
				
					throw new TextFieldException("Text Field is empty!");
				
			}
			if(String.valueOf(passWordText.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);

					throw new TextFieldException("Text Field is empty!");

			}
			if(String.valueOf(RePassWoordText.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
					throw new TextFieldException("Text Field is empty!");

					}
			
			if(!(String.valueOf(RePassWoordText.getPassword()).equals(String.valueOf(passWordText.getPassword())))) {
				JOptionPane.showMessageDialog(null, "Please write the same passwords!", "Warrning",JOptionPane.WARNING_MESSAGE);
				throw new TextFieldException("Passwords are not the same!");
			}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		String username= userText.getText();
		String password= String .valueOf(passWordText.getPassword());

		username=username.replaceAll("\\s+", "");

		
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {  //Register button action
			try {
				TextFieldEmpty();
				Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users_manager", "root","123456"); //connection with database
				String query= "SELECT * FROM accounts WHERE username='"+username+"' AND password='"+password+"'";  // the query which get the copatibility between the inputs and values from database
				PreparedStatement pst= connection.prepareStatement(query);
				ResultSet rezSet=pst.executeQuery();
				System.out.println(rezSet);
				if(rezSet.next()) {
					JOptionPane.showMessageDialog(null, "Username or Password exists! ","Warrning",JOptionPane.WARNING_MESSAGE);
				
				}else {

					JOptionPane.showMessageDialog(null, "Welcome, "+username+", your account was successfully created");
					query ="INSERT INTO accounts values('"+"0'"+",'"+ username +"','"+ password +"')";
					Statement sta=connection.createStatement();
					int x=sta.executeUpdate(query);
				}
				
				
				connection.close();
				
				
				userText.setText("");
				passWordText.setText("");
				RePassWoordText.setText("");
				setVisible(false);
				dispose();
				
				LoginFrame frame=new LoginFrame();
				
			}catch(Exception e1) {
				System.out.println("An exception occured: "+e1);
			}
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
		
}
		
	


