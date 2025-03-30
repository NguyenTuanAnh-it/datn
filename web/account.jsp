<%-- 
    Document   : account
    Created on : Feb 19, 2025, 1:22:28 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
        <%
            String ten_dang_nhap_err = "", email_err = "", mat_khau_err = "";
            if(request.getAttribute("ten_dang_nhap_err") != null){
                ten_dang_nhap_err = (String) request.getAttribute("ten_dang_nhap_err");
            }
            if(request.getAttribute("email_err") != null){
                email_err = (String) request.getAttribute("email_err");
            }
            if(request.getAttribute("mat_khau_err") != null){
                mat_khau_err = (String) request.getAttribute("mat_khau_err");
            }
            
            String ten_dang_nhap = "", email = "", mat_khau = "";
            if(request.getAttribute("ten_dang_nhap") != null){
                ten_dang_nhap = (String) request.getAttribute("ten_dang_nhap");
            }
            if(request.getAttribute("email") != null){
                email = (String) request.getAttribute("email");
            }
            if(request.getAttribute("mat_khau") != null){
                mat_khau = (String) request.getAttribute("mat_khau");
            }
            
            String err = "";
            if(request.getAttribute("err") != null){
                err = (String) request.getAttribute("err");
            }
        %>
        <jsp:include page="header.jsp"></jsp:include>
            <section id="form"><!--form-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1">
                            <div class="login-form"><!--login form-->
                                <h2>Đăng nhập hệ thống</h2>
                                <form action="login" method="post">
                                    <p style="color: red"><%=err%></p>
                                    <input type="email" placeholder="Email của bạn" name="email"/>
                                    <input type="password" placeholder="Nhập mật khẩu" name="mat_khau" />
                                    <span>
                                        <input type="checkbox" class="checkbox"> 
                                        Nhớ đăng nhập
                                    </span>
                                    <button type="submit" class="btn btn-default">Đăng Nhập</button>
                                </form>
                            </div><!--/login form-->
                        </div>
                        <div class="col-sm-1">
                            
                        </div>
                        <div class="col-sm-4">
                            <div class="signup-form"><!--sign up form-->
                                <h2>Đăng ký</h2>
                                <form action="register" method="POST">
                                    <p style="color: red"><%=ten_dang_nhap_err%></p>
                                <input type="text" placeholder="Nhập tên tài khoản" name="ten_dang_nhap" value="<%=ten_dang_nhap%>"/>
                                    <p style="color: red"><%=email_err%></p>
                                    <input type="email" placeholder="Email của bạn" name="email" value="<%=email%>"/>
                                    <p style="color: red"><%=mat_khau_err%></p>
                                    <input type="password" placeholder="Mật khẩu" name="mat_khau" value="<%=mat_khau%>"/>
                                    <button type="submit" class="btn btn-default">Đăng ký</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section><!--/form-->
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
