/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Category;
import Entity.User;
import com.google.gson.Gson;
import dao.CategoryDao;
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

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CategoryControl", urlPatterns = {"/category"})
public class CategoryControl extends HttpServlet {

    private CategoryDao categoriesDao = new CategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if (action == null) {
                getTotalCategories(request, response);
               
            }
            if ("edit".equalsIgnoreCase(action)) {
                getCategoryById(request, response);
            }
            if ("delete".equalsIgnoreCase(action)) {
                deleteCategory(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CategoryControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "update":
                    updateCategory(request, response);
                    break;
                case "add":
                    addCategory(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Error: " + ex.getMessage());
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/category.jsp").forward(request, response);
        }

    }

    private void getTotalCategories(HttpServletRequest request, HttpServletResponse response)
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
        int count = categoriesDao.getTotalCategory(keyword);
        int endPage = count / 10;
        if (count % 10 != 0) {
            endPage++;
        }
        List<Category> list = categoriesDao.pagingCategory(index, keyword);
        request.setAttribute("listc", list);
        request.setAttribute("endP", endPage);
        request.setAttribute("searchKeyword", keyword);
        request.setAttribute("currentPage", index);
        request.getRequestDispatcher("admin/category.jsp").forward(request, response);
    }
    
    private void getCategoryById(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Category category = categoriesDao.getCategoryById(id);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            if (category != null) {
                String categoryJson = new Gson().toJson(category);
                response.getWriter().write(categoryJson);
            } else {
                System.out.println("⚠️ Không tìm thấy category với ID: " + id);
                response.getWriter().write("{}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        String name = request.getParameter("name");
        if (categoriesDao.isCategoryNameExists(name)) {
            response.sendRedirect(request.getContextPath() + "/category?message="
                    + java.net.URLEncoder.encode("Tên danh mục đã tồn tại!", "UTF-8")
                    + "&messageType=danger");
            return;
        }
        Category category = new Category(name);
        boolean isAdded = categoriesDao.addCategory(category);
        
        String message = isAdded ? "Thêm danh mục thành công" : "Thêm danh mục thất bại";
        String messageType = isAdded ? "success" : "danger";
        
        response.sendRedirect(request.getContextPath() + "/category?message="
                + java.net.URLEncoder.encode(message, "UTF-8") + "&messageType=" + messageType);
    }
    
    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Category category = new Category();
            category.setId(Integer.parseInt(request.getParameter("id")));
            category.setName(request.getParameter("category_name"));
            boolean isUpdated = categoriesDao.updateCategory(category);
            String message = isUpdated ? "Cập nhật thành công" : "Cập nhật thất bại";
            String messageType = isUpdated ? "success" : "danger";
            response.sendRedirect(request.getContextPath() + "/category?message="
                    + java.net.URLEncoder.encode(message, "UTF-8")
                    + "&messageType=" + messageType);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/category?message="
                    + java.net.URLEncoder.encode("Lỗi hệ thống", "UTF-8")
                    + "&messageType=danger");
        }
    }
    
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            
            boolean isDeleted = categoriesDao.deleteCategory(userId);
            String message = isDeleted ? "Xóa danh mục thành công" : "Xóa thất bại";
            String messageType = isDeleted ? "success" : "danger";
            
            response.sendRedirect(request.getContextPath() + "/category?message="
                    + java.net.URLEncoder.encode(message, "UTF-8")
                    + "&messageType=" + messageType);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/category?message="
                    + java.net.URLEncoder.encode("Lỗi hệ thống", "UTF-8")
                    + "&messageType=danger");
        }
    }
    
}
