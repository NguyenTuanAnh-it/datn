/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Context.KetNoi;
import DTO.Request.OrderDetailReq;
import DTO.Request.OrderReq;
import DTO.Response.RevenueReportRes;
import Entity.Order;
import Entity.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class OrderDao {

    public int insertOrder(Order order) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO `order` (user_id, order_date, recipient_name, status, phone, address, patmentMethod) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int orderId = -1;

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId().getId()); // Nếu userId là Integer, dùng order.getUserId()
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setString(3, order.getRecipientName());
            ps.setString(4, order.getStatus());
            ps.setString(5, order.getPhone());
            ps.setString(6, order.getAddress());
            ps.setString(7, order.getPaymentMethod());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderId;
    }

    public int getNewOrdersCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM `order` WHERE status = 'SHIPPING'";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<OrderDetailReq> getOrdersPaginatedSearch(int page, int pageSize, String searchQuery) throws ClassNotFoundException {
        List<OrderDetailReq> orders = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        searchQuery = searchQuery == null ? "" : searchQuery.trim(); // Loại bỏ khoảng trắng

        String query = "SELECT o.*, u.*, "
                + "(SELECT SUM(od.quantity * od.price) FROM order_detail od WHERE od.order_id = o.id) AS totalAmount "
                + "FROM `order` o "
                + "JOIN user u ON o.user_id = u.id "
                + "WHERE o.recipient_name LIKE ? OR o.phone LIKE ? OR o.address LIKE ? " + // Tìm kiếm theo tên, số điện thoại, địa chỉ
                "ORDER BY o.order_date DESC "
                + "LIMIT ? OFFSET ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + searchQuery + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setInt(4, pageSize);
            pstmt.setInt(5, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OrderDetailReq order = new OrderDetailReq();
                    order.setId(rs.getInt("o.id"));
                    order.setUserId(rs.getInt("o.user_id"));
                    order.setOrderDate(rs.getDate("o.order_date"));
                    order.setRecipientName(rs.getString("o.recipient_name"));
                    order.setStatus(rs.getString("o.status"));
                    order.setPhone(rs.getString("o.phone"));
                    order.setAddress(rs.getString("o.address"));
                    order.setTotalAmount(rs.getDouble("totalAmount"));
                    order.setPaymentMethod(rs.getString("o.patmentMethod"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<OrderDetailReq> getOrdersPaginated(int page, int pageSize) throws ClassNotFoundException {
        List<OrderDetailReq> orders = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String query = "SELECT o.*, u.*, "
                + "(SELECT SUM(od.quantity * od.price) FROM order_detail od WHERE od.order_id = o.id) AS totalAmount "
                + "FROM `order` o "
                + "JOIN user u ON o.user_id = u.id "
                + "ORDER BY o.order_date DESC "
                + "LIMIT ? OFFSET ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OrderDetailReq order = new OrderDetailReq();
                    order.setId(rs.getInt("o.id"));
                    order.setUserId(rs.getInt("o.user_id"));
                    order.setOrderDate(rs.getDate("o.order_date"));
                    order.setRecipientName(rs.getString("o.recipient_name")); // Sửa từ setRecipient_name() thành setRecipientName()
                    order.setStatus(rs.getString("o.status"));
                    order.setPhone(rs.getString("o.phone"));
                    order.setAddress(rs.getString("o.address"));
                    order.setTotalAmount(rs.getDouble("totalAmount"));
                    order.setPaymentMethod(rs.getString("o.patmentMethod"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public int getTotalOrders() throws ClassNotFoundException {
        String query = "SELECT COUNT(*) AS total FROM `order`";

        try (Connection conn = KetNoi.KNCSDL();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean updateOrderStatus(int orderId, String status) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE `order` SET status = ? WHERE id = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public void restoreStockIfCanceled(int orderId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE product p\n"
                + "JOIN order_detail od ON p.id = od.product_id\n"
                + "SET p.quantity = p.quantity + od.quantity \n"
                + "WHERE od.order_id = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        }
    }

    public OrderReq getOrderById(int orderId) throws ClassNotFoundException {
        OrderReq order = null;
        String sql = "SELECT o.id, o.patmentMethod, SUM(od.price * od.quantity) AS total_amount \n"
                + "FROM `order` o\n"
                + "JOIN order_detail od ON o.id = od.order_id\n"
                + "WHERE o.id = ?\n"
                + "GROUP BY o.id, o.patmentMethod";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                order = new OrderReq();
                order.setId(rs.getInt("id"));
                order.setPayment_method(rs.getString("patmentMethod"));
                order.setTotal_amout(rs.getDouble("total_amount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public double getTodayRevenue() throws SQLException, ClassNotFoundException {
        double totalRevenue = 0;
        String query = "SELECT SUM(amount) FROM payment WHERE DATE(payment_date) = CURDATE() AND status = 'PAID'";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalRevenue = rs.getDouble(1);
            }
        }
        return totalRevenue;
    }

    public double getWeeklyRevenue() throws SQLException, ClassNotFoundException {
        double totalRevenue = 0;
        String query = "SELECT SUM(amount) FROM payment WHERE YEARWEEK(payment_date, 1) = YEARWEEK(CURDATE(), 1) AND status = 'PAID'";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalRevenue = rs.getDouble(1);
            }
        }
        return totalRevenue;
    }

    public double getMonthlyRevenue() throws SQLException, ClassNotFoundException {
        double totalRevenue = 0;
        String query = "SELECT SUM(amount) FROM payment WHERE YEAR(payment_date) = YEAR(CURDATE()) AND MONTH(payment_date) = MONTH(CURDATE()) AND status = 'PAID'";

        try (
                Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalRevenue = rs.getDouble(1);
            }
        }
        return totalRevenue;
    }

    public List<RevenueReportRes> getRevenueReport(Date startDate, Date endDate, int page, int pageSize) throws ClassNotFoundException {
        List<RevenueReportRes> reportList = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT o.id, o.order_date, o.recipient_name, o.phone, o.address, "
                + "p.payment_date, p.amount, p.payment_method, p.status "
                + "FROM `order` o "
                + "JOIN `payment` p ON o.id = p.id "
                + "WHERE p.status = 'PAID' "
                + "AND DATE(p.payment_date) BETWEEN ? AND ? "
                + "ORDER BY p.payment_date DESC"
        );

        // Chỉ thêm LIMIT/OFFSET nếu page và pageSize hợp lệ
        if (page > 0 && pageSize > 0) {
            query.append(" LIMIT ? OFFSET ?");
        }

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setDate(1, startDate);
            ps.setDate(2, endDate);

            if (page > 0 && pageSize > 0) {
                ps.setInt(3, pageSize);
                ps.setInt(4, (page - 1) * pageSize);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RevenueReportRes report = new RevenueReportRes(
                            rs.getInt("id"),
                            rs.getDate("order_date"),
                            rs.getString("recipient_name"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getDate("payment_date"),
                            rs.getDouble("amount"),
                            rs.getString("payment_method"),
                            rs.getString("status")
                    );
                    reportList.add(report);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportList;
    }

    public int getTotalRevenueRecords(Date startDate, Date endDate) throws ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM `order` o "
                + "JOIN `payment` p ON o.id = p.id "
                + "WHERE p.status = 'PAID' "
                + "AND DATE(p.payment_date)BETWEEN ? AND ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getTotalRevenueAmount(Date startDate, Date endDate) throws ClassNotFoundException {
        double totalRevenue = 0;
        String query = "SELECT SUM(p.amount) FROM payment p "
                + "JOIN `order` o ON o.id = p.id "
                + "WHERE p.status = 'PAID' AND DATE(p.payment_date) BETWEEN ? AND ?";

        try (Connection con = KetNoi.KNCSDL();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                totalRevenue = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRevenue;
    }

}
