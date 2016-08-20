public class Main {

    public static void main(String[] args) {
        Login login = new Login();
        Home home = new Home();
        login.setNextPage(home);
        
        login.run();
    }
}
