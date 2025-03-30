package Controller;

import DTO.Response.RevenueReportRes;
import dao.OrderDao;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "ExportExcelServlet", urlPatterns = {"/ExportExcelServlet"})
public class ExportExcelRevenueControl extends HttpServlet {

    private OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Kiểm tra thư viện POI trước khi thực hiện
//            checkPOIDependencies();

            exportToExcel(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Lỗi khi xuất file Excel: " + ex.getMessage());
        }
    }

//    /**
//     * Kiểm tra sự tồn tại của các class cần thiết từ thư viện POI
//     */
//    private void checkPOIDependencies() throws ClassNotFoundException {
//        // Danh sách các class quan trọng cần kiểm tra
//        String[] requiredClasses = {
//            "org.apache.poi.xssf.usermodel.XSSFWorkbook",
//            "org.apache.poi.ss.usermodel.Workbook",
//            "org.apache.xmlbeans.XmlException", // Thư viện phụ thuộc quan trọng
//            "org.apache.commons.collections4.ListValuedMap" // Thư viện commons-collections4
//        };
//        
//        for (String className : requiredClasses) {
//            try {
//                Class.forName(className);
//                System.out.println("Đã tìm thấy class: " + className);
//            } catch (ClassNotFoundException e) {
//                throw new ClassNotFoundException("Không tìm thấy class: " + className + 
//                    ". Vui lòng thêm thư viện cần thiết.", e);
//            }
//        }
//    }
    private void exportToExcel(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String startDateParam = request.getParameter("startDate");
        String endDateParam = request.getParameter("endDate");

        if (startDateParam == null || endDateParam == null || startDateParam.isEmpty() || endDateParam.isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng chọn ngày bắt đầu và ngày kết thúc.");
            request.getRequestDispatcher("admin/revenue.jsp").forward(request, response);
            return;
        }

        Date startDate = Date.valueOf(startDateParam);
        Date endDate = Date.valueOf(endDateParam);
        List<RevenueReportRes> reportList = orderDao.getRevenueReport(startDate, endDate, -1, -1);
        double totalRevenue = orderDao.getTotalRevenueAmount(startDate, endDate);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Báo cáo doanh thu");

            // 🌟 Style tiêu đề
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 14);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            // 🌟 Style border cho tất cả ô
            CellStyle borderedStyle = workbook.createCellStyle();
            borderedStyle.setBorderBottom(BorderStyle.THIN);
            borderedStyle.setBorderTop(BorderStyle.THIN);
            borderedStyle.setBorderLeft(BorderStyle.THIN);
            borderedStyle.setBorderRight(BorderStyle.THIN);
            borderedStyle.setAlignment(HorizontalAlignment.CENTER);

            // 🌟 Style header
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.cloneStyleFrom(borderedStyle);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);

            // 🎯 Tiêu đề báo cáo
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Báo cáo doanh thu mỹ phẩm VEGAN từ " + startDate + " đến " + endDate);
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

            // 🏆 Tạo header row
            Row headerRow = sheet.createRow(1);
            String[] headers = {"STT", "Mã đơn hàng", "Ngày đặt hàng", "Người nhận", "Số điện thoại", "Địa chỉ",
                "Ngày thanh toán", "Số tiền (VNĐ)", "Phương thức", "Trạng thái"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // 📝 Đổ dữ liệu
            int rowNum = 2;
            int stt = 1;
            for (RevenueReportRes item : reportList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(stt++);
                row.createCell(1).setCellValue(item.getOrderId());
                row.createCell(2).setCellValue(item.getOrderDate().toString());
                row.createCell(3).setCellValue(item.getRecipientName());
                row.createCell(4).setCellValue(item.getPhone());
                row.createCell(5).setCellValue(item.getAddress());
                row.createCell(6).setCellValue(item.getPaymentDate() != null ? item.getPaymentDate().toString() : "Chưa thanh toán");
                row.createCell(7).setCellValue(item.getAmount());
                row.createCell(8).setCellValue(item.getPaymentMethod());
                row.createCell(9).setCellValue(item.getStatus());

                // Áp dụng border cho tất cả ô
                for (int i = 0; i < headers.length; i++) {
                    row.getCell(i).setCellStyle(borderedStyle);
                }
            }

            // 💰 Dòng tổng doanh thu
            Row totalRow = sheet.createRow(rowNum + 1);
            for (int i = 0; i <= 6; i++) {
                totalRow.createCell(i).setCellStyle(headerStyle); // Giữ màu giống header
            }
            sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 6));

            Cell totalLabelCell = totalRow.createCell(0);
            totalLabelCell.setCellValue("Tổng doanh thu:");
            totalLabelCell.setCellStyle(headerStyle);
            totalLabelCell.getCellStyle().setAlignment(HorizontalAlignment.RIGHT);

            Cell totalCell = totalRow.createCell(7);
            totalCell.setCellValue(totalRevenue);
            totalCell.setCellStyle(headerStyle);
            totalCell.getCellStyle().setAlignment(HorizontalAlignment.CENTER);

            // 🎯 Căn chỉnh độ rộng cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // 🎯 Xuất file
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=bao_cao_doanh_thu_" + startDate + "_" + endDate + ".xlsx");
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo file Excel", e);
        }
    }

}
