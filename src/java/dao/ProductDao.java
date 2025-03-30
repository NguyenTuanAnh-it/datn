/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Context.KetNoi;
import DTO.Response.GoodsReportRes;
import Entity.Category;
import Entity.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ProductDao {

    public List<Product> getListByCategory(String categoryId, int page, int pageSize) throws ClassNotFoundException {
        List<Product> list = new ArrayList<>();
        String sql = (categoryId == null || categoryId.trim().isEmpty())
                ? "SELECT * FROM product ORDER BY id LIMIT ? OFFSET ?"
                : "SELECT * FROM product WHERE ctgid = ? ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize;
            if (categoryId == null || categoryId.trim().isEmpty()) {
                ps.setInt(1, pageSize);
                ps.setInt(2, offset);
            } else {
                ps.setString(1, categoryId);
                ps.setInt(2, pageSize);
                ps.setInt(3, offset);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("id"), rs.getInt("ctgid"), rs.getString("name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("quantity"), rs.getString("detail")
                    ));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Product> getListByKeyword(String keyword, int categoryId, int page, int pageSize) throws ClassNotFoundException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name LIKE ?";
        if (categoryId > 0) {
            sql += " AND ctgid = ?";
        }
        sql += " ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize;
            ps.setString(1, "%" + keyword + "%");

            int paramIndex = 2;
            if (categoryId > 0) {
                ps.setInt(paramIndex++, categoryId);
            }
            ps.setInt(paramIndex++, pageSize);
            ps.setInt(paramIndex, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("id"), rs.getInt("ctgid"), rs.getString("name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("quantity"), rs.getString("detail")
                    ));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getTotalProductCount(String categoryId) throws ClassNotFoundException {
        String sql = (categoryId == null || categoryId.trim().isEmpty())
                ? "SELECT COUNT(*) FROM product"
                : "SELECT COUNT(*) FROM product WHERE ctgid = ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            if (categoryId != null && !categoryId.trim().isEmpty()) {
                ps.setString(1, categoryId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getTotalProductCountSearch(String keyword, int categoryId) throws ClassNotFoundException {
        Connection conn = KetNoi.KNCSDL();
        int totalProducts = 0;

        // SQL động: Nếu categoryId = 0 thì bỏ lọc theo danh mục
        String sql = "SELECT COUNT(*) FROM product WHERE name LIKE ?";
        if (categoryId > 0) {
            sql += " AND ctgid = ?";
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            // Nếu có categoryId hợp lệ, thêm vào câu lệnh SQL
            if (categoryId > 0) {
                ps.setInt(2, categoryId);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalProducts = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return totalProducts;
    }

    public Product getProductById(int productId) throws ClassNotFoundException {
        String sql = "SELECT * FROM product WHERE id = ?";
        Product product = null;

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Product(
                            rs.getInt("id"), rs.getInt("ctgid"), rs.getString("name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("quantity"), rs.getString("detail")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return product;
    }

    public void updateProductQuantity(int productId, int quantity) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE product SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Không thể trừ số lượng, sản phẩm có thể đã hết hàng.");
            }
        }
    }

    public int getProductQuantity(int productId) throws ClassNotFoundException, SQLException {
        String sql = "SELECT quantity FROM product WHERE id = ?";
        int quantity = 0;

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    quantity = rs.getInt("quantity");
                }
            }
        }
        return quantity;
    }

    public List<Category> getAllCategories() throws ClassNotFoundException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category";
        Connection conn = KetNoi.KNCSDL();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public boolean isProductNameExists(String name) throws ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM product WHERE name = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT > 0 tức là sản phẩm đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addProduct(Product product) throws ClassNotFoundException {
        if (isProductNameExists(product.getName())) {
            return false;
        }
        String sql = "INSERT INTO product (name, ctgid, price, description, image, quantity, detail) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = KetNoi.KNCSDL();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getCategoryId());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getDescription());
            stmt.setString(5, product.getImage());
            stmt.setInt(6, product.getQuantity());
            stmt.setString(7, product.getDetail());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalProducts(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM product WHERE name LIKE ?";
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

    public List<Product> pagingProducts(int index, String keyword) throws SQLException, ClassNotFoundException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS name "
                + "FROM product p "
                + "JOIN category c ON p.ctgid = c.id "
                + "WHERE p.name LIKE ? "
                + "ORDER BY p.id "
                + "LIMIT 5 OFFSET ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, (index - 1) * 5);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3),
                            rs.getDouble(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getInt(7),
                            rs.getString(8)
                    );
                    product.setCategoryName(rs.getString(9));
                    list.add(product);
                }
            }
        }
        return list;
    }

    public boolean updateProduct(Product product) throws ClassNotFoundException {
        String sql = "UPDATE product SET name = ?, ctgid = ?, price = ?, description = ?, image = ?, quantity = ?, detail = ? WHERE id = ?";
        Connection conn = KetNoi.KNCSDL();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getCategoryId());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getDescription());
            stmt.setString(5, product.getImage());
            stmt.setInt(6, product.getQuantity());
            stmt.setString(7, product.getDetail());
            stmt.setInt(8, product.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }

    }

    public List<Product> getBestSellingProducts(int limit) throws ClassNotFoundException {
        List<Product> bestSellingProducts = new ArrayList<>();
        String sql = "SELECT p.* FROM product p "
                + "JOIN order_detail od ON p.id = od.product_id "
                + "GROUP BY p.id "
                + "ORDER BY SUM(od.quantity) DESC "
                + "LIMIT ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bestSellingProducts.add(new Product(
                            rs.getInt("id"), rs.getInt("ctgid"), rs.getString("name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("quantity"), rs.getString("detail")
                    ));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bestSellingProducts;
    }

    public List<Product> getRelatedProducts(int categoryId, int excludeProductId) throws ClassNotFoundException {
        List<Product> relatedProducts = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE ctgid = ? AND id != ? LIMIT 6";

        try {
            Connection conn = KetNoi.KNCSDL();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            stmt.setInt(2, excludeProductId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                relatedProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return relatedProducts;
    }

    public List<GoodsReportRes> getProductsByCategory(Integer categoryId, int limit, int offset) throws SQLException, ClassNotFoundException {
        List<GoodsReportRes> productList = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.price, p.description, p.quantity, c.name as category_name "
                + "FROM product p JOIN category c ON p.ctgid = c.id ";
        if (categoryId != null) {
            sql += " WHERE p.ctgid = ?";
        }
        sql += " LIMIT ? OFFSET ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (categoryId != null) {
                stmt.setInt(paramIndex++, categoryId);
            }
            stmt.setInt(paramIndex++, limit);
            stmt.setInt(paramIndex, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GoodsReportRes product = new GoodsReportRes(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getString("category_name")
                );
                productList.add(product);
            }
        }
        return productList;
    }

    public int getTotalProducts(Integer categoryId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM product";
        if (categoryId != null) {
            sql += " WHERE ctgid = ?";
        }

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (categoryId != null) {
                stmt.setInt(1, categoryId);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<GoodsReportRes> getProductsByCategory(Integer categoryId) throws SQLException, ClassNotFoundException {
        List<GoodsReportRes> productList = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.price, p.description, p.quantity, c.name as category_name "
                + "FROM product p JOIN category c ON p.ctgid = c.id ";
        if (categoryId != null) {
            sql += " WHERE p.ctgid = ?";
        }

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (categoryId != null) {
                stmt.setInt(1, categoryId);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GoodsReportRes product = new GoodsReportRes(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getString("category_name")
                );
                productList.add(product);
            }
        }
        return productList;
    }

    public int getTotalQuantityAllProducts(Integer categoryId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(quantity) FROM product";
        if (categoryId != null) {
            sql += " WHERE ctgid = " + categoryId;
        }

        try (Connection conn = KetNoi.KNCSDL();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

}
