package UserIntroDates;


import java.awt.Color;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;




import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;



import net.proteanit.sql.DbUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;





public class Cancelattion extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	private JLabel title;
	
	private JLabel TicketID;
	 private JLabel FlightCode;
	 


	
	// all panels
	private JPanel Title;
	private JPanel SelectButtons;
	private JPanel ButtonsPane;
	private JScrollPane ScrollTabelPane;
	private JPanel SelectArea;
	
	
	//Select fields
	private static JComboBox<String> ticketID;
	private static JTextField flightCode;
	

	
	// sSelectLabels.elect buttons
	private JButton cancel;
	private JButton reset;
	private JButton back;

	private JPanel mainTablePane;
	private JTable table;
	private Object[] columns={};
	private DefaultTableModel model;
	
	
	
	private GridBagConstraints gbc;
	
	Cancelattion(){
		
		initFrame();
		DisplayItems();

	}

	
	private void initFrame() {
		ImageIcon icon=new ImageIcon("AirplaneIcon.png");
		
		setIconImage(icon.getImage());
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,550);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		title=new JLabel("Tickets Cancellation");
		title.setFont(new Font("Oswald",Font.BOLD,20));
		Title=new JPanel();
		Title.add(title);
		Title.setBounds(0,0,800,100);
		
		TicketID=new JLabel("Ticket ID");
		FlightCode=new JLabel("Flight Code"); 
		
	

		
		ticketID=new JComboBox<String>(getTicketsId());
		ticketID.addActionListener(this);

		
		flightCode=new JTextField(20);
		flightCode.setEditable(false);
	

		gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.ipadx=25;
		gbc.ipady=5;
		gbc.insets=new Insets(5,5,5,5);

		SelectButtons=new JPanel();
		SelectButtons.setLayout(new GridBagLayout()); 
		SelectButtons.setPreferredSize(new Dimension(1000,100));
		gbc.gridx=0;
		gbc.gridy=0;
		SelectButtons.add(TicketID,gbc);
		gbc.gridx=1;
		gbc.gridy=0;
		SelectButtons.add(FlightCode,gbc);
		
		
		gbc.gridx=0;
		gbc.gridy=1;
		SelectButtons.add(ticketID,gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		SelectButtons.add(flightCode,gbc);
		
		
		cancel=new JButton("Cancel");  
		reset=new JButton("Reset");  
		back=new JButton("Back");  
		
		cancel.addActionListener(this);
		reset.addActionListener(this);
		back.addActionListener(this);
		
		ButtonsPane=new JPanel();
		ButtonsPane.setLayout(new FlowLayout());
		ButtonsPane.add(cancel);
		ButtonsPane.add(reset);
		ButtonsPane.add(back);

		SelectArea=new JPanel();
		SelectArea.setLayout(new GridBagLayout());
		gbc.gridx=0;
		gbc.gridy=0;
		SelectArea.add(SelectButtons,gbc);
		gbc.gridx=0;
		gbc.gridy=1;

		SelectArea.add(ButtonsPane,gbc);
		SelectArea.setBounds(0,100,800,120);

		model=new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			 public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		};
		model.setColumnIdentifiers(columns);
		
		table=new JTable();
		table.setModel(model);

		
		mainTablePane=new JPanel(null);
		table.setFont(new Font("Tahoma",Font.PLAIN,15));
		table.setBackground(Color.white);
		table.setForeground(Color.black);
		table.setGridColor(Color.blue);
		table.setSelectionBackground(Color.red);
		table.setSelectionForeground(Color.white);
		table.setBounds(0,0,600,300);

		
		ScrollTabelPane=new JScrollPane(table);
		ScrollTabelPane.setHorizontalScrollBarPolicy(
			    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		ScrollTabelPane.setForeground(Color.RED);
		ScrollTabelPane.setBackground(Color.white);
		ScrollTabelPane.setBounds(25,0,750,250);
		mainTablePane.add(ScrollTabelPane);
		mainTablePane.setBounds(0,250,800,270);

	
		add(Title);
		add(SelectArea);
		add(mainTablePane);

		
		setVisible(true);

	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)  {

		String FlightCode=flightCode.getText();
		String ticketSelectID=(String)ticketID.getSelectedItem();



		//BOOK BUTTON 
		if(cancel.equals(e.getSource())) { //cancel button action
			
			if(TextFieldEmpty()!=0) {
				try {
					String date = null;
					Connection connectFlightsDB=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flights_info", "root","123456");
					String query="SELECT * FROM flights WHERE id='"+FlightCode+"'";
					Statement st=connectFlightsDB.createStatement();
					ResultSet rezSet=st.executeQuery(query);
					if(rezSet.next()) {
						 date=rezSet.getString("takeof_date");
					}
					System.out.println(date);
					
					Connection connectCancelsDB =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456"); //connection with database
					query= "SELECT * FROM cancellations WHERE ticket_ID='"+ticketSelectID+"'";  // the query which get the compatibility between the inputs values and values from database
					PreparedStatement pst= connectCancelsDB.prepareStatement(query); //the statement which have the query
					rezSet=pst.executeQuery(); // variable where I pass in the value of the query 
					
					if(rezSet.next()) {
						JOptionPane.showMessageDialog(null, "This cancellation already exists !");

					}else {
						query ="INSERT INTO cancellations values("+"'0','"+ticketSelectID+"','"+FlightCode+"','"+ date +"')";//insert values into database
						st=connectCancelsDB.createStatement();
						st.executeUpdate(query);
						JOptionPane.showMessageDialog(null, "Ticket Canceled ! ");


					}
					
					
					
					
					connectCancelsDB.close();
					connectFlightsDB.close();
						DisplayItems();
						ClearFields();

					
				}catch(Exception e1) {
					System.out.println("An exception occured: "+e1);
			}
		}
		else {
			System.out.println("Text Field Empty");
		}
			
		
	}
		
		//RESET BUTTON 
		if(reset.equals(e.getSource())) {
			ClearFields();
	}

		//BACK BUTTON
		if(back.equals(e.getSource())) {
			ClearFields();
			setVisible(false);
			dispose();
			new FirstPageFrame("MENU");

		}
		if(e.getSource()==ticketID) {
			GetTicketsData();
		}
		
}
//END ACTION LISTENER 	
	
	
	//	Function for verify if text fields are filled
	static int TextFieldEmpty() {
		

		
		if(flightCode.getText().equals("")) {
			
				JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
				return 0;
			
		}
		
		
		return 1;
		
	}
	
	
	//	function for clear the fields
	private static void ClearFields() {
		flightCode.setText("");

	}
	
	
	
	//Function for rewrite the table if a value has changed
	public void DisplayItems() {
		
				try {
					Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456"); //connection with database
					String query= "SELECT * FROM cancellations ";  // the query which get the compatibility between the inputs values and values from database
					Statement st= connection.createStatement();
					ResultSet rs=st.executeQuery(query);
					table.setModel(DbUtils.resultSetToTableModel(rs));

					
				}catch(Exception e) {
					e.printStackTrace();
				}	
		}
	
	// function for get all passagers IDs from DataBase Id to pass into the ComboBox
	private static String[] getTicketsId() {
		Connection connection;
		String query;
		Statement st;
		ResultSet rs;
		List<String> values=new ArrayList<String>();
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456");
			query= "SELECT ticket_id FROM tickets  ";  // the query which get the passagers Ids
			st= connection.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()) {
				values.add(String.valueOf(rs.getInt("ticket_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} //connection with database

			String[] returnValues=new String[values.size()];
			values.toArray(returnValues);
			return returnValues;
		
	}
	
	
	
	private static void GetTicketsData() {
		Connection connection;
		String query;
		Statement st;
		ResultSet rs;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456");
			query= "SELECT * FROM tickets WHERE ticket_id='"+ticketID.getSelectedItem().toString()+"'";  // the query which get the passagers Ids
			st= connection.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()) {
				flightCode.setText(rs.getString("flight_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} //connection with database

	}

}
