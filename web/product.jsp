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
    String categoryIdParam = request.getParameter("id");
    String categoryId = (categoryIdParam != null && categoryIdParam.matches("\\d+")) ? categoryIdParam : "";

    int pageSize = 8;
    int pages = 1;
    String pageParam = request.getParameter("page");
    if (pageParam != null && pageParam.matches("\\d+")) {
        pages = Integer.parseInt(pageParam);
        if (pages < 1) {
            pages = 1;
        }
    }

    ProductDao productDao = new ProductDao();
    int totalProducts = productDao.getTotalProductCount(categoryId);
    int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

    if (pages > totalPages && totalPages > 0) {
        pages = totalPages;
    }

    List<Product> bestSellingProducts = productDao.getBestSellingProducts(4);
    request.setAttribute("bestSellingProducts", bestSellingProducts);

    List<Product> productList = productDao.getListByCategory(categoryId, pages, pageSize);
    request.setAttribute("productList", productList);
    request.setAttribute("currentPage", pages);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("categoryId", categoryId);

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
            .best-selling-section {
                padding: 40px 0;
                background-color: #f8f9fa;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }

            .best-selling-title {
                font-size: 28px;
                font-weight: bold;
                text-transform: uppercase;
                color: #333;
                margin-bottom: 30px;
                text-align: center;
                letter-spacing: 1px;
            }

            .best-selling-row {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 20px;
            }

            .best-selling-product {
                width: 260px;
                background: #fff;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                overflow: hidden;
                transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
            }

            .best-selling-product:hover {
                transform: translateY(-5px);
                box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.2);
            }

            .product-image {
                width: 100%;
                height: auto;
                max-height: 220px; /* Đảm bảo ảnh không bị cắt quá nhiều */
                display: block;
                object-fit: contain; /* Hiển thị đầy đủ ảnh, không bị cắt */
                background-color: #f9f9f9; /* Tạo nền sáng để ảnh trông rõ ràng hơn */
                padding: 10px;
                border-bottom: 1px solid #ddd;
            }


            .product-info {
                padding: 15px;
                text-align: center;
            }

            .product-price {
                font-size: 20px;
                font-weight: bold;
                color: #e44d26;
                margin-bottom: 10px;
            }

            .product-name {
                font-size: 16px;
                font-weight: 600;
                color: #333;
                margin-bottom: 8px;
            }

            .product-status {
                font-size: 14px;
                font-weight: bold;
                margin-bottom: 10px;
            }

            .in-stock {
                color: green;
            }

            .out-of-stock {
                color: red;
            }

            .add-to-cart-btn {
                display: inline-block;
                background-color: #ff6f61;
                color: white;
                padding: 8px 15px;
                font-size: 14px;
                border-radius: 5px;
                text-decoration: none;
                transition: background 0.3s;
            }

            .add-to-cart-btn:hover {
                background-color: #e44d26;
            }

            .top-rank {
                position: absolute;
                top: 10px;
                left: 10px;
                background: linear-gradient(45deg, #ff9a9e, #fad0c4); /* Hiệu ứng màu nổi bật */
                color: white;
                font-size: 16px;
                font-weight: bold;
                padding: 5px 12px;
                border-radius: 8px;
                box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
            }

            .product-image-wrapper {
                position: relative;
                overflow: hidden;
                transition: transform 0.3s ease-in-out;
            }

            .product-image-wrapper:hover {
                transform: scale(1.05); /* Hiệu ứng phóng to nhẹ khi hover */
            }
            .top-rank:nth-child(1) { background: linear-gradient(45deg, #ff512f, #dd2476); } /* Đỏ - Top 1 */
            .top-rank:nth-child(2) { background: linear-gradient(45deg, #ff9a9e, #fad0c4); } /* Hồng - Top 2 */
            .top-rank:nth-child(3) { background: linear-gradient(45deg, #a18cd1, #fbc2eb); } /* Tím - Top 3 */
            .top-rank:nth-child(4) { background: linear-gradient(45deg, #84fab0, #8fd3f4); } /* Xanh - Top 4 */

            .single-products{
                height: 484px;
            }
            .single-products .productinfo img {
                width: 100%; /* Để phù hợp với kích thước của phần tử cha */
                max-width: 400px; /* Giới hạn chiều rộng */
                height: 300px; /* Đặt chiều cao cố định */
                object-fit: cover; /* Cắt ảnh để vừa khung mà không méo */
                display: block;
                margin: 0 auto; /* Căn giữa */
            }

            .single-products .productinfo {
                width: 100%;
                max-width: 400px; /* Đồng bộ chiều rộng */
                height: auto; /* Tự động điều chỉnh */
                text-align: center;
                padding: 10px;
                height: 484px;
                display: grid;
                grid-template-rows: auto auto auto auto 1fr;
            }
            .single-products .productinfo img {
                width: auto; /* Để tránh bị kéo dài quá mức */
                max-width: 400px;
                height: 300px;
            }

            .product-image {
                width: 100%;
                max-width: 400px;
                height: 300px;
                object-fit: cover;
                display: block;
                margin: 0 auto;
            }


            .product-container {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
                gap: 20px;
            }

            .product-image-wrapper {
                position: relative;
                overflow: hidden;
                background: #fff;
                border: 1px solid #ddd;
                padding: 15px;
                border-radius: 5px;
                text-align: center;
                height: 100%;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }

            /* Ảnh sản phẩm chính */
            .product-image {
                width: 100%;
                height: 200px;
                object-fit: cover;
                transition: 0.3s ease-in-out;
            }

            /* Overlay khi hover */
            .pagination li a, .pagination li span {
                color: #fff;
                font-weight: 700;
            }

            /* Cố định nút "Thêm vào giỏ hàng" */
            .add-to-cart {
                display: block;
                background: #ff9800;
                color: white;
                padding: 8px 12px;
                margin-top: 10px;
                border-radius: 5px;
                font-weight: bold;
                text-decoration: none;
                transition: 0.3s;
                align-self: end;
            }

            .add-to-cart:hover {
                background: #e68900;
            }

            /* Cố định phần trạng thái hàng */
            .product-status {
                font-weight: bold;
            }

            .in-stock {
                color: green;
            }

            .out-of-stock {
                color: red;
            }

            /* Clearfix để giữ bố cục đẹp */
            .clearfix {
                clear: both;
            }

        </style>

    </head>
    <body>
        <div class="col-sm-9 padding-right">
            <div class="features_items">
                <h2 class="title text-center">Sản Phẩm</h2>

                <c:if test="${empty productList}">
                    <p class="text-center">Không có sản phẩm nào trong danh mục này.</p>
                </c:if>

                <c:forEach var="product" items="${productList}" varStatus="status">
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
                <h2 class="title text-center">Sản phẩm bán chạy</h2>
                <div class="row">
                    <c:forEach var="product" items="${bestSellingProducts}" varStatus="loop">
                        <div class="col-sm-3">
                            <div class="product-image-wrapper">
                                <div class="single-products">
                                    <div class="productinfo text-center">
                                        <!-- Hiển thị Top Rank -->
                                        <div class="top-rank">Top ${loop.index + 1}</div>

                                        <img class="product-image" src="images/home/${product.image}" alt="${product.name}" />
                                        <h2><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/> VNĐ</h2>
                                        <p>${product.name}</p>

                                        <!-- Hiển thị trạng thái hàng -->
                                        <p class="product-status ${product.quantity > 0 ? 'in-stock' : 'out-of-stock'}">
                                            ${product.quantity > 0 ? 'Còn hàng' : 'Hết hàng'}
                                        </p>

                                        <a href="CartControl?command=insert&id=${product.id}&cartID=<%=System.currentTimeMillis()%>" class="btn btn-default add-to-cart">
                                            <i class="fa fa-shopping-cart"></i> Thêm vào giỏ hàng
                                        </a>
                                    </div>
                                </div>
                                <div class="choose">
                                    <ul class="nav nav-pills nav-justified">
                                        <li><a href="detail?id=${product.id}&categoryId=${product.categoryId}">
                                                <i class="fa fa-plus-square"></i>Xem chi tiết</a></li>
                                    </ul>
                                </div>
                            </div>

                        </div>
                    </c:forEach>
                </div>



            </div>

        </div>
    </body>
</html>