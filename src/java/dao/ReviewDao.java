/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Context.KetNoi;
import Entity.Review;
import DTO.Request.ReviewReq;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class ReviewDao {

    public List<Review> getReviewsByProductId(int productId, int page, int pageSize) throws ClassNotFoundException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.id, r.user_id, u.email, r.product_id, r.rating, r.comment, r.review_date "
                + "FROM review r JOIN user u ON r.user_id = u.id "
                + "WHERE r.product_id = ? ORDER BY r.review_date DESC LIMIT ? OFFSET ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setInt(2, pageSize);
            ps.setInt(3, (page - 1) * pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review();
                    review.setId(rs.getInt("id"));
                    review.setUser_id(rs.getInt("user_id"));
                    review.setUsername(rs.getString("email"));
                    review.setProduct_id(rs.getInt("product_id"));
                    review.setRating(rs.getInt("rating"));
                    review.setComment(rs.getString("comment"));
                    review.setReview_date(rs.getDate("review_date").toLocalDate());
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public int getTotalReviewsCount(int productId) throws ClassNotFoundException {
        String sql = "SELECT COUNT(*) as total FROM review WHERE product_id = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addReview(int userId, int productId, int rating, String comment) {
        String sql = "INSERT INTO review (user_id, product_id, rating, comment, review_date) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.setInt(3, rating);
            stmt.setString(4, comment);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<ReviewReq> pagingCategory(int index, String keyword) throws SQLException, ClassNotFoundException {
        List<ReviewReq> list = new ArrayList<>();
        String sql = "SELECT u.email, r.id, r.comment, r.review_date "
                + "FROM review r "
                + "JOIN `user` u ON r.user_id = u.id "
                + "WHERE u.email LIKE ? "
                + "ORDER BY r.review_date DESC "
                + "LIMIT 10 OFFSET ?";

        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, (index - 1) * 3);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ReviewReq(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("comment"),
                            rs.getDate("review_date").toLocalDate()
                    ));
                }
            }
        }
        return list;
    }

    public int getTotalCategory(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) AS total_reviews "
                + "FROM review r "
                + "JOIN `user` u ON r.user_id = u.id "
                + "WHERE u.email LIKE ?";

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

    public boolean deleteReview(int id) throws ClassNotFoundException {
        String sql = "DELETE FROM review WHERE id = ?";
        try (Connection conn = KetNoi.KNCSDL();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
