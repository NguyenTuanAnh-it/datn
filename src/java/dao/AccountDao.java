/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Context.KetNoi;
import Entity.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class AccountDao {

    public boolean kiemTraTaiKhoan(String ten_dang_nhap) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM user WHERE customername = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ten_dang_nhap);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có bản ghi, trả về true
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean kiemTraEmail(String email) throws ClassNotFoundException {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có bản ghi, trả về true
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void themTaiKhoan(User user) throws ClassNotFoundException {
        String sql = "INSERT INTO user (customername, password, email, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setBoolean(4, false); // role = 0 (user)
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User kiemTraDangNhap(String email, String mat_khau) throws ClassNotFoundException {
        String sql = "SELECT id, customername, role FROM user WHERE email = ? AND password = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, mat_khau);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String customerName = rs.getString("customername");
                    boolean role = rs.getBoolean("role");
                    User user = new User();
                    user.setUserName(customerName);
                    user.setId(id);
                    user.setRole(role);
                    return user;// Trả về đối tượng User
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // Trả về null nếu đăng nhập thất bại
    }

    public User getTaiKhoan(String email) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
