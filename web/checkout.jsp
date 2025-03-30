<%-- 
    Document   : checkout
    Created on : Mar 3, 2025, 10:22:54 PM
    Author     : ADMIN
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page import="Entity.Product"%>
<%@page import="Entity.Cart"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CheckOut</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
    </head>
    <body>
        <%
            if (session.getAttribute("username") == null) {
                response.sendRedirect("/QL_BanMyPham/account.jsp");
            } else {
                Cart cart = (Cart) session.getAttribute("cart");
                if (cart == null) {
                    cart = new Cart();
                    session.setAttribute("cart", cart);
                }
                TreeMap<Product, Integer> list = cart.getList();
        %>

        <jsp:include page="header.jsp"></jsp:include>
            <section id="cart_items">
                <div class="container">
                    <div class="breadcrumbs">
                        <ol class="breadcrumb">
                            <li><a href="#">Home</a></li>
                            <li class="active">Check out</li>
                        </ol>
                    </div><!--/breadcrums-->
                    <div class="shopper-informations">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="shopper-info">
                                    <p>Thông tin thanh toán</p>
                                    <form action="CheckoutControl" method="post">
                                        <p>Tên người nhận</p>
                                        <input type="text" name="receiver_name" placeholder="Nhập tên người nhận" required>

                                        <p>Số điện thoại</p>
                                        <input type="text" name="receiver_phone" placeholder="Nhập số điện thoại" required>

                                        <p>Địa chỉ giao hàng</p>
                                        <textarea name="address" placeholder="Nhập địa chỉ giao hàng" rows="5" required></textarea>

                                        <p>Phương thức thanh toán</p>
                                        <select name="payment">
                                            <option value="COD">Thanh toán khi nhận hàng</option>
                                            <option value="VNPay">Thanh toán qua ví điện tử VNPay</option>
                                        </select>
                                        <input type="hidden" value="<%=session.getAttribute("username")%>"/>
                                    <input type="submit" value="Xác nhận thanh toán" class="btn btn-primary"/>
                                </form>
                            </div>
                        </div>				
                    </div>
                </div>
                <div class="review-payment">
                    <h2>Thông tin đơn hàng</h2>
                </div>
                <div class="table-responsive cart_info">
                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td class="image">Item</td>
                                <td class="description"></td>
                                <td class="price">Price</td>
                                <td class="quantity">Quantity</td>
                                <td class="total">Total</td>
                                <td></td>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (Map.Entry<Product, Integer> ds : list.entrySet()) {
                            %>
                            <tr>
                                <td class="cart_product">
                                    <a href=""><img src="images/home/<%= ds.getKey().getImage() %>" alt="" width="150" height="150"></a>
                                </td>
                                <td class="cart_description">
                                    <h4><a href=""> <%= ds.getKey().getName()%> </a></h4>
                                </td>
                                <td class="cart_price">
                                    <p><fmt:formatNumber value="<%= ds.getKey().getPrice()%>" type="currency" currencySymbol="VNĐ"/></p>

                                </td>
                                <td class="cart_quantity">
                                    <div class="cart_quantity_button">
                                        <a class="cart_quantity_up" href="CartControl?command=plus&id=<%= ds.getKey().getId()%>&cartID=<%=System.currentTimeMillis()%>"> + </a>
                                        <input class="cart_quantity_input" type="text" name="quantity" value="<%= ds.getValue()%>" autocomplete="off" size="2">
                                        <a class="cart_quantity_down" href="CartControl?command=sub&id=<%= ds.getKey().getId()%>&cartID=<%=System.currentTimeMillis()%>"> - </a>
                                    </div>
                                </td>
                                <td class="cart_total">
                                    <p class="cart_total_price"><fmt:formatNumber value="<%= ds.getValue() * ds.getKey().getPrice()%>" type="currency" currencySymbol="VNĐ"/></p>
                                </td>
                                <td class="cart_delete">
                                    <a class="cart_quantity_delete" href="CartControl?command=remove&id=<%= ds.getKey().getId()%>&cartID=<%=System.currentTimeMillis()%>"><i class="fa fa-times"></i></a>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>

            </div>
            <%
                double totalPrice = 0;
                for (Map.Entry<Product, Integer> ds : list.entrySet()) {
                    totalPrice += ds.getKey().getPrice() * ds.getValue();
                }
            %>

            <div class="total-section">
                <p class="total-price">Tổng tiền: 
                    <fmt:formatNumber value="<%= totalPrice%>" type="currency" currencySymbol="VNĐ"/>
                </p>
            </div>
        </section> <!--/#cart_items-->
        <jsp:include page="footer.jsp"></jsp:include>

        <%

            }
        %>
    </body>
</html>
