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
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;





public class PassengersManager extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String[] nationality= {"Romanian","Andorra", "Argentina","Armenia","Australia","Austria","Bolivia","Brazil","Bulgaria","Cyprus","Czech Republic","France","Germany","Hungary","Italy"};
	private static String[] gender= {"Male","Female"};

	private JLabel title;
	
	private JLabel PassagerName;
	 private JLabel Nationality;
	 private JLabel Gender;
	 private JLabel PassportNumber;
	 private JLabel Adress;
	 private JLabel Phone;


	
	// all panels
	private JPanel Title;
	private JPanel SelectButtons;
	private JPanel ButtonsPane;
	private JScrollPane ScrollTabelPane;
	private JPanel SelectArea;
	
	
	//Select fields
	private static JTextField passagerName;
	private static JComboBox<String> nationalitySelection;
	private static JComboBox<String> genderSelection;
	private static JTextField passportNumber;
	private static JTextField adress;
	private static JTextField phoneNumber;

	
	// sSelectLabels.elect buttons
	private JButton save;
	private JButton edit;
	private JButton delete;
	private JButton back;

	private JPanel mainTablePane;
	private JTable table;
	private Object[] columns={"ID","Source","Destination","Date","Nr.Seats"};
	private DefaultTableModel model;
	
	
	
	private GridBagConstraints gbc;
	
	PassengersManager(){
		
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
		
		title=new JLabel("Passagers Manager");
		title.setFont(new Font("Oswald",Font.BOLD,20));
		Title=new JPanel();
		Title.add(title);
		Title.setBounds(0,0,800,100);
		
		PassagerName=new JLabel("Passager Name");
		Nationality=new JLabel("Nationality");    
		Gender=new JLabel("Gender"); 
		PassportNumber=new JLabel("Passport Number");
		Adress=new JLabel("Adress"); 
		Phone=new JLabel("Phone Number");
		
	
		passagerName=new JTextField(20);
		nationalitySelection=new JComboBox<String>(nationality);
		genderSelection=new JComboBox<String>(gender) ;
		adress=new JTextField(10);
		passportNumber=new JTextField(10);
		phoneNumber=new JTextField(10);
		
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
		SelectButtons.add(PassagerName,gbc);
		gbc.gridx=1;
		gbc.gridy=0;
		SelectButtons.add(Nationality,gbc);
		gbc.gridx=2;
		gbc.gridy=0;
		SelectButtons.add(Gender,gbc);
		gbc.gridx=3;
		gbc.gridy=0;
		SelectButtons.add(PassportNumber,gbc);
		gbc.gridx=4;
		gbc.gridy=0;
		SelectButtons.add(Adress,gbc);
		gbc.gridx=5;
		gbc.gridy=0;
		SelectButtons.add(Phone,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		SelectButtons.add(passagerName,gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		SelectButtons.add(nationalitySelection,gbc);
		gbc.gridx=2;
		gbc.gridy=1;
		SelectButtons.add(genderSelection,gbc);
		gbc.gridx=3;
		gbc.gridy=1;
		SelectButtons.add(passportNumber,gbc);
		gbc.gridx=4;
		gbc.gridy=1;
		SelectButtons.add(adress,gbc);
		gbc.gridx=5;
		gbc.gridy=1;
		SelectButtons.add(phoneNumber,gbc);
		
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
				 passagerName.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
				nationalitySelection.setSelectedItem(table.getValueAt(table.getSelectedRow(), 2).toString());
				 genderSelection.setSelectedItem(table.getValueAt(table.getSelectedRow(), 3));
				 passportNumber.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
				 adress.setText(table.getValueAt(table.getSelectedRow(), 5).toString());
				 phoneNumber.setText(table.getValueAt(table.getSelectedRow(), 6).toString());
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

		String name=passagerName.getText();
		String provenience=(String)nationalitySelection.getSelectedItem();
		String gender=(String)genderSelection.getSelectedItem();
		String passNumber=passportNumber.getText();
		String Adress=adress.getText();
		String phoneNumb=phoneNumber.getText();



		//SAVE BUTTON
		if(save.equals(e.getSource())) {  //Register button action
			if(TextFieldEmpty()!=0) {
				try {
					Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456"); //connection with database
					String query= "SELECT * FROM passagers_info WHERE passport_number='"+passNumber+"'";  // the query which get the compatibility between the inputs values and values from database
					PreparedStatement pst= connection.prepareStatement(query); //the statement which have the query
					ResultSet rezSet=pst.executeQuery(); // variable where I pass in the value of the query 
					if(rezSet.next()) {
						JOptionPane.showMessageDialog(null, "Passager already exists!");

					}else {
						JOptionPane.showMessageDialog(null, "Passager has successfully recorded!");
						query ="INSERT INTO passagers_info values('"+"0','"+name+"'"+",'"+ provenience +"','"+ gender +"','"+passNumber+"','"+Adress+"','"+phoneNumb+"')"; //insert values into database
						Statement sta=connection.createStatement();
						sta.executeUpdate(query);
					}
						
						connection.close();
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
		
		//EDIT BUTTON
		if(edit.equals(e.getSource())) {
			
			
			if(TextFieldEmpty()!=0) {
				if(IsCellSelected(table)==-1) {
					JOptionPane.showMessageDialog(null, "Please select a flight ","Warning",JOptionPane.WARNING_MESSAGE);
				}else {
					if(passNumber.equals(table.getValueAt(table.getSelectedRow(), 4).toString())) {
						try {
							Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456"); //connection with database
							String query= "UPDATE passagers_info SET passager_name='"+name+"', nationality='"+provenience+"', gender='"+gender+"', adress='"+Adress+"', phone='"+phoneNumb+"'WHERE passport_number='"+passNumber+"'";  
							Statement update=connection.createStatement();
							update.executeUpdate(query);
							
							JOptionPane.showMessageDialog(null, "Passager Updated ");
	
							connection.close();
							
							DisplayItems();
							ClearFields();
	
						}catch(Exception ex) {
							System.out.println(ex.getMessage());
						}
					}else {

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
			
			int SelectedRow=IsCellSelected(table);
			

			if(SelectedRow!=-1) {
				if(passNumber.equals(table.getValueAt(table.getSelectedRow(), 4).toString())) {

				try {
					Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passagers", "root","123456"); //connection with database
					String query= "DELETE FROM passagers_info WHERE passport_number="+"'"+passNumber+"'";  
					Statement deleteStatement=connection.createStatement();
					deleteStatement.executeUpdate(query);
					JOptionPane.showMessageDialog(null, "Passager Deleted ");

					
					connection.close();
					DisplayItems();
					ClearFields();

				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Please let the same Passport Number! ","Warning",JOptionPane.WARNING_MESSAGE);

			}

		}
			else {
				JOptionPane.showMessageDialog(null, "Please select a flight ","Warning",JOptionPane.WARNING_MESSAGE);
	
	}
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
		if(adress.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
			return 0;
			}
		if(phoneNumber.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Text Field Empty!", "Warrning",JOptionPane.WARNING_MESSAGE);
			return 0;
			}
		return 1;
		
	}
	
	
	//	function for clear the fields
	static void ClearFields() {
		passagerName.setText("");

		passportNumber.setText("");
		
		adress.setText("");
		
		phoneNumber.setText("");
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
					String query= "SELECT * FROM passagers_info ";  // the query which get the compatibility between the inputs values and values from database
					Statement st= connection.createStatement();
					ResultSet rs=st.executeQuery(query);
					table.setModel(DbUtils.resultSetToTableModel(rs));

					
				}catch(Exception e) {
					e.printStackTrace();
				}	
		}

}
