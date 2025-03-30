package Controller;

import Entity.Cart;
import Entity.Product;
import dao.ProductDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CartControl extends HttpServlet {

    private ProductDao productDao = new ProductDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String msp = request.getParameter("id");
        String command = request.getParameter("command");
        List<Long> listBuy = (List<Long>) session.getAttribute("cartID");

        if (listBuy == null) {
            listBuy = new ArrayList<>();
            session.setAttribute("cartID", listBuy);
        }

        String url = "/cart.jsp";
        try {
            if (msp != null && request.getParameter("cartID") != null) {
                long idBuy = Long.parseLong(request.getParameter("cartID"));
                Product product = productDao.getProductById(Integer.parseInt(msp));
                int currentQuantity = cart.getQuantity(product); // Số lượng trong giỏ hàng
                int stockQuantity = product.getQuantity();
                switch (command) {
                    case "insert":
                        if (!listBuy.contains(idBuy)) {
                            if (currentQuantity < stockQuantity) {
                                cart.addToCart(product, 1);
                                listBuy.add(idBuy);
                            } else {
                                session.setAttribute("error", "Không đủ hàng trong kho!");
                            }
                        }
                        break;

                    case "plus":
                        if (currentQuantity < stockQuantity) {
                            cart.addToCart(product, 1);
                        } else {
                            session.setAttribute("error", "Không thể thêm sản phẩm, số lượng tồn kho không đủ!");
                        }
                        break;

                    case "sub":
                        if (currentQuantity > 1) {
                            cart.subToCart(product, 1);
                        } else {
                            cart.removeToCart(product); // Nếu số lượng về 0, xóa khỏi giỏ hàng
                        }
                        break;

                    case "remove":
                        cart.removeToCart(product);
                        break;

                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Thêm để debug lỗi
        }

        // Chuyển trang đúng cách
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
}
