<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"
        integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e" crossorigin="anonymous">
    <link rel="stylesheet" href="css/user.css">
    <title>Document</title>
    <style>
        * {
            font-family: Arial, Helvetica, sans-serif;
        }

        .heading img {
            width: 160px;
        }

        .heading {
            display: flex;
            align-items: center;
            margin: 0;
            padding: 20px;
            justify-content: space-between;
        }

        .head {
            display: flex;
            align-items: center;
            margin-left: 150px;
        }

        .heading div {
            font-size: 27px;
            font-weight: bold;
            margin-left: 20px;
            color: #2980b9;
        }

        .heading a {
            text-decoration: none;
            color: #f53b57;
            font-size: 18px;
            margin-right: 50px;
        }

        main {
            width: 100%;
            height: 814px;
            background-color: #00b894;
            display: flex;
            justify-content: space-between;
        }

        /* form article{
    width: 500px;
    height: 600px;
} */
        form {
            background-color: white;
            width: 460px;
            border-radius: 5px;
            height: 480px;
            margin-right: 180px;
            margin-top: 150px;
        }

        form div.head-form {
            font-size: 23px;
            padding: 30px;
            padding-left: 50px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        form div.head-form i {
            font-size: 30px;
            margin-right: 50px;
            color: #f53b57;
        }

        form input {
            width: 83%;
            margin: 0 32px;
            margin-bottom: 45px;
            padding: 8px;
        }

        #btn-submit {
            border: none;
            background: #ff5e57;
            color: white;
            width: 83%;
            margin: auto 32px;
            padding: 18px;
            border-radius: 2px;
        }

        div.footer-form {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin: 4px 35px;
            font-size: 13px;
        }

        article div i {
            color: white;
            font-size: 300px;
        }

        article div {
            margin-left: 461px;
            margin-top: 80px;
        }

        article div div {
            padding: 0;
            margin: 0;
            font-size: 25px;
            width: 306px;
            font-weight: bold;
            color: white;
            text-align: center;
        }

        div.sigup {
            font-size: 19px;
            padding: 30px;
            display: flex;
            align-items: center;
            text-align: center;
            padding-left: 63px;
            padding-top: 15px;
        }
    </style>
</head>
<script src="js/dangnhap.js"></script>

<body>
    <header class="heading">
        <div class="head">
            <img src="images/home/veganlogo.jpg" alt="">
            <div>Đăng Nhập</div>
        </div>
        <a href="#">Bạn cần giúp gì?</a>
    </header>
    <main>
        <article>
            <div>
                <i class="bi bi-cart-check-fill"></i>
                <div>Uy tín tạo niềm tin - Chất lượng số 1 Việt Nam</div>
            </div>
        </article>
        <form action="index.html" onsubmit="dangnhap(); return false">
            <div class="head-form">
                <p>Đăng nhập</p>
                <i class="bi bi-qr-code"></i>
            </div>
            <input type="text" id="ingame" required placeholder="Email/Số điện thoạii">
            <input type="password" id="pw" required placeholder="Mật khẩu">

            <button id="btn-submit" type="submit" onsubmit="dangnhap(); return false">Đăng nhập</button>
            <div class="footer-form">
                <a href="">Quên mật khẩu</a>
                <a href="">Đăng nhập với gmail</a>
            </div>
            <hr>
            <div class="sigup">Bạn chưa có tài khoản: <a href="dangki.html">Đăng kí</a></div>
        </form>
    </main>
</body>
<script src="js/dangnhap.js"></script>

</html>