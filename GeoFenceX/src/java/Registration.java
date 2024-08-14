import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/Registration"})
public class Registration extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Registration</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Registration at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");

        if ("company".equals(type)) {
            registerCompany(request, response);
        } else if ("parental".equals(type)) {
            registerParental(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void registerParental(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement pstmt = null;
        String k = "", l = "", m = "", n = "", o = "", p = "", q = "", r = "";
        k = request.getParameter("pname");
        l = request.getParameter("cname");
        m = request.getParameter("pemail");
        n = request.getParameter("cemail");
        o = request.getParameter("ppwd");
        p = request.getParameter("cpwd");
        q = request.getParameter("pphno");
        r = request.getParameter("cphno");
    
        long pphno = Long.parseLong(q);
        long cphno = Long.parseLong(r);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/22r21a05d1", "root","root");
            String sql1 = "INSERT INTO pregistration(pname, cname, pemail, cemail, ppwd, cpwd, pphno, cphno) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, k);
            pstmt.setString(2, l);
            pstmt.setString(3, m);
            pstmt.setString(4, n);
            pstmt.setString(5, o);
            pstmt.setString(6, p);
            pstmt.setString(7, q);
            pstmt.setString(8, r);
            pstmt.executeUpdate();
            response.sendRedirect("Registration.html");
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<font color=red size=24>Registration failed!!<br>");
            out.println("<a href=Registration.html>Go back to Registration</a>");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (SQLException ex) {
                out.println("Error in closing resources: " + ex.getMessage());
            }
        }
    }

    private void registerCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement pstmt = null;
        String s = "", t = "", u = "", v = "", w = "", x = "", y = "", z = "";
        s = request.getParameter("mname");
        t = request.getParameter("ename");
        u = request.getParameter("memail");
        v = request.getParameter("eemail");
        w = request.getParameter("mpwd");
        x = request.getParameter("epwd");
        y = request.getParameter("mphno");
        z = request.getParameter("ephno");
    
        long pphno = Long.parseLong(y);
        long cphno = Long.parseLong(z);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/22r21a05d1", "root","root");
            String sq2 = "INSERT INTO cregistration(mname, ename, memail, eemail, mpwd, epwd, mphno, ephno) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sq2);
            pstmt.setString(1, s);
            pstmt.setString(2, t);
            pstmt.setString(3, u);
            pstmt.setString(4, v);
            pstmt.setString(5, w);
            pstmt.setString(6, x);
            pstmt.setString(7, y);
            pstmt.setString(8, z);
            pstmt.executeUpdate();
            response.sendRedirect("Registration.html");
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<font color=red size=24>Registration failed!!<br>");
            out.println("<a href=Registration.html>Go back to Registration</a>");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (SQLException ex) {
                out.println("Error in closing resources: " + ex.getMessage());
            }
        }
    }// Similarly, handle company registration here using the "cregistration" table
        // You can follow a similar structure as registerParental() method
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
