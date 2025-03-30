<%-- 
    Document   : order
    Created on : Mar 25, 2025, 2:16:37 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="includes/header.jsp" />
<jsp:include page="includes/navbar.jsp" />

<div class="container-fluid mt-3">
    <c:if test="${not empty message}">
        <div class="alert alert-success text-center">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger text-center">${error}</div>
    </c:if>

    <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand">QUẢN LÝ ĐƠN HÀNG</a>
        <form class="form-inline" action="OrderManagerControl" method="get">
            <input type="text" name="search" value="${searchQuery}" class="form-control mr-sm-2" placeholder="Tìm kiếm đơn hàng" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0 mr-2" type="submit">Tìm kiếm</button>
        </form>
    </nav>


    <div class="table-responsive">
        <table class="table table-hover table-bordered text-center w-100">
            <thead class="thead-dark">
                <tr>
                    <th>Ngày Đặt Hàng</th>
                    <th>Tên Người Nhận</th>
                    <th>Trạng Thái</th>
                    <th>Số Điện Thoại</th>
                    <th>Địa Chỉ</th>
                    <th>Phương Thức TT</th>
                    <th>Cập Nhật</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>
                            <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/>
                        </td>
                        <td>${order.recipientName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${order.status eq 'CANCELED'}">
                                    <span class="badge bg-danger text-white">Đã Hủy</span>
                                </c:when>
                                <c:otherwise>
                                    <form action="OrderManagerControl" method="post" class="d-flex justify-content-center align-items-center">
                                        <input type="hidden" name="orderId" value="${order.id}">
                                        <select name="status" class="form-control w-auto">
                                            <option value="PENDING" ${order.status eq 'PENDING' ? 'selected' : ''}>Chờ Xử Lý</option>
                                            <option value="SHIPPING" ${order.status eq 'SHIPPING' ? 'selected' : ''}>Đang Giao Hàng</option>
                                            <option value="CANCELED" ${order.status eq 'CANCELED' ? 'selected' : ''}>Đã Hủy</option>
                                            <option value="DELIVERED" ${order.status eq 'DELIVERED' ? 'selected' : ''}>Đã Giao</option>
                                        </select>
                                    </c:otherwise>
                                </c:choose>
                        </td>
                        <td>${order.phone}</td>
                        <td>${order.address}</td>
                        <td>${order.paymentMethod}</td>
                        <td>
                            <c:if test="${order.status ne 'CANCELED'}">
                                <button type="submit" class="btn btn-primary">Cập Nhật</button>
                            </c:if>
                            </form>
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
                <a class="page-link" href="OrderManagerControl?page=${currentPage - 1}">&laquo;</a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="OrderManagerControl?page=${i}">${i}</a>
            </li>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <li class="page-item">
                <a class="page-link" href="OrderManagerControl?page=${currentPage + 1}">&raquo;</a>
            </li>
        </c:if>
    </ul>

    <p class="text-muted text-center mt-2">
        Tổng số đơn hàng: ${totalOrders} | Trang ${currentPage}/${totalPages}
    </p>
</div>

<jsp:include page="includes/scripts.jsp" />
<jsp:include page="includes/footer.jsp" />
