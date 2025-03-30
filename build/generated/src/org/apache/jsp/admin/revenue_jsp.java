package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class revenue_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "includes/header.jsp", out, false);
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "includes/navbar.jsp", out, false);
      out.write("\n");
      out.write("\n");
      out.write("<!-- Thanh tiêu đề -->\n");
      out.write("<nav class=\"navbar navbar-light bg-light\">\n");
      out.write("    <a class=\"navbar-brand\">BÁO CÁO DOANH THU</a>\n");
      out.write("    <a href=\"#\" class=\"d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm\">\n");
      out.write("        <i class=\"fas fa-download fa-sm text-white-50\"></i> Xuất báo cáo\n");
      out.write("    </a>\n");
      out.write("</nav>\n");
      out.write("\n");
      out.write("<!-- Khu vực hiển thị các số liệu thống kê -->\n");
      out.write("<div class=\"container mt-4\">\n");
      out.write("    <div class=\"row\">\n");
      out.write("        <!-- Doanh thu trong ngày -->\n");
      out.write("        <div class=\"col-xl-3 col-md-6 mb-4\">\n");
      out.write("            <div class=\"card border-left-success shadow h-100 py-2\">\n");
      out.write("                <div class=\"card-body\">\n");
      out.write("                    <div class=\"row no-gutters align-items-center\">\n");
      out.write("                        <div class=\"col mr-2\">\n");
      out.write("                            <div class=\"text-xs font-weight-bold text-success text-uppercase mb-1\">\n");
      out.write("                                Doanh thu trong ngày\n");
      out.write("                            </div>\n");
      out.write("                            <fmt:formatNumber value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${getTodayRevenue}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\" type=\"number\" pattern=\"#,###\" var=\"formattedRevenue\"/>\n");
      out.write("                            <div class=\"h5 mb-0 font-weight-bold text-gray-800\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${formattedRevenue}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write(" VNĐ</div>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"col-auto\">\n");
      out.write("                            <i class=\"fas fa-dollar-sign fa-2x text-gray-300\"></i>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <!-- Doanh thu trong tuần -->\n");
      out.write("        <div class=\"col-xl-3 col-md-6 mb-4\">\n");
      out.write("            <div class=\"card border-left-success shadow h-100 py-2\">\n");
      out.write("                <div class=\"card-body\">\n");
      out.write("                    <div class=\"row no-gutters align-items-center\">\n");
      out.write("                        <div class=\"col mr-2\">\n");
      out.write("                            <div class=\"text-xs font-weight-bold text-success text-uppercase mb-1\">\n");
      out.write("                                Doanh thu trong tuần\n");
      out.write("                            </div>\n");
      out.write("                            <fmt:formatNumber value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${weeklyRevenue}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\" type=\"number\" pattern=\"#,###\" var=\"formattedRevenue\"/>\n");
      out.write("                            <div class=\"h5 mb-0 font-weight-bold text-gray-800\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${formattedRevenue}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write(" VNĐ</div>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"col-auto\">\n");
      out.write("                            <i class=\"fas fa-dollar-sign fa-2x text-gray-300\"></i>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <!-- Doanh thu trong tháng -->\n");
      out.write("        <div class=\"col-xl-3 col-md-6 mb-4\">\n");
      out.write("            <div class=\"card border-left-success shadow h-100 py-2\">\n");
      out.write("                <div class=\"card-body\">\n");
      out.write("                    <div class=\"row no-gutters align-items-center\">\n");
      out.write("                        <div class=\"col mr-2\">\n");
      out.write("                            <div class=\"text-xs font-weight-bold text-success text-uppercase mb-1\">\n");
      out.write("                                Doanh thu trong tháng\n");
      out.write("                            </div>\n");
      out.write("                            <fmt:formatNumber value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${monthlyRevenue}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\" type=\"number\" pattern=\"#,###\" var=\"formattedRevenue\"/>\n");
      out.write("                            <div class=\"h5 mb-0 font-weight-bold text-gray-800\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${formattedRevenue}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write(" VNĐ</div>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"col-auto\">\n");
      out.write("                            <i class=\"fas fa-dollar-sign fa-2x text-gray-300\"></i>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("                        \n");
      out.write("                     <div class=\"col-xl-3 col-md-6 mb-4\">\n");
      out.write("            <div class=\"card border-left-success shadow h-100 py-2\">\n");
      out.write("                <div class=\"card-body\">\n");
      out.write("                    <div class=\"row no-gutters align-items-center\">\n");
      out.write("                        <div class=\"col mr-2\">\n");
      out.write("                            <div class=\"text-xs font-weight-bold text-success text-uppercase mb-1\">\n");
      out.write("                                Doanh thu năm nay\n");
      out.write("                            </div>\n");
      out.write("                            <fmt:formatNumber value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${monthlyRevenue}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\" type=\"number\" pattern=\"#,###\" var=\"formattedRevenue\"/>\n");
      out.write("                            <div class=\"h5 mb-0 font-weight-bold text-gray-800\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${formattedRevenue}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write(" VNĐ</div>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"col-auto\">\n");
      out.write("                            <i class=\"fas fa-dollar-sign fa-2x text-gray-300\"></i>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>   \n");
      out.write("\n");
      out.write("    </div> <!-- Kết thúc row -->\n");
      out.write("</div> <!-- Kết thúc container -->\n");
      out.write("\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "includes/scripts.jsp", out, false);
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "includes/footer.jsp", out, false);
      out.write('\n');
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
