/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DTO.Response.GoodsReportRes;
import dao.ProductDao;
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

@WebServlet(name = "ProductReportControl", urlPatterns = {"/ProductReportControl"})
public class ProductReportControl extends HttpServlet {

    ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            showProduct(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProductReportControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductReportControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void showProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        try {
            int page = 1;
            int pageSize = 7;
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }

            Integer categoryId = null;
            String categoryParam = request.getParameter("categoryId");
            if (categoryParam != null && !categoryParam.isEmpty()) {
                categoryId = Integer.parseInt(categoryParam);
            }

            int offset = (page - 1) * pageSize;
            List<GoodsReportRes> products = productDao.getProductsByCategory(categoryId, pageSize, offset);

            int totalProducts = productDao.getTotalProducts(categoryId);
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            request.setAttribute("products", products);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("categoryId", categoryId);

            request.getRequestDispatcher("/admin/goods_report.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error retrieving products", e);
        }
    }

}
