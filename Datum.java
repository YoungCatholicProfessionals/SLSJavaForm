import javax.swing.JRadioButton;

public class Datum {

	String name;
	String email;
	String phone;
	String city;
	String type;
	String interest;

	public Datum(String name, String email, String phone, String city, JRadioButton youngcp, JRadioButton seasonedcp,
			JRadioButton diocesan, JRadioButton religious, JRadioButton becomeMember, JRadioButton aboutConference,
			JRadioButton startAChapter, JRadioButton findAChapter) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.city = city;
		this.type = formType(youngcp, seasonedcp, diocesan, religious);
		this.interest = formInterest(becomeMember, aboutConference, startAChapter, findAChapter);
	}

	private String formInterest(JRadioButton becomeMember, JRadioButton aboutConference, JRadioButton startAChapter,
			JRadioButton findAChapter) {
		String rv = "";
		if(becomeMember.isSelected())
			rv += "become a member";
		if(aboutConference.isSelected())
			if(rv.equals(""))
				rv += "about conference";
			else {
				rv += ";about conference";
			}
		if(startAChapter.isSelected())
			if(rv.equals(""))
				rv += "start a chapter";
			else {
				rv += ";about conference";
			}
		if(findAChapter.isSelected())
			if(rv.equals(""))
				rv += "find a chapter";
			else {
				rv += ";about conference";
			}
		return rv;
	}

	private String formType(JRadioButton youngcp, JRadioButton seasonedcp, JRadioButton diocesan,
			JRadioButton religious) {
		String rv = "";
		if(youngcp.isSelected())
			rv += "young catholic professionals";
		if(seasonedcp.isSelected())
			if(rv.equals(""))
				rv += "seasoned catholic professional";
			else {
				rv += ";seasoned catholic professional";
			}
		if(diocesan.isSelected())
			if(rv.equals(""))
				rv += "diocesan employee";
			else {
				rv += ";diocesan employee";
			}
		if(religious.isSelected())
			if(rv.equals(""))
				rv += "religious";
			else {
				rv += ";religious";
			}
		return rv;
	}

}
