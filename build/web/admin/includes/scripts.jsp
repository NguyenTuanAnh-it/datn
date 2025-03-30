<%@page contentType="text/html" pageEncoding="UTF-8"%>  
<script>
    $(document).ready(function () {
        console.log("jQuery loaded:", typeof $ !== 'undefined');
        console.log("Bootstrap modal loaded:", typeof $('.modal').modal !== 'undefined');
    });

// Sửa lại hàm editCategory để xử lý rõ ràng hơn
    function editCategory(id) {
        console.log("Editing category: " + id);

        // Kiểm tra xem modal có tồn tại không
        console.log("Modal exists:", $("#editCategoryModal").length > 0);

        $.ajax({
            url: '${pageContext.request.contextPath}/category?action=edit&id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function (category) {
                console.log("Received category data:", category);
                if (category && category.id) {
                    $('#edit-id').val(category.id);
                    $('#edit-name').val(category.name);
                    // Thử gọi modal theo cách khác
                    $('#editCategoryModal').modal({show: true});
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
</script>
<!-- Bootstrap core JavaScript-->
  <script src="<%= request.getContextPath() %>/admin/vendor/jquery/jquery.min.js"></script>
  <script src="<%= request.getContextPath() %>/admin/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="<%= request.getContextPath() %>/admin/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="<%= request.getContextPath() %>/admin/js/sb-admin-2.min.js"></script>

  <!-- Page level plugins -->
  <script src="<%= request.getContextPath() %>/admin/vendor/chart.js/Chart.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="<%= request.getContextPath() %>/admin/js/demo/chart-area-demo.js"></script>
  <script src="<%= request.getContextPath() %>/admin/js/demo/chart-pie-demo.js"></script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>