<%@page import="Entity.Product"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProductDao"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    // Lấy tham số từ request và kiểm tra hợp lệ
    String productIdStr = request.getParameter("id");
    String categoryIdStr = request.getParameter("categoryId");

    if (productIdStr == null || categoryIdStr == null) {
        out.println("<h3 class='text-danger text-center'>Thông tin sản phẩm không hợp lệ!</h3>");
        return;
    }

    try {
        int productId = Integer.parseInt(productIdStr);
        int categoryId = Integer.parseInt(categoryIdStr);

        // Khởi tạo ProductDao
        ProductDao productDao = new ProductDao();

        // Lấy danh sách sản phẩm liên quan
        List<Product> relatedProducts = productDao.getRelatedProducts(categoryId, productId);
        request.setAttribute("relatedProducts", relatedProducts);

        // Lấy thông tin sản phẩm
        Product product = productDao.getProductById(productId);
        if (product != null) {
            request.setAttribute("product", product);
        } else {
            out.println("<h3 class='text-danger text-center'>Sản phẩm không tồn tại!</h3>");
            return;
        }
    } catch (NumberFormatException e) {
        out.println("<h3 class='text-danger text-center'>Dữ liệu không hợp lệ!</h3>");
    }
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi Tiết Sản Phẩm</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css">

        <!-- JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
        <script src="js/bootstrap.min.js"></script>

        <style>
            .product-details { margin-bottom: 30px; }
            .product-image { width: 100%; max-height: 400px; object-fit: cover; }
            .product-info { padding-left: 20px; }
            .tab-content { padding: 20px; border: 1px solid #ddd; background: #fff; min-height: 200px; }
            .review-item { 
                border-bottom: 1px solid #ddd; 
                padding: 10px 0;    
            }
            .review-form { margin-top: 20px; }
            @keyframes blink {
                0% { opacity: 1; }
                50% { opacity: 0; }
                100% { opacity: 1; }
            }

            .blinking-text {
                font-weight: bold;
                animation: blink 1s infinite;
            }

            .in-stock {
                color: green;
            }

            .out-of-stock {
                color: red;
            }
            .rating {
                direction: rtl;
                display: inline-flex;
            }

            .rating input {
                display: none;
            }

            .rating label {
                font-size: 25px;
                color: gray;
                cursor: pointer;
            }

            .rating input:checked ~ label {
                color: gold;
            }
            .pagination-container {
                text-align: center;
                margin-top: 20px;
                margin-bottom: 20px;
            }

            .pagination > li > a {
                color: #FE980F;
                border: 1px solid #F0F0E9;
                padding: 5px 10px;
            }

            .pagination > li.active > a {
                background-color: #FE980F;
                border-color: #FE980F;
                color: white;
            }

            .pagination > li:hover > a {
                background-color: #FE980F;
                color: white;
            }
            .related-products {
                margin-top: 30px;
                margin-bottom: 30px;
                text-align: center;
            }

            .related-products h3 {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
                text-transform: uppercase;
                color: #333;
            }

            .owl-carousel .item {
                background: #fff;
                border-radius: 10px;
                padding: 10px;
                box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease-in-out;
                margin-bottom: 30px;
                height: 415px; 
               
            }

            .owl-carousel .item:hover {
                transform: scale(1.05);
            }

            .owl-carousel .item img {
                width: 100%; /* Chiếm toàn bộ chiều rộng của div */
                height: 250px; /* Đặt chiều cao cố định */
                object-fit: cover; /* Cắt ảnh vừa khung mà không méo hình */
                border-radius: 10px; /* Bo góc ảnh (tùy chọn) */
            }
            .item a{
                height: 415px; 
                display: grid;
                grid-template-rows: auto auto 1fr;
            }

            .owl-carousel .item p {
                margin-top: 10px;
                font-size: 16px;
                color: #555;
            }
            .item .price{
                align-self: end;
                margin-bottom: 15px;
            }

            .owl-carousel .item p b {
                color: #E91E63;
            }

            .owl-nav {
                position: absolute;
                top: 50%;
                width: 100%;
                transform: translateY(-50%);
            }

            .owl-prev, .owl-next {
                position: absolute;
                top: 50%;
                background: rgba(0, 0, 0, 0.6);
                color: #fff;
                padding: 10px;
                border-radius: 50%;
                font-size: 18px;
                transition: background 0.3s;
            }

            .owl-prev {
                left: -30px;
            }

            .owl-next {
                right: -30px;
            }

            .owl-prev:hover, .owl-next:hover {
                background: rgba(0, 0, 0, 0.8);
            }

        </style>

    </style>
</head>


<body>
    <jsp:include page="header.jsp"></jsp:include>

        <section>
            <div class="container">
                <div class="row">
                <jsp:include page="category.jsp"></jsp:include>

                    <!-- Chi tiết sản phẩm -->
                    <div class="col-sm-9">
                        <div class="product-details row">
                            <!-- Ảnh sản phẩm -->
                            <div class="col-sm-5">
                                <img src="images/home/${product.image}" alt="${product.name}" class="product-image">
                        </div>

                        <!-- Thông tin sản phẩm -->
                        <div class="col-sm-7 product-info">
                            <h2>${product.name}</h2>
                            <p><b>Mã sản phẩm:</b> ${product.id}</p>
                            <p><b>Mô tả:</b> ${product.description}</p>
                            <p><b>Tình trạng:</b> 
                                <c:choose>
                                    <c:when test="${product.quantity > 0}">
                                        <span class="blinking-text in-stock">Còn hàng</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="blinking-text out-of-stock">Hết hàng</span>
                                    </c:otherwise>
                                </c:choose>
                            </p>

                            <p><b>Giá:</b> <span><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/> VNĐ</span></p>
                            <br>
                            <c:choose>
                                <c:when test="${product.quantity > 0}">
                                    <a href="CartControl?command=insert&id=${product.id}&cartID=<%=System.currentTimeMillis()%>" class="btn btn-primary" style="margin-top: 10px;">
                                        <i class="fa fa-shopping-cart"></i> Thêm vào giỏ hàng
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-secondary" style="margin-top: 10px;" disabled>
                                        <i class="fa fa-ban"></i> Hết hàng
                                    </button>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>

                    <!-- Tabs chi tiết và đánh giá -->
                    <div class="category-tab shop-details-tab">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#details" data-toggle="tab">Chi tiết</a></li>
                            <li><a href="#reviews" data-toggle="tab">Đánh giá (${totalReviews})</a></li>
                        </ul>

                        <div class="tab-content">
                            <!-- Tab Chi tiết -->
                            <div class="tab-pane fade in active" id="details">
                                <h4>Thông tin sản phẩm</h4>
                                <p style="white-space: pre-line; margin-bottom: 10px;">${product.detail}</p>
                            </div>

                            <!-- Tab Đánh giá -->
                            <div class="tab-pane fade" id="reviews">
                                <h4>Đánh giá sản phẩm</h4>

                                <!-- Thông báo lỗi nếu có -->
                                <c:if test="${param.error == 'not_purchased'}">
                                    <p class="text-danger">Bạn cần mua sản phẩm trước khi đánh giá!</p>
                                </c:if>

                                <!-- Hiển thị đánh giá từ cơ sở dữ liệu -->
                                <div id="review-list">
                                    <c:if test="${empty reviews}">
                                        <p>Chưa có đánh giá nào cho sản phẩm này.</p>
                                    </c:if>

                                    <c:forEach var="review" items="${reviews}">
                                        <div class="review-item" id="review-${review.id}">
                                            <p><b>Người dùng:</b> ${review.username}</p>
                                            <p>
                                                <i class="fa fa-calendar-o"></i> ${review.review_date.toString()} 
                                            </p>
                                            <p>${review.comment}</p>
                                            <p>
                                                <!-- Hiển thị ngôi sao thay vì số -->
                                                Điểm đánh giá: 
                                                <c:forEach var="i" begin="1" end="${review.rating}">
                                                    <i class="fa fa-star text-warning"></i>
                                                </c:forEach>
                                                <c:forEach var="i" begin="${review.rating + 1}" end="5">
                                                    <i class="fa fa-star-o text-muted"></i>
                                                </c:forEach>
                                            </p>
                                            <hr>
                                        </div>
                                    </c:forEach>

                                    <!-- Hiển thị phân trang -->
                                    <c:if test="${totalPages > 1}">
                                        <div class="pagination-container">
                                            <ul class="pagination">
                                                <!-- Nút Previous -->
                                                <c:if test="${currentPage > 1}">
                                                    <li><a href="detail?id=${product.id}&categoryId=${product.categoryId}&page=${currentPage - 1}#reviews">&laquo;</a></li>
                                                    </c:if>

                                                <!-- Các trang -->
                                                <c:forEach var="i" begin="1" end="${totalPages}">
                                                    <c:choose>
                                                        <c:when test="${i == currentPage}">
                                                            <li class="active"><a href="#">${i}</a></li>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <li><a href="detail?id=${product.id}&categoryId=${product.categoryId}&page=${i}#reviews">${i}</a></li>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>

                                                <!-- Nút Next -->
                                                <c:if test="${currentPage < totalPages}">
                                                    <li><a href="detail?id=${product.id}&categoryId=${product.categoryId}&page=${currentPage + 1}#reviews">&raquo;</a></li>
                                                    </c:if>

                                            </ul>
                                        </div>
                                    </c:if>
                                </div>

                                <!-- Form đánh giá -->
                                <c:if test="${hasPurchased}">
                                    <div class="review-form">
                                        <h4>Viết đánh giá của bạn</h4>
                                        <form action="review" method="post">
                                            <input type="hidden" name="productId" value="${product.id}">
                                            <input type="hidden" name="userId" value="${sessionScope.userId}">
                                            <input type="hidden" name="categoryId" value="${product.categoryId}"> 

                                            <div class="form-group">
                                                <label>Nhận xét</label>
                                                <textarea class="form-control" name="comment" rows="3" placeholder="Viết đánh giá của bạn" required></textarea>
                                            </div>

                                            <div class="form-group">
                                                <label>Chấm điểm:</label>
                                                <div class="rating">
                                                    <input type="radio" name="rating" value="5" id="star5"><label for="star5">&#9733;</label>
                                                    <input type="radio" name="rating" value="4" id="star4"><label for="star4">&#9733;</label>
                                                    <input type="radio" name="rating" value="3" id="star3"><label for="star3">&#9733;</label>
                                                    <input type="radio" name="rating" value="2" id="star2"><label for="star2">&#9733;</label>
                                                    <input type="radio" name="rating" value="1" id="star1"><label for="star1">&#9733;</label>
                                                </div>
                                            </div>

                                            <button type="submit" class="btn btn-success">Gửi đánh giá</button>
                                        </form>
                                    </div>
                                </c:if>

                                <c:if test="${not hasPurchased && param.error != 'not_purchased' && justReviewed != true}">
                                    <p class="text-warning">Bạn cần mua sản phẩm này để đánh giá.</p>
                                </c:if>
                            </div>

                        </div>

                    </div>
                </div>
            </div>
            <div class="related-products">
                <h3>Sản phẩm liên quan</h3>
                <div class="owl-carousel owl-theme">
                    <c:forEach var="relatedProduct" items="${relatedProducts}">
                        <div class="item">
                            <a href="detail?id=${relatedProduct.id}&categoryId=${product.categoryId}">
                                <img src="images/home/${relatedProduct.image}" alt="${relatedProduct.name}" class="img-fluid">
                                <p><b>${relatedProduct.name}</b></p>
                                <p class="price"><b>Giá:</b> <fmt:formatNumber value="${relatedProduct.price}" type="number" maxFractionDigits="0"/> VNĐ</p>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>

        </div>
    </section>
    <jsp:include page="footer.jsp"></jsp:include>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            // Kiểm tra nếu URL có chứa "#reviews"
            if (window.location.hash === "#reviews") {
                // Mở tab "Đánh giá"
                document.querySelector('a[href="#reviews"]').click();
            }
        });
    </script>

    <script>
        $(document).ready(function () {
            $(".owl-carousel").owlCarousel({
                loop: true,
                margin: 20,
                nav: true,
                dots: false,
                autoplay: true,
                autoplayTimeout: 3000,
                autoplayHoverPause: true,
                navText: ["<i class='fa fa-chevron-left'></i>", "<i class='fa fa-chevron-right'></i>"],
                responsive: {
                    0: {items: 1},
                    600: {items: 2},
                    1000: {items: 4}
                }
            });
        });
    </script>

</body>
</html>
