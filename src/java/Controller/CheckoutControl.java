package Controller;

import Entity.Cart;
import Entity.Order;
import Entity.OrderDetail;
import Entity.Product;
import Entity.User;
import dao.AccountDao;
import dao.OrderDao;
import dao.OrderDetailDao;
import dao.ProductDao;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tools.EmailSender;

public class CheckoutControl extends HttpServlet {

    private OrderDao orderDao = new OrderDao();
    private OrderDetailDao orderDetailDao = new OrderDetailDao();
    private AccountDao accDao = new AccountDao();
    ProductDao productDao = new ProductDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String receiver_name = request.getParameter("receiver_name");
        String receiver_phone = request.getParameter("receiver_phone");
        String address = request.getParameter("address");
        String payment = request.getParameter("payment");

        HttpSession session = request.getSession();
        String username = (session.getAttribute("username") != null) ? (String) session.getAttribute("username") : "guest";
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getList().isEmpty()) {
            response.sendRedirect("/QL_BanMyPham/cart.jsp?error=CartEmpty");
            return;
        }

        User user = null;
        String userEmail = "";

        try {
            user = accDao.getTaiKhoan(username);
            if (user != null) {
                userEmail = user.getEmail();
            } else {
                Logger.getLogger(CheckoutControl.class.getName()).log(Level.WARNING, "Không tìm thấy tài khoản: " + username);
            }
        } catch (Exception e) {
            Logger.getLogger(CheckoutControl.class.getName()).log(Level.SEVERE, "Lỗi khi lấy tài khoản", e);
        }

        // Tạo đơn hàng
        Order order = new Order(0, user, new Timestamp(new Date().getTime()), receiver_name, "shipping", receiver_phone, address, payment);
        int orderId = -1;

        try {
            orderId = orderDao.insertOrder(order);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CheckoutControl.class.getName()).log(Level.SEVERE, "Lỗi khi thêm đơn hàng", ex);
        }

        if (orderId > 0) {
            order.setId(orderId);
            StringBuilder orderDetails = new StringBuilder();

            for (Map.Entry<Product, Integer> ds : cart.getList().entrySet()) {
                Product product = ds.getKey();
                int quantity = ds.getValue();
                try {
                    productDao.updateProductQuantity(product.getId(), quantity);
                    orderDetailDao.insertOrderDetail(new OrderDetail(0, order, product, quantity, product.getPrice()));
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CheckoutControl.class.getName()).log(Level.SEVERE, "Lỗi khi thêm chi tiết đơn hàng", ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CheckoutControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                orderDetails.append(product.getName()).append(" - Số lượng: ").append(quantity)
                        .append(" - Giá: ").append(product.getPrice()).append("\n");
            }

            // Tạo bảng HTML cho chi tiết đơn hàng
            StringBuilder orderDetailsHtml = new StringBuilder();
            orderDetailsHtml.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse; width: 100%; font-size: 14px; color: #333;'>");
            orderDetailsHtml.append("<tr style='background-color: #f2f2f2;'>");
            orderDetailsHtml.append("<th style='padding: 8px;'>Sản phẩm</th>");
            orderDetailsHtml.append("<th style='padding: 8px;'>Số lượng</th>");
            orderDetailsHtml.append("<th style='padding: 8px;'>Đơn giá</th>");
            orderDetailsHtml.append("<th style='padding: 8px;'>Thành tiền</th>");
            orderDetailsHtml.append("</tr>");

            double totalPrice = 0;

            for (Map.Entry<Product, Integer> ds : cart.getList().entrySet()) {
                Product product = ds.getKey();
                int quantity = ds.getValue();
                double itemTotal = quantity * product.getPrice();
                totalPrice += itemTotal; 

                orderDetailsHtml.append("<tr>");
                orderDetailsHtml.append("<td style='padding: 8px;'>" + product.getName() + "</td>");
                orderDetailsHtml.append("<td style='padding: 8px; text-align: center;'>" + quantity + "</td>");
                orderDetailsHtml.append("<td style='padding: 8px; text-align: right;'>" + product.getPrice() + " đ</td>");
                orderDetailsHtml.append("<td style='padding: 8px; text-align: right;'>" + itemTotal + " đ</td>");
                orderDetailsHtml.append("</tr>");
            }

            // Thêm hàng tổng tiền
            orderDetailsHtml.append("<tr style='font-weight: bold;'>");
            orderDetailsHtml.append("<td colspan='3' style='padding: 8px; text-align: right;'>Tổng tiền:</td>");
            orderDetailsHtml.append("<td style='padding: 8px; text-align: right; color: red;'>" + totalPrice + " đ</td>");
            orderDetailsHtml.append("</tr>");

            orderDetailsHtml.append("</table>");

            // Gửi email xác nhận đơn hàng
            if (username != null && !username.isEmpty()) {
                String subject = "Xác nhận đơn hàng từ MixiShop";

                String message = "<html><body>"
                        + "<h2>Xin chào " + receiver_name + ",</h2>"
                        + "<p>Cảm ơn bạn đã tin tưởng và mua sắm tại <strong>MixiShop</strong>. Chúng tôi rất vui thông báo rằng đơn hàng của bạn đã được xác nhận.</p>"
                        + "<h3>Thông tin đơn hàng:</h3>"
                        + "<ul>"
                        + "<li><strong>Tên người nhận:</strong> " + receiver_name + "</li>"
                        + "<li><strong>Số điện thoại:</strong> " + receiver_phone + "</li>"
                        + "<li><strong>Địa chỉ giao hàng:</strong> " + address + "</li>"
                        + "<li><strong>Phương thức thanh toán:</strong> " + payment + "</li>"
                        + "</ul>"
                        + "<h3>Chi tiết đơn hàng:</h3>"
                        + orderDetailsHtml.toString()
                        + "<p><strong>Tổng tiền thanh toán: " + totalPrice + " đ</strong></p>"
                        + "<p>Chúng tôi sẽ sớm xử lý và giao hàng đến bạn trong thời gian sớm nhất.</p>"
                        + "<p>Trân trọng,<br/><strong>MixiShop</strong></p>"
                        + "</body></html>";

                EmailSender.sendEmail(username, subject, message);
            } else {
                Logger.getLogger(CheckoutControl.class.getName()).log(Level.WARNING, "Không thể gửi email: Email của người dùng rỗng hoặc null.");
            }

            session.removeAttribute("cart");
            session.setAttribute("orderSuccess", "true"); 
            response.sendRedirect("/QL_BanMyPham/index.jsp");
        } else {
            response.sendRedirect("/QL_BanMyPham/checkout.jsp");
        }
    }
}
