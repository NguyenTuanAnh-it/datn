/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Context.KetNoi;
import Entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class UserDao {

    public List<User> getUsersWithPagination(int page, int pageSize, String keyword) throws ClassNotFoundException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE customername LIKE ? LIMIT ? OFFSET ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + (keyword != null ? keyword : "") + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, (page - 1) * pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(
                            rs.getInt("id"),
                            rs.getString("customername"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getBoolean("role")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getAllUser(String keyword) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " WHERE customername LIKE ? OR email LIKE ?";
        }

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(4),
                            rs.getString(3),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getBoolean(7)));
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean addUser(User user) throws ClassNotFoundException {
        String sql = "INSERT INTO user (customername, email, password, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setBoolean(6, user.isRole());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE user SET customername=?, email=?, phone=?, address=?, role=? WHERE id=?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setBoolean(5, user.isRole());
            ps.setInt(6, user.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteUser(int id) throws ClassNotFoundException {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(int id) {
        String query = "SELECT id, customername, email, phone, address, role FROM user WHERE id = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserName(rs.getString("customername"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setRole(rs.getBoolean("role"));
                    return user;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public int getTotalAccount(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM user WHERE customername LIKE ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public List<User> pagingAccount(int index, String keyword) throws SQLException, ClassNotFoundException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE customername LIKE ? ORDER BY id LIMIT 10 OFFSET ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, (index - 1) * 3);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new User(
                            rs.getInt("id"),
                            rs.getString("customername"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getBoolean("role")));
                }
            }
        }
        return list;
    }

    public static void main(String[] args) throws SQLException {
        UserDao dao = new UserDao();
        //int count = dao.getTotalAccount();
        //System.out.println(count);
    }
}
