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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            .container {
                width: 80% !important;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <jsp:include page="slider.jsp"></jsp:include>

            <section>
                <div class="container">
                    <div class="row">
                    <jsp:include page="category.jsp"></jsp:include>
                    <jsp:include page="product.jsp"></jsp:include>
                    </div>
                </div>
            </section>

        <jsp:include page="footer.jsp"></jsp:include>
        <jsp:include page="thankyou.jsp"></jsp:include>
            <script src="js/bootstrap.min.js"></script>
            <script>
                $(document).ready(function () {
                    var orderSuccess = '<%= session.getAttribute("orderSuccess")%>';
                    if (orderSuccess === 'true') {
                        $('#thankYouModal').modal('show');
            <% session.removeAttribute("orderSuccess");%>
                    }
                });
        </script>
    </body>
</html>
