import java.sql.*;
import java.io.*;

class Main {

    String URL = "jdbc:mysql://localhost:3306/assign2?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String username = "root";
    String password = "iiita";

    void run() {
        System.out.println("Type help for all commands");

        while (true) {
            System.out.print("> ");
            String input = getInput();
            if (input.equals("search")) {
                search();
            } else if (input.equals("delete")) {
                delete();
            } else if (input.equals("update")) {
                update();
            } else if (input.equals("display")) {
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
        Info info = new Info(input);
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, username, password);
            String query = "INSERT INTO info (name, fathername, address, age, mobilenumber, cgpa, rollno) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, info.name);
            pstmt.setString(2, info.fathername);
            pstmt.setString(3, info.address);
            pstmt.setInt(4, info.age);
            pstmt.setString(5, info.mobilenumber);
            pstmt.setFloat(6, info.cgpa);
            pstmt.setString(7, info.rollno);
            pstmt.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void display() {
        System.out.print("Sort by: ");
        String sortBy = getInput();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs;
            if (sortBy.equals("name age")) {
                rs = stmt.executeQuery("SELECT * FROM info ORDER BY NAME, AGE ASC");
            } else if (sortBy.equals("")) {
                rs = stmt.executeQuery("SELECT * FROM info");
            } else {
                rs = stmt.executeQuery("SELECT * FROM info ORDER BY " + sortBy + " ASC");
            }
            displayResults(rs);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void search() {
        System.out.print("Rollno: ");
        String rollno = getInput();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, username, password);
            String query = "SELECT * FROM info WHERE rollno = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, rollno);
            ResultSet rs = pstmt.executeQuery();
            displayResults(rs);
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void update() {
        System.out.print("Rollno: ");
        String rollno = getInput();
        String[] input = getInput().split(" ");
        Info info = new Info(input);
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, username, password);
            String query = "UPDATE info SET name = ?, fathername = ?, address = ?, age = ?, mobilenumber = ?, cgpa = ?, rollno = ? WHERE rollno = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, info.name);
            pstmt.setString(2, info.fathername);
            pstmt.setString(3, info.address);
            pstmt.setInt(4, info.age);
            pstmt.setString(5, info.mobilenumber);
            pstmt.setFloat(6, info.cgpa);
            pstmt.setString(7, info.rollno);
            pstmt.setString(8, rollno);
            pstmt.execute();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void delete() {
        System.out.println("Rollno: ");
        String rollno = getInput();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, username, password);
            String query = "DELETE FROM info WHERE rollno = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, rollno);
            pstmt.execute();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void displayOptions() {
        System.out.println("insert");
        System.out.println("display");
        System.out.println("search");
        System.out.println("update");
        System.out.println("delete");
        System.out.println("help");
        System.out.println("exit");
    }

    void displayResults(ResultSet rs) throws SQLException {
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

class Info {
    String rollno;
    String name;
    String fathername;
    String address;
    int age;
    String mobilenumber;
    float cgpa;

    Info(String[] input) {
        rollno = input[0];
        name = input[1];
        fathername = input[2];
        address = input[3];
        age = Integer.parseInt(input[4]);
        mobilenumber = input[5];
        cgpa = Float.parseFloat(input[6]);
    }
}
