import java.sql.*;
import java.io.*;

class Main {

	String URL = "jdbc:mysql://localhost:3306/assign2?useSSL=false";

	void run() {

		while (true) {
			System.out.print("> ");
			String input = getInput();
			if (input.equals("display")) {
				display();
			} else if (input.equals("insert")) {
				insert();
			} else if (input.equals("help")) {
				displayOptions();
			} else if (input.equals("exit")) {
				break;
			}
		}
	}

	void insert() {
		String[] input = getInput().split(" ");
		String rollno = input[0];
		String name = input[1];
		String fathername = input[2];
		String address = input[3];
		int age = Integer.parseInt(input[4]);
		String mobilenumber = input[5];
		float cgpa = Float.parseFloat(input[6]);

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(URL, "root", "iiita");
			String query = "INSERT INTO info (name, fathername, address, age, mobilenumber, cgpa, rollno) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, fathername);
			pstmt.setString(3, address);
			pstmt.setInt(4, age);
			pstmt.setString(5, mobilenumber);
			pstmt.setFloat(6, cgpa);
			pstmt.setString(7, rollno);

			pstmt.execute();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void display() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(URL, "root", "iiita");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM info");

			while (rs.next()) {
				String rollno = rs.getString("rollno");
				String name = rs.getString("name");
				String fathername = rs.getString("fathername");
				String address = rs.getString("address");
				int age = rs.getInt("age");
				String mobilenumber = rs.getString("mobilenumber");
				float cgpa = rs.getFloat("cgpa");

				System.out.println(rollno + " " + name + " " + fathername + " " + address + " " + age + " " + mobilenumber + " " + cgpa);
			}

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

