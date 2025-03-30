<%-- 
    Document   : product
    Created on : Feb 17, 2025, 11:08:36 AM
    Author     : ADMIN
--%>

<%@page import="Entity.Cart"%>
<%@page import="Entity.Product"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProductDao"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) {
        cart = new Cart();
        session.setAttribute("cart", cart);
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sản Phẩm</title>
        <style>
            /* Hiệu ứng nhấp nháy */
            @keyframes blink {
                0% { opacity: 1; }
                50% { opacity: 0.5; }
                100% { opacity: 1; }
            }

            /* Trạng thái sản phẩm */
            .product-status {
                font-weight: bold !important;
                font-size: 14px;
                margin-top: 5px;
            }

            /* "Còn hàng" - màu xanh, nhấp nháy */
            .in-stock {
                color: green !important;
                animation: blink 1s infinite;
            }

            /* "Hết hàng" - màu đỏ, nhấp nháy */
            .out-of-stock {
                color: red !important;
                animation: blink 1s infinite;
            }
            .product-overlay {
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.7); /* Làm mờ nền */
            }

            .overlay-content img {
                max-width: 80%;  /* Giữ ảnh trong phạm vi overlay */
                max-height: 80%; /* Không để ảnh vượt quá kích thước */
                object-fit: contain; /* Đảm bảo ảnh không bị méo */
                border-radius: 10px; /* Làm mềm góc ảnh */
            }


        </style>

    </head>
    <body>
        <div class="col-sm-9 padding-right">
            <div class="features_items">
                <h2 class="title text-center">Sản Phẩm</h2>

                <c:if test="${empty productSearch}">
                    <p class="text-center">Không có sản phẩm nào trong danh mục này.</p>
                </c:if>

                <c:forEach var="product" items="${productSearch}" varStatus="status">
                    <div class="col-sm-3 col-xs-6">
                        <div class="product-image-wrapper">
                            <div class="single-products">
                                <div class="productinfo text-center">
                                    <img src="images/home/${product.image}" 
                                         alt="${product.name}" 
                                         class="product-image"/>
                                    <h2><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/> VNĐ</h2>
                                    <p>${product.name}</p>

                                    <!-- Hiển thị trạng thái hàng -->
                                    <p class="product-status ${product.quantity > 0 ? 'in-stock' : 'out-of-stock'}">
                                        ${product.quantity > 0 ? 'Còn hàng' : 'Hết hàng'}
                                    </p>

                                    <a href="#" class="btn btn-default add-to-cart">
                                        <i class="fa fa-shopping-cart"></i> Thêm vào giỏ hàng
                                    </a>
                                </div>

                                <!-- Overlay khi hover -->
                                <div class="product-overlay">
                                    <div class="overlay-content">
                                        <img src="images/home/${product.image}" alt="${product.name}" class="overlay-image"/>
                                        <h2><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/> VNĐ</h2>
                                        <p>${product.name}</p>

                                        <p class="product-status ${product.quantity > 0 ? 'in-stock' : 'out-of-stock'}">
                                            ${product.quantity > 0 ? 'Còn hàng' : 'Hết hàng'}
                                        </p>

                                        <a href="CartControl?command=insert&id=${product.id}&cartID=<%=System.currentTimeMillis()%>" 
                                           class="btn btn-default add-to-cart">
                                            <i class="fa fa-shopping-cart"></i> Thêm vào giỏ hàng
                                        </a>
                                    </div>
                                </div>
                            </div>

                            <!-- Phần "Xem chi tiết" -->
                            <div class="choose">
                                <ul class="nav nav-pills nav-justified">
                                    <li><a href="detail?id=${product.id}&categoryId=${product.categoryId}">
                                            <i class="fa fa-plus-square"></i> Xem chi tiết</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Clearfix để giữ bố cục đẹp -->
                    <c:if test="${(status.index + 1) % 4 == 0}">
                        <div class="clearfix"></div>
                    </c:if>
                </c:forEach>

                <div class="clearfix"></div> 

                <c:if test="${totalPages > 0}">
                    <div class="text-center">
                        <ul class="pagination">
                            <c:if test="${currentPage > 1}">
                                <li><a href="?id=${categoryId}&page=${currentPage - 1}">&laquo;</a></li>
                                </c:if>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <c:choose>
                                    <c:when test="${currentPage == i}">
                                        <li class="active"><a href="#">${i}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                        <li><a href="?id=${categoryId}&page=${i}">${i}</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>

                            <c:if test="${currentPage < totalPages}">
                                <li><a href="?id=${categoryId}&page=${currentPage + 1}">&raquo;</a></li>
                                </c:if>
                        </ul>
                    </div>
                </c:if>
            </div>
        </div>
    </body>
</html>