/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DTO.Response.RevenueReportRes;
import dao.OrderDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
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
@WebServlet(name = "RevenueControl", urlPatterns = {"/RevenueControl"})
public class RevenueControl extends HttpServlet {

    private OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            listRevenueReport(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RevenueControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void listRevenueReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {

        String startDateParam = request.getParameter("startDate");
        String endDateParam = request.getParameter("endDate");

        
        int page = 1; 
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            page = 1;
        }

        int pageSize = 10;

        if (startDateParam == null || endDateParam == null) {
            LocalDate today = LocalDate.now();
            startDateParam = today.withDayOfMonth(1).toString();
            endDateParam = today.toString();
        }

        Date startDate = Date.valueOf(startDateParam);
        Date endDate = Date.valueOf(endDateParam);

        List<RevenueReportRes> reportList = orderDao.getRevenueReport(startDate, endDate, page, pageSize);
        int totalRecords = orderDao.getTotalRevenueRecords(startDate, endDate);

        // Tính toán số trang
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        totalPages = Math.max(totalPages, 1); 

        if (page > totalPages) {
            page = totalPages;
        }

        double totalRevenueAmount = orderDao.getTotalRevenueAmount(startDate, endDate);

        request.setAttribute("reportList", reportList);
        request.setAttribute("currentPage", page);
        request.setAttribute("endP", totalPages); 
        request.setAttribute("totalRevenueAmount", totalRevenueAmount);
        request.setAttribute("totalOrders", totalRecords); 
        request.setAttribute("startDate", startDateParam); 
        request.setAttribute("endDate", endDateParam);
        request.getRequestDispatcher("admin/revenue.jsp").forward(request, response);
    }

}
