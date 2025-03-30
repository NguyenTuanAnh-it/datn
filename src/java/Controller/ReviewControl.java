/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DTO.Request.ReviewReq;
import dao.OrderDetailDao;
import dao.ReviewDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ReviewControl", urlPatterns = {"/review"})
public class ReviewControl extends HttpServlet {

    ReviewDao reviewDao = new ReviewDao();
    OrderDetailDao orderDetailDao = new OrderDetailDao();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReviewControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReviewControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if (action == null) {
                getTotalReview(request, response);
            }
             if ("delete".equalsIgnoreCase(action)) {
                deleteReview(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReviewControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReviewControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int userId = Integer.parseInt(request.getParameter("userId"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        // Xử lý rating: Nếu không chọn thì mặc định 5 sao
        String ratingParam = request.getParameter("rating");
        int rating = (ratingParam == null || ratingParam.isEmpty()) ? 5 : Integer.parseInt(ratingParam);

        String comment = request.getParameter("comment");

        try {
            if (orderDetailDao.kiemTraNguoiDungDaMua(userId, productId)) {
                reviewDao.addReview(userId, productId, rating, comment);

                // Đặt thuộc tính vào request
                request.setAttribute("hasPurchased", true);
                request.setAttribute("justReviewed", true);

                // Forward đến trang detail.jsp
                request.getRequestDispatcher("detail?id=" + productId + "&category=" + categoryId).forward(request, response);
            } else {
                response.sendRedirect("detail.jsp?id=" + productId + "&error=not_purchased");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReviewControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getTotalReview(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ServletException, ClassNotFoundException {
        response.setContentType("text/html; charset=UTF-8");
        String indexPage = request.getParameter("index");
        String keyword = request.getParameter("search");
        if (keyword == null) {
            keyword = "";
        }
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);
        int count = reviewDao.getTotalCategory(keyword);
        int endPage = count / 10;
        if (count % 10 != 0) {
            endPage++;
        }
        List<ReviewReq> list = reviewDao.pagingCategory(index, keyword);
        request.setAttribute("listr", list);
        request.setAttribute("endP", endPage);
        request.setAttribute("searchKeyword", keyword);
        request.setAttribute("currentPage", index);
        request.getRequestDispatcher("admin/review.jsp").forward(request, response);
    }

    private void deleteReview(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int reviewId = Integer.parseInt(request.getParameter("id"));
            
            boolean isDeleted = reviewDao.deleteReview(reviewId);
            String message = isDeleted ? "Xóa đánh giá thành công" : "Xóa thất bại";
            String messageType = isDeleted ? "success" : "danger";
            
            response.sendRedirect(request.getContextPath() + "/review?message="
                    + java.net.URLEncoder.encode(message, "UTF-8")
                    + "&messageType=" + messageType);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/review?message="
                    + java.net.URLEncoder.encode("Lỗi hệ thống", "UTF-8")
                    + "&messageType=danger");
        }
    }
}
