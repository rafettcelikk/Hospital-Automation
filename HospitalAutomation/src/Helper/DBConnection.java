package Helper;
import java.sql.*;

public class DBConnection {
	Connection c = null;
	
	public DBConnection() {
		
	}
	
	public Connection connectDB() {
		try {
			this.c = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital?user=root&password=");
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
}
