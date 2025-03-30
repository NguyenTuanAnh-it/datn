/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Product;
import com.google.gson.Gson;
import dao.ProductDao;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ProductsControl", urlPatterns = {"/product"})
@MultipartConfig
public class ProductsControl extends HttpServlet {

    private ProductDao productDao = new ProductDao();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductsControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductsControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if (action == null) {
                getTotalProducts(request, response);
                return;
            }
            if (action.equalsIgnoreCase("edit")) {
                getProductById(request, response);
                return;
            }
            if (action.equalsIgnoreCase("delete")) {
                deleteProduct(request, response);
                return;
            }
        } catch (Exception e) {
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                addProduct(request, response);
                break;
            case "update":
                editProduct(request, response);
                break;
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try {

                // Lấy dữ liệu từ form
                String name = request.getParameter("name");
                int categoryId = Integer.parseInt(request.getParameter("category"));
                double price = Double.parseDouble(request.getParameter("price"));
                String description = request.getParameter("title");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String detail = request.getParameter("detail");
                if (productDao.isProductNameExists(name)) {
                    request.setAttribute("message", "Sản phẩm đã tồn tại, vui lòng chọn tên khác.");
                    request.setAttribute("status", "error");
                    getTotalProducts(request, response);
                    request.getRequestDispatcher("/admin/products.jsp").forward(request, response);
                    return;
                }
                // Xử lý upload ảnh
                Part filePart = request.getPart("image");
                String fileName = null;

                if (filePart != null && filePart.getSize() > 0) {
                    fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                    // Đường dẫn tuyệt đối đến thư mục lưu ảnh
                    String uploadDirectory = "D:/Learned/Year_4/DATN/Path/QL_BanMyPham/QL_BanMyPham/web/images/home";
                    Path uploadPath = Paths.get(uploadDirectory);

                    System.out.println("Thư mục upload: " + uploadPath);

                    // Tạo thư mục nếu chưa tồn tại
                    if (!Files.exists(uploadPath)) {
                        try {
                            Files.createDirectories(uploadPath);
                            System.out.println("Đã tạo thư mục: " + uploadPath);
                        } catch (IOException e) {
                            System.out.println("Không thể tạo thư mục upload: " + e.getMessage());
                            e.printStackTrace();
                            return;
                        }
                    }

                    // Kiểm tra nếu file bị trùng thì đổi tên file
                    Path filePath = uploadPath.resolve(fileName);
                    if (Files.exists(filePath)) {
                        String newFileName = System.currentTimeMillis() + "_" + fileName;
                        fileName = newFileName; // Đổi tên file để tránh trùng
                        filePath = uploadPath.resolve(fileName);
                    }

                    // Lưu file vào thư mục - sử dụng Files.copy thay vì filePart.write()
                    try (InputStream inputStream = filePart.getInputStream()) {
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Ảnh đã lưu vào: " + filePath.toString());
                    } catch (IOException e) {
                        System.out.println("Lỗi khi lưu file: " + e.getMessage());
                        e.printStackTrace();
                        return;
                    }
                } else {
                    System.out.println("Không có file nào được tải lên.");
                }

                // Tạo đối tượng Product
                Product product = new Product();
                product.setName(name);
                product.setCategoryId(categoryId);
                product.setPrice(price);
                product.setDescription(description);
                product.setQuantity(quantity);
                product.setDetail(detail);
                product.setImage(fileName); // Chỉ lưu tên file vào DB

                // Gọi DAO để lưu vào DB
                boolean success = productDao.addProduct(product);
                if (success) {
                    request.setAttribute("message", "Thêm sản phẩm thành công");
                    request.setAttribute("status", "success");
                } else {
                    request.setAttribute("message", "Thêm sản phẩm thất bại");
                    request.setAttribute("status", "error");
                }
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
            getTotalProducts(request, response);
            request.getRequestDispatcher("/admin/products.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductsControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getTotalProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String indexPage = request.getParameter("index");
        String keyword = request.getParameter("search");
        if (keyword == null) {
            keyword = "";
        }
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);
        int count = productDao.getTotalProducts(keyword);
        int endPage = count / 5;
        if (count % 5 != 0) {
            endPage++;
        }
        List<Product> list = productDao.pagingProducts(index, keyword);
        request.setAttribute("listP", list);
        request.setAttribute("endP", endPage);
        request.setAttribute("searchKeyword", keyword);
        request.setAttribute("currentPage", index);
        request.getRequestDispatcher("/admin/products.jsp").forward(request, response);
    }

    private void getProductById(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = productDao.getProductById(id);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (product != null) {
                String productJson = new Gson().toJson(product);
                response.getWriter().write(productJson);
            } else {
                System.out.println("⚠️ Không tìm thấy sản phẩm với ID: " + id);
                response.getWriter().write("{}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int categoryId = Integer.parseInt(request.getParameter("category"));
            double price = Double.parseDouble(request.getParameter("price"));
            String description = request.getParameter("title");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String detail = request.getParameter("detail");

            // Lấy sản phẩm hiện tại từ DB
            Product existingProduct = productDao.getProductById(id);
            if (existingProduct == null) {
                request.setAttribute("message", "Sản phẩm không tồn tại");
                request.setAttribute("status", "error");
                request.getRequestDispatcher("admin/products.jsp").forward(request, response);
                return;
            }

            // Xử lý upload ảnh
            Part filePart = request.getPart("image");
            String fileName = existingProduct.getImage(); // Giữ ảnh cũ nếu không có ảnh mới

            if (filePart != null && filePart.getSize() > 0) {
                fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                // Đường dẫn thư mục upload
                String uploadDirectory = "D:/Learned/Year_4/DATN/Path/QL_BanMyPham/QL_BanMyPham/web/images/home";
                Path uploadPath = Paths.get(uploadDirectory);

                // Tạo thư mục nếu chưa tồn tại
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Kiểm tra nếu file bị trùng thì đổi tên
                Path filePath = uploadPath.resolve(fileName);
                if (Files.exists(filePath)) {
                    String newFileName = System.currentTimeMillis() + "_" + fileName;
                    fileName = newFileName;
                    filePath = uploadPath.resolve(fileName);
                }

                // Lưu file vào thư mục
                try (InputStream inputStream = filePart.getInputStream()) {
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            // Cập nhật sản phẩm
            existingProduct.setName(name);
            existingProduct.setCategoryId(categoryId);
            existingProduct.setPrice(price);
            existingProduct.setDescription(description);
            existingProduct.setQuantity(quantity);
            existingProduct.setDetail(detail);
            existingProduct.setImage(fileName); // Cập nhật ảnh mới hoặc giữ nguyên ảnh cũ

            // Gọi DAO để cập nhật sản phẩm
            boolean success = productDao.updateProduct(existingProduct);
            if (success) {
                request.setAttribute("message", "Cập nhật sản phẩm thành công");
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("message", "Cập nhật sản phẩm thất bại");
                request.setAttribute("status", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi: " + e.getMessage());
            request.setAttribute("status", "error");
        }
        try {
            getTotalProducts(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductsControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("admin/products.jsp").forward(request, response);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            // Xóa ảnh của sản phẩm (nếu có)
            Product product = productDao.getProductById(id);
            if (product != null && product.getImage() != null) {
                String uploadDirectory = "D:/Learned/Year_4/DATN/Path/QL_BanMyPham/QL_BanMyPham/web/images/home";
                Path imagePath = Paths.get(uploadDirectory, product.getImage());

                try {
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    System.out.println("Lỗi khi xóa ảnh: " + e.getMessage());
                }
            }

            boolean success = productDao.deleteProduct(id);

            if (success) {
                request.setAttribute("message", "Xóa sản phẩm thành công");
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("message", "Xóa sản phẩm thất bại");
                request.setAttribute("status", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi: " + e.getMessage());
            request.setAttribute("status", "error");
        }
        try {
            getTotalProducts(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductsControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("admin/products.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
