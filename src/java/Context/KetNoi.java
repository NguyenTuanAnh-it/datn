/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Context;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class KetNoi {
    public static Connection KNCSDL() throws ClassNotFoundException {
    String url = "jdbc:mysql://localhost:3306/quanly_mypham?useSSL=false&serverTimezone=UTC";
    String user = "root";
    String password = "123456";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("Kết nối thành công!");
        return conn;
    } catch (SQLException ex) {
        Logger.getLogger(KetNoi.class.getName()).log(Level.SEVERE, "Lỗi kết nối CSDL", ex);
        ex.printStackTrace();  // In lỗi chi tiết
    }
    return null;
}

    public static void main(String[] args) throws ClassNotFoundException {
    Connection conn = KNCSDL();
    if (conn == null) {
        System.out.println("Lỗi: Không thể kết nối đến CSDL!");
    } else {
        System.out.println("Kết nối thành công!");
    }
}

}
