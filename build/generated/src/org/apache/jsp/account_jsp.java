package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class account_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("        <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/font-awesome.min.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/prettyPhoto.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/price-range.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/animate.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/main.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/responsive.css\" rel=\"stylesheet\">\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        ");

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
        
      out.write("\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
      out.write("\n");
      out.write("            <section id=\"form\"><!--form-->\n");
      out.write("                <div class=\"container\">\n");
      out.write("                    <div class=\"row\">\n");
      out.write("                        <div class=\"col-sm-4 col-sm-offset-1\">\n");
      out.write("                            <div class=\"login-form\"><!--login form-->\n");
      out.write("                                <h2>Đăng nhập hệ thống</h2>\n");
      out.write("                                <form action=\"login\" method=\"post\">\n");
      out.write("                                    <p style=\"color: red\">");
      out.print(err);
      out.write("</p>\n");
      out.write("                                    <input type=\"email\" placeholder=\"Email của bạn\" name=\"email\"/>\n");
      out.write("                                    <input type=\"password\" placeholder=\"Nhập mật khẩu\" name=\"mat_khau\" />\n");
      out.write("                                    <span>\n");
      out.write("                                        <input type=\"checkbox\" class=\"checkbox\"> \n");
      out.write("                                        Nhớ đăng nhập\n");
      out.write("                                    </span>\n");
      out.write("                                    <button type=\"submit\" class=\"btn btn-default\">Đăng Nhập</button>\n");
      out.write("                                </form>\n");
      out.write("                            </div><!--/login form-->\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"col-sm-1\">\n");
      out.write("                            \n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"col-sm-4\">\n");
      out.write("                            <div class=\"signup-form\"><!--sign up form-->\n");
      out.write("                                <h2>Đăng ký</h2>\n");
      out.write("                                <form action=\"register\" method=\"POST\">\n");
      out.write("                                    <p style=\"color: red\">");
      out.print(ten_dang_nhap_err);
      out.write("</p>\n");
      out.write("                                <input type=\"text\" placeholder=\"Nhập tên tài khoản\" name=\"ten_dang_nhap\" value=\"");
      out.print(ten_dang_nhap);
      out.write("\"/>\n");
      out.write("                                    <p style=\"color: red\">");
      out.print(email_err);
      out.write("</p>\n");
      out.write("                                    <input type=\"email\" placeholder=\"Email của bạn\" name=\"email\" value=\"");
      out.print(email);
      out.write("\"/>\n");
      out.write("                                    <p style=\"color: red\">");
      out.print(mat_khau_err);
      out.write("</p>\n");
      out.write("                                    <input type=\"password\" placeholder=\"Mật khẩu\" name=\"mat_khau\" value=\"");
      out.print(mat_khau);
      out.write("\"/>\n");
      out.write("                                    <button type=\"submit\" class=\"btn btn-default\">Đăng ký</button>\n");
      out.write("                                </form>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </section><!--/form-->\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "footer.jsp", out, false);
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
