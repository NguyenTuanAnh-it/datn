/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DTO.Request.OrderDetailReq;
import Entity.Order;
import com.google.gson.JsonObject;
import dao.OrderDao;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "DashboardControl", urlPatterns = {"/dashboard"})
public class DashboardControl extends HttpServlet {

    private OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int newOrdersCount = orderDao.getNewOrdersCount(); // Lấy số đơn hàng mới
            request.setAttribute("newOrdersCount", newOrdersCount); // Gửi sang JSP
            showOrder(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashboardControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void loadOrderCount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int newOrdersCount = orderDao.getNewOrdersCount();
        JsonObject json = new JsonObject();
        json.addProperty("count", newOrdersCount);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }

    protected void showOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        int page = 1;
        int pageSize = 10;

        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }

        List<OrderDetailReq> orders = orderDao.getOrdersPaginated(page, pageSize);

        int totalOrders = orderDao.getTotalOrders();
        int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

        request.setAttribute("orders", orders);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalOrders", totalOrders);

        request.getRequestDispatcher("/admin/index.jsp").forward(request, response);

    }
}
