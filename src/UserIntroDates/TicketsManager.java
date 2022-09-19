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





public class TicketsManager extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	private JLabel title;
	
	private JLabel PassagerId;
	 private JLabel PassagerName;
	 private JLabel FlightCode;
	 private JLabel Gender;
	 private JLabel PassportNumber;
	 private JLabel Nationality;


	
	// all panels
	private JPanel Title;
	private JPanel SelectButtons;
	private JPanel ButtonsPane;
	private JScrollPane ScrollTabelPane;
	private JPanel SelectArea;
	
	
	//Select fields
	private static JComboBox<String> PasagerSelectID;
	private static JTextField passagerName;
	private static JComboBox<String> FlightCodeSelection;
	private static JTextField gender;
	private static JTextField passportNumber;
	private static JTextField nationality;

	
	// sSelectLabels.elect buttons
	private JButton book;
	private JButton reset;
	private JButton back;

	private JPanel mainTablePane;
	private JTable table;
	private Object[] columns={};
	private DefaultTableModel model;
	
	
	
	private GridBagConstraints gbc;
	
	TicketsManager(){
		
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
		
		title=new JLabel("Tickets Booking");
		title.setFont(new Font("Oswald",Font.BOLD,20));
		Title=new JPanel();
		Title.add(title);
		Title.setBounds(0,0,800,100);
		
		PassagerName=new JLabel("Passager Name");
		Nationality=new JLabel("Nationality");    
		Gender=new JLabel("Gender"); 
		PassportNumber=new JLabel("Passport Number");
		FlightCode=new JLabel("Flight Code"); 
		PassagerId=new JLabel("Passager Id");
		
	
		passagerName=new JTextField(20);
		passagerName.setEditable(false);
		
		PasagerSelectID=new JComboBox<String>(getPassagerId());
		FlightCodeSelection=new JComboBox<String>(getFlightCode()) ;
		
		gender=new JTextField(10);
		gender.setEditable(false);

		passportNumber=new JTextField(10);
		passportNumber.setEditable(false);

		nationality=new JTextField(10);
		nationality.setEditable(false);

		
		PasagerSelectID.addActionListener(this);

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
		SelectButtons.add(PassagerId,gbc);
		gbc.gridx=1;
		gbc.gridy=0;
		SelectButtons.add(PassagerName,gbc);
		gbc.gridx=2;
		gbc.gridy=0;
		SelectButtons.add(FlightCode,gbc);
		gbc.gridx=3;
		gbc.gridy=0;
		SelectButtons.add(Gender,gbc);
		gbc.gridx=4;
		gbc.gridy=0;
		SelectButtons.add(PassportNumber,gbc);
		gbc.gridx=5;
		gbc.gridy=0;
		SelectButtons.add(Nationality,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		SelectButtons.add(PasagerSelectID,gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		SelectButtons.add(passagerName,gbc);
		gbc.gridx=2;
		gbc.gridy=1;
		SelectButtons.add(FlightCodeSelection,gbc);
		gbc.gridx=3;
		gbc.gridy=1;
		SelectButtons.add(gender,gbc);
		gbc.gridx=4;
		gbc.gridy=1;
		SelectButtons.add(passportNumber,gbc);
		gbc.gridx=5;
		gbc.gridy=1;
		SelectButtons.add(nationality,gbc);
		
		book=new JButton("Book");  
		reset=new JButton("Reset");  
		back=new JButton("Back");  
		
		book.addActionListener(this);
		reset.addActionListener(this);
		back.addActionListener(this);
		
		ButtonsPane=new JPanel();
		ButtonsPane.setLayout(new FlowLayout());
		ButtonsPane.add(book);
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

		String name=passagerName.getText();
		String passagerSelectID=(String)PasagerSelectID.getSelectedItem();
		String flightSelectCode=(String)FlightCodeSelection.getSelectedItem();
		String passNumber=passportNumber.getText();
		String Gender=gender.getText();
		String nation=nationality.getText();



		//BOOK BUTTON 
		if(book.equals(e.getSource())) { //Register button action
			int cont=0;
			int currentSeats=0;
			if(TextFieldEmpty()!=0) {
				try {
					
					
					Connection connectPassagersDB =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456"); //connection with database
					String query= "SELECT * FROM tickets WHERE flight_id='"+flightSelectCode+"'";  // the query which get the compatibility between the inputs values and values from database
					PreparedStatement pst= connectPassagersDB.prepareStatement(query); //the statement which have the query
					ResultSet rezSet=pst.executeQuery(); // variable where I pass in the value of the query 
					
					while(rezSet.next()) {
						cont++;
					}
					
					Connection connectFlightsDB =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flights_info", "root","123456"); //connection with database
					query="SELECT * FROM flights WHERE id='"+flightSelectCode+"'";
					pst=connectFlightsDB.prepareStatement(query);
					rezSet=pst.executeQuery();
					
					if(rezSet.next()) {
						currentSeats=rezSet.getInt("seats");

					}
					
					query="SELECT passagers_ID,flight_id FROM tickets WHERE passagers_ID='"+passagerSelectID+"'AND flight_id='"+flightSelectCode+"'";
					rezSet=pst.executeQuery();
					if(rezSet.next()) {
						if(cont>=currentSeats) {
							JOptionPane.showMessageDialog(null, "This flight is full ");
						}else {
							query ="INSERT INTO tickets values("+"'0','"+passagerSelectID+"','"+name+"'"+",'"+ flightSelectCode +"','"+ Gender +"','"+passNumber+"','"+nation+"')"; //insert values into database
							Statement sta=connectPassagersDB.createStatement();
							sta.executeUpdate(query);
							JOptionPane.showMessageDialog(null, "Ticket Booked ! ");

						}
					}else {
						
						JOptionPane.showMessageDialog(null, "This reservation already exists ! ");

					}
					
					
					
					connectPassagersDB.close();
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
		if(e.getSource()==PasagerSelectID) {
			GetPassagersData();
		}
		
}
//END ACTION LISTENER 	
	
	
	//	Function for verify if text fields are filled
	static int TextFieldEmpty() {
		

		
		if(passagerName.getText().equals("")) {
			
				JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
				return 0;
			
		}
		
		if(passportNumber.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
				return 0;
				
				}
		if(gender.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
			return 0;
			}
		if(nationality.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
			return 0;
			}
		return 1;
		
	}
	
	
	//	function for clear the fields
	private static void ClearFields() {
		passagerName.setText("");

		passportNumber.setText("");
		
		gender.setText("");
		
		nationality.setText("");
	}
	
	//Function for verify if a row from a table is selected
	static int IsCellSelected(JTable table) {
		int Index=table.getSelectedRow();
		return Index;
		
	}
	
	//Function for rewrite the table if a value has changed
	public void DisplayItems() {
		
				try {
					Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456"); //connection with database
					String query= "SELECT * FROM tickets ";  // the query which get the compatibility between the inputs values and values from database
					Statement st= connection.createStatement();
					ResultSet rs=st.executeQuery(query);
					table.setModel(DbUtils.resultSetToTableModel(rs));

					
				}catch(Exception e) {
					e.printStackTrace();
				}	
		}
	
	// function for get all passagers IDs from DataBase Id to pass into the ComboBox
	private static String[] getPassagerId() {
		Connection connection;
		String query;
		Statement st;
		ResultSet rs;
		List<String> values=new ArrayList<String>();
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456");
			query= "SELECT id FROM passagers_info  ";  // the query which get the passagers Ids
			st= connection.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()) {
				values.add(rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} //connection with database

			String[] returnValues=new String[values.size()];
			values.toArray(returnValues);
			return returnValues;
		
	}
	
	
	// function for get all Flights IDs from DataBase Id to pass into the ComboBox
	private static String[] getFlightCode() {
		Connection connection;
		String query;
		Statement st;
		ResultSet rs;
		List<String> values=new ArrayList<String>();
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flights_info", "root","123456");
			query= "SELECT id FROM flights";  // the query which get the passagers Ids
			st= connection.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()) {
				values.add(rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} //connection with database

		String[] returnValues=new String[values.size()];
		values.toArray(returnValues);
		return returnValues;
		
	}
	
	private static void GetPassagersData() {
		Connection connection;
		String query;
		Statement st;
		ResultSet rs;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456");
			query= "SELECT * FROM passagers_info WHERE id='"+PasagerSelectID.getSelectedItem()+"'";  // the query which get the passagers Ids
			st= connection.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()) {
				passagerName.setText(rs.getString("passager_name"));
				gender.setText(rs.getString("gender"));
				passportNumber.setText(rs.getString("passport_number"));
				nationality.setText(rs.getString("nationality"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} //connection with database

	}

}
