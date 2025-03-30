/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.User;
import com.google.gson.Gson;
import dao.UserDao;
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
import tools.ECrypt;

/**
 *
 * @author Admin
 */
@WebServlet("/user")
public class UserControl extends HttpServlet {
    
    private UserDao userDao;
    
    @Override
    public void init() throws ServletException {
        userDao = new UserDao();
    }

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if (action == null) {
                getTotalAccount(request, response);
                //getAllUser(request, response);
                
                return;
            }
            if ("edit".equalsIgnoreCase(action)) {
                getUserById(request, response);
            }
            if ("delete".equalsIgnoreCase(action)) {
                deleteUser(request, response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error: " + ex.getMessage());
        }
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
                    updateUser(request, response);
                    break;
                case "add":
                    addUser(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Error: " + ex.getMessage());
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/users.jsp").forward(request, response);
        }
    }
    
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        String userName = request.getParameter("username");
        String password = request.getParameter(ECrypt.maHoaMD5("password"));
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        boolean role = "1".equals(request.getParameter("role"));
        User user = new User(userName, email, password, phone, address, role);
        boolean isAdded = userDao.addUser(user);
        
        String message = isAdded ? "Thêm người dùng thành công" : "Thêm người dùng thất bại";
        String messageType = isAdded ? "success" : "danger";
        
        response.sendRedirect(request.getContextPath() + "/user?message="
                + java.net.URLEncoder.encode(message, "UTF-8") + "&messageType=" + messageType);
    }
    
    
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = new User();
            user.setId(Integer.parseInt(request.getParameter("id")));
            user.setUserName(request.getParameter("username"));
            user.setEmail(request.getParameter("email"));
            user.setPhone(request.getParameter("phone"));
            user.setAddress(request.getParameter("address"));
            user.setRole("1".equals(request.getParameter("role")));
            boolean isUpdated = userDao.updateUser(user);
            String message = isUpdated ? "Cập nhật thành công" : "Cập nhật thất bại";
            String messageType = isUpdated ? "success" : "danger";
            response.sendRedirect(request.getContextPath() + "/user?message="
                    + java.net.URLEncoder.encode(message, "UTF-8")
                    + "&messageType=" + messageType);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/user?message="
                    + java.net.URLEncoder.encode("Lỗi hệ thống", "UTF-8")
                    + "&messageType=danger");
        }
    }
    
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            
            boolean isDeleted = userDao.deleteUser(userId);
            String message = isDeleted ? "Xóa người dùng thành công" : "Xóa thất bại";
            String messageType = isDeleted ? "success" : "danger";
            
            response.sendRedirect(request.getContextPath() + "/user?message="
                    + java.net.URLEncoder.encode(message, "UTF-8")
                    + "&messageType=" + messageType);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/user?message="
                    + java.net.URLEncoder.encode("Lỗi hệ thống", "UTF-8")
                    + "&messageType=danger");
        }
    }
    
    private void getUserById(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userDao.getUserById(id);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            if (user != null) {
                String userJson = new Gson().toJson(user);
                response.getWriter().write(userJson);
            } else {
                System.out.println("⚠️ Không tìm thấy user với ID: " + id);
                response.getWriter().write("{}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void getTotalAccount(HttpServletRequest request, HttpServletResponse response)
        throws IOException, SQLException, ServletException, ClassNotFoundException {
        response.setContentType("text/html; charset=UTF-8");
    String indexPage = request.getParameter("index");
    String keyword = request.getParameter("search");
    if (keyword == null) {
        keyword = "";
    }
    if(indexPage == null){
        indexPage = "1";
    }
    int index = Integer.parseInt(indexPage);
    int count = userDao.getTotalAccount(keyword);
    int endPage = count / 10;
    if (count % 10 != 0) {
        endPage++;
    }
    List<User> list = userDao.pagingAccount(index, keyword);
    request.setAttribute("listP", list);
    request.setAttribute("endP", endPage);
    request.setAttribute("searchKeyword", keyword);
    request.setAttribute("currentPage", index);
    request.getRequestDispatcher("admin/users.jsp").forward(request, response);
}
    


    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
