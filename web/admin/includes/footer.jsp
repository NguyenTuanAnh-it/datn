<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Footer -->
<footer class="sticky-footer bg-white">
    <div class="my-auto">
        <div class="copyright text-center my-auto">
            <span>Copyright &copy; Nguyễn Tuấn Anh</span>
        </div>
    </div>
</footer>
<!-- End of Footer -->
</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->
</div>
<!-- End of Content Wrapper -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js"></script>
<script>
    function editCategory(id) {
        console.log("Editing category: " + id);

        // Kiểm tra xem modal có tồn tại không
        var modalExists = $('#editCategoryModal').length > 0;
        console.log("Modal exists:", modalExists);

        if (!modalExists) {
            alert("Không tìm thấy modal với ID 'editCategoryModal'");
            return;
        }

        // Kiểm tra xem có thể gọi modal không
        try {
            // Test mở modal trước khi làm gì khác
            $('#editCategoryModal').modal('show');
            console.log("Modal opened successfully in test");
            $('#editCategoryModal').modal('hide');
        } catch (e) {
            console.error("Error testing modal:", e);
            alert("Lỗi khi test modal: " + e);
            return;
        }
        console.log("Editing category: " + id); // Debug log

        $.ajax({
            url: '${pageContext.request.contextPath}/category?action=edit&id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function (category) {
                console.log("Received category data:", category); // Debug log
                if (category && category.id) {
                    $('#edit-id').val(category.id);
                    $('#edit-name').val(category.name);
                    $('#editCategoryModal').modal('show');
                } else {
                    alert('Không tìm thấy thông tin danh mục');
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error:", status, error);
                console.log("Response:", xhr.responseText);
                alert('Có lỗi xảy ra khi lấy thông tin danh mục: ' + error);
            }
        });
    }
    
    function editProduct(id) {
        console.log("Editing product: " + id); // Debug log

        $.ajax({
            url: '${pageContext.request.contextPath}/product?action=edit&id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function (product) {
                console.log("Received product data:", product);
                if (product && product.id) {
                    $('#edit-id').val(product.id);
                    $('#edit-name').val(product.name);
                    $('#edit-category').val(product.categoryId);
                    $('#edit-price').val(product.price);
                    $('#edit-title').val(product.description);
                    $('#edit-quantity').val(product.quantity);
                    $('#edit-detail').val(product.detail);
                    if (product.image) {
                        // Hiển thị ảnh nếu có
                        $('#edit-image-preview')
                                .attr('src', '${pageContext.request.contextPath}/images/home/' + product.image)
                                .show();
                    } else {
                        // Ẩn ảnh nếu không có
                        $('#edit-image-preview').hide();
                    }
                    $('#editProductModal').modal('show');
                } else {
                    alert('Không tìm thấy thông tin sản phẩm');
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error:", status, error);
                console.log("Response:", xhr.responseText);
                alert('Có lỗi xảy ra khi lấy thông tin sản phẩm: ' + error);
            }
        });
    }


    function editUser(id) {
        console.log("Editing user: " + id); // Debug log

        $.ajax({
            url: '${pageContext.request.contextPath}/user?action=edit&id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function (user) {
                console.log("Received user data:", user); // Debug log
                if (user && user.id) {
                    $('#edit-id').val(user.id);
                    $('#edit-username').val(user.userName);
                    $('#edit-email').val(user.email);
                    $('#edit-phone').val(user.phone);
                    $('#edit-address').val(user.address);
                    $('#edit-role').val(user.role ? '1' : '0');
                    $('#editUserModal').modal('show');
                } else {
                    alert('Không tìm thấy thông tin người dùng');
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error:", status, error);
                console.log("Response:", xhr.responseText);
                alert('Có lỗi xảy ra khi lấy thông tin người dùng: ' + error);
            }
        });
    }

    
</script>
</body>

</html>
