import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Form extends JFrame {
	JTextField nameField = new JTextField("Name");
	JTextField emailField = new JTextField("email");
	JTextField potentialChapterField = new JTextField("City");
	JTextField phoneField = new JTextField("Phone number");
	
	JLabel basicInfo = new JLabel("Contact Information (* means required)");
	JLabel warningLabel = new JLabel("Enter 1) Name, 2) valid Email,  3) City and Pick  4) Type and 5) Interest");
	JLabel nameLabel = new JLabel("Name*");
	JLabel emailLabel = new JLabel("Email*");
	JLabel phoneLabel = new JLabel("Phone");
	JLabel chapterLabel = new JLabel("City/Chapter*");
	
	JLabel type = new JLabel("I am a...*");
	JRadioButton youngcp = new JRadioButton("Young Catholic (20-39)");
	JRadioButton seasonedcp = new JRadioButton("Seasoned Catholic (40+)");
	JRadioButton diocesan = new JRadioButton("Diocesan/Church Employee");
	JRadioButton religious = new JRadioButton("Priest/Religious");
	
	
	JLabel desire = new JLabel("I want to...*");
	JRadioButton startAChapter = new JRadioButton("Start a chapter");
	JRadioButton aboutConference = new JRadioButton("Find out about the conference");
	JRadioButton becomeMember = new JRadioButton("Become a member");
	JRadioButton findAChapter = new JRadioButton("Join a chapter");
	
	JButton submit = new JButton("Submit");
	
	ArrayList<Datum> data = new ArrayList<>();
	
	static OutputWriter ow; 
	static Connection conn;

	public static void main(String[] args) {
		ow = new OutputWriter("/Users/justin/Dropbox/YCP/SLS 2020/macoutput.csv");
		conn = OutputWriter.connectToDB();
		new Form();
		Scanner scan = new Scanner(System.in);
		String line = scan.nextLine();
		if(line.equals("stop")) {
			ow.close();
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Form() {
		// Set up the JFrame
		this.setLayout(new GridLayout(12,2));
		this.setPreferredSize(new Dimension(1000, 500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(basicInfo);
		this.add(warningLabel);
		warningLabel.setVisible(false);
		this.add(nameLabel);
		this.add(nameField);
		this.add(emailLabel);
		this.add(emailField);
		this.add(phoneLabel);
		this.add(phoneLabel);
		this.add(phoneField);
		this.add(chapterLabel);
		this.add(potentialChapterField);
		
		this.add(type);
		this.add(desire);
		this.add(youngcp);
		this.add(becomeMember);
		this.add(seasonedcp);
		this.add(aboutConference);
		this.add(diocesan);
		this.add(startAChapter);
		this.add(religious);
		this.add(findAChapter);
		
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validateData() && twoButtonsSelected()) {
					Datum datum = new Datum(nameField.getText(), emailField.getText(),
							phoneField.getText(), potentialChapterField.getText(),
							youngcp, seasonedcp, diocesan, religious, becomeMember,
							aboutConference, startAChapter, findAChapter);
					//ow.writeDataToFile( datum);
					ow.writeDataToScreen(datum);
					ow.writeDataToDatabase(datum, conn);
					warningLabel.setVisible(false);
					clearFields();
					clearButtons();
				}
				else {
					warningLabel.setVisible(true);
				}
			}
		});

		this.add(submit);
		
		this.pack();
		this.setVisible(true);

	}
	
	private boolean twoButtonsSelected() {
		if(!youngcp.isSelected() && !seasonedcp.isSelected() && !diocesan.isSelected() && !religious.isSelected()) {
			return false;
		}
		if(!becomeMember.isSelected() && !aboutConference.isSelected() &&
				!startAChapter.isSelected() && !findAChapter.isSelected()) {
			return false;
		}
		return true;
	}
	
	boolean validateData() {
		return emailField.getText().contains("@") && !nameField.getText().equals("") && !phoneField.getText().equals("");
	}
	
	void clearFields() {
		nameField.setText("");
		emailField.setText("");
		phoneField.setText("");
		potentialChapterField.setText("");
	}
	void clearButtons() {
		youngcp.setSelected(false);
		seasonedcp.setSelected(false);
		diocesan.setSelected(false);
		religious.setSelected(false);
		becomeMember.setSelected(false);
		aboutConference.setSelected(false);
		startAChapter.setSelected(false);
		findAChapter.setSelected(false);
	}

}
