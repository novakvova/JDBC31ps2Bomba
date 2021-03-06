
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MainApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("Hello Maven");	
		try {
			Connection conn = GetConnection();
			GetVersionPostgres(conn);
			int action = 0;
			do {
				System.out.println("Select operation");
				System.out.println("0.Exit");
				System.out.println("1.Add new author");
				System.out.println("2.Select authors");
				System.out.println("3.Remove by id");
				Scanner in = new Scanner(System.in);
				action = in.nextInt();
				switch (action) {
				case 1: {
					System.out.println("Enter name: ");
					String name = in.next();
					InsertAuthor(conn, name);
					break;
				}
				case 2: {
					SelectAuthors(conn);
					break;
				}
				case 3: {
					System.out.println("Enter author id: ");
					int id = in.nextInt();
					DeleteAuthor(conn, id);
					break;
				}
				}
				
			} while (action != 0);
		
			
		} catch (Exception e) {
			System.out.println("Problem work db");
		}
	}
	
	private static Connection GetConnection() 
			throws SQLException, ClassNotFoundException {
		String hostName = "DESKTOP-3DQAIHJ";
		String dbName = "oksanadb";
		String userName = "ivan";
		String password = "Qwerty1-";
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://" + hostName + ":5432/" + dbName, userName,
				password);
		return conn;
	}
	
	private static void GetVersionPostgres(Connection conn) 
			throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT VERSION()");

		if (rs.next()) {
			System.out.println(rs.getString(1));
		}
	}

	private static void InsertAuthor(Connection conn, String name) 
			throws SQLException {
		String query = "INSERT INTO authors(name) VALUES(?)";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, name);
		pst.executeUpdate();
	}

	private static void SelectAuthors(Connection conn) throws SQLException {
		PreparedStatement pst = conn.prepareStatement("SELECT id, name FROM authors");
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {

			System.out.print(rs.getInt(1));
			System.out.print(": ");
			System.out.println(rs.getString(2));
		}
	}

	private static void DeleteAuthor(Connection conn, int id) 
			throws SQLException {
		String query = "DELETE FROM authors WHERE id = ?";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setInt(1, id);
		pst.executeUpdate();
	}
}
