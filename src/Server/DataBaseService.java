package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DataBaseService {
    private static Connection connection;
    private static Statement statement;

    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:userDB.sqlite");
            statement = connection.createStatement();
        } catch (SQLException |ClassNotFoundException e) {
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

    public static List<String> blacklistLoad(String nick) {
        List<String> blacklist = new ArrayList<>();
        try {
            String sql_request = String.format("SELECT ban FROM main.blacklist WHERE nick = '%s'", nick);
            ResultSet rs = statement.executeQuery(sql_request);
            while (rs.next()){
                blacklist.add(rs.getString("ban"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blacklist;
    }

    public static void blacklistAdd(String nick, String ban) {
        try {
            String sql_request = String.format("INSERT INTO main.blacklist (nick, ban) VALUES ('%s', '%s')", nick, ban);
            statement.executeQuery(sql_request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void blacklistRemove(String nick, String ban) {
        try {
            String sql_request = String.format("DELETE FROM main.blacklist WHERE nick = '%s' AND ban = '%s'", nick, ban);
            statement.executeQuery(sql_request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
