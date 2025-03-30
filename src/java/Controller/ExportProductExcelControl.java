/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DTO.Response.GoodsReportRes;
import dao.ProductDao;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ExportProductExcelControl", urlPatterns = {"/ExportProductExcelControl"})
public class ExportProductExcelControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        exportProductReport(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không sử dụng doPost
    }

    protected void exportProductReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryIdStr = request.getParameter("categoryId");
        Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : null;

        ProductDao productDao = new ProductDao();
        List<GoodsReportRes> productList = null;
        try {
            productList = productDao.getProductsByCategory(categoryId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=BaoCaoHangHoa.xlsx");

        try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
            Sheet sheet = workbook.createSheet("Báo cáo hàng hóa");

            // Style cho tiêu đề báo cáo
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            // Thêm border cho tiêu đề
            titleStyle.setBorderBottom(BorderStyle.THIN);
            titleStyle.setBorderTop(BorderStyle.THIN);
            titleStyle.setBorderLeft(BorderStyle.THIN);
            titleStyle.setBorderRight(BorderStyle.THIN);

            // Style cho tiêu đề cột
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Style cho dữ liệu
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Style cho số tiền
            CellStyle currencyStyle = workbook.createCellStyle();
            currencyStyle.cloneStyleFrom(dataStyle);
            DataFormat format = workbook.createDataFormat();
            currencyStyle.setDataFormat(format.getFormat("#,##0.00"));

            // Tạo dòng tiêu đề báo cáo
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("THỐNG KÊ HÀNG HÓA VEGAN SHOP");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6)); // Merge 7 cột

            // Đảm bảo các ô merged đều có style
            for (int i = 1; i <= 6; i++) {
                Cell mergedCell = titleRow.createCell(i);
                mergedCell.setCellStyle(titleStyle);
            }

            // Tạo hàng tiêu đề cột (dòng thứ 2)
            Row headerRow = sheet.createRow(1);
            String[] columns = {"STT", "ID", "Tên sản phẩm", "Danh mục", "Giá", "Mô tả", "Số lượng"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Thêm dữ liệu vào bảng (bắt đầu từ dòng thứ 3)
            int rowNum = 2;
            int totalQuantity = 0;
            int stt = 1; // Số thứ tự bắt đầu từ 1

            for (GoodsReportRes product : productList) {
                Row row = sheet.createRow(rowNum++);

                // Cột STT
                Cell sttCell = row.createCell(0);
                sttCell.setCellValue(stt++);
                sttCell.setCellStyle(dataStyle);

                // Các cột thông tin sản phẩm
                Cell idCell = row.createCell(1);
                idCell.setCellValue(product.getId());
                idCell.setCellStyle(dataStyle);

                Cell nameCell = row.createCell(2);
                nameCell.setCellValue(product.getName());
                nameCell.setCellStyle(dataStyle);

                Cell categoryCell = row.createCell(3);
                categoryCell.setCellValue(product.getCategoryName());
                categoryCell.setCellStyle(dataStyle);

                Cell priceCell = row.createCell(4);
                priceCell.setCellValue(product.getPrice());
                priceCell.setCellStyle(currencyStyle);

                Cell descCell = row.createCell(5);
                descCell.setCellValue(product.getDescription());
                descCell.setCellStyle(dataStyle);

                Cell quantityCell = row.createCell(6);
                quantityCell.setCellValue(product.getQuantity());
                quantityCell.setCellStyle(dataStyle);

                // Cộng dồn số lượng hàng tồn
                totalQuantity += product.getQuantity();
            }

            // Thêm hàng tổng số lượng hàng tồn
            Row totalRow = sheet.createRow(rowNum);

            // Thêm border cho toàn bộ dòng tổng
            for (int i = 0; i < columns.length; i++) {
                Cell cell = totalRow.createCell(i);
                cell.setCellStyle(headerStyle);
            }

            Cell totalLabelCell = totalRow.getCell(5);
            totalLabelCell.setCellValue("Tổng số lượng hàng tồn:");

            Cell totalValueCell = totalRow.getCell(6);
            totalValueCell.setCellValue(totalQuantity);

            // Tự động căn chỉnh độ rộng cột
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
