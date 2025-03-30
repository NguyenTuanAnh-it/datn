/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DTO.Request.OrderDetailReq;
import DTO.Request.OrderReq;
import Entity.Order;
import Entity.OrderStatusRequest;
import dao.OrderDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import dao.PaymentDao;
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
@WebServlet(name = "OrderManagerControl", urlPatterns = {"/OrderManagerControl"})
public class OrderManagerControl extends HttpServlet {

    private OrderDao orderDao = new OrderDao();
    private PaymentDao paymentDao = new PaymentDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            showOrder(request, response);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderManagerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            updateOrderStatus(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderManagerControl.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Lỗi hệ thống: Không tìm thấy lớp cần thiết.");
            request.getRequestDispatcher("/admin/order.jsp").forward(request, response);
        }

    }

    protected void showOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        int page = 1;
        int pageSize = 10;
        String searchQuery = request.getParameter("search");

        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }

        List<OrderDetailReq> orders = orderDao.getOrdersPaginatedSearch(page, pageSize, searchQuery);

        int totalOrders = orderDao.getTotalOrders();
        int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

        request.setAttribute("orders", orders);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("searchQuery", searchQuery);

        request.getRequestDispatcher("/admin/order.jsp").forward(request, response);

    }

    protected void updateOrderStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        String orderId = request.getParameter("orderId");
        String status = request.getParameter("status");

        if (orderId != null && status != null) {
            try {
                int id = Integer.parseInt(orderId);
                boolean updateSuccess = orderDao.updateOrderStatus(id, status);

                if (updateSuccess) {
                    if ("CANCELED".equals(status)) {
                        orderDao.restoreStockIfCanceled(id);
                    } else if ("DELIVERED".equals(status)) {
                        // Lấy thông tin đơn hàng
                        OrderReq order = orderDao.getOrderById(id);

                        if (order != null) {
                            double amount = order.getTotal_amout();
                            String paymentMethod = order.getPayment_method();
                            paymentDao.insertPayment(id, amount, paymentMethod);
                        }
                    }
                }

                request.setAttribute("message", updateSuccess ? "Cập nhật thành công" : "Cập nhật thất bại");
            } catch (Exception e) {
                request.setAttribute("error", "Lỗi khi cập nhật trạng thái");
            }
        } else {
            request.setAttribute("error", "Thiếu thông tin cập nhật");
        }
        showOrder(request, response);
        request.getRequestDispatcher("/admin/order.jsp").forward(request, response);
    }

}
