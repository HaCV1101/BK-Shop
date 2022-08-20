package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static final String HOST_URI = "jdbc:mysql://localhost:3306/BKSHOP";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    public static final String packagePath = "com.mysql.cj.jdbc.Driver";
    public static Connection connection;

    public Statement statement;

    public ResultSet resultSet;

    public Database() {
        super();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean getConnect() {
        try {
            Class.forName(packagePath);
            connection = DriverManager.getConnection(HOST_URI, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean execute(String s) {
        try {
            return statement.execute(s);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int executeUpdate(String s) {
        try {
            return statement.executeUpdate(s);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean executeQuery(String s) {
        try {
            resultSet = statement.executeQuery(s);
            return resultSet != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean resulSetNext() {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getResulString(String colName) {
        try {
            return resultSet.getString(colName);
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
