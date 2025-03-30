<%@page import="Entity.Category"%>
<%@page import="dao.ProductDao"%>
<%@page import="java.util.List"%>
<%@page import="Entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="includes/header.jsp" />
<jsp:include page="includes/navbar.jsp" />
<c:if test="${not empty param.message}">
    <div class="alert alert-${param.messageType} alert-dismissible fade show" role="alert">
        ${param.message}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>
<%
    ProductDao productDAO = new ProductDao();
    List<Category> categories = productDAO.getAllCategories();
%>
<%
    String message = (String) request.getAttribute("message");
    String status = (String) request.getAttribute("status"); // "success" hoặc "danger"

    if (message != null) {
%>
<div class="alert alert-<%= (status != null && status.equals("success")) ? "success" : "danger"%>">
    <%= message%>
</div>
<% } %>

<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand">QUẢN LÝ SẢN PHẨM</a>
    <form class="form-inline" action="product" method="get">
        <input type="text" name="search" class="form-control mr-sm-2" placeholder="Tìm kiếm sản phẩm" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0 mr-2" type="submit">Tìm kiếm</button>
        <button type="button" class="btn btn-success" data-toggle="modal" data-target="#productModal">
            Thêm sản phẩm
        </button>
    </form>
</nav>
<div class="table-responsive">
    <table class="table table-bordered mt-2 text-center">
        <thead>
            <tr>
                <th style="width: 25%;">Tên sản phẩm</th>
                <th style="width: 15%;">Giá</th>
                <th style="width: 15%;">Danh mục</th>
                <th style="width: 25%;">Hình ảnh</th>
                <th style="width: 10%;">Số lượng</th>
                <th style="width: 15%;">Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${listP}">
                <tr>
                    <td>${product.name}</td>

                    <td>
                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫"/>
                    </td>
                    <td>${product.categoryName}</td>
                    <td>
                        <img src="images/home/${product.image}" alt="${product.name}" style="max-height: 100px;">
                    </td>
                    <td>${product.quantity}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editProduct(${product.id})">Sửa</button>
                        <a href="product?action=delete&id=${product.id}" class="btn btn-danger btn-sm"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- Hiển thị phân trang -->
<ul class="pagination justify-content-center mt-3">
    <c:if test="${currentPage > 1}">
        <li class="page-item">
            <a class="page-link" href="product?index=${currentPage - 1}&search=${searchKeyword}">&laquo;</a>
        </li>
    </c:if>

    <c:forEach begin="1" end="${endP}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
            <a class="page-link" href="product?index=${i}&search=${searchKeyword}">${i}</a>
        </li>
    </c:forEach>

    <c:if test="${currentPage < endP}">
        <li class="page-item">
            <a class="page-link" href="product?index=${currentPage + 1}&search=${searchKeyword}">&raquo;</a>
        </li>
    </c:if>
</ul>

<div class="modal fade" id="productModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thông tin sản phẩm</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <form action="${pageContext.request.contextPath}/product?action=add" method="POST" enctype="multipart/form-data">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Tên sản phẩm:</label>
                        <input type="text" name="name" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Danh mục:</label>
                        <select name="category" class="form-control" required>
                            <option value="">Chọn danh mục</option>
                            <% for (Category category : categories) {%>
                            <option value="<%= category.getId()%>"><%= category.getName()%></option>
                            <% } %>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Giá:</label>
                        <input type="text" name="price" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Tiêu đề sản phẩm:</label>
                        <textarea name="title" class="form-control" rows="2" required></textarea>
                    </div>

                    <div class="form-group">
                        <label>Hình ảnh:</label>
                        <input type="file" name="image" class="form-control-file">
                    </div>

                    <div class="form-group">
                        <label>Số lượng:</label>
                        <input type="number" name="quantity" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Mô tả chi tiết:</label>
                        <textarea name="detail" class="form-control" rows="4" required></textarea>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="this.form.reset()">Đóng</button>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="editProductModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Chỉnh sửa thông tin sản phẩm</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <form action="${pageContext.request.contextPath}/product?action=update" method="POST" enctype="multipart/form-data">
                <div class="modal-body">
                    <input type="hidden" name="id" id="edit-id">

                    <div class="form-group">
                        <label>Tên sản phẩm:</label>
                        <input type="text" name="name" id="edit-name" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Danh mục:</label>
                        <select name="category" id="edit-category" class="form-control" required>
                            <option value="">Chọn danh mục</option>
                            <% for (Category category : categories) {%>
                            <option value="<%= category.getId()%>"><%= category.getName()%></option>
                            <% }%>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Giá:</label>
                        <input type="text" name="price" id="edit-price" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Tiêu đề sản phẩm:</label>
                        <textarea name="title" id="edit-title" class="form-control" rows="2" required></textarea>
                    </div>

                    <div class="form-group">
                        <label>Hình ảnh:</label>
                        <img id="edit-image-preview" src="" alt="Ảnh sản phẩm" 
                             style="max-width: 150px; display: none; margin-bottom: 10px;">
                        <input type="file" name="image" id="edit-image" class="form-control-file">
                    </div>

                    <div class="form-group">
                        <label>Số lượng:</label>
                        <input type="number" name="quantity" id="edit-quantity" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Mô tả chi tiết:</label>
                        <textarea name="detail" id="edit-detail" class="form-control" rows="4" required></textarea>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                </div>
            </form>
        </div>
    </div>
</div>


</div>
<jsp:include page="includes/scripts.jsp" />
<jsp:include page="includes/footer.jsp" />