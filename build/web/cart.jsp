<%@page import="java.util.Map"%>
<%@page import="Entity.Product"%>
<%@page import="java.util.TreeMap"%>
<%@page import="Entity.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Giỏ hàng</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
        <style>
            .total-section {
                padding: 10px;
                border-radius: 5px;
                display: flex;
                justify-content: space-between; /* Căn hai phần về hai bên */
                align-items: center; /* Căn giữa theo chiều dọc */
                margin-bottom: 20px;
            }

            .total-price {
                font-size: 24px;
                font-weight: bold;
                color: #FE980F;
                margin: 0;
            }

            .checkout-button {
                padding: 12px 25px;
                font-size: 18px;
                font-weight: bold;
                background-color: #FE980F;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .checkout-button:hover {
                background-color: #e08e0b;
            }   
            .cart_info table tr td {
                border-top: 0 none;
                vertical-align: inherit;
                /* width: 48%; */
            }
            
            .cart_product {
                display: block;
                margin: 15px 5px 10px 25px !important;
            }
            @media (min-width: 1200px) {
                .container {
                    width: 1350px !important;
                }
                .cart_quantity{
                    width: 156px;
                }
            }

        </style>
    </head>
    <%
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        TreeMap<Product, Integer> list = cart.getList();
    %>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <section>
                <div class="container">
                    <div class="row">
                        <section id="cart_items">
                            <div class="table-responsive cart_info">
                                <table class="table table-condensed">
                                    <thead>
                                        <tr class="cart_menu">
                                            <td class="image">Sản phẩm</td>
                                            <td class="description"></td>
                                            <td class="price">Giá</td>
                                            <td class="quantity">Số lượng</td>
                                            <td class="total">Tổng tiền</td>
                                            <td>Hành động</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        for (Map.Entry<Product, Integer> ds : list.entrySet()) {
                                    %>
                                    <tr>
                                        <td class="cart_product">
                                            <a href=""><img src="images/home/<%= ds.getKey().getImage()%>" alt="" width="150" height="150"></a>
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

                                                <input class="cart_quantity_input" type="text" name="quantity" 
                                                       value="<%= ds.getValue()%>" autocomplete="off" size="2" readonly>

                                                <a class="cart_quantity_down" href="CartControl?command=sub&id=<%= ds.getKey().getId()%>&cartID=<%=System.currentTimeMillis()%>"> - </a>
                                            </div>
                                            <%
                                                if (ds.getValue() > ds.getKey().getQuantity()) {
                                            %>
                                            <p style="color: red; font-weight: bold;">⚠ Chỉ còn <%= ds.getKey().getQuantity()%> sản phẩm!</p>
                                            <%
                                                }
                                            %>
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
                    </section>
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
                        <form action="CheckoutControl" method="POST">
                            <a class="checkout-button" href="checkout.jsp">Xác nhận thanh toán</a>
                        </form>
                    </div>
                </div>
            </div>
        </section>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>