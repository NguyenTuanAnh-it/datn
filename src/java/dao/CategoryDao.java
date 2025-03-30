/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Context.KetNoi;
import Entity.Category;
import Entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class CategoryDao {

    public ArrayList<Category> getListCategory() throws ClassNotFoundException {
        String sql = "SELECT * FROM category";
        ArrayList<Category> listCategory = new ArrayList<>();
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                listCategory.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCategory;
    }

    public List<Category> getCategoriesWithPagination(int page, int pageSize, String keyword) throws ClassNotFoundException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category WHERE name LIKE ? LIMIT ? OFFSET ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + (keyword != null ? keyword : "") + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, (page - 1) * pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    categories.add(new Category(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public int getTotalCategory(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM category WHERE name LIKE ?";
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

    public List<Category> pagingCategory(int index, String keyword) throws SQLException, ClassNotFoundException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category WHERE name LIKE ? ORDER BY id LIMIT 10 OFFSET ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, (index - 1) * 3);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Category(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Category(rs.getInt("id"), rs.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addCategory(Category category) throws ClassNotFoundException {
        String sql = "INSERT INTO category (name) VALUES (?)";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isCategoryNameExists(String name) {
        String sql = "SELECT COUNT(*) FROM category WHERE name = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCategory(Category category) throws ClassNotFoundException {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategory(int id) throws ClassNotFoundException {
        String sql = "DELETE FROM category WHERE id = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    

}
