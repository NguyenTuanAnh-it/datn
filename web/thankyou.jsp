<%-- 
    Document   : thank_you_modal
    Created on : Mar 5, 2025, 4:20:23 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="thankYouModal" class="modal fade" role="dialog">
            <div class="modal-dialog" style="max-width: 350px;">
                <div class="modal-content text-center p-3">
                    <div class="modal-header border-0">
                        <h3 class="modal-title text-success font-weight-bold w-100">🎉 Cảm ơn bạn!</h3> 
                    </div>
                    <div class="modal-body">
                        <p class="font-weight-bold" style="font-size: 18px;">Đơn hàng của bạn đã được ghi nhận.<br> Vui lòng kiểm tra email của bạn.</p>
                        <i class="fa fa-check-circle text-success" style="font-size: 40px;"></i>
                    </div>
                    <div class="modal-footer border-0">
                        <button type="button" class="btn btn-success btn-block font-weight-bold" data-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
