package Controller;

import Entity.Product;
import dao.ProductDao;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SearchControl", urlPatterns = {"/search"})
public class SearchControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy từ khóa tìm kiếm, nếu null thì mặc định là chuỗi rỗng
            String keyword = request.getParameter("keyword");
            if (keyword == null) {
                keyword = ""; 
            }

            // Lấy categoryId, nếu không có hoặc lỗi thì đặt mặc định là 0
            int categoryId = 0;
            String categoryIdParam = request.getParameter("id");
            if (categoryIdParam != null && categoryIdParam.matches("\\d+")) {
                categoryId = Integer.parseInt(categoryIdParam);
            }

            // Thiết lập số sản phẩm trên mỗi trang
            int pageSize = 8;
            int page = 1;

            // Lấy số trang từ request, nếu không hợp lệ thì mặc định là trang 1
            String pageParam = request.getParameter("page");
            if (pageParam != null && pageParam.matches("\\d+")) {
                page = Integer.parseInt(pageParam);
                if (page < 1) {
                    page = 1;
                }
            }

            // Gọi DAO để lấy tổng số sản phẩm phù hợp với điều kiện tìm kiếm
            ProductDao productDao = new ProductDao();
            int totalProducts = productDao.getTotalProductCountSearch(keyword, categoryId);
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            // Đảm bảo page không vượt quá totalPages
            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }

            // Lấy danh sách sản phẩm theo từ khóa và categoryId
            List<Product> productList = productDao.getListByKeyword(keyword, categoryId, page, pageSize);

            // Gửi dữ liệu về JSP
            request.setAttribute("productSearch", productList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("keyword", keyword);
            request.setAttribute("categoryId", categoryId);
            
            request.getRequestDispatcher("/shop.jsp").forward(request, response);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
