package Server;

import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement statement;

    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:userDB.sqlite");
            statement = connection.createStatement();
        } catch (SQLException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String password){
        String sql_request = String.format("SELECT nick FROM main.main WHERE login = '%s' AND password = '%s'",
                login, password);
        try {
            ResultSet rs = statement.executeQuery(sql_request);
            if (rs.next()){
                return rs.getString("nick");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
     }

    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
