import java.sql.*;
import java.io.*;

class Main {

	void run() {

		while (true) {
			System.out.print("> ");
			String input = getInput();
			
			if (input.equals("insert")) {
				insert();
			} else if (input.equals("help")) {
				displayOptions();
			} else if (input.equals("exit")) {
				break;
			}
		}
	}

	void insert() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/assign2", "root", "iiita");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM info");
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void displayOptions() {
		System.out.println("insert");
		System.out.println("help");
		System.out.println("exit");
	}

	String getInput() {
		String inp = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			inp = br.readLine();
		} catch (IOException iex) {
			iex.printStackTrace();
		}
		return inp;
	}

	public static void main(String[] args) {
		new Main().run();
	}
}

