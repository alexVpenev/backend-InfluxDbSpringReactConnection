package dbconnection.demo.service;

import dbconnection.demo.entity.Account;
import dbconnection.demo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.*;
import java.util.Optional;

public class AccountService {

    @Autowired
    private Environment env ;


    protected Connection getDatabaseConnection() {

        String url = env.getProperty("spring.datasource.url");
//        String username = env.getProperty();"jdbc:postgresql://52.58.123.96:3636"
//        String password = env.getProperty();


        try {
//            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, "postgres", "asdasd");
            connection.setAutoCommit(false);
            return connection;
        }
        catch (SQLException e) {
//            throw new IllegalStateException("JDBC driver failed to connect to the database " + "jdbc:postgresql://52.58.123.96:3636" + ".", e);
            throw new IllegalStateException(e);
        }

    }


    public boolean addAccount(Account account) {
        Connection connection = this.getDatabaseConnection();
        String sql = "INSERT INTO user (`email`, `password`,`role`) VALUES (?,?,?);";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account.getPassword());
            statement.setString(2, account.getEmail());
            statement.setObject(3,account.getRole());



            statement.executeUpdate();
            return true;


        } catch (SQLException throwable) {}
        finally {
            try{
                connection.commit();
                connection.close();
            }
            catch (SQLException throwable){
                System.out.println("Can't close connection");
            }
        }
        return false;
    }



    public Account getAccountByUsername(String username) {

        String sql = "SELECT * from user WHERE username = ?";
        Connection connection = this.getDatabaseConnection();
        Account account = null;
        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            String id = resultSet.getString("id");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            Role role = Role.valueOf(resultSet.getObject("role").toString());
            account = new Account(id, email, password, role);


        } catch (SQLException throwable) {
            System.out.println("Can't get account by username");
        } finally {
            try {
                connection.commit();
                connection.close();
            } catch (SQLException throwable) {
                System.out.println("Can't close connection");
            }
        }

        return account;

    }

}
