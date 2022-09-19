package UserIntroDates;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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


import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;




public class FlightsManager extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String[] locations= {"Roma","Berlin","Belgrad","Bacau","Bucuresti","Paris","New York","Barcelona","Amsterdam"};
	
	private JLabel title;
	
	private JLabel FlightCode;
	 private JLabel Start;
	 private JLabel End;
	 private JLabel TakeofDate;
	 private JLabel Seats;


	
	// all panels
	private JPanel Title;
	private JPanel SelectLabels;
	private JPanel SelectFields;
	private JPanel SelectButtons;
	private JPanel ButtonsPane;
	private JPanel TableTitle;
	private JScrollPane ScrollTabelPane;
	private JPanel SelectArea;
	
	
	//Select fields
	private static JComboBox<String> startLocationsMenu;
	private static JComboBox<String> DestinationsMenu;
	private static JTextField flightCode;
	private static JTextField seatsField;

	
	// sSelectLabels.elect buttons
	private JButton save;
	private JButton edit;
	private JButton delete;
	private JButton back;

	private JPanel mainTablePane;
	private JTable table;
	private Object[] columns={"ID","Source","Destination","Date","Nr.Seats"};
	private DefaultTableModel model;
	
	private JPanel calendarPanel;
	private static JDateChooser dateChooser;
	private static SimpleDateFormat dateFormat;
	private static Date defaultDate;
	
	private GridBagConstraints gbc;
	
	FlightsManager(){
		
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
		
		title=new JLabel("FLIGHT MANAGER");
		title.setFont(new Font("Oswald",Font.BOLD,20));
		Title=new JPanel();
		Title.add(title);
		Title.setBounds(0,0,800,100);
		
		FlightCode=new JLabel("Flight ID");
		Start=new JLabel("Source");    
		End=new JLabel("Destination"); 
		TakeofDate=new JLabel("Takeof Date");
		Seats=new JLabel("Number of Seats"); 
		
		
	
		flightCode=new JTextField(20);
		startLocationsMenu=new JComboBox<String>(locations);
		DestinationsMenu=new JComboBox<String>(locations) ;
		dateChooser = new JDateChooser();
		seatsField=new JTextField(10);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();  
		dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			defaultDate=dateFormat.parse(dtf.format(now));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(defaultDate);
		
		
		calendarPanel =new JPanel();
		calendarPanel.setLayout(new GridLayout(1,1));
		calendarPanel.setPreferredSize(new Dimension(120,50));
		calendarPanel.add(dateChooser);
		
	
		
		
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
		SelectButtons.add(FlightCode,gbc);
		gbc.gridx=1;
		gbc.gridy=0;
		SelectButtons.add(Start,gbc);
		gbc.gridx=2;
		gbc.gridy=0;
		SelectButtons.add(End,gbc);
		gbc.gridx=3;
		gbc.gridy=0;
		SelectButtons.add(TakeofDate,gbc);
		gbc.gridx=4;
		gbc.gridy=0;
		SelectButtons.add(Seats,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		SelectButtons.add(flightCode,gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		SelectButtons.add(startLocationsMenu,gbc);
		gbc.gridx=2;
		gbc.gridy=1;
		SelectButtons.add(DestinationsMenu,gbc);
		gbc.gridx=3;
		gbc.gridy=1;
		SelectButtons.add(calendarPanel,gbc);
		gbc.gridx=4;
		gbc.gridy=1;
		SelectButtons.add(seatsField,gbc);
		
		save=new JButton("Save");  
		edit=new JButton("Edit");  
		delete=new JButton("Delete");
		back=new JButton("Back");  
		
		save.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		back.addActionListener(this);
		
		ButtonsPane=new JPanel();
		ButtonsPane.setLayout(new FlowLayout());
		ButtonsPane.add(save);
		ButtonsPane.add(edit);
		ButtonsPane.add(delete);
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

		
		//put selected items into the textFields with a MouseListener added to table
		table.addMouseListener(new MouseListener(){
	        

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				flightCode.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
	        	startLocationsMenu.setSelectedItem(table.getValueAt(table.getSelectedRow(), 1).toString());
	        	DestinationsMenu.setSelectedItem(table.getValueAt(table.getSelectedRow(), 2).toString());
	        	
	        	String simpleDate=table.getValueAt(table.getSelectedRow(), 3).toString();
	    		dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    		try {
	    			defaultDate=dateFormat.parse(simpleDate);
	    		} catch (ParseException e1) {
	    			e1.printStackTrace();
	    		}
	    		dateChooser.setDateFormatString("yyyy-MM-dd");
	    		dateChooser.setDate(defaultDate);
	    		
	    		seatsField.setText(table.getValueAt(table.getSelectedRow(), 4).toString());				
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    });
		
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

		
		 
		String id=flightCode.getText();
		String source=(String)startLocationsMenu.getSelectedItem();
		String detination=(String)DestinationsMenu.getSelectedItem();
		DateFormat date=new SimpleDateFormat("yyyy-MM-dd");
		String strDate=date.format(dateChooser.getDate());
		String seats=seatsField.getText();

		
		//SAVE BUTTON
		if(save.equals(e.getSource())) {  //Register button action
			if(TextFieldEmpty()!=0) {
				try {
					Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flights_info", "root","123456"); //connection with database
					String query= "SELECT * FROM flights WHERE id='"+id+"'";  // the query which get the compatibility between the inputs values and values from database
					PreparedStatement pst= connection.prepareStatement(query); //the statement which have the query
					ResultSet rezSet=pst.executeQuery(); // variable where I pass in the value of the query 
					System.out.println(rezSet);
					
					if(rezSet.next()) { // function for compare the values which are already into database
						JOptionPane.showMessageDialog(null, "This FLIGHT already exists! ","Warning",JOptionPane.WARNING_MESSAGE);
						connection.close();
					
					}else {

						JOptionPane.showMessageDialog(null, "Flight has successfully recorded");
						query ="INSERT INTO flights values('"+id+"'"+",'"+ source +"','"+ detination +"','"+strDate+"','"+seats+"')"; //insert values into database
						Statement sta=connection.createStatement();
						sta.executeUpdate(query);
						connection.close();
						DisplayItems();
						ClearFields();

					}
				}catch(Exception e1) {
					System.out.println("An exception occured: "+e1);
			}
		}
			else {
				System.out.println("Text Field Empty");
			}
			
		
	}
		
		//EDIT BUTTON
		if(edit.equals(e.getSource())) {
			
			String eId;
			String eSource;
			String eDestination;
			DateFormat eDate;
			String eStrDate;
			String eSeats;
			
			if(TextFieldEmpty()!=0) {
				if(IsCellSelected(table)==-1) {
					JOptionPane.showMessageDialog(null, "Please select a flight ","Warning",JOptionPane.WARNING_MESSAGE);
				}else {
					 eId=table.getValueAt(table.getSelectedRow(), 0).toString();
					 eSource= startLocationsMenu.getSelectedItem().toString();
					 eDestination=DestinationsMenu.getSelectedItem().toString();
					 eDate=new SimpleDateFormat("yyyy-MM-dd");
					 eStrDate=eDate.format(dateChooser.getDate());
					 eSeats=seatsField.getText();
				
				
				try {
					Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flights_info", "root","123456"); //connection with database
					String query= "UPDATE flights SET source='"+eSource+"', destination='"+eDestination+"', takeof_date='"+eStrDate+"', seats='"+eSeats+"'WHERE id='"+eId+"'";  
					Statement update=connection.createStatement();
					update.executeUpdate(query);
					
					JOptionPane.showMessageDialog(null, "Passager Updated ");

					connection.close();
					
					DisplayItems();
					ClearFields();

				}catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}else {
			System.out.println("Text Field Empty");
		}

			
			
	}

		//BACK BUTTON
		if(back.equals(e.getSource())) {
			ClearFields();
			setVisible(false);
			dispose();
			new FirstPageFrame("MENU");

		}
		
		
		//DELETE BUTTON
		if(delete.equals(e.getSource())) {
			
			String deleteId;
			int SelectedRow=IsCellSelected(table);
			

			if(SelectedRow!=-1) {
				try {
					deleteId=flightCode.getText();
					Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flights_info", "root","123456"); //connection with database
					String query= "DELETE FROM flights WHERE id="+"'"+deleteId+"'";  
					Statement deleteStatement=connection.createStatement();
					deleteStatement.executeUpdate(query);
					JOptionPane.showMessageDialog(null, "Passager Deleted ");

					
					connection.close();
					DisplayItems();
					ClearFields();

				}catch(Exception ex) {
					ex.printStackTrace();
				}

			}
			else {
				JOptionPane.showMessageDialog(null, "Please select a flight ","Warning",JOptionPane.WARNING_MESSAGE);
	
		}
	}
		
		
}
	
	
	
	//Function for verify if text fields are filled
	static int TextFieldEmpty() {
		
		DateFormat date=new SimpleDateFormat("yyyy-MM-dd");
		String strDate=date.format(dateChooser.getDate());
		
		if(flightCode.getText().equals("")) {
			
				JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
				return 0;
			
		}
		
		if(seatsField.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
				return 0;
				
				}
		if(strDate.equals(null)) {
			JOptionPane.showMessageDialog(null, "Date Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
			return 0;
			}
		return 1;
		
	}
	
	
	//function for clear the fields
	static void ClearFields() {
		flightCode.setText("");

		seatsField.setText("");
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd ");  
		LocalDateTime now = LocalDateTime.now();  
		
		dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			defaultDate=dateFormat.parse(dtf.format(now));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(defaultDate);
	}
	
	//Function for verify if a row from a table is selected
	static int IsCellSelected(JTable table) {
		int Index=table.getSelectedRow();
		return Index;
		
	}
	
	//Function for rewrite the table if a value has changed
	public void DisplayItems() {
		
				try {
					Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flights_info", "root","123456"); //connection with database
					String query= "SELECT * FROM flights ";  // the query which get the compatibility between the inputs values and values from database
					Statement st= connection.createStatement();
					ResultSet rs=st.executeQuery(query);
					table.setModel(DbUtils.resultSetToTableModel(rs));

					
				}catch(Exception e) {
					e.printStackTrace();
				}	

		
	}

}
