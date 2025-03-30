<%@page import="dao.ProductDao"%>
<%@page import="DTO.Response.GoodsReportRes"%>
<%@page import="Entity.Category"%>
<%@page import="java.util.List"%>
<%@page import="dao.CategoryDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="includes/header.jsp" />
<jsp:include page="includes/navbar.jsp" />
<%
    // Lấy categoryId từ request parameter
    String categoryIdStr = request.getParameter("categoryId");
    Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : null;

    CategoryDao categoryDao = new CategoryDao();
    List<Category> categoryList = categoryDao.getListCategory();
    request.setAttribute("categoryList", categoryList);

    // Tính tổng số lượng hàng tồn
    ProductDao productDao = new ProductDao();
    int totalQuantity = 0;
    if (request.getAttribute("products") != null) {
        List<GoodsReportRes> products = (List<GoodsReportRes>) request.getAttribute("products");
        for (GoodsReportRes product : products) {
            totalQuantity += product.getQuantity();
        }
    }
    request.setAttribute("totalQuantity", totalQuantity);

    // Tính tổng số lượng tất cả sản phẩm (không phân trang)
    int totalQuantityAll = productDao.getTotalQuantityAllProducts(categoryId);
    request.setAttribute("totalQuantityAll", totalQuantityAll);
%>
<div class="container-fluid px-4 mt-3">
    <!-- Filter Section -->
    <div class="card mb-4 shadow-sm">
        <div class="card-header bg-light py-3">
            <div class="d-flex justify-content-between align-items-center">
                <h5 class="mb-0">BÁO CÁO HÀNG HÓA</h5>
                <form action="ExportProductExcelControl" method="GET" class="d-inline">
                    <input type="hidden" name="categoryId" value="${param.categoryId}">
                    <button type="submit" class="btn btn-success btn-sm">
                        <i class="fas fa-download fa-sm"></i> Xuất báo cáo
                    </button>
                </form>
            </div>
        </div>
        <div class="card-body">
            <form action="ProductReportControl" method="GET" class="row g-3 align-items-center">
                <div class="col-auto">
                    <label for="categoryId" class="col-form-label fw-bold">Danh mục:</label>
                </div>
                <div class="col-auto">
                    <select id="categoryId" name="categoryId" class="form-select form-select-sm">
                        <option value="">Tất cả</option>
                        <c:forEach var="category" items="${categoryList}">
                            <option value="${category.id}" ${param.categoryId == category.id ? 'selected' : ''}>
                                ${category.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary btn-sm">
                        <i class="fas fa-filter fa-sm"></i> Lọc
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Summary Card -->
    <div class="card mb-4 shadow-sm">
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <h6 class="fw-bold">Tổng số lượng hàng tồn kho:</h6>
                    <h4 class="text-primary">${totalQuantityAll}</h4>
                </div>
                <div class="col-md-6">
                    <h6 class="fw-bold">Tổng số lượng hàng tồn kho (trang hiện tại):</h6>
                    <h4 class="text-primary">${totalQuantity}</h4>
                </div>
            </div>
        </div>
    </div>

    <!-- Report Table -->
    <div class="card shadow-sm">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-bordered table-hover mb-0">
                    <thead>
                        <tr class="table-light border-dark border-top border-bottom border-start border-end">
                            <th class="text-center border-dark border-end">ID</th>
                            <th class="text-center border-dark border-end">Tên sản phẩm</th>
                            <th class="text-center border-dark border-end">Danh mục</th>
                            <th class="text-center border-dark border-end">Giá</th>
                            <th class="text-center border-dark border-end">Mô tả</th>
                            <th class="text-center border-dark">Số lượng</th> <!-- Không cần border-end cho cột cuối -->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td class="text-center">${product.id}</td>
                                <td>${product.name}</td>
                                <td class="text-center">${product.categoryName}</td>
                                <td class="text-end"><fmt:formatNumber value="${product.price}" type="number" pattern="#,###"/> VNĐ</td>
                                <td>${product.description}</td>
                                <td class="text-center">${product.quantity}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <!-- Thêm dòng tổng số lượng cho trang hiện tại -->
                    <tfoot>
                        <tr class="table-info">
                            <td colspan="5" class="text-end fw-bold">Tổng số lượng trang hiện tại:</td>
                            <td class="text-center fw-bold">${totalQuantity}</td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>

    <!-- Pagination -->
    <div class="d-flex justify-content-center mt-3">
        <nav>
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="ProductReportControl?page=${currentPage - 1}&categoryId=${param.categoryId}">&laquo; Trước</a>
                    </li>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="ProductReportControl?page=${i}&categoryId=${param.categoryId}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="ProductReportControl?page=${currentPage + 1}&categoryId=${param.categoryId}">Sau &raquo;</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>

<jsp:include page="includes/scripts.jsp" />
<jsp:include page="includes/footer.jsp" />