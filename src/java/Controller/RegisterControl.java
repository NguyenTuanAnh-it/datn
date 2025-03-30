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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tools.ECrypt;

/**
 *
 * @author ADMIN
 */
@WebServlet("/register")
public class RegisterControl extends HttpServlet {

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
            out.println("<title>Servlet RegisterControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterControl at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        String ten_dang_nhap = request.getParameter("ten_dang_nhap");
        String email = request.getParameter("email");
        String mat_khau = request.getParameter("mat_khau");
        String ten_dang_nhap_err = "", email_err = "", mat_khau_err = "";
        try {
            if (ten_dang_nhap.equals("")) {
                ten_dang_nhap_err = "Vui lòng nhập tên tài khoản";
            } else if (accDao.kiemTraTaiKhoan(ten_dang_nhap) == true) {
                ten_dang_nhap_err = "Tên tài khoản đã được đăng ký!";
            }
            if (ten_dang_nhap_err.length() > 0) {
                request.setAttribute("ten_dang_nhap_err", ten_dang_nhap_err);
            }
            
            if (email.equals("")) {
                email_err = "Vui lòng nhập email!";
            }   else if (accDao.kiemTraEmail(email) == true) {
                email_err = "Email đã được đăng ký!";
            }
            
            if (email_err.length() > 0) {
                request.setAttribute("email_err", email_err);
            }
            
            if (mat_khau.equals("")) {
                mat_khau_err = "Vui lòng nhập mật khẩu!";
            }
            if (mat_khau_err.length() > 0) {
                request.setAttribute("mat_khau_err", mat_khau_err);
            }
        } catch (Exception e) {
        }
        request.setAttribute("ten_dang_nhap", ten_dang_nhap);
        request.setAttribute("email", email);
        request.setAttribute("mat_khau", mat_khau);
        String url = "/account.jsp";
        try {
            if (ten_dang_nhap_err.length() == 0 && email_err.length() == 0 && mat_khau_err.length() == 0) {
                User user = new User();
                user.setUserName(ten_dang_nhap);
                user.setEmail(email);
                user.setPassword(ECrypt.maHoaMD5(mat_khau));
                user.setRole(false);
                accDao.themTaiKhoan(user);
                
                url = "/index.jsp";
            } else {
                url = "/account.jsp";
            }

            RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception e) {
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
