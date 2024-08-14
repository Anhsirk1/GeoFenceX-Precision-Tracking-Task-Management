import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Company_login", urlPatterns = {"/Company_login"})
public class Company_login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String managerEmail = request.getParameter("memail");
        String managerPassword = request.getParameter("mpwd");
        String employeeEmail = request.getParameter("eemail");
        String employeePassword = request.getParameter("epwd");

        boolean isAuthenticated = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/22r21a05d1", "root", "root");
            String sql = "SELECT * FROM cregistration WHERE (memail = ? AND mpwd = ?) OR (eemail = ? AND epwd = ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, managerEmail);
            pst.setString(2, managerPassword);
            pst.setString(3, employeeEmail);
            pst.setString(4, employeePassword);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                isAuthenticated = true;
            }

            if (isAuthenticated) {
                response.sendRedirect("Dashboard.html");
            } 
            else {
                response.sendRedirect("Company_login.html?error=Invalid+credentials");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Company_login Servlet";
    }
}
