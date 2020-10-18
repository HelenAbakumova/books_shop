//package repository;
//
//import entity.User;
//
//import java.sql.*;
//
//public class UserRepository {
//    public User checkLogin(String email, String password) throws SQLException,
//            ClassNotFoundException {
//        String jdbcURL = "jdbc:mysql://localhost:8090/books_shop";
//        String dbUser = "root";
//        String dbPassword = "password";
//
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
//        String sql = "SELECT * FROM users WHERE email = ? and password = ?";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, email);
//        statement.setString(2, password);
//
//        ResultSet result = statement.executeQuery();
//
//        User user = null;
//
//        if (result.next()) {
//            user = new User();
//            user.setFullname(result.getString("fullname"));
//            user.setEmail(email);
//        }
//
//        connection.close();
//
//        return user;
//    }
//}
