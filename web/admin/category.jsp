<%-- 
    Document   : category
    Created on : Mar 18, 2025, 1:07:12 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:if test="${not empty param.message}">
    <div class="alert alert-${param.messageType} alert-dismissible fade show" role="alert">
        ${param.message}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>
<jsp:include page="includes/header.jsp" />
<jsp:include page="includes/navbar.jsp" />
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand">QUẢN LÝ DANH MỤC</a>
    <form class="form-inline" action="category" method="get">
        <input type="text" name="search" class="form-control mr-sm-2" placeholder="Tìm kiếm danh mục" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0 mr-2" type="submit">Tìm kiếm</button>
        <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addCategoryModal">
            Thêm Danh mục
        </button>
    </form>
</nav>
<div class="table-responsive">
    <table class="table table-bordered mt-2 text-center">
        <thead>
            <tr>
                <th style="width: 25%;">Mã danh mục</th>
                <th style="width: 60%;">Tên Danh mục</th>
                <th style="width: 15%;">Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="category" items="${listc}">
                <tr>
                    <td>${category.id}</td>
                    <td>${category.name}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editCategory(${category.id})">Sửa</button>
                        <a href="category?action=delete&id=${category.id}" class="btn btn-danger btn-sm"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<ul class="pagination justify-content-center mt-3">
    <c:if test="${currentPage > 1}">
        <li class="page-item">
            <a class="page-link" href="category?index=${currentPage - 1}&search=${searchKeyword}">&laquo;</a>
        </li>
    </c:if>

    <c:forEach begin="1" end="${endP}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
            <a class="page-link" href="category?index=${i}&search=${searchKeyword}">${i}</a>
        </li>
    </c:forEach>

    <c:if test="${currentPage < endP}">
        <li class="page-item">
            <a class="page-link" href="category?index=${currentPage + 1}&search=${searchKeyword}">&raquo;</a>
        </li>
    </c:if>
</ul>
<div class="modal fade" id="addCategoryModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thông tin danh mục</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <form action="${pageContext.request.contextPath}/category?action=add" method="POST">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Tên danh mục:</label>
                        <input type="text" name="name" class="form-control" required>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="this.form.reset()">Đóng</button>
                        <button type="submit" class="btn btn-primary">Lưu</button>
                    </div>
            </form>
        </div>
    </div>
</div>
</div>

<div class="modal fade" id="editCategoryModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Chỉnh sửa danh mục</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <form action="${pageContext.request.contextPath}/category?action=update" method="POST">
                <div class="modal-body">
                    <input type="hidden" name="id" id="edit-id">
                    <div class="form-group">
                        <label>Tên danh mục:</label>
                        <input type="text" name="category_name" id="edit-name" class="form-control" required>
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