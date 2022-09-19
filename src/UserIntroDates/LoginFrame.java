package UserIntroDates;

import java.awt.BorderLayout;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginFrame extends JFrame implements ActionListener,KeyListener{

	JPanel titlePane=new JPanel();
	JLabel title=new JLabel("Stefan Airlines System");
	
	JPanel userTextPane=new JPanel();
	static JTextField userName=new JTextField(25);
	static JPasswordField passWord=new JPasswordField(25);
	JLabel user=new JLabel("Username");
	JLabel pass=new JLabel("Password");
	
	JPanel buttonsPane=new JPanel();
	JButton Login=new JButton("Login");
	JButton SignUp=new JButton("Sign Up");
	JButton Close=new JButton("Close");
	
	public LoginFrame(){
		
		ImageIcon icon=new ImageIcon("AirplaneIcon.png");
		
		setIconImage(icon.getImage());
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,250);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
		
		
		
		title.setFont(new Font("Serif", Font. BOLD, 30));
		title.setForeground(Color.black);
		
//		titlePane.setBackground(new Color(59,186,243));
		titlePane.setLayout(new FlowLayout());
		titlePane.add(title);
		
		
		user.setFont(new Font("SansSerif",Font.BOLD,20));
		user.setForeground(Color.black);
		pass.setFont(new Font("SansSerif",Font.BOLD,20));
		pass.setForeground(Color.black);


		userTextPane.setLayout(new FlowLayout());
//		userTextPane.setBackground(new Color(59,186,243));
		userTextPane.add(user);
		userTextPane.add(userName);
		userTextPane.add(pass);
		userTextPane.add(passWord);
		
		buttonsPane.setLayout(new FlowLayout());
//		buttonsPane.setBackground(new Color(59,186,243));
		buttonsPane.add(Login);
		buttonsPane.add(SignUp);
		buttonsPane.add(Close);

		
		Login.addActionListener(this);
		SignUp.addActionListener(this);
		Close.addActionListener(this);
		passWord.addKeyListener(this);
		

		add(titlePane,BorderLayout.NORTH);
		add(userTextPane,BorderLayout.CENTER);		
		add(buttonsPane,BorderLayout.SOUTH);


	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		String username=userName.getText();
		String password=String.valueOf(passWord.getPassword());
		
		username=username.replaceAll("\\s+", "");


		SignUpFrame frame=new SignUpFrame();
		
		if(Login.equals(e.getSource())) {      //Login Button action
			
			try {
				TextFieldEmpty();
				Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users_manager", "root","123456"); //connection with database
				String query= "SELECT * FROM accounts WHERE username='"+username+"' AND password='"+password+"'"; // the query which get the compatibility
				PreparedStatement pst= connection.prepareStatement(query);
				ResultSet rezSet=pst.executeQuery();
				if(rezSet.next()) {          
					
					userName.setText("");
					passWord.setText("");
					setVisible(false);
					dispose();
					FirstPageFrame firstPgFrame=new FirstPageFrame("MENU");
					
				}else {

					JOptionPane.showMessageDialog(null, "Username or Password does not exists! ","Warrning",JOptionPane.WARNING_MESSAGE);

					
				}
				
				connection.close();
				
			}catch(Exception e1) {
				System.out.println("An error occur!"+e1);
			}
			
		}
		
		if(SignUp.equals(e.getSource())) {
			setVisible(false); 
			dispose();
			
			SignUpFrame Sframe=new SignUpFrame("SignUpFrame");
		}
		
		if(Close.equals(e.getSource())) {
			setVisible(false); 
			dispose();
		}
		
		
		
	}
	
	
	
	

	
	static void TextFieldEmpty() throws TextFieldException{
		
		
		if(userName.getText().equals("")) {
			
				JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
			
				throw new TextFieldException("Text Field is empty!");
			
		}
		if(String.valueOf(passWord.getPassword()).equals("")) {
				JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);

				throw new TextFieldException("Text Field is empty!");

		}
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		
		
}
	


	@Override
	public void keyPressed(KeyEvent e) {
		String username=userName.getText();
		String password=String.valueOf(passWord.getPassword());
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			try {
			TextFieldEmpty();
			Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users_manager", "root","123456"); //connection with database
			String query= "SELECT * FROM accounts WHERE username='"+username+"' AND password='"+password+"'"; // the query which get the compatibility
			PreparedStatement pst= connection.prepareStatement(query);
			ResultSet rezSet=pst.executeQuery();
			if(rezSet.next()) {          
				
				userName.setText("");
				passWord.setText("");
				setVisible(false);
				dispose();
				FirstPageFrame firstPgFrame=new FirstPageFrame("MENU");
				
			}else {

				JOptionPane.showMessageDialog(null, "Username or Password does not exists! ","Warrning",JOptionPane.WARNING_MESSAGE);

				
			}
			
			connection.close();
			
		}catch(Exception e1) {
			System.out.println("An error occur!"+e1);
		}
	}		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
