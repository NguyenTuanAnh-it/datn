/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Context.KetNoi;
import Entity.OrderDetail;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class OrderDetailDao {

    public void insertOrderDetail(OrderDetail orderDetail) throws ClassNotFoundException {
        String sql = "INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderDetail.getOrder().getId());
            ps.setInt(2, orderDetail.getProduct().getId());
            ps.setInt(3, orderDetail.getQuantity());
            ps.setDouble(4, orderDetail.getPrice());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean kiemTraNguoiDungDaMua(int userId, int productId) throws ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM `order` o "
                + "JOIN order_detail od ON o.id = od.order_id "
                + "WHERE o.user_id = ? AND od.product_id = ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu COUNT(*) > 0 tức là đã mua hàng
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
