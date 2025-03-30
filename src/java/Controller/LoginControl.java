/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.User;
import dao.AccountDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tools.ECrypt;

/**
 *
 * @author ADMIN
 */
@WebServlet("/login")
public class LoginControl extends HttpServlet {

    private AccountDao accDao = new AccountDao();

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
            out.println("<title>Servlet LoginControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginControl at " + request.getContextPath() + "</h1>");
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
        String command = request.getParameter("command");
        if (command != null && command.equals("logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("/QL_BanMyPham/index.jsp");
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
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String email = request.getParameter("email");
        String mat_khau = request.getParameter("mat_khau");
        String err = "";

        if (email.isEmpty() || mat_khau.isEmpty()) {
            err = "Vui lòng nhập đầy đủ thông tin";
        } else {
            try {
                User user = accDao.kiemTraDangNhap(email, ECrypt.maHoaMD5(mat_khau));
                if (user == null) {
                    err = "Email hoặc mật khẩu không chính xác!";
                }

                String url = "/account.jsp";
                if (!err.isEmpty()) {
                    request.setAttribute("err", err);
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", email);
                    session.setAttribute("userId", user.getId());
                    session.setAttribute("customerName", user.getUserName());
                    session.setAttribute("role", user.isRole()); // Lưu role vào session

                    // Kiểm tra role để chuyển hướng
                    if (user.isRole() == true) {
                        url = "/dashboard"; 
                    } else {
                        url = "/index.jsp";
                    }
                }

                RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
