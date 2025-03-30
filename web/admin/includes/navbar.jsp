<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar" style="background: #123559;">

    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="dashboard">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Tuấn Anh <sup>DZ</sup></div>
    </a>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">
    <!-- Nav Item - Quản lý người dùng -->
    <li class="nav-item">
        <a class="nav-link" href="user">
            <i class="fas fa-fw fa-users"></i>
            <span>Quản lý người dùng</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="product">
            <i class="fas fa-fw fa-users"></i>
            <span>Quản lý sản phẩm</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="category">
            <i class="fas fa-fw fa-users"></i>
            <span>Quản lý danh mục</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="review">
            <i class="fas fa-fw fa-users"></i>
            <span>Quản lý đánh giá</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="OrderManagerControl">
            <i class="fas fa-fw fa-users"></i>
            <span>Quản lý đơn hàng</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseReports"
           aria-expanded="false" aria-controls="collapseReports">
            <i class="fas fa-fw fa-chart-bar"></i>
            <span>Báo cáo thống kê</span>
        </a>
        <div id="collapseReports" class="collapse" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="RevenueControl">Doanh thu</a>
                <a class="collapse-item" href="ProductReportControl">Hàng hóa</a>
            </div>
        </div>
    </li>
    <!-- Divider -->
    <hr class="sidebar-divider d-none d-md-block">

    <!-- Sidebar Toggler (Sidebar) -->
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>
</ul>
<div id="content-wrapper" class="d-flex flex-column">
    <div id="content">
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
            <ul class="navbar-nav ml-auto">
                <!-- Nav Item - User Information -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="mr-2 d-none d-lg-inline text-gray-600 small">Tên Người Dùng</span>
                        <img class="img-profile rounded-circle" src="https://images.immediate.co.uk/production/volatile/sites/3/2023/08/2023.06.28-06.20-boundingintocomics-649c79f009cdf-Cropped-8d74232.png">
                    </a>
                    <!-- Dropdown - User Information -->
                    <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                         aria-labelledby="userDropdown">
                        <a class="dropdown-item" href="logout.jsp">
                            <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                            Đăng xuất
                        </a>
                    </div>
                </li>
            </ul>
        </nav>