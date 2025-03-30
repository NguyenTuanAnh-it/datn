package Controller;

import Entity.Product;
import Entity.Review;
import dao.OrderDetailDao;
import dao.ProductDao;
import dao.ReviewDao;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ProductDetailControl", urlPatterns = {"/detail"})
public class ProductDetailControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        // Lấy thông tin sản phẩm
        int productId = Integer.parseInt(request.getParameter("id"));
        ProductDao productDao = new ProductDao();
        Product product = productDao.getProductById(productId);
        request.setAttribute("product", product);
        
        // Xử lý phân trang
        int page = 1;
        int pageSize = 3; // Số đánh giá trên mỗi trang
        
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        
        // Lấy danh sách đánh giá theo trang
        ReviewDao reviewDao = new ReviewDao();
        List<Review> reviews = reviewDao.getReviewsByProductId(productId, page, pageSize);
        request.setAttribute("reviews", reviews);
        
        // Lấy tổng số đánh giá
        int totalReviews = reviewDao.getTotalReviewsCount(productId);
        int totalPages = (int) Math.ceil((double) totalReviews / pageSize);
        
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalReviews", totalReviews);
        
        // Kiểm tra xem người dùng đã mua sản phẩm này chưa
        int userId = 0;
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            userId = (Integer) session.getAttribute("userId");
            OrderDetailDao orderDetailDao = new OrderDetailDao();
            boolean hasPurchased = orderDetailDao.kiemTraNguoiDungDaMua(userId, productId);
            request.setAttribute("hasPurchased", hasPurchased);
        }
        
        // Kiểm tra xem người dùng vừa đánh giá không
        Boolean justReviewed = (Boolean) session.getAttribute("justReviewed");
        if (justReviewed != null && justReviewed) {
            request.setAttribute("justReviewed", true);
            session.removeAttribute("justReviewed"); // Xóa để không hiển thị lại
        }
        
        // Forward đến trang detail.jsp
        request.getRequestDispatcher("detail.jsp").forward(request, response);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(ProductDetailControl.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
