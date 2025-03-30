<%-- 
    Document   : category
    Created on : Feb 17, 2025, 11:05:15 AM
    Author     : ADMIN
--%>
<%@page import="dao.ProductDao"%>
<%@page import="java.util.List"%>
<%@page import="Entity.Category"%>
<%@page import="dao.CategoryDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    CategoryDao categoryDao = new CategoryDao();
    List<Category> listC = categoryDao.getListCategory();
    request.setAttribute("listC", listC);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/price-range.css" rel="stylesheet">
        <link href="css/animate.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">
    </head>

    <body>
        <div class="col-sm-3">
            <div class="left-sidebar">
                <h2>Danh Mục</h2>
                <div class="panel-group category-products" id="accordian">
                    <c:forEach var="category" items="${listC}">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a href="index.jsp?id=${category.id}">${category.name}</a>
                                </h4>
                            </div>
                        </div>
                    </c:forEach>
                </div><!--/category-products-->
                <div class="shipping text-center"><!--shipping-->
                    <img src="images/home/shipping.jpg" alt="" />
                </div><!--/shipping-->
                <div style="clear: both; margin-bottom: 40px"></div>
            </div>
        </div>
    </body>
</html>
