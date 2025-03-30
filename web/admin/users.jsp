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


<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand">QUẢN LÝ NGƯỜI DÙNG</a>
    <form class="form-inline" action="user" method="get">
        <input type="text" name="search" class="form-control mr-sm-2" value="${searchKeyword}" placeholder="Tìm kiếm người dùng" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0 mr-2" type="submit">Tìm kiếm</button>
        <button type="button" class="btn btn-success" data-toggle="modal" data-target="#userModal">
            Thêm người dùng
        </button>
    </form>
</nav>

<div class="table-responsive">
    <table class="table table-bordered mt-2 text-center">
        <thead>
            <tr>
                <th style="width: 20%;">Tên người dùng</th>
                <th style="width: 20%;">Email</th>
                <th style="width: 15%;">Điện thoại</th>
                <th style="width: 25%;">Địa chỉ</th>
                <th style="width: 10%;">Quyền</th>
                <th style="width: 15%;">Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${listP}">
                <tr>
                    <td>${user.userName}</td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td>${user.address}</td>
                    <td>
                        <c:choose>
                            <c:when test="${user.role == true}">Quản trị viên</c:when>
                            <c:otherwise>Người dùng</c:otherwise>
                        </c:choose>

                    </td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editUser(${user.id})">Sửa</button>
                        <a href="user?action=delete&id=${user.id}" class="btn btn-danger btn-sm"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa?');">Xóa</a>

                    </td>
                </tr>
            </c:forEach>
        </tbody>

    </table>
   <ul class="pagination justify-content-center mt-3">
    <c:if test="${currentPage > 1}">
        <li class="page-item">
            <a class="page-link" href="user?index=${currentPage - 1}&search=${searchKeyword}">&laquo;</a>
        </li>
    </c:if>

    <c:forEach begin="1" end="${endP}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
            <a class="page-link" href="user?index=${i}&search=${searchKeyword}">${i}</a>
        </li>
    </c:forEach>

    <c:if test="${currentPage < endP}">
        <li class="page-item">
            <a class="page-link" href="user?index=${currentPage + 1}&search=${searchKeyword}">&raquo;</a>
        </li>
    </c:if>
</ul>


</div>

<!-- Form Thêm/Sửa Người Dùng (Modal) -->
<div class="modal fade" id="userModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thông tin người dùng</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <form action="${pageContext.request.contextPath}/user?action=add" method="POST">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Tên đăng nhập:</label>
                        <input type="text" name="username" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Mật khẩu:</label>
                        <input type="password" name="password" class="form-control">
                    </div>

                    <div class="form-group">
                        <label>Email:</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Số điện thoại:</label>
                        <input type="text" name="phone" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Địa chỉ:</label>
                        <textarea name="address" class="form-control" rows="3" required></textarea>
                    </div>

                    <div class="form-group">
                        <label>Vai trò:</label>
                        <select name="role" class="form-control">
                            <option value="0">Người dùng</option>
                            <option value="1">Quản trị viên</option>
                        </select>
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
<div class="modal fade" id="editUserModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Chỉnh sửa thông tin người dùng</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <form action="${pageContext.request.contextPath}/user?action=update" method="POST">
                <div class="modal-body">
                    <input type="hidden" name="id" id="edit-id">

                    <div class="form-group">
                        <label>Tên đăng nhập:</label>
                        <input type="text" name="username" id="edit-username" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Email:</label>
                        <input type="email" name="email" id="edit-email" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Số điện thoại:</label>
                        <input type="text" name="phone" id="edit-phone" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Địa chỉ:</label>
                        <textarea name="address" id="edit-address" class="form-control" rows="3" required></textarea>
                    </div>

                    <div class="form-group">
                        <label>Vai trò:</label>
                        <select name="role" id="edit-role" class="form-control">
                            <option value="0">Người dùng</option>
                            <option value="1">Quản trị viên</option>
                        </select>
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

<jsp:include page="includes/scripts.jsp" />
<jsp:include page="includes/footer.jsp" />

