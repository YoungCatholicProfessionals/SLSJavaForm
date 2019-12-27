import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OutputWriter {

	 final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	 final static String DB_URL = "jdbc:mysql://localhost/sls?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
	 final static String USER = "root";
	 final static String PASS = "AGRmarc6";

	FileWriter fw;
	public OutputWriter(String filename) {
		try {
			fw = new FileWriter(filename, true); // append mode
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*try {
			fw.write("Name, Email, Phone, City, Type, Interest\n");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	void writeDataToFile(Datum d) {
		try {
			fw.write(d.name +","+d.email+","+d.phone+","+d.city+","+d.type+","+d.interest+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void writeDataToScreen(Datum d) {
		System.out.println(d.name +","+d.email+","+d.phone+","+d.city+","+d.type+","+d.interest);
	}

	void writeDataToDatabase(Datum d, Connection conn) {
		if(conn == null)
			conn = connectToDB();
		Statement stmt = null;
		if(conn == null) {
			System.out.println("Can't connect to DB");
			return; 
		}
		else {
			try {
				stmt = conn.createStatement();
				String datumAsInsert = makeInsertStatement(d);
				stmt.execute(datumAsInsert);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String makeInsertStatement(Datum d) {
		String rv ="Insert into people (name, email, city, phone, type, interest) Values (";
		rv += "\""+d.name+ "\", ";
		rv += "\""+d.email+ "\", ";
		rv += "\""+d.city+ "\", ";
		rv += "\""+d.phone+ "\", ";
		rv += "\""+d.type+ "\", ";
		rv += "\""+d.interest+"\");";
		System.out.println(rv);
		return rv;
	}

	static Connection connectToDB() {
		Connection conn = null;
		try{
			Class.forName(JDBC_DRIVER);
			//Open a connection
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return  conn;
	}

	void close() {
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
