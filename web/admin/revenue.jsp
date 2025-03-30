<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="includes/header.jsp" />
<jsp:include page="includes/navbar.jsp" />

<script>
function validateDate() {
    var startDate = new Date(document.getElementById('startDate').value);
    var endDate = new Date(document.getElementById('endDate').value);
    
    if (startDate && endDate && startDate > endDate) {
        alert('Ngày bắt đầu không được lớn hơn ngày kết thúc');
        return false;
    }
    return true;
}
</script>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
</c:if>
<div class="container-fluid px-4 mt-3">
    <!-- Filter Section -->
    <div class="card mb-4 shadow-sm">
        <div class="card-header bg-light py-3">
            <div class="d-flex justify-content-between align-items-center">
                <h5 class="mb-0">BÁO CÁO DOANH THU</h5>
                <form action="ExportExcelServlet" method="GET" class="d-inline">
                    <input type="hidden" name="startDate" value="${param.startDate}">
                    <input type="hidden" name="endDate" value="${param.endDate}">
                    <button type="submit" class="btn btn-success btn-sm">
                        <i class="fas fa-download fa-sm"></i> Xuất báo cáo
                    </button>
                </form>
            </div>
        </div>
        <div class="card-body">
            <form action="RevenueControl" method="GET" class="row g-3 align-items-center" onsubmit="return validateDate()">
                <div class="col-auto">
                    <label for="startDate" class="col-form-label fw-bold">Từ ngày:</label>
                </div>
                <div class="col-auto">
                    <input type="date" id="startDate" name="startDate" class="form-control form-control-sm" value="${param.startDate}" required>
                </div>

                <div class="col-auto">
                    <label for="endDate" class="col-form-label fw-bold">Đến ngày:</label>
                </div>
                <div class="col-auto">
                    <input type="date" id="endDate" name="endDate" class="form-control form-control-sm" value="${param.endDate}" required>
                </div>

                <div class="col-auto">
                    <button type="submit" class="btn btn-primary btn-sm">
                        <i class="fas fa-sync-alt fa-sm"></i> Cập nhật
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Total Revenue -->
    <div class="alert alert-info py-2 mb-3 text-end">
        <h5 class="mb-0 fw-bold">Tổng doanh thu: 
            <fmt:formatNumber value="${totalRevenueAmount}" type="currency" currencySymbol="VNĐ"/>
        </h5>
    </div>

    <!-- Report Table -->
    <div class="card shadow-sm">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-bordered table-hover mb-0">
                    <thead class="table-light">
                        <tr>
                            <th class="text-center">Mã đơn hàng</th>
                            <th class="text-center">Ngày đặt hàng</th>
                            <th class="text-center">Người nhận</th>
                            <th class="text-center">Số điện thoại</th>
                            <th class="text-center">Địa chỉ</th>
                            <th class="text-center">Ngày thanh toán</th>
                            <th class="text-center">Số tiền (VNĐ)</th>
                            <th class="text-center">Phương thức thanh toán</th>
                            <th class="text-center">Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="revenue" items="${reportList}">
                            <tr>
                                <td class="text-center">${revenue.orderId}</td>
                                <td class="text-center"><fmt:formatDate value="${revenue.orderDate}" pattern="dd/MM/yyyy"/></td>
                                <td>${revenue.recipientName}</td>
                                <td class="text-center">${revenue.phone}</td>
                                <td>${revenue.address}</td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${not empty revenue.paymentDate}">
                                            <fmt:formatDate value="${revenue.paymentDate}" pattern="dd/MM/yyyy"/>
                                        </c:when>
                                        <c:otherwise>Chưa thanh toán</c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-end"><fmt:formatNumber value="${revenue.amount}" type="number" pattern="#,###"/></td>
                                <td class="text-center">${revenue.paymentMethod}</td>
                                <td class="text-center">${revenue.status}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Pagination -->
    <nav class="mt-3">
        <ul class="pagination justify-content-center">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="RevenueControl?page=${currentPage - 1}&startDate=${param.startDate}&endDate=${param.endDate}">&laquo;</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${endP}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="RevenueControl?page=${i}&startDate=${param.startDate}&endDate=${param.endDate}">${i}</a>
                </li>
            </c:forEach>

            <c:if test="${currentPage < endP}">
                <li class="page-item">
                    <a class="page-link" href="RevenueControl?page=${currentPage + 1}&startDate=${param.startDate}&endDate=${param.endDate}">&raquo;</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>

<jsp:include page="includes/scripts.jsp" />
<jsp:include page="includes/footer.jsp" />