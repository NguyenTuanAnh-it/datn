<%-- 
    Document   : review
    Created on : Mar 18, 2025, 4:03:25 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <a class="navbar-brand">QUẢN LÝ ĐÁNH GIÁ</a>
    <form class="form-inline" action="review" method="get">
        <input type="text" name="search" class="form-control mr-sm-2" placeholder="Tìm kiếm đánh giá" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0 mr-2" type="submit">Tìm kiếm</button>
    </form>
</nav>
<div class="table-responsive">
    <table class="table table-bordered mt-2 text-center">
        <thead>
            <tr>
                <th style="width: 25%;">Email người dùng</th>
                <th style="width: 50%;">Nội dung</th>
                <th style="width: 15%;">Thời gian</th>
                <th style="width: 15%;">Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="review" items="${listr}">
                <tr>
                    <td>${review.email}</td>
                    <td>${review.content}</td>
                    <td>
                       ${review.review_date}
                    </td>
                    <td>
                        <a href="review?action=delete&id=${review.id}" class="btn btn-danger btn-sm"
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
            <a class="page-link" href="review?index=${currentPage - 1}&search=${searchKeyword}">&laquo;</a>
        </li>
    </c:if>

    <c:forEach begin="1" end="${endP}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
            <a class="page-link" href="review?index=${i}&search=${searchKeyword}">${i}</a>
        </li>
    </c:forEach>

    <c:if test="${currentPage < endP}">
        <li class="page-item">
            <a class="page-link" href="review?index=${currentPage + 1}&search=${searchKeyword}">&raquo;</a>
        </li>
    </c:if>
</ul>

<jsp:include page="includes/footer.jsp" />
<jsp:include page="includes/scripts.jsp" />

