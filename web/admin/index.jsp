<%@page import="dao.OrderDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="includes/header.jsp" />
<jsp:include page="includes/navbar.jsp" />

<!-- Begin Page Content -->
<div class="container-fluid">
    <%
        OrderDao orderDao = new OrderDao();
        Double getTodayRevenue = orderDao.getTodayRevenue();
        request.setAttribute("getTodayRevenue", getTodayRevenue);
        double weeklyRevenue = orderDao.getWeeklyRevenue(); // Doanh thu theo tuần
        double monthlyRevenue = orderDao.getMonthlyRevenue(); // Doanh thu theo tháng
        request.setAttribute("weeklyRevenue", weeklyRevenue);
        request.setAttribute("monthlyRevenue", monthlyRevenue);
    %>

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
        <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
            <i class="fas fa-download fa-sm text-white-50"></i> Generate Report
        </a>
    </div>

    <!-- Content Row -->
    <div class="row">

        <!-- Đơn đặt hàng mới -->
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-danger shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">Đơn đặt hàng mới</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                <span id="new-orders-count"> <p><strong>${newOrdersCount}</strong></p></span> 
                            </div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-shopping-cart fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Doanh thu trong ngày -->
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Doanh thu trong ngày</div>
                            <fmt:formatNumber value="${getTodayRevenue}" type="number" pattern="#,###" var="formattedRevenue"/>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${formattedRevenue} VNĐ</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Doanh thu trong tuần</div>
                           <fmt:formatNumber value="${weeklyRevenue}" type="number" pattern="#,###" var="formattedRevenue"/>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${formattedRevenue} VNĐ</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Doanh thu trong tháng</div>
                            <fmt:formatNumber value="${monthlyRevenue}" type="number" pattern="#,###" var="formattedRevenue"/>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${formattedRevenue} VNĐ</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="table-responsive">
        <table class="table table-bordered mt-2 text-center">
            <thead>
                <tr>
                    <th style="width: 15%;">Ngày Đặt Hàng</th>
                    <th style="width: 25%;">Tên Người Nhận</th>
                    <th style="width: 15%;">Trạng Thái</th>
                    <th style="width: 15%;">Số Điện Thoại</th>
                    <th style="width: 20%;">Địa Chỉ</th>
                    <th style="width: 20%;">Tổng tiền</th>
                    <th style="width: 10%;">Phương Thức TT</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>
                            ${order.orderDate}
                        </td>
                        <td>${order.recipientName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${order.status == 'PENDING'}">
                                    <span class="badge bg-warning text-dark">Chờ Xử Lý</span>
                                </c:when>
                                <c:when test="${order.status == 'shipping'}">
                                    <span class="badge bg-warning text-dark">Đang vận chuyển</span>
                                </c:when>
                                <c:when test="${order.status == 'canceled'}">
                                    <span class="badge bg-success">Đã hủy</span>
                                </c:when>
                                <c:when test="${order.status == 'delived'}">
                                    <span class="badge bg-success">Đã giao hàng</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">${order.status}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${order.phone}</td>
                        <td>${order.address}</td>
                        <td>${order.totalAmount}</td>
                        <td>${order.paymentMethod}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Hiển thị phân trang -->
    <ul class="pagination justify-content-center mt-3">
        <c:if test="${currentPage > 1}">
            <li class="page-item">
                <a class="page-link" href="dashboard?page=${currentPage - 1}">&laquo;</a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="dashboard?page=${i}">${i}</a>
            </li>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <li class="page-item">
                <a class="page-link" href="dashboard?page=${currentPage + 1}">&raquo;</a>
            </li>
        </c:if>
    </ul>

    <p class="text-muted text-center mt-2">
        Tổng số đơn hàng: ${totalOrders} | Trang ${currentPage}/${totalPages}
    </p>


</div> <!-- End container-fluid -->

<jsp:include page="includes/footer.jsp" />
<jsp:include page="includes/scripts.jsp" />
