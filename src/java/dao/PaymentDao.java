/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Context.KetNoi;
import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class PaymentDao {

    public void insertPayment(int orderId, double amount, String paymentMethod) throws ClassNotFoundException {
        String sql = "INSERT INTO payment (order_id, payment_date, amount, payment_method, status) VALUES (?, NOW(), ?, ?, ?)";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, paymentMethod);
            pstmt.setString(4, "PAID"); // Đảm bảo hợp lệ với DB
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
